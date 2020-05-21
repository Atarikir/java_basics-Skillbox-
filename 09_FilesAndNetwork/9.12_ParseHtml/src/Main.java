import java.io.*;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {

    private static final String webSiteURL = "http://www.lenta.ru";

    private static final String folderPath = "09_FilesAndNetwork\\9.12_ParseHtml\\res";

    public static void main(String[] args) {

        try {

            Document doc = Jsoup.connect(webSiteURL).get();

            Elements img = doc.getElementsByTag("img");

            for (Element el : img) {

                String src = el.absUrl("src");

                System.out.println("Image Found!");
                System.out.println("src attribute is : " + src);

                getImages(src);

            }

        } catch (IOException ex) {
            System.err.println("There was an error");
        }
    }

    private static void getImages(String src) throws IOException {

        int indexname = src.lastIndexOf("/");

        if (indexname == src.length()) {
            src = src.substring(1, indexname);
        }

        indexname = src.lastIndexOf("/");
        String name = src.substring(indexname);

        System.out.println(name);

        URL url = new URL(src);
        InputStream in = url.openStream();

        OutputStream out = new BufferedOutputStream(new FileOutputStream(folderPath + name));

        for (int b; (b = in.read()) != -1; ) {
            out.write(b);
        }
        out.close();
        in.close();
    }
}