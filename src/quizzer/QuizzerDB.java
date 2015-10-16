package quizzer;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Armando
 */
public class QuizzerDB {
    
    private final Database db;
    public static final String COURSE_TITLE = "title";
    public static final String PROBLEM_QUESTION = "question";
    public static final String PROBLEM_TYPE = "problemType";
    public static final String PROBLEM_EXPLANATION = "explanation";
    public static final String PROBLEM_ANSWER = "answer";
    public static final String PROBLEM_TAG = "tag";
    public static final String IMG_ID = "imgid";
    public static final String IMG_CONTENT = "content";
    public static final String IMG_CAPTION = "caption";
    
    public final int T_INT = 0;
    public final int T_DOUBLE = 1;
    public final int T_STRING = 2;
    public final int T_BOOLEAN = 3;
    public final int T_BYTES = 4;
    
    public QuizzerDB(String url, String user, String password) {
        this.db = new Database(url, user, password);
        this.db.openConnection();
    }
    
    public void closeConnection() {
        this.db.closeConnection();
    }
    
    /** Note that this method does not modify problem type of problem. **/
    public boolean addAnswers(String courseTitle, String question
            , Set<String> answers, Boolean isCorrect) {
        boolean success = true;
        
        for (String ans : answers)
            if (!db.query("{call addAnswer(?,?,?,?)}"
                    , courseTitle, question, ans, isCorrect))
                success = false;
        
        return success;
    }
    
    public boolean setAnswer(String courseTitle, String question
            , String oldAnswer, String newAnswer, Integer newProblemType) {
        
        if (!db.query("{call editAnswer(?,?,?,?)}"
                , courseTitle, question, oldAnswer, newAnswer))
            return false;
        
        // update problem type in database if new type specified
        if (newProblemType != null)
            return db.query("{call editProblemType(?,?,?)}"
                    , courseTitle, question, newProblemType);
        
        return true;
    }
    
    public boolean deleteAnswer(String courseTitle, String question, String answer) {
        return db.query("{call deleteAnswer(?,?,?)}"
                , courseTitle, question, answer);
    }
    
    private Set<String> getAnswers(String courseTitle, String question
            , boolean isCorrect) {
        Set<String> answers;
        Map<String,Integer> resultTypes;
        List<Map<String,Object>> qResults;
        String sql;
        
        answers = new HashSet<>();
        resultTypes = new HashMap<>();
        resultTypes.put(PROBLEM_ANSWER, T_STRING);
        sql = "{call get"
                + ((isCorrect) ? "Correct" : "Wrong")
                + "Answers(?,?)}";
        
        qResults = db.getResults_Query(sql
                , resultTypes, courseTitle, question);
        
        for (Map<String,Object> result : qResults)
            answers.add((String)result.get(PROBLEM_ANSWER));
        
        return answers;
    }
    
    public Set<String> getCorrectAnswers(String courseTitle, String question) {
        return getAnswers(courseTitle, question, true);
    }
    
    public Set<String> getWrongAnswers(String courseTitle, String question) {
        return getAnswers(courseTitle, question, false);
    }
    
    public boolean setCaption(int imageID, String newCaption) {
        return db.query("update Images set caption = ? where imgid = ?"
                , newCaption, imageID);
    }
    
    public boolean addCourse(String courseTitle) {
        return db.query("insert into Courses (title) values (?)"
                , courseTitle);
    }
    
    public boolean deleteCourse(String courseTitle) {
        return db.query("delete from Courses where title = ?"
                , courseTitle);
        
    }
    
    public List<Map<String,Object>> getCourses() {
        Map<String,Integer> resultTypes;
        
        resultTypes = new HashMap<>();
        resultTypes.put(COURSE_TITLE, T_STRING);
        
        return db.getResults_Query("select * from courses"
                , resultTypes, (Object) null);
    }
    
    public boolean setCourseTitle(String oldTitle, String newTitle) {
        return db.query("update Courses set title = ? where title = ?"
                , oldTitle, newTitle);
    }
    
    public boolean setExplanation(String courseTitle, String question, String newExplanation) {
        return db.query("{call addExplanation(?,?,?)}"
                , courseTitle, question, newExplanation);
    }
    
    public int addImage(File imageFile, String caption) {
        return db.getGeneratedKey_Query(
                "insert into Images (content, caption) values (?,?)"
                , imageFile, caption);
    }
    
    public boolean deleteImage(int imageID) {
        return db.query("delete from Images where imgid = ?"
                , imageID);
    }
    
    public List<Map<String,Object>> getImages() {
        Map<String,Integer> resultTypes;
        
        resultTypes = new HashMap<>();
        resultTypes.put(IMG_ID, T_INT);
        resultTypes.put(IMG_CAPTION, T_STRING);
        resultTypes.put(IMG_CONTENT, T_BYTES);
        
        return db.getResults_Query("select * from Images"
                , resultTypes, (Object) null);
    }
    
    public Set<Integer> getImageIDs(String courseTitle, String question) {
        List<Map<String,Object>> results;
        Map<String,Integer> resultTypes;
        Set<Integer> imageIDs;
        
        resultTypes = new HashMap<>();
        resultTypes.put(IMG_ID, T_INT);
        results = db.getResults_Query("{call getImageIDs(?,?)}"
                , resultTypes, courseTitle, question);
        
        imageIDs = new HashSet<>();
        for (Map<String,Object> result : results)
            imageIDs.add((Integer)result.get(IMG_ID));
        
        return imageIDs;
    }
    
    public boolean removeImageFromProblem(String courseTitle, String question, int imageID) {
        return db.query("{call deleteImage(?,?,?)}"
                , courseTitle, question, imageID);
    }
    
    // TODO allow this to pass along a custom caption for the image
    public boolean associateImagesWithProblem(String courseTitle
            , String question, Set<Integer> imageIDs) {
        boolean success = true;
        
        for (int id : imageIDs) {
            if (!db.query("{call addImageToProblem(?,?,?,?)}"
                    , courseTitle, question, id, null))
                success = false;
        }
        
        return success;
    }
    
    public boolean addProblem(String courseTitle, String question
            , Set<String> answers, Set<String> nonAnswerChoices, Set<String> tags
            , String explanation, Set<Integer> imageIDs, int problemType) {
        
        String firstAnswer;
        Set<String> remainingAnswers;
        boolean success = true;
        
        firstAnswer = answers.iterator().next();
        remainingAnswers = new HashSet<>(answers);
        remainingAnswers.remove(firstAnswer);
        
        // add minimum problem info to database
        if (!db.query("{call addProblem(?,?,?,?,?)}"
                , courseTitle, question, firstAnswer, explanation, problemType))
            return false;
        
        // add remaining answers/choices/tags/image-associations to the database
        if (!addAnswers(courseTitle, question, remainingAnswers, true))
            success = false;
        if (!addAnswers(courseTitle, question, nonAnswerChoices, false))
            success = false;
        if (!addTags(courseTitle, question, tags))
            success = false;
        if (!associateImagesWithProblem(courseTitle, question, imageIDs))
            success = false;
        
        return success;
    }
    
    public boolean deleteProblem(String courseTitle, String question) {
        return db.query("{call deleteProblem(?,?)}"
                , courseTitle, question);
    }
    
    public List<Map<String,Object>> getProblems() {
        Map<String,Integer> resultTypes;
        
        resultTypes = new HashMap<>();
        resultTypes.put(PROBLEM_QUESTION, T_STRING);
        resultTypes.put(PROBLEM_TYPE, T_INT);
        resultTypes.put(PROBLEM_EXPLANATION, T_STRING);
        resultTypes.put(COURSE_TITLE, T_STRING);
        
        return db.getResults_Query("{call getProblems()}"
                , resultTypes, (Object) null);
    }
    
    public boolean setIsCorrect(String courseTitle, String question
            , String answer, boolean newValue) {
        
        return db.query("update Problem_Answers set isCorrect = ? where pid = "
                + "problemExists(?,?) and answer = ?"
                , newValue, courseTitle, question, answer);
    }
    
    public boolean setProblemType(String courseTitle, String question, int newType) {
        return db.query(
                "update Problems set problemType = ? where pid = problemExists(?,?)"
                , newType, courseTitle, question);
    }
    
    public boolean setQuestion(String courseTitle, String oldQuestion, String newQuestion) {
        return db.query("{call editQuestion(?,?,?)}"
                , courseTitle, oldQuestion, newQuestion);
    }
    
    public boolean addTags(String courseTitle, String question, Set<String> tags) {
        boolean success = true;
        
        for (String tag : tags) {
            if (!db.query("{call addTag(?,?,?)}"
                    , courseTitle, question, tag))
                success = false;
        }
        
        return success;
    }
    
    public boolean deleteTags(String courseTitle, String question
            , Set<String> tags) {
        return db.batchQuery("delete from Problem_Tags "
                        + "where pid = problemExists(?,?) and tag = ?"
                , courseTitle, question, tags);
    }
    
    public Set<String> getTags(String courseTitle, String question) {
        List<Map<String,Object>> results;
        Map<String,Integer> resultTypes;
        Set<String> tags;
        
        resultTypes = new HashMap<>();
        resultTypes.put(PROBLEM_TAG, T_STRING);
        
        results = db.getResults_Query("{call getTags(?,?)}"
                , resultTypes, courseTitle, question);
        
        tags = new HashSet<>();
        for (Map<String,Object> result : results)
            tags.add((String)result.get(PROBLEM_TAG));
        
        return tags;
    }
    
}
