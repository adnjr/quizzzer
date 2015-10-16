/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quizzer;

import DBObjects.Problem;
import DBObjects.ImageInfo;
import DBObjects.ImageIconInfo;
import ErrorService.ErrorListener;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 *
 * @author Armando
 */
public interface DatabaseInterface {
    
    public static final int INTEGER_PROBLEM = 0;
    public static final int FLOAT_PROBLEM = 1;
    public static final int TEXT_PROBLEM = 2;
    public static final int MULTIPLECHOICE_PROBLEM = 3;
    
    /** Loads all data from the database into memory. **/
    public void initData();
    
    public void registerErrorListener(ErrorListener notifyThis);
    
    /** Returns the errors reported for the last database operation. Returns empty list if no errors. **/
    public List<String> getErrors();
    
    /** @return True if connection to the database was opened successfully, false otherwise **/
    public boolean openConnection();
    
    /** closes the connection to the database */
    public void closeConnection();
    
    /** adds a course with the given title
     * @param courseTitle Title of the course to add. It must be unique.
     * @return True if the course was added successfully, false otherwise **/
//    public boolean addCourse(String courseTitle);
    
    /** Do <b>NOT</b> call this method for multiple choice problems. Use addMultipleChoiceProblem instead.<br>
     * This adds a problem to both the database and memory.
     * 
     * @param courseTitle The name of the course to which this problem belongs.
     * @param question The question this problem asks.
     * @param answer The answer to the question.
     * @param explanation An optional explanation to the problem.
     * @param imageFileInfo An optional SortedSet of objects, which can either be ImageFileData objects (stores
     *        info on a new image), or Integer objects that correspond to the image IDs of existing images.
     * @return True if the problem was added successfully, false otherwise.
     */
    public boolean addProblem(String courseTitle, String question, String answer
            , String explanation, Set<? extends ImageInfo> imageFileInfo);
    
    
    /** This adds a problem to both the database and memory.
     * 
     * @param courseTitle The name of the course to which this problem belongs.
     * @param question The question this problem asks.
     * @param answers A sorted set of answers to the question.
     * @param nonAnswerChoices A sorted set of choices that aren't answers. Use null if all choices are answers.
     * @param explanation An optional explanation to the problem.
     * @param imageFileInfo An optional SortedSet of objects, which can either be ImageFileData objects (stores
     *        info on a new image), or Integer objects that correspond to the image IDs of existing images.
     * @return True if the problem was added successfully, false otherwise.
     */
    public boolean addMultipleChoiceProblem(String courseTitle, String question
            , SortedSet<String> answers, SortedSet<String> nonAnswerChoices, String explanation
            , SortedSet<? extends ImageInfo> imageFileInfo);
            
    public boolean deleteCourse(String courseTitle);
    public boolean deleteProblem(String courseTitle, String question);
    public boolean deleteTags(String courseTitle, String question, Collection<String> tags);
    
    /** @return SortedSet of String objects, each corresponding to a course in the database **/
//    public SortedSet<String> getCourses();
    
    /** Finds the course with <code>oldTitle</code> and changes its title to
     * <code>newTitle</code> in both the database and memory.
     * @param oldTitle The title of the target course.
     * @param newTitle The new title.
     * @return <code>true</code> on success, or <code>false</code> on failure.
     */
    public boolean setCourseTitle(String oldTitle, String newTitle);
    
    /** @param courseTitle The course to look into for questions
     * @return SortedSet of String objects, each corresponding to a question for the given course **/
    public SortedSet<String> getQuestions(String courseTitle);
    
    /** Adds the specified answer as both an answer and a choice to a multiple choice problem.
     * If you want to edit an answer to a non-multiple-choice problem, use
     * setAnswer(courseTitle, question, answer) instead.
     * @param courseTitle The title of the course the target problem belongs to.
     * @param question The question of the target problem.
     * @param answer The answer to add. It must already be a choice of the problem.
     * @return True on success, false on failure. **/
    public boolean addAnswer(String courseTitle, String question, String answer);
    
    /** Makes an existing choice of a multiple choice problem an answer. If the answer is
     * not already a choice for the problem, this will fail.
     * @param courseTitle The title of the course the target problem belongs to.
     * @param question The question of the target problem.
     * @param answer The answer to add. It must already be a choice of the problem.
     * @return True on success, false on failure. **/
//    public boolean makeAnswer(String courseTitle, String question, String answer);
    
    /**@param courseTitle the title of the course in which to search for the question
     * @param question the question with the answer we are looking for
     * @return returns the answer as sorted set of strings, or null if the specified question doesn't exist **/
    public SortedSet<String> getAnswers(String courseTitle, String question);
    
    public boolean addChoice(String courseTitle, String question, String choice);
    
    public SortedSet<String> getChoices(String courseTitle, String question);
    
    /** returns a constant from DatabaseInterface indicating whether the problem has an answer of type
     * integer, float, text, or multiple choice **/
    public int getProblemType(String courseTitle, String question);
    
    /**@param courseTitle The course to look in
     * @param question The question whose answer is needed
     * @return A list of strings, each corresponding to a tag for the given question in the given course **/
    public SortedSet<String> getTags(String courseTitle, String question);
    
    public List<String> getTags();
    
    public String getExplanation(String courseTitle, String question);
    
//    public SortedSet<ImageIconInfo> getImages();
    
    public SortedSet<ImageIconInfo> getImages(String courseTitle, String question);

    public Problem getProblem(String courseTitle, String question);
    
    public List<Problem> getProblems(String courseTitle);
    
    public SortedSet<Problem> getProblemsByTags(Collection<String> tags);
    
    /** Adds the choice to the choices table in the database. If the question does not belong to a
     * multiple choice problem, or if the choice already exists for the problem the addition will fail.
     * @return True on success, or false on failure. **/
    public boolean addChoiceToDatabase(String courseTitle, String question, String choice);

    public boolean addTag(String courseTitle, String problem, String tag);
    
    /** Adds image to database and memory. Returns image ID on success (1 or greater), or 0 on failure. **/
//    public int addImage(ImageFileInfo imagFileData);
    
    /** Adds existing image with specified ID to the specified course in database and memory.
      * @return true on success, false on failure.**/
    public boolean addImageToCourse(String courseTitle, String question, int imageID);
    
    /** This can only be invoked on multiple choice problems.
     * @param courseTitle The title of the course the question belongs to.
     * @param question The question the answer belongs to.
     * @param answer The answer to delete.
     * @return True if deletion successful, false otherwise.
     * @throws IllegalArgumentException Thrown when this is called on a non-multiple choice problem,
     * or when trying to delete the last answer to a question. **/
    public boolean deleteAnswer(String courseTitle, String question, String answer);
    
    /** This can only be invoked on multiple choice problems.
     * @param courseTitle The title of the course the question belongs to.
     * @param question The question the choice belongs to.
     * @param choice The choice to delete.
     * @return True if deletion successful, false otherwise.
     * @throws IllegalArgumentException Thrown when this is called on a non-multiple choice problem,
     * or when trying to delete a choice that is also the last answer. **/
    public boolean deleteChoice(String courseTitle, String question, String choice);
    
    public boolean deleteImage(int imageID);
    public boolean deleteImageFromProblem(String courseTitle, String question, int imageID);

    public boolean changeCaption(int imageID, String newCaption);
    public boolean changeExplanation(String courseTitle, String question, String newExplanation);
    public boolean changeQuestion(String courseTitle, String oldQuestion, String newQuestion);
    public boolean changeIntegerAnswer(String courseTitle, String question, int newAnswer);
    public boolean changeFloatAnswer(String courseTitle, String question, double newAnswer);
    public boolean changeTextAnswer(String courseTitle, String question, String newAnswer);
    public boolean changeMultipleChoiceAnswer(String courseTitle, String question, String oldAnswer, String newAnswer);
    
    /** Returns the number of problems associated with the image with the specified ID
     * @param imageID The ID of the target image.
     * @return The number of problems associated with the target image. **/
    public int getNumAssociated(int imageID);
    
    public boolean parameterizedQuery(String sql, Object... parameters);
    
    public int getGeneratedKey();
    
}
