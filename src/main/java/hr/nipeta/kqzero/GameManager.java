package hr.nipeta.kqzero;

import hr.nipeta.kqzero.collision.CollisionManager;
import hr.nipeta.kqzero.drawers.GeekStatsOverlayDrawer;
import hr.nipeta.kqzero.drawers.HelpOverlayDrawer;
import hr.nipeta.kqzero.drawers.MessageDrawer;
import hr.nipeta.kqzero.entities.*;
import hr.nipeta.kqzero.entities.enemies.BlobLight;
import hr.nipeta.kqzero.entities.enemies.Enemy;
import hr.nipeta.kqzero.entities.enemies.Scarecrow;
import hr.nipeta.kqzero.entities.neutral.Bird;
import hr.nipeta.kqzero.entities.neutral.Fish;
import hr.nipeta.kqzero.entities.neutral.Neutral;
import hr.nipeta.kqzero.items.*;
import hr.nipeta.kqzero.world.World;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
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

    public Scene createScene() {

        Canvas canvas = new Canvas(COLS_PER_SCREEN * TILE_SIZE, ROWS_PER_SCREEN * TILE_SIZE);
        gc = canvas.getGraphicsContext2D();

        keyHandler = new KeyHandler(this);
        spriteManager = new SpriteManager(this);

        geekStatsOverlayDrawer = new GeekStatsOverlayDrawer(this);
        helpOverlayDrawer = new HelpOverlayDrawer(this);
        messageDrawer = new MessageDrawer(this);

        world = new World(this, "/maps/map1.map");
        player = new Player(this, 21, 21);

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

        List<Tree> trees = Arrays.asList(
                new Tree(this, 2.3, 3.2),
                new Tree(this, 2.5, 3.3),
                new Tree(this, 2.3, 3.1),
                new Tree(this, 2.4, 3.2),
                new Tree(this, 2.7, 3.44)

        );
        itemsOnMap.addAll(trees);

        for (double i = 0; i < 30; i = i + (0.4 + (double) new Random().nextInt(5) / 10)) {
            for (double j = 0; j < 30; j = j + (0.4 + (double) new Random().nextInt(5) / 10)) {
                Tree tree = new Tree(this, 25 + i - 0.5 + new Random().nextDouble(), 15 + j - 0.5 + new Random().nextDouble());
                itemsOnMap.add(tree);
            }

        }

        for (double i = 0; i < 10; i = i + (0.6 + (double) new Random().nextInt(5) / 10)) {
            for (double j = 0; j < 10; j = j + (0.6 + (double) new Random().nextInt(5) / 10)) {
                Pine tree = new Pine(this, 2 + i - 0.5 + new Random().nextDouble(), 20 + j - 0.5 + new Random().nextDouble());
                itemsOnMap.add(tree);
            }

        }

        for (int i = 0; i < 300; i++) {
            Tree tree = new Tree(this, (double)random.nextInt(world.COLS_TOTAL), (double)random.nextInt(world.ROWS_TOTAL));
            itemsOnMap.add(tree);
        }

        enemies = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            int worldX = random.nextInt(world.COLS_TOTAL);
            int worldY = random.nextInt(world.ROWS_TOTAL);
            BlobLight blob = new BlobLight(this, worldX, worldY);
            if (blob.collidesWith(world.tiles[worldY][worldX])) {
                i--;
                continue;
            }
            enemies.add(blob);
        }
        for (int i = 0; i < 22; i++) {
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
        for (int i = 0; i < 25; i++) {
            int worldX = random.nextInt(world.COLS_TOTAL);
            int worldY = random.nextInt(world.ROWS_TOTAL);
            Fish f = new Fish(this, worldX, worldY);
            if (f.collidesWith(world.tiles[worldY][worldX])) {
                i--;
                continue;
            }
            neutrals.add(f);
        }

        for (int i = 0; i < 21; i++) {
            int worldX = random.nextInt(world.COLS_TOTAL);
            int worldY = random.nextInt(world.ROWS_TOTAL);
            Bird b = new Bird(this, worldX, worldY);
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
            log.debug("Total   = {} microseconds", (System.nanoTime() - nano) / 1e3);
        }

    }

    public void update(double deltaTimeInSeconds) {

        long nano = System.nanoTime();

        player.update(deltaTimeInSeconds);
        enemies.forEach(e -> e.update(deltaTimeInSeconds));
        neutrals.forEach(e -> e.update(deltaTimeInSeconds));

        if (DebugConfig.logTimeToUpdateAndDraw) {
            log.debug("Update  = {} microseconds", (System.nanoTime() - nano) / 1e3);
        }

    }

    public void draw() {

        long nano = System.nanoTime();

        world.drawCenteredAt(player.worldTileX, player.worldTileY);
        itemsOnMap.forEach(Item::draw);
        player.draw();
        enemies.forEach(Enemy::draw);
        neutrals.forEach(Neutral::draw);

        geekStatsOverlayDrawer.draw();
        helpOverlayDrawer.draw();
        messageDrawer.draw();

        if (DebugConfig.logTimeToUpdateAndDraw) {
            log.debug("Drawing = {} microseconds", (System.nanoTime() - nano) / 1e3);
        }

    }

}
