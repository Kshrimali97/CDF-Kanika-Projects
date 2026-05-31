import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public class UserJourneyMapGenerator {
    private static final int WIDTH = 2400;
    private static final int HEIGHT = 1500;
    private static final int MARGIN = 64;

    private static final Color BG = new Color(247, 248, 251);
    private static final Color INK = new Color(30, 34, 44);
    private static final Color MUTED = new Color(92, 99, 112);
    private static final Color BORDER = new Color(218, 224, 234);
    private static final Color PANEL = Color.WHITE;
    private static final Color ROSE = new Color(218, 54, 104);
    private static final Color ORANGE = new Color(244, 126, 55);
    private static final Color PURPLE = new Color(80, 64, 142);
    private static final Color ACTION_BG = new Color(242, 242, 244);
    private static final Color THOUGHT_BG = new Color(239, 243, 255);
    private static final Color FEELING_BG = new Color(248, 241, 255);
    private static final Color PAIN_BG = new Color(255, 239, 239);
    private static final Color PAIN_TEXT = new Color(141, 42, 42);
    private static final Color OPP_BG = new Color(232, 247, 239);
    private static final Color OPP_TEXT = new Color(38, 109, 75);
    private static final Color HIGHLIGHT_BG = new Color(255, 247, 230);
    private static final Color HIGHLIGHT_BORDER = new Color(235, 172, 63);

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: java UserJourneyMapGenerator <journey-map-data.json> <output.png>");
            System.exit(1);
        }

        Map<String, Object> data = asObject(new JsonParser(readUtf8(args[0])).parse());
        BufferedImage image = render(data);
        ImageIO.write(image, "png", new File(args[1]));
        System.out.println("Created " + args[1] + " (" + WIDTH + "x" + HEIGHT + ")");
    }

    private static BufferedImage render(Map<String, Object> data) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(BG);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        drawHeader(g, data);
        drawPersonaAndLegend(g, data);
        drawJourneyGrid(g, data);
        drawFooter(g);
        g.dispose();
        return image;
    }

    private static void drawHeader(Graphics2D g, Map<String, Object> data) {
        int x = MARGIN;
        int y = 42;
        int w = WIDTH - MARGIN * 2;
        int h = 210;

        Shape banner = new RoundRectangle2D.Double(x, y, w, h, 28, 28);
        g.setPaint(new java.awt.GradientPaint(x, y, ROSE, x + w, y + h, ORANGE));
        g.fill(banner);
        g.setColor(new Color(255, 255, 255, 36));
        for (int i = 0; i < 7; i++) {
            g.fillOval(x + 90 + i * 320, y + 22 + (i % 3) * 42, 150, 150);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 54));
        g.drawString(text(data, "app") + " " + text(data, "feature"), x + 42, y + 78);
        g.setFont(new Font("SansSerif", Font.PLAIN, 25));
        drawWrapped(g, text(data, "featureDescription"), x + 44, y + 118, w - 88, 32, Color.WHITE);
        drawPill(g, "Fictional new feature", x + 44, y + 160);
        drawPill(g, "7-stage journey map", x + 300, y + 160);
        drawPill(g, "PNG deliverable", x + 535, y + 160);
    }

    private static void drawPersonaAndLegend(Graphics2D g, Map<String, Object> data) {
        Map<String, Object> persona = asObject(data.get("persona"));
        drawCard(g, MARGIN, 280, 1000, 205);
        g.setColor(PURPLE);
        g.setFont(new Font("SansSerif", Font.BOLD, 27));
        g.drawString("Primary Persona", MARGIN + 28, 320);
        drawAvatar(g, MARGIN + 32, 345);
        g.setColor(INK);
        g.setFont(new Font("SansSerif", Font.BOLD, 32));
        g.drawString(text(persona, "name") + ", " + text(persona, "age"), MARGIN + 138, 365);
        g.setColor(ROSE);
        g.setFont(new Font("SansSerif", Font.BOLD, 21));
        g.drawString(text(persona, "type"), MARGIN + 138, 398);
        g.setFont(new Font("SansSerif", Font.PLAIN, 21));
        drawWrapped(g, text(persona, "goal"), MARGIN + 138, 430, 805, 27, MUTED);

        int lx = 1100;
        drawCard(g, lx, 280, WIDTH - MARGIN - lx, 205);
        g.setColor(PURPLE);
        g.setFont(new Font("SansSerif", Font.BOLD, 27));
        g.drawString("Map Includes", lx + 28, 320);
        drawLegendChip(g, "Actions", "what Maya does", lx + 28, 350, ACTION_BG, INK);
        drawLegendChip(g, "Thoughts", "decision questions", lx + 360, 350, THOUGHT_BG, INK);
        drawLegendChip(g, "Feelings", "emotional state", lx + 692, 350, FEELING_BG, INK);
        drawLegendChip(g, "Pain points", "friction or risk", lx + 28, 415, PAIN_BG, PAIN_TEXT);
        drawLegendChip(g, "Opportunities", "product improvements", lx + 360, 415, OPP_BG, OPP_TEXT);
        drawLegendChip(g, "Highlights", "key moments", lx + 692, 415, HIGHLIGHT_BG, new Color(118, 78, 13));
    }

    private static void drawJourneyGrid(Graphics2D g, Map<String, Object> data) {
        List<Object> stages = asList(data.get("stages"));
        int x = MARGIN;
        int y = 520;
        int w = WIDTH - MARGIN * 2;
        int h = 820;
        int labelW = 190;
        int stageW = (w - labelW) / stages.size();
        int[] rows = new int[] { y, y + 88, y + 228, y + 368, y + 508, y + 648, y + h };

        drawCard(g, x, y, w, h);
        g.setColor(new Color(245, 246, 248));
        g.fillRect(x + 1, y + 1, labelW - 1, h - 2);
        g.fillRect(x + labelW, y + 1, w - labelW - 1, 87);
        g.setStroke(new BasicStroke(2f));
        g.setColor(BORDER);
        g.drawLine(x + labelW, y, x + labelW, y + h);
        for (int i = 1; i < stages.size(); i++) {
            int vx = x + labelW + i * stageW;
            g.drawLine(vx, y, vx, y + h);
        }
        for (int i = 1; i < rows.length; i++) {
            g.drawLine(x, rows[i], x + w, rows[i]);
        }

        drawRowLabels(g, x, rows, labelW);
        String[] keys = { "action", "thought", "feeling", "painPoint", "opportunity" };
        Color[] fills = { ACTION_BG, THOUGHT_BG, FEELING_BG, PAIN_BG, OPP_BG };
        Color[] textColors = { INK, INK, INK, PAIN_TEXT, OPP_TEXT };

        for (int i = 0; i < stages.size(); i++) {
            Map<String, Object> stage = asObject(stages.get(i));
            int cellX = x + labelW + i * stageW;
            boolean highlight = bool(stage, "highlight");
            if (highlight) {
                drawRound(g, cellX + 10, y + 12, stageW - 20, 62, 16, HIGHLIGHT_BG, HIGHLIGHT_BORDER, 2);
                g.setColor(new Color(255, 247, 230, 90));
                g.fillRect(cellX + 1, y + 89, stageW - 2, h - 90);
            }
            g.setColor(PURPLE);
            g.setFont(new Font("SansSerif", Font.BOLD, 22));
            drawCenteredWrapped(g, (i + 1) + ". " + text(stage, "name"), cellX + 14, y + 30, stageW - 28, 27, PURPLE);

            for (int row = 0; row < keys.length; row++) {
                int cellY = rows[row + 1];
                int cellH = rows[row + 2] - rows[row + 1];
                drawRound(g, cellX + 14, cellY + 14, stageW - 28, cellH - 28, 14, fills[row], null, 0);
                g.setFont(new Font("SansSerif", row == 2 ? Font.BOLD : Font.PLAIN, row == 2 ? 20 : 18));
                drawWrapped(g, text(stage, keys[row]), cellX + 28, cellY + 40, stageW - 56, 23, textColors[row]);
            }
        }
    }

    private static void drawRowLabels(Graphics2D g, int x, int[] rows, int labelW) {
        String[] labels = { "Stage", "Action", "Thought", "Feeling", "Pain point", "Opportunity" };
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        for (int i = 0; i < labels.length; i++) {
            g.setColor(i == 0 ? PURPLE : INK);
            drawWrapped(g, labels[i], x + 22, rows[i] + 40, labelW - 44, 26, g.getColor());
        }
    }

    private static void drawFooter(Graphics2D g) {
        int x = MARGIN;
        int y = 1370;
        int w = WIDTH - MARGIN * 2;
        drawRound(g, x, y, w, 72, 16, new Color(236, 239, 244), null, 0);
        g.setFont(new Font("SansSerif", Font.PLAIN, 19));
        drawWrapped(g, "References: Pew Research Center and DataReportal for Instagram audience context; diagrams.net/draw.io as the open-source diagramming tool reference; Service Design Tools for journey-map structure.", x + 24, y + 31, w - 48, 25, MUTED);
    }

    private static void drawAvatar(Graphics2D g, int x, int y) {
        g.setColor(new Color(248, 203, 215));
        g.fillOval(x, y, 78, 78);
        g.setColor(new Color(83, 56, 64));
        g.fillArc(x + 9, y + 14, 60, 44, 0, 180);
        g.setColor(new Color(255, 236, 225));
        g.fillOval(x + 23, y + 30, 32, 32);
        g.setColor(PURPLE);
        g.fillRoundRect(x + 18, y + 58, 44, 20, 10, 10);
    }

    private static void drawCard(Graphics2D g, int x, int y, int w, int h) {
        drawRound(g, x, y, w, h, 18, PANEL, BORDER, 2);
    }

    private static void drawLegendChip(Graphics2D g, String label, String description, int x, int y, Color fill, Color textColor) {
        drawRound(g, x, y, 290, 48, 14, fill, null, 0);
        g.setColor(textColor);
        g.setFont(new Font("SansSerif", Font.BOLD, 17));
        g.drawString(label, x + 14, y + 21);
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g.drawString(description, x + 14, y + 39);
    }

    private static void drawPill(Graphics2D g, String label, int x, int y) {
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        int w = fm.stringWidth(label) + 32;
        drawRound(g, x, y, w, 38, 19, new Color(255, 255, 255, 45), null, 0);
        g.setColor(Color.WHITE);
        g.drawString(label, x + 16, y + 25);
    }

    private static void drawRound(Graphics2D g, int x, int y, int w, int h, int arc, Color fill, Color stroke, int strokeWidth) {
        Shape shape = new RoundRectangle2D.Double(x, y, w, h, arc, arc);
        if (fill != null) {
            g.setColor(fill);
            g.fill(shape);
        }
        if (stroke != null && strokeWidth > 0) {
            g.setColor(stroke);
            g.setStroke(new BasicStroke(strokeWidth));
            g.draw(shape);
        }
    }

    private static void drawWrapped(Graphics2D g, String text, int x, int y, int maxWidth, int lineHeight, Color color) {
        g.setColor(color);
        FontMetrics fm = g.getFontMetrics();
        List<String> lines = wrap(text, fm, maxWidth);
        for (int i = 0; i < lines.size(); i++) {
            g.drawString(lines.get(i), x, y + i * lineHeight);
        }
    }

    private static void drawCenteredWrapped(Graphics2D g, String text, int x, int y, int maxWidth, int lineHeight, Color color) {
        g.setColor(color);
        FontMetrics fm = g.getFontMetrics();
        List<String> lines = wrap(text, fm, maxWidth);
        for (int i = 0; i < lines.size(); i++) {
            int lineX = x + (maxWidth - fm.stringWidth(lines.get(i))) / 2;
            g.drawString(lines.get(i), lineX, y + i * lineHeight);
        }
    }

    private static List<String> wrap(String text, FontMetrics fm, int maxWidth) {
        List<String> lines = new ArrayList<String>();
        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            String candidate = line.length() == 0 ? word : line + " " + word;
            if (fm.stringWidth(candidate) <= maxWidth) {
                line.setLength(0);
                line.append(candidate);
            } else {
                if (line.length() > 0) {
                    lines.add(line.toString());
                    line.setLength(0);
                }
                line.append(word);
            }
        }
        if (line.length() > 0) {
            lines.add(line.toString());
        }
        return lines;
    }

    private static String readUtf8(String path) throws Exception {
        FileInputStream input = new FileInputStream(path);
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int count;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
            return new String(output.toByteArray(), StandardCharsets.UTF_8);
        } finally {
            input.close();
        }
    }

    private static String text(Map<String, Object> object, String key) {
        Object value = object.get(key);
        if (value == null) {
            return "";
        }
        if (value instanceof Number) {
            double number = ((Number) value).doubleValue();
            if (Math.floor(number) == number) {
                return String.valueOf((long) number);
            }
        }
        return String.valueOf(value);
    }

    private static boolean bool(Map<String, Object> object, String key) {
        Object value = object.get(key);
        return value instanceof Boolean && ((Boolean) value).booleanValue();
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> asObject(Object value) {
        return (Map<String, Object>) value;
    }

    @SuppressWarnings("unchecked")
    private static List<Object> asList(Object value) {
        return (List<Object>) value;
    }

    private static class JsonParser {
        private final String input;
        private int index;

        JsonParser(String input) {
            this.input = input;
        }

        Object parse() {
            Object value = parseValue();
            skipWhitespace();
            if (index != input.length()) {
                throw error("Unexpected trailing content");
            }
            return value;
        }

        private Object parseValue() {
            skipWhitespace();
            char ch = input.charAt(index);
            if (ch == '{') return parseObject();
            if (ch == '[') return parseArray();
            if (ch == '"') return parseString();
            if (ch == 't' || ch == 'f') return parseBoolean();
            if (ch == 'n') return parseNull();
            if (ch == '-' || Character.isDigit(ch)) return parseNumber();
            throw error("Unexpected character " + ch);
        }

        private Map<String, Object> parseObject() {
            expect('{');
            Map<String, Object> object = new LinkedHashMap<String, Object>();
            skipWhitespace();
            if (peek('}')) {
                index++;
                return object;
            }
            while (true) {
                String key = parseString();
                skipWhitespace();
                expect(':');
                object.put(key, parseValue());
                skipWhitespace();
                if (peek('}')) {
                    index++;
                    return object;
                }
                expect(',');
                skipWhitespace();
            }
        }

        private List<Object> parseArray() {
            expect('[');
            List<Object> values = new ArrayList<Object>();
            skipWhitespace();
            if (peek(']')) {
                index++;
                return values;
            }
            while (true) {
                values.add(parseValue());
                skipWhitespace();
                if (peek(']')) {
                    index++;
                    return values;
                }
                expect(',');
            }
        }

        private String parseString() {
            expect('"');
            StringBuilder builder = new StringBuilder();
            while (index < input.length()) {
                char ch = input.charAt(index++);
                if (ch == '"') return builder.toString();
                if (ch == '\\') {
                    char escaped = input.charAt(index++);
                    if (escaped == '"' || escaped == '\\' || escaped == '/') builder.append(escaped);
                    else if (escaped == 'n') builder.append('\n');
                    else if (escaped == 'r') builder.append('\r');
                    else if (escaped == 't') builder.append('\t');
                    else if (escaped == 'b') builder.append('\b');
                    else if (escaped == 'f') builder.append('\f');
                    else if (escaped == 'u') {
                        String hex = input.substring(index, index + 4);
                        builder.append((char) Integer.parseInt(hex, 16));
                        index += 4;
                    } else {
                        throw error("Invalid escape");
                    }
                } else {
                    builder.append(ch);
                }
            }
            throw error("Unterminated string");
        }

        private Boolean parseBoolean() {
            if (input.startsWith("true", index)) {
                index += 4;
                return Boolean.TRUE;
            }
            if (input.startsWith("false", index)) {
                index += 5;
                return Boolean.FALSE;
            }
            throw error("Invalid boolean");
        }

        private Object parseNull() {
            if (!input.startsWith("null", index)) throw error("Invalid null");
            index += 4;
            return null;
        }

        private Number parseNumber() {
            int start = index;
            if (peek('-')) index++;
            while (index < input.length() && Character.isDigit(input.charAt(index))) index++;
            if (peek('.')) {
                index++;
                while (index < input.length() && Character.isDigit(input.charAt(index))) index++;
            }
            return Double.valueOf(input.substring(start, index));
        }

        private void skipWhitespace() {
            while (index < input.length() && Character.isWhitespace(input.charAt(index))) index++;
        }

        private boolean peek(char expected) {
            return index < input.length() && input.charAt(index) == expected;
        }

        private void expect(char expected) {
            if (!peek(expected)) throw error("Expected " + expected);
            index++;
        }

        private IllegalArgumentException error(String message) {
            return new IllegalArgumentException(message + " at character " + index);
        }
    }
}
