package net.shtyftu.ubiquode;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

/**
 * @author shtyftu
 */
public class QuestlerLifeServer {

    private static final Logger log = LoggerFactory.getLogger(QuestlerLifeServer.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(QuestlerLifeServer.class, args);
        } catch (Exception e) {
            log.error("Error while starting server", e);
            System.exit(1);
        }
    }

}
