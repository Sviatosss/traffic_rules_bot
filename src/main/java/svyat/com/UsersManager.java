package svyat.com;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by sviatosss on 18.12.2018.
 */
public class UsersManager {
    private static UsersManager sInstance;
    public MongoCollection<Document>  mUsersCollection = DataBaseManager.getInstance().getmUsersCollection();

    public static UsersManager getInstance() {
        if (sInstance == null) {
            sInstance = new UsersManager();
        }

        return sInstance;
    }

    public void issetUser(Update update){
        String id = Functions.getInstance().getId(update);
        Document query = new Document("id", id);
        Document user = mUsersCollection.find(query).first();

        if (user == null) {
            Document newUser = new Document("id", id)
                    .append("firstName", update.getMessage().getChat().getFirstName())
                    .append("lastName", update.getMessage().getChat().getLastName())
                    .append("username", update.getMessage().getChat().getUserName())
                    .append("current_quest", "");
            mUsersCollection.insertOne(newUser);
            System.out.println("New user");
        }
    }

    public void updateQuest(Update update, String newQuest){
        String id = Functions.getInstance().getId(update);
        mUsersCollection.updateOne(eq("id", id), new Document("$set", new Document("current_quest", newQuest)));
    }
    public String getQuest(Update update){
        String id = Functions.getInstance().getId(update);
        Document query = new Document("id", id);
        Document user = mUsersCollection.find(query).first();
        return user.getString("current_quest");
    }

}
