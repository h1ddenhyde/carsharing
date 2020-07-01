package web.parser.carsharing.task;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {

    private final String input;

    public InputHandler(String string) {
        this.input = string;
    }

    public String handle() {
        String _cars = input.substring(input.indexOf("var cars ="), input.indexOf("]);") + 1);
        String decoded = decode(_cars);
        return parse(decoded);
    }

    private String decode(String s) {
        Charset cset = StandardCharsets.UTF_8;
        ByteBuffer buf = cset.encode(s);
        byte[] b = buf.array();
        return new String(b);
    }


    private String parse(String s) {

        StringBuilder sb = new StringBuilder();


        Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
        Matcher m = p.matcher(s);

        int lastIndex = 0;
        while (m.find()) {

            sb.append(s, lastIndex, m.start());
            lastIndex = m.end();

            sb.append((char) Integer.parseInt(m.group(1), 16));
        }

        if (lastIndex < s.length())
            sb.append(s.substring(lastIndex));

        return sb.toString();
    }
}
