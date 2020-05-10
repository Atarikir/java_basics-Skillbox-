import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import metro.Line;
import metro.Metro;
import metro.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonResponse {
    private static final String OUTFILE = "09_FilesAndNetwork\\9.14_ParseJSON(mos_metro)\\src\\main\\resources\\MosMetro.json";
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
    private static Map<String, List<String>> stations = new HashMap<>();
    private static List<Line> lines = new ArrayList<>();
    private static List<List<Station>> connections = new ArrayList<>();
    private static Metro metro;

    static void parseStations(String stationName, List<String> lineNumbers, List<String> connectionsLineName) {
        String lineNum = lineNumbers.get(0);
        if (!stations.containsKey(lineNum)) {
            stations.put(lineNum, new ArrayList<>());
            stations.get(lineNum).add(stationName);
        } else stations.get(lineNum).add(stationName);

        if (connectionsLineName.size() == 2) {
            if (!stations.containsKey(lineNumbers.get(1)))
                stations.put(lineNumbers.get(1), new ArrayList<>());
            else stations.get(lineNumbers.get(1)).add(stationName);
        }
    }

    static void parseLines(List<String> lineNumbers, String lineName) {
        Line line = new Line(lineNumbers.get(0), lineName);
        if (!lines.contains(line)) {
            lines.add(line);
        }
    }

    static void parseConnections(Elements columns, String stationName) {
        List<String> connectionsLine = columns.get(3).children().eachAttr("title");
        List<String> connectionsNumber = columns.get(3).children().eachText();
        List<String> lineNumbers = columns.get(0).children().eachText();
        String lineNum = lineNumbers.get(1);
        if (connectionsNumber.size() != 0) {
            List<Station> temp = new ArrayList<>();
            temp.add(new Station(lineNum, stationName));
            for (int i = 0; i < connectionsNumber.size(); i++) {
                temp.add(new Station(connectionsNumber.get(i), connectionsLine.get(i)));
            }
            connections.add(temp);
        }
    }

    static void createJsonFile() throws IOException {

        metro = new Metro(JsonResponse.stations, JsonResponse.lines, JsonResponse.connections);
        try (FileWriter file = new FileWriter(OUTFILE)) {
            file.write(GSON.toJson(metro));
            file.flush();
        }

        //String json = GSON.toJson(metro);
        //System.out.println(json);
    }

    static String parseFile(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(line -> sb.append(line).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    static void JsonParse() throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonData = (JSONObject) parser.parse(parseFile(OUTFILE));

        Map<String, List<String>> stations = (Map<String, List<String>>) jsonData.get("stations");
        for (String lineNumber : stations.keySet()) {
            JSONArray arrayStations = (JSONArray) stations.get(lineNumber);
            for (Line line : metro.getLines()) {
                if (line.getNumber().equals(lineNumber)) {
                    System.out.println("Линия " + lineNumber + " " + line.getName() + " - количество станций: " + arrayStations.size());
                }
            }

        }
    }
}
