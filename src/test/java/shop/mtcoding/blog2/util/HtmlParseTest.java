package shop.mtcoding.blog2.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

public class HtmlParseTest {

    @Test
    public void jsoup_test1() throws Exception {
        System.out.println("==========================================");
        Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/Main_Page").get();
        System.out.println(doc.title());
        System.out.println("==========================================");
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
            System.out.println(headline.attr("title"));
            System.out.println(headline.absUrl("href"));
            System.out.println("------------------------------------------");
        }
    }

    @Test
    public void jsoup_test2() throws Exception {
        String html = "<p>1</p><p><script src=\"\"></script><img id=\"img1\" src=\"data:image/png;base64,iVBORw0KG\"><img id=\"img2\" src=\"data:image/jpeg;base64,iVBORw0KGaefd\"></p><p><b>1</b></p>";
        Document doc = Jsoup.parse(html);
        Elements els = doc.select("img");
        if (els.size() == 0) {
            System.out.println("사진 없음");
        } else {
            Element el = els.get(0);
            String img = el.attr("src");
            System.out.println(img);
        }
        // insert, update
    }

    @Test
    public void jsoup_test3() throws Exception {
        Document doc = Jsoup.connect("https://comic.naver.com/webtoon/weekdayList?week=wed").get();
        System.out.println(doc.title());
        // :nth-child() 지우기
        Elements tag = doc.select("#content > div.list_area.daily_img > ul > li > dl > dt > a");

        for (Element element : tag) {
            String attr = element.attr("title");
            System.out.println(element.text());
            System.out.println(attr);
        }
    }
}
