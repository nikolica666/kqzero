package hr.nipeta.kqzero.drawers;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.Main;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.TreeSet;

import static java.lang.System.currentTimeMillis;

@Slf4j
public class MessageDrawer extends Drawer {

    private final Set<Message> messages = new TreeSet<>();
    private int messageCounter;

    public MessageDrawer(GameManager gm) {
        super(gm);
    }

    public void addMessage(String text) {
        messages.add(new Message(++messageCounter, text));
    }

    @Override
    public void draw() {

        messages.removeIf(Message::isDead);

        if (messages.isEmpty()) {
            return;
        }
        log.debug("I HAVE {} messages to display", messages.size());

        double canvasHeight = gm.gc.getCanvas().getHeight();
        double canvasWidth = gm.gc.getCanvas().getWidth();
        double textLineHeight = 30d;

        double rectWidth = canvasWidth / 2;
        double rectHeight = messages.size() * textLineHeight + 2 * 6;
        double rectStartX = 6;
        double rectStartY = canvasHeight - rectHeight - 6;

        gm.gc.setFill(Color.color(0, 0, 0, 0.84d));
        gm.gc.fillRect(rectStartX, rectStartY, rectWidth, rectHeight);

        gm.gc.setStroke(Color.WHEAT);
        gm.gc.setLineWidth(3d);
        gm.gc.strokeRect(rectStartX, rectStartY, rectWidth, rectHeight);

        gm.gc.setFont(Main.regularFont);
        gm.gc.setFill(Color.WHEAT);

        int row = -1;
        for (Message message : messages) {
            row++;
            gm.gc.fillText(message.text, 16, canvasHeight - 18 - (row * textLineHeight));
        }


    }

    @AllArgsConstructor
    public static class Message implements Comparable<Message> {
        private int ordinal; // For covering case when multiple messages are born in the same millisecond
        private String text;
        private long bornMs;
        private long durationMs;

        public Message(int ordinal, String text) {
            this.ordinal = ordinal;
            this.text = text;
            this.bornMs = System.currentTimeMillis();
            this.durationMs = 2_500;
        }

        // There shouldn't be a lot of messages around, so we don't bother with optimization
        private boolean isDead() {
            return currentTimeMillis() - bornMs > durationMs;
        }

        @Override
        public int compareTo(Message other) {

            int timestampComparison = Long.compare(this.bornMs, other.bornMs);
            if (timestampComparison != 0) {
                return timestampComparison;
            }

            return Integer.compare(this.ordinal, other.ordinal);

        }
    }

}
