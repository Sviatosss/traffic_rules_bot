package svyat.com;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Random;
import java.util.UUID;

/**
 * Created by sviatosss on 18.12.2018.
 */
public class Functions {
    private static Functions sInstance;

    public static Functions getInstance() {
        if (sInstance == null) {
            sInstance = new Functions();
        }

        return sInstance;
    }
    public String getId(Update update){
        if (update.hasMessage()){
            return update.getMessage().getChatId().toString();
        }else if(update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId().toString();
        }else {
            return null;
        }
    }
    public  String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }
    public int getRandom(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
