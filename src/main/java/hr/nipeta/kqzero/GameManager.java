package hr.nipeta.kqzero;

import hr.nipeta.kqzero.collision.CollisionManager;
import hr.nipeta.kqzero.drawers.*;
import hr.nipeta.kqzero.gameobjects.entities.Player;
import hr.nipeta.kqzero.gameobjects.entities.enemies.BlobLight;
import hr.nipeta.kqzero.gameobjects.entities.enemies.Enemy;
import hr.nipeta.kqzero.gameobjects.entities.enemies.Scarecrow;
import hr.nipeta.kqzero.gameobjects.entities.neutral.Bird;
import hr.nipeta.kqzero.gameobjects.entities.neutral.Fish;
import hr.nipeta.kqzero.gameobjects.entities.neutral.Neutral;
import hr.nipeta.kqzero.gameobjects.items.*;
import hr.nipeta.kqzero.world.World;
import hr.nipeta.kqzero.world.WorldTile;
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
    public MessageDrawer messageDrawer;
    public PauseOverlayDrawer pauseDrawer;
    public InventoryOverlayDrawer inventoryOverlayDrawer;

    private GameState gameState;

    public Scene createScene() {

        Canvas canvas = new Canvas(COLS_PER_SCREEN * TILE_SIZE, ROWS_PER_SCREEN * TILE_SIZE);
        gc = canvas.getGraphicsContext2D();

        keyHandler = new KeyHandler(this);
        spriteManager = new SpriteManager(this);

        geekStatsOverlayDrawer = new GeekStatsOverlayDrawer(this);
        helpOverlayDrawer = new HelpOverlayDrawer(this);
        messageDrawer = new MessageDrawer(this);
        pauseDrawer = new PauseOverlayDrawer(this);
        inventoryOverlayDrawer = new InventoryOverlayDrawer(this);

        world = new World(this, "/maps/map1.map");
        player = new Player(this, new WorldTile(21, 21));

        itemsOnMap = new ArrayList<>();
        itemsOnMap.add(new Door(this, new WorldTile(16d, 2d)));
        itemsOnMap.add(new Door(this, new WorldTile(3d, 6d)));
        itemsOnMap.add(new Door(this, new WorldTile(4d, 9d)));

        Random random = new Random();
        for (int i = 0; i < 700; i++) {
            WorldTile worldTile = new WorldTile(random.nextInt(world.COLS_TOTAL), random.nextInt(world.ROWS_TOTAL));
            Coin coin = new Coin(this, worldTile);
            itemsOnMap.add(coin);
        }

        for (int i = 0; i < 300; i++) {
            WorldTile worldTile = new WorldTile(random.nextInt(world.COLS_TOTAL), random.nextInt(world.ROWS_TOTAL));
            Key key = new Key(this, worldTile);
            itemsOnMap.add(key);
        }

        for (int i = 0; i < 78; i++) {
            int worldX = random.nextInt(world.COLS_TOTAL);
            int worldY = random.nextInt(world.ROWS_TOTAL);
            WorldTile worldTile = new WorldTile(
                    worldX + new Random().nextDouble(.3),
                    worldY + new Random().nextDouble(.3));
            Tree tree = new Tree(this, worldTile);
            if (tree.isSpawnableOn(world.tiles[worldY][worldX])) {
                itemsOnMap.add(tree);
            } else {
                i--;
            }
        }

        for (int i = 0; i < 55; i++) {
            int worldX = random.nextInt(world.COLS_TOTAL);
            int worldY = random.nextInt(world.ROWS_TOTAL);
            WorldTile worldTile = new WorldTile(
                    worldX + new Random().nextDouble(.3),
                    worldY + new Random().nextDouble(.3)
            );
            Pine tree = new Pine(this, worldTile);
            if (tree.isSpawnableOn(world.tiles[worldY][worldX])) {
                itemsOnMap.add(tree);
            } else {
                i--;
            }
        }

        enemies = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            int worldX = random.nextInt(world.COLS_TOTAL);
            int worldY = random.nextInt(world.ROWS_TOTAL);
            BlobLight blob = new BlobLight(this, new WorldTile(worldX, worldY));
            if (blob.collidesWith(world.tiles[worldY][worldX])) {
                i--;
                continue;
            }
            enemies.add(blob);
        }
        for (int i = 0; i < 22; i++) {
            int worldX = random.nextInt(world.COLS_TOTAL);
            int worldY = random.nextInt(world.ROWS_TOTAL);
            Scarecrow sc = new Scarecrow(this, new WorldTile(worldX, worldY));
            if (sc.collidesWith(world.tiles[worldY][worldX])) {
                i--;
                continue;
            }
            enemies.add(sc);
        }

        neutrals = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            int worldX = random.nextInt(world.COLS_TOTAL);
            int worldY = random.nextInt(world.ROWS_TOTAL);
            Fish f = new Fish(this, new WorldTile(worldX, worldY));
            if (f.collidesWith(world.tiles[worldY][worldX])) {
                i--;
                continue;
            }
            neutrals.add(f);
        }

        for (int i = 0; i < 21; i++) {
            int worldX = random.nextInt(world.COLS_TOTAL);
            int worldY = random.nextInt(world.ROWS_TOTAL);
            Bird b = new Bird(this, new WorldTile(worldX, worldY));
            if (b.collidesWith(world.tiles[worldY][worldX])) {
                i--;
                continue;
            }
            neutrals.add(b);
        }

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
            log.debug("Total   = {} ms", (System.nanoTime() - nano) / 1e6);
        }

    }

    public void update(double deltaTimeInSeconds) {

        long nano = System.nanoTime();

        player.update(deltaTimeInSeconds);
        enemies.forEach(e -> e.update(deltaTimeInSeconds));
        neutrals.forEach(e -> e.update(deltaTimeInSeconds));

        if (DebugConfig.logTimeToUpdateAndDraw) {
            log.debug("Update  = {} ms", (System.nanoTime() - nano) / 1e6);
        }

    }

    public void draw() {

        long nano = System.nanoTime();

        world.drawCenteredAt(player.tile);
        itemsOnMap.forEach(Item::draw);
        player.draw();
        enemies.forEach(Enemy::draw);
        neutrals.forEach(Neutral::draw);

        geekStatsOverlayDrawer.draw();
        helpOverlayDrawer.draw();
        messageDrawer.draw();
        pauseDrawer.draw();
        inventoryOverlayDrawer.draw();

        if (DebugConfig.logTimeToUpdateAndDraw) {
            log.debug("Drawing = {} ms", (System.nanoTime() - nano) / 1e6);
        }

    }

}
