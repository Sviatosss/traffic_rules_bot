package svyat.com;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

/**
 * Created by Sviat on 26.10.2018.
 */
public class Sending extends TrafficRules_bot {
    public void sendThumbnail(Update update, String url, String title){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setCaption(title);
        sendPhoto.setPhoto(url);
        sendPhoto.setChatId(Functions.getInstance().getId(update));
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public  void sendMsg(Update update, String s){
        if (update.hasCallbackQuery()){
            editMessage(update,s);
        }else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(Functions.getInstance().getId(update));
            sendMessage.setText(s);
            sendMessage.disableWebPagePreview();
            try {
                execute(sendMessage);
            }catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    }
    public void keyboards(Update update, ArrayList<String> comandList){
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.enableMarkdown(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboar = new ArrayList<>();

        int i = 0;
        KeyboardRow row = new KeyboardRow();
        for (String comand:  comandList) {
            row.add(comand);
            i++;
            if (i % 2 == 0){
                keyboar.add(row);
                row = new KeyboardRow();
            }
        }
        replyKeyboardMarkup.setKeyboard(keyboar);
        replyKeyboardMarkup.setResizeKeyboard(true);
        sendMessage.setText("Виберіть дію з меню");
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.disableWebPagePreview();
        try {
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    public void sendMainMenu(Update update){

        SendMessage message = InlineKeyboardBuilder.create(update.getMessage().getChat().getId())
                .setText("Головне меню:")
                .row()
                .button("Підібрати виріб", "menu_main_filter")
                .button("Послуги", "menu_main_service")
                .button("Увесь каталог", "menu_main_catalog")
                .endRow()
                .row()
                .button("Контакти", "menu_main_contacts")
                .button("Написа нам !", "menu_main_send_mail")
                .endRow()
                .build();

        try {
            // Send the message
            sendApiMethod(message);
            //execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void editMessage(Update update, String answer){
        EditMessageText new_message = new EditMessageText()
                .setChatId(update.getCallbackQuery().getMessage().getChatId())
                .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                .setText(answer);
        try {
            execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendingBootomNavs(Update update){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Рендомний тест");
        arrayList.add("Додати тест");
        arrayList.add("ВИЙТИ");
        arrayList.add("Інструкція");
        keyboards(update, arrayList);
    }
    public void sendingAnswer(Update update, Answer answer, int iteration){
        SendMessage message = InlineKeyboardBuilder.create( Long.parseLong(Functions.getInstance().getId(update)))
                .setText("Варіат - " + iteration)
                .row()
                .button(answer.theAnswer, answer.someID)
                .endRow()
                .build();
        try {
            // Send the message
            sendApiMethod(message);
            //execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendingTest(Update update, Test test){
        sendMsg(update, test.question);
        if (test.imgUrl != null){
            sendThumbnail(update, test.imgUrl, "Картинка");
        }
        int iteration = 1;
        for (Answer answer: test.answers){
            sendingAnswer(update, answer, iteration);
            iteration++;
        }
    }
    public void sendingInstraction(Update update){
        String instraction = "Використовуйте нижнє меню для навігації !\n" +
                "Рендомний тест - надсилає випадкове завдання \n" +
                "Виберіть один з варіантів та натисніть на кнопку \n" +
                "Додати тест - ви можете добавити ще одне завдання \n" +
                "УВАЖНО ПОДИВІТЬСЯ НА ЗРАЗОК, якщо ви все зробили правильно вам прийде повідомлення про успішне добавлення тесту\n" +
                "Для того щоб вийти з функції добавлення чи проходження тесту нажміть на ВИЙТИ";
        sendMsg(update, instraction);
    }
}