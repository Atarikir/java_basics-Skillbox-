import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DownloadFileUsingNIO {

    private static final String webSiteURL = "http://www.lenta.ru";

    private static final String folderPath = "09_FilesAndNetwork\\9.12_ParseHtml\\res\\";

    public static void main(String[] args) {

        try {

            Document doc = Jsoup.connect(webSiteURL).get();

            Elements img = doc.getElementsByTag("img");

            for (Element el : img) {

                String src = el.absUrl("src");

                System.out.println("Image Found!");
                System.out.println("src attribute is : " + src);

                getImage(src);

                //downloadUsingStream(src);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void getImage(String src) throws IOException {

        int indexname = src.lastIndexOf("/");

        if (indexname == src.length()) {
            src = src.substring(1, indexname);
        }

        indexname = src.lastIndexOf("/");
        String name = src.substring(indexname);

        System.out.println(name);

        URL url = new URL(src);
        String[] str = src.split("/");
        String fileName = str[str.length - 1];
        String pathToFile = folderPath + fileName;
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        if (fileName.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)")) {
            FileOutputStream fos = new FileOutputStream(pathToFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
        }
        rbc.close();
    }

//    private static void downloadUsingStream(String src) throws IOException{
//
//        int indexname = src.lastIndexOf("/");
//
//        if (indexname == src.length()) {
//            src = src.substring(1, indexname);
//        }
//
//        indexname = src.lastIndexOf("/");
//        String name = src.substring(indexname);
//
//        System.out.println(name);
//
//        URL url = new URL(src);
//        String[] str = src.split("/");
//        String fileName = str[str.length - 1];
//        String pathToFile = folderPath + fileName;
//        BufferedInputStream bis = new BufferedInputStream(url.openStream());
//        FileOutputStream fis = new FileOutputStream(pathToFile);
//        byte[] buffer = new byte[1024];
//        int count=0;
//        while((count = bis.read(buffer,0,1024)) != -1)
//        {
//            fis.write(buffer, 0, count);
//        }
//        fis.close();
//        bis.close();
//    }
}
