package svyat.com;

import java.util.ArrayList;

/**
 * Created by sviatosss on 18.12.2018.
 */
public class Test {
    public int id;
    public String question;
    public ArrayList<Answer> answers;
    public String imgUrl;

    public Test(int id, String question, ArrayList<Answer> answers, String imgUrl) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.imgUrl = imgUrl;
    }

    public Test(int id, String question, ArrayList<Answer> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }

    public Test(){}
}
