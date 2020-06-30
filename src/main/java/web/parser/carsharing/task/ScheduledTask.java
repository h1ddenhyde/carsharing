package web.parser.carsharing.task;

import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Component
public class ScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        try {
            Document doc = Jsoup.connect("https://carsharing.gde-luchshe.ru/map").maxBodySize(0).userAgent("Google").get();

            String o = doc.outerHtml();

//            String o = "var cars = ([{\"id\":\"423148579\",\"name\":\"Lifan X50 синий\",\"latitude\":\"56.77866\",\"longitude\":\"60.5293732\",\"partner_id\":\"32\",\"group_id\":\"bc58e5c9a549c9728f4e280b51bb64648920e301\",\"content\":\"\\u003cdiv class='map_marker'\\u003eUramobil \\u003cb\\u003eLifan X50 синий\\u003c/b\\u003e (41%)\\u003c/div\\u003e\"},{\"id\":\"423148580\",\"name\":\"Nissan X-Trail\",\"latitude\":\"55.82876\",\"longitude\":\"37.592498\",\"partner_id\":\"11\",\"group_id\":\"d7ebd9bc9fbef8b15c61cb2416a621653c9c9fa6\",\"content\":\"\\u003cdiv class='map_marker'\\u003eYouDrive \\u003cb\\u003eNissan X-Trail\\u003c/b\\u003e (57%)\\u003c/div\\u003e\"},{\"id\":\"423148614\",\"name\":\"smart fortwo\",\"latitude\":\"55.794861\",\"longitude\":\"37.781348\",\"partner_id\":\"11\",\"group_id\":\"ab2e4de41d504cc43f3806516dc98038bb71c1ca\",\"content\":\"\\u003cdiv class='map_marker'\\u003eYouDrive \\u003cb\\u003esmart fortwo\\u003c/b\\u003e (60%)\\u003c/div\\u003e\"},{\"id\":\"423148620\",\"name\":\"Lifan X50 Серебро\",\"latitude\":\"55.2620125\",\"longitude\":\"61.401825\",\"partner_id\":\"32\",\"group_id\":\"e16b1c3fa66a12fb32a032cd12cc770f145273ca\",\"content\":\"\\u003cdiv class='map_marker'\\u003eUramobil \\u003cb\\u003eLifan X50 Серебро\\u003c/b\\u003e (99%)\\u003c/div\\u003e\"}]);";

            String s2 = new InputHandler(o).handle();
            FileCreator file = new FileCreator(s2);
            log.info(file.createFile() ? "file " + file.path + " created" : "error: file was not created");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }
}
