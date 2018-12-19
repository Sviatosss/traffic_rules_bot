package svyat.com;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sviatosss on 18.12.2018.
 */
public class TestsManager {
    private static TestsManager sInstance;
    public MongoCollection<Document> mTestsCollection;

    public static TestsManager getInstance() {
        if (sInstance == null) {
            sInstance = new TestsManager();
        }

        return sInstance;
    }
    private TestsManager() {
        mTestsCollection = DataBaseManager.getInstance().getmTestsCollection();
    }
    public String addNewTest(String q){
        String[] strings = q.split("\n\n");
        if (strings.length < 2){
            return "Помилка, слідуйте зразку !!!";
        }else if (strings.length == 2){
            String question = strings[0];
            String[] answers = strings[1].split("\n");

            List<Document> docs = new ArrayList<>();
            boolean issetTrue = false;
            for (String s: answers){
                String someId = Functions.getInstance().generateString();
                if (s.charAt(0) == '-'){
                    issetTrue = true;
                    s = s.substring(1);
                    someId = "true" + someId;
                }
                docs.add(new Document("some_id", "TEST_" + someId).append("answer", s));
            }
            if (!issetTrue){
                return "Помилка, задайте правильну відповідь !!!";
            }

            Document newTest = new Document("id", mTestsCollection.count())
                    .append("question", question)
                    .append("answers", docs);

            mTestsCollection.insertOne(newTest);
            return "Тест успішно додано";
        }else if (strings.length == 3){
            String question = strings[0];
            String imgUrl = strings[2];
            String[] answers = strings[1].split("\n");

            List<Document> docs = new ArrayList<>();
            boolean issetTrue = false;
            for (String s: answers){
                String someId = Functions.getInstance().generateString();
                if (s.charAt(0) == '-'){
                    issetTrue = true;
                    s = s.substring(1);
                    someId = "true" + someId;
                }
                docs.add(new Document("some_id", "TEST_" + someId).append("answer", s));
            }
            if (!issetTrue){
                return "Помилка, задайте правильну відповідь !!!";
            }

            Document newTest = new Document("id", mTestsCollection.count())
                    .append("question", question)
                    .append("img", imgUrl)
                    .append("answers", docs);

            mTestsCollection.insertOne(newTest);
            return "Тест успішно додано";
        }else if (strings.length > 3){
            return "Помилка, слідуйте зразку !!!";
        }
        return "error";
    }

    public void getOneTest(Update update){
        int id = Functions.getInstance().getRandom(0, (int)mTestsCollection.count()-1);
        Document query = new Document("id", id);
        Document testDoc = mTestsCollection.find(query).first();
        testDoc.getString("img");
        ArrayList answers = (ArrayList) testDoc.get( "answers");
        ArrayList<Answer> answersList = getAllAnswer(answers);

        Test test = new Test(id, testDoc.getString("question"), answersList, testDoc.getString("img"));
        Sending sending = new Sending();
        sending.sendingTest(update, test);
    }
    public ArrayList<Answer> getAllAnswer(ArrayList answers){
        ArrayList<Answer> allAnswerList = new ArrayList<>();
        for(Iterator< Object > it = answers.iterator(); it.hasNext(); ) {
            Document dbo  = (Document) it.next();
            String the_id = (String) dbo.get("some_id");
            String the_answer = (String) dbo.get("answer");
            Answer currentAnswer = new Answer(the_id, the_answer);
            allAnswerList.add(currentAnswer);
        }
        return allAnswerList;
    }
}
