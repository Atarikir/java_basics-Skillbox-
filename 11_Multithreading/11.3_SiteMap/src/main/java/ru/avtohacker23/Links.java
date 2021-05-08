package ru.avtohacker23;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.RecursiveTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Links extends RecursiveTask<String> {

    private String webSiteURL;
    private static String parentalURL;
    private static CopyOnWriteArraySet<String> allLinks = new CopyOnWriteArraySet<>();

    public Links(String url) {
        this.webSiteURL = url.trim();
    }

    public Links(String url, String startUrl) {
        this.webSiteURL = url.trim();
        Links.parentalURL = startUrl.trim();
    }


    @Override
    protected String compute() {
        StringBuffer sb = new StringBuffer(webSiteURL + "\n");
        Set<Links> subTask = new HashSet<>();

        getChildren(subTask);

        for (Links link : subTask) {
            sb.append(link.join());
        }
        return sb.toString();
    }

    private void getChildren(Set<Links> subTask) {
        Document doc;
        Elements elements;
        try {
            Thread.sleep(200);
            doc = Jsoup.connect(webSiteURL).get();
            elements = doc.select("a");
            for (Element el : elements) {
                String attr = el.attr("abs:href");
                if (!attr.isEmpty() && attr.startsWith(parentalURL) && !allLinks.contains(attr) && !attr
                        .contains("#")) {
                    Links linkExecutor = new Links(attr);
                    linkExecutor.fork();
                    subTask.add(linkExecutor);
                    allLinks.add(attr);
                }
            }
        } catch (InterruptedException | IOException ignored) {
        }
    }
}
