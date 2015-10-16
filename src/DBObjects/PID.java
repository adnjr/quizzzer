package DBObjects;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Armando
 */
public class PID {
    private String course;
    private String question;
    
    public PID(String courseTitle, String question) {
        this.course = courseTitle;
        this.question = question;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    
    public void setPID(String courseTitle, String question) {
        this.course = courseTitle;
        this.question = question;
    }
    
//    public Map<String,String> getPID() {
//        Map<String,String> questionToCourse = new HashMap<>();
//        questionToCourse.put(question, course);
//        return questionToCourse;
//    }
    
    
}
