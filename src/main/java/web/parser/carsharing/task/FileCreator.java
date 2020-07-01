package web.parser.carsharing.task;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileCreator {
    String input;
    String path;
    String workingDirectory;

    public FileCreator(String input, String workingDirectory) {
        this.input = input;
        this.workingDirectory = workingDirectory;
    }

    public boolean createFile() throws IOException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-z");
        String format = dateFormat.format(new Date());

        this.path = workingDirectory + format + "_cars.txt";

        JSONArray data = getJSON(input);
        StringBuilder sb = new StringBuilder();
        data.forEach(o -> sb.append("name ").append((String) ((JSONObject) o).get("name"))
                .append(" id ").append(((JSONObject) o).get("id"))
                .append(" latitude ").append(((JSONObject) o).get("latitude"))
                .append(" longitude ").append(((JSONObject) o).get("longitude")).append("\n"));

        Files.createFile(Paths.get(path));
        Files.write(Paths.get(path), sb.toString().getBytes(), new StandardOpenOption[]{StandardOpenOption.APPEND});

        return Files.exists(Paths.get(path));
    }

    private JSONArray getJSON(String string) throws ParseException {
        String substring = string.substring(string.indexOf("["), string.indexOf("]") + 1);
        JSONParser jsonParser = new JSONParser();
        return (JSONArray) jsonParser.parse(substring);
    }
}
