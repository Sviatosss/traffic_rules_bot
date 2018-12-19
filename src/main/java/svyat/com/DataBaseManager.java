package svyat.com;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by sviatosss on 01.12.2018.
 */
public class DataBaseManager {
    private static DataBaseManager sInstance;

    private static final String DB_HOST = "ds127961.mlab.com";
    private static final int DB_PORT = 27961;
    private static final String DB_NAME = "heroku_zzgm4kgg";
    private static final String DB_USER = "Svyat";
    private static final String DB_PASSWORD = "qwe232390";

    private static final String DB_URL = "mongodb://" + DB_USER + ":" + DB_PASSWORD + "@" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    private MongoCollection<Document> mUsersCollection;
    private MongoCollection<Document> mTestsCollection;

    private DataBaseManager() {
        MongoClientURI clientURI = new MongoClientURI(DB_URL);
        MongoClient client = new MongoClient(clientURI);
        MongoDatabase db = client.getDatabase(DB_NAME);
        mUsersCollection = db.getCollection("users");
        mTestsCollection = db.getCollection("tests");
    }

    public static DataBaseManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataBaseManager();
        }
        return sInstance;
    }

    public MongoCollection<Document> getmUsersCollection() {
        return mUsersCollection;
    }

    public MongoCollection<Document> getmTestsCollection() {
        return mTestsCollection;
    }
}
