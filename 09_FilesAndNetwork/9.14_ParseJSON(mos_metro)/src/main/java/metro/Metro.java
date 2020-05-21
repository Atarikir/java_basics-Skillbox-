package metro;

import java.util.List;
import java.util.Map;

public class Metro {
    private Map<String, List<String>> stations;
    private List<Line> lines;
    private List<List<Station>> connections;

    public Metro(Map<String, List<String>> stations, List<Line> lines, List<List<Station>> connections) {

        this.stations = stations;
        this.lines = lines;
        this.connections = connections;
    }

    public List<Line> getLines() {
        return lines;
    }
}
