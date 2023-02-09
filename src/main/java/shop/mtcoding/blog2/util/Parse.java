package shop.mtcoding.blog2.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parse {
    public static String thumbnailPasing(String html) {
        String thumbnail = "";
        // html 전부를 document로 받아오기
        Document doc = Jsoup.parse(html);
        // 이걸 img로 파싱하기
        Elements els = doc.select("img");

        // img 태그가 있는가 확인하기
        if (els.size() == 0) {
            thumbnail = "/images/dora.png";
            // 디비 thumbnail 에다가 /images/dora.png
        } else {
            // 있으면 0번지에 있는 값의 src속성을 들고오기
            Element el = els.get(0);
            String img = el.attr("src");
            thumbnail = img;
        }

        return thumbnail;
    }
}