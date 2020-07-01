package web.parser.carsharing.task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class ScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    private static final String URL = "https://carsharing.gde-luchshe.ru/map";
    private static final String GOOGLE = "Google";
    int count = 0;
    private String workingDirectory;

    {
        init();
    }

    private void init() {
        workingDirectory = "data/";
        try {
            if (Files.notExists(Paths.get(workingDirectory)))
                Files.createDirectory(Paths.get(workingDirectory));
        } catch (IOException e) {
            workingDirectory = "";
        }
    }

    /**
     * once every 8 hours
     */
    @Scheduled(fixedRate = 28800000)
//    @Scheduled(fixedRate = 5000)

    public void reportCurrentTime() {
        try {
            System.out.println("Count " + ++count);

            Document doc = Jsoup.connect(URL).maxBodySize(0).userAgent(GOOGLE).get();

            String s2 = new InputHandler(doc.outerHtml()).handle();
            FileCreator file = new FileCreator(s2, workingDirectory);
            boolean created = file.createFile();
            log.info("file " + file.path + " created: " + created);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
