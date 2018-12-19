package svyat.com;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TrafficRules_bot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        Sending sending = new Sending();

        if (update.hasMessage() && update.getMessage().hasText()) {
            UsersManager.getInstance().issetUser(update);
            String s = update.getMessage().getText();
            if (s.equals("/start")){
                sending.sendingBootomNavs(update);
            }else if (s.equals("Рендомний тест")){
                UsersManager.getInstance().updateQuest(update, "test_start");
                TestsManager.getInstance().getOneTest(update);
            }else if (s.equals("Додати тест")){
                sending.sendMsg(update, "(ЗРАЗОК) запитання ?\n" +
                        "\n" +
                        "Відповідь 1\n" +
                        "-Відповідь 2 (правильна з позначкою)\n" +
                        "Відповідь 3 + відповідей може бути безліч\n" +
                        "Відповідь 4\n" +
                        "\n" +
                        "посилання на картинку(не обов'язково)");
                UsersManager.getInstance().updateQuest(update, "add_test");
            }else if(UsersManager.getInstance().getQuest(update).equals("add_test")){
                sending.sendMsg(update, TestsManager.getInstance().addNewTest(update.getMessage().getText()));
            }else if(s.equals("Інструкція")){
                sending.sendingInstraction(update);
            }
            else if (s.equals("ВИЙТИ")){
                UsersManager.getInstance().updateQuest(update, "");
            }
        } else if (update.hasCallbackQuery()) {
            if(UsersManager.getInstance().getQuest(update).equals("test_start")){
                String myCallBack = update.getCallbackQuery().getData();
                if (myCallBack.contains("TEST_")){
                    if (myCallBack.contains("TEST_true")){
                        sending.editMessage(update, "Правильна відповідь !");
//                        TestsManager.getInstance().getOneTest(update);
                    }else {
                        sending.editMessage(update, "Спробуйте інший варіант :)");
                    }
                }
            }
        }

    }
    public String getBotUsername() {
        return "traffic_rules_bot";
    }
    public String getBotToken() {
        return "732894509:AAFabd1WMkEMGlFmojVV25-FZlpMeKj56Fs";
    }
}