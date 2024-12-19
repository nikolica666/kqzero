package hr.nipeta.game2d;

import hr.nipeta.game2d.collision.CollisionManager;
import hr.nipeta.game2d.drawers.GeekStatsOverlayDrawer;
import hr.nipeta.game2d.drawers.HelpOverlayDrawer;
import hr.nipeta.game2d.entities.*;
import hr.nipeta.game2d.entities.enemies.BlobLight;
import hr.nipeta.game2d.entities.enemies.Enemy;
import hr.nipeta.game2d.entities.enemies.Scarecrow;
import hr.nipeta.game2d.entities.neutral.Bird;
import hr.nipeta.game2d.entities.neutral.Fish;
import hr.nipeta.game2d.entities.neutral.Neutral;
import hr.nipeta.game2d.items.Coin;
import hr.nipeta.game2d.items.Door;
import hr.nipeta.game2d.items.Item;
import hr.nipeta.game2d.items.Key;
import hr.nipeta.game2d.world.World;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class GameManager {

    public final int TILE_SIZE = 48;
    public final int COLS_PER_SCREEN = 39;
    public final int ROWS_PER_SCREEN = 25;

    public final int CENTRAL_TILE_TOP_LEFT_X = (int)(COLS_PER_SCREEN / 2) * TILE_SIZE;
    public final int CENTRAL_TILE_TOP_LEFT_Y = (int)(ROWS_PER_SCREEN / 2) * TILE_SIZE;

    public GraphicsContext gc;

    public World world;
    public Player player;

    public final CollisionManager collisionManager = new CollisionManager();
    public SpriteManager spriteManager;

    public List<Item> itemsOnMap;
    public List<Enemy> enemies;
    public List<Neutral> neutrals;

    public final GameLoop gameLoop = GameLoop.withHandler(this::updateAndDraw);
    public KeyHandler keyHandler;

    public GeekStatsOverlayDrawer geekStatsOverlayDrawer;
    public HelpOverlayDrawer helpOverlayDrawer;

    public Scene createScene() {

        Canvas canvas = new Canvas(COLS_PER_SCREEN * TILE_SIZE, ROWS_PER_SCREEN * TILE_SIZE);
        gc = canvas.getGraphicsContext2D();

        keyHandler = new KeyHandler(this);

        world = new World(this, "/maps/map1.map");
        player = new Player(this, 21, 21);

        spriteManager = new SpriteManager(this);

        itemsOnMap = new ArrayList<>();
        itemsOnMap.add(new Door(this, 16d,2d));
        itemsOnMap.add(new Door(this, 3d,6d));
        itemsOnMap.add(new Door(this, 4d,9d));

        Random random = new Random();
        for (int i = 0; i < 700; i++) {
            Coin coin = new Coin(this, (double)random.nextInt(world.COLS_TOTAL), (double)random.nextInt(world.ROWS_TOTAL));
            itemsOnMap.add(coin);
        }

        for (int i = 0; i < 300; i++) {
            Key key = new Key(this, (double)random.nextInt(world.COLS_TOTAL), (double)random.nextInt(world.ROWS_TOTAL));
            itemsOnMap.add(key);
        }

        enemies = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            int worldX = random.nextInt(world.COLS_TOTAL);
            int worldY = random.nextInt(world.ROWS_TOTAL);
            BlobLight blob = new BlobLight(this, worldX, worldY);
            if (blob.collidesWith(world.tiles[worldY][worldX])) {
                i--;
                continue;
            }
            enemies.add(blob);
        }
        for (int i = 0; i < 12; i++) {
            int worldX = random.nextInt(world.COLS_TOTAL);
            int worldY = random.nextInt(world.ROWS_TOTAL);
            Scarecrow sc = new Scarecrow(this, worldX, worldY);
            if (sc.collidesWith(world.tiles[worldY][worldX])) {
                i--;
                continue;
            }
            enemies.add(sc);
        }

        neutrals = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            int worldX = random.nextInt(world.COLS_TOTAL);
            int worldY = random.nextInt(world.ROWS_TOTAL);
            Fish f = new Fish(this, worldX, worldY);
            if (f.collidesWith(world.tiles[worldY][worldX])) {
                i--;
                continue;
            }
            neutrals.add(f);
        }

        for (int i = 0; i < 11; i++) {
            int worldX = random.nextInt(world.COLS_TOTAL);
            int worldY = random.nextInt(world.ROWS_TOTAL);
            Bird b = new Bird(this, worldX, worldY);
            if (b.collidesWith(world.tiles[worldY][worldX])) {
                i--;
                continue;
            }
            neutrals.add(b);
        }

        geekStatsOverlayDrawer = new GeekStatsOverlayDrawer(this);
        helpOverlayDrawer = new HelpOverlayDrawer(this);

        updateAndDraw(0);

        gameLoop.start();

        Scene scene = new Scene(new Pane(canvas));
        scene.setOnKeyPressed(keyHandler);
        scene.setOnKeyReleased(keyHandler);

        return scene;

    }

    public void updateAndDraw(double deltaTimeInSeconds) {

        long nano = System.nanoTime();

        update(deltaTimeInSeconds);
        draw();

        if (DebugConfig.logTimeToUpdateAndDraw) {
            log.debug("Draw + Update = {} microseconds", (System.nanoTime() - nano) / 1e3);
        }

    }

    public void update(double deltaTimeInSeconds) {
        player.update(deltaTimeInSeconds);
        enemies.forEach(e -> e.update(deltaTimeInSeconds));
        neutrals.forEach(e -> e.update(deltaTimeInSeconds));
    }

    public void draw() {

        world.drawCenteredAt(player.worldTileX, player.worldTileY);
        itemsOnMap.forEach(Item::draw);
        player.draw();
        enemies.forEach(Enemy::draw);
        neutrals.forEach(Neutral::draw);

        geekStatsOverlayDrawer.draw();
        helpOverlayDrawer.draw();

    }

}
