package hr.nipeta.kqzero.gameobjects.entities;

import hr.nipeta.kqzero.DebugConfig;
import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.SpriteManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import hr.nipeta.kqzero.movement.Movement;
import hr.nipeta.kqzero.world.WorldTile;
import hr.nipeta.kqzero.world.tiles.Tile;
import javafx.scene.paint.Color;

import java.util.Set;

public abstract class NonPlayer extends Entity {

    protected NonPlayer(GameManager gm, WorldTile worldTile, CollisionTolerance collisionTolerance, Set<Tile> collidesWith, Movement movement) {
        super(gm, worldTile, collisionTolerance, collidesWith, movement);
    }

    @Override
    public void update(double deltaTimeInSeconds) {
        updateMovementAndCollision(deltaTimeInSeconds);
        updateSpriteCount(deltaTimeInSeconds);
    }

    private void updateMovementAndCollision(double deltaTimeInSeconds) {

        double tileDistanceTraveled = movement.getSpeed().getCurrent() * deltaTimeInSeconds;

        boolean hasCollidedWithTile = gm.collisionManager.check(gm.world, this, tileDistanceTraveled);
        if (hasCollidedWithTile) {
            movement.getCollisionStrategy().apply(movement);
            return;
        }

        switch (movement.getDirection()) {
            case UP -> this.tile.y -= tileDistanceTraveled;
            case DOWN -> this.tile.y += tileDistanceTraveled;
            case LEFT -> this.tile.x -= tileDistanceTraveled;
            case RIGHT -> this.tile.x += tileDistanceTraveled;
            default -> throw new UnsupportedOperationException();
        }

        movement.incrementTotalAndCurrentDirectionDistance(tileDistanceTraveled);

        movement.getTraversalStrategy().apply(movement);

    }

    private void updateSpriteCount(double deltaTimeInSeconds) {

        // Track when was last sprite updated
        spriteCounter.addToLastChangeInSeconds(deltaTimeInSeconds);

        // Set sprite index if needed, so draw() will switch
        spriteCounter.incrementCounterIfSpriteChangeNeeded();

    }

    @Override
    public final void draw() {

        double relativeToPlayerX = gm.player.tile.x - this.tile.x;
        if (Math.abs(relativeToPlayerX) > (double) gm.world.COLS_TOTAL / 2) {
            return;
        }

        double relativeToPlayerY = gm.player.tile.y - this.tile.y;
        if (Math.abs(relativeToPlayerY) > (double) gm.world.ROWS_TOTAL / 2) {
            return;
        }

        // TODO possible optimization? We have to call spriteManager only when sprite will change, not every time...
        SpriteManager.SpriteSheetResult spriteSheetInfo = gm.spriteManager.calculateNonPlayerEntitySpriteSheet(this);

        gm.gc.drawImage(
                spriteSheetInfo.getSpriteSheet(),
                spriteSheetInfo.getSource().getMinX(),
                spriteSheetInfo.getSource().getMinY(),
                spriteSheetInfo.getSource().getWidth(),
                spriteSheetInfo.getSource().getHeight(),
                gm.CENTRAL_TILE_TOP_LEFT_X - relativeToPlayerX * gm.TILE_SIZE,
                gm.CENTRAL_TILE_TOP_LEFT_Y - relativeToPlayerY * gm.TILE_SIZE,
                gm.TILE_SIZE,
                gm.TILE_SIZE);

        if (DebugConfig.drawEntityCollisionBorder) {
            drawCollisionBorder(relativeToPlayerX, relativeToPlayerY);
        }

    }

    private void drawCollisionBorder(double relativeToPlayerX, double relativeToPlayerY) {

        double topLeftX = gm.CENTRAL_TILE_TOP_LEFT_X - relativeToPlayerX * gm.TILE_SIZE + collisionTolerance.left * gm.TILE_SIZE;
        double topLeftY = gm.CENTRAL_TILE_TOP_LEFT_Y - relativeToPlayerY * gm.TILE_SIZE + collisionTolerance.top * gm.TILE_SIZE;
        double width = collisionTolerance.width * gm.TILE_SIZE;
        double height = collisionTolerance.height * gm.TILE_SIZE;

        gm.gc.setStroke(Color.RED);
        gm.gc.setLineWidth(2);
        gm.gc.strokeRect(topLeftX, topLeftY, width, height);

    }

}
