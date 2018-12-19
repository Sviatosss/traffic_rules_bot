package svyat.com;

/**
 * Created by sviatosss on 18.12.2018.
 */
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args){
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TrafficRules_bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
