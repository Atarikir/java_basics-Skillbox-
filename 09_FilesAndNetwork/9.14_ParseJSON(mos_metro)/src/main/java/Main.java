import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final String webSiteUrl = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";

    public static void main(String[] args) throws IOException, ParseException {

        try {
            Document doc = Jsoup.connect(webSiteUrl)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .maxBodySize(0).get();

            Element table = doc.select("table").get(3);

            Elements rows = table.select("tr");

            rows.stream().skip(1).forEach((el) -> {
                Elements columns = el.select("td");
                String stationName = columns.get(1).select("a").first().text();
                String lineName = columns.get(0).child(1).attr("title");
                List<String> lineNumbers = columns.get(0).children().eachText();
                List<String> connectionsLineName = columns.get(0).children().eachAttr("title");
                List<String> connectionsNumber = columns.get(3).children().eachText();

                JsonResponse.parseStations(stationName, lineNumbers, connectionsLineName);
                JsonResponse.parseLines(lineNumbers, lineName);
                if (connectionsNumber.size() != 0) {
                    JsonResponse.parseConnections(columns, stationName);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonResponse.createJsonFile();
        JsonResponse.JsonParse();
    }
}