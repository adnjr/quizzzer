package DBObjects;


import ErrorService.AbstractErrorReporter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import quizzer.DatabaseInterface;

/**
 *
 * @author Armando
 */
public class Problem extends AbstractErrorReporter implements Comparable<Problem> {
    private Course course;
    private int problemType;
    private String question;
    private Set<Answer> allAnswers;
    private Set<String> tags;
    private String explanation;
    private Set<ImageIconInfo> images;
    
    private static final String LAST_CORRECT_ANSWER = "Since only 1 answer "
            + "remains for the problem, it cannot be deleted.";
    private static final String TAG_EXISTS = "The following tag already exists: ";
    private static final String IMAGE_EXISTS = "The following image already exists: ";
    
    /**
     * Represents a problem. Some parameters are optional (they can be null).
     * @param course The Course object this problem belongs to.
     * @param problemType A constant from DatabaseInterface, representing answer type, including multiple choice.
     * @param question The question this problem asks.
     * @param answers A sorted set of answers. Only multiple choice problems should have size > 1.
     * @param tags <b>Optional</b> A sorted set of tags associated with this problem.
     * @param explanation <b>Optional</b> A string explaining the answer to this problem.
     * @param images <b>Optional</b> A sorted set of DBImage objects, which extend ImageIcon.
     * @param nonAnswerChoices <b>Optional</b> Choices that aren't answers. Single-choice problems ignore this.
     */
    public Problem(Course course, int problemType, String question
            , Set<String> answers, Set<String> tags, String explanation
            , Set<ImageIconInfo> images, Set<String> nonAnswerChoices) {
        
        if (problemType < 0 || problemType > 3)
            throw new IllegalArgumentException("Invalid problem type. Use a constant from DatabaseInterface.");
        if (answers == null || answers.isEmpty())
            throw new IllegalArgumentException("Problem constructor: At least one answer must be given.");
        
        this.course = course;
        this.problemType = problemType;
        this.question = question;
        this.allAnswers = new HashSet<>();
        for (String ans : answers)
            if (!this.allAnswers.add(new Answer(ans, true)))
                reportError("Failed to add answer: " + ans);
        for (String ans : nonAnswerChoices)
            if (!this.allAnswers.add(new Answer(ans, false)))
                reportError("Failed to add answer: " + ans);
        
        this.explanation          =      (explanation == null) ? ""              : explanation;
        this.tags                 =             (tags == null) ? new HashSet<>() : tags;
        this.images               =           (images == null) ? new HashSet<>() : images;
        for (ImageIconInfo img : this.images) {
            if (img.getImageID() < 1)
                throw new IllegalArgumentException("Image ID invalid for " + img.getCaption());
        }
    }
    
    public String getCourseTitle() {
        return this.course.getTitle();
    }
    
    public Set<Answer> getAnswers() {
        return allAnswers;
    }

    /** @return All possible choices for this problem (including non-answer choices) **/
    public Set<String> getAllAnswers() {
        Set<String> answers = new HashSet<>();
        for (Answer ans : allAnswers)
            answers.add(ans.getAnswer());
        return answers;
    }

    /** @return The answer(s) to this problem as a set of Strings, no matter the problem type **/
    public Set<String> getCorrectAnswers() {
        Set<String> correctAnswers = new HashSet<>();
        for (Answer ans : allAnswers)
            if (ans.isCorrect())
                correctAnswers.add(ans.getAnswer());
        return correctAnswers;
    }

    public Set<String> getTags() {
        return this.tags;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public Set<ImageIconInfo> getImages() {
        return this.images;
    }
    
    public int getProblemType() {
        return this.problemType;
    }
    
    public void addIncorrectAnswer(String wrongAnswer) {
        if (wrongAnswer != null && wrongAnswer.trim().length() > 0)
            this.allAnswers.add(new Answer(wrongAnswer, false));
        if (!isMultipleChoice())
            setProblemType(DatabaseInterface.MULTIPLECHOICE_PROBLEM);
    }
    
    /** This should ONLY be used for multiple choice problems.<br>
     * This adds the answer to the problem as both an answer and a choice.
     * @param answer The answer to add as a choice and an answer. **/
    public void addAnswer(String answer){
        
        if (answer != null && answer.trim().length() > 0)
            this.allAnswers.add(new Answer(answer, true));
        else
            System.err.println("Cannot add null or empty answer.");
        
        if (!isMultipleChoice())
            setProblemType(DatabaseInterface.MULTIPLECHOICE_PROBLEM);
    }
    
    public void addTags(Set<String> tags) {
        if (tags != null)
            for (String tag : tags)
                addTag(tag);
        else
            System.err.println("Cannot add empty set of tags to a problem.");
    }
    
    private void addTag(String tag) {
        if (tag != null && !tag.isEmpty()) {
            if (!this.tags.contains(tag))
                this.tags.add(tag);
            else
                reportError(TAG_EXISTS + tag);
        }
    }
    
    public void addImages(Set<ImageIconInfo> images) {
        if (images != null)
            for (ImageIconInfo img : images)
                addImage(img);
    }
    
    public boolean addImage(ImageIconInfo image) {
        if (image == null)
            return false;
        
        if (this.images.add(image)) {
            image.associateProblem(this);
            return true;
        }
        
        reportError(IMAGE_EXISTS + image.getCaption());
        return false;
    }
    
    public void setExplanation(String newExplanation) {
        if (newExplanation != null && newExplanation.length() > 0)
            this.explanation = newExplanation;
    }
    
    public boolean setIsCorrect(String answer, boolean newValue) {
        for (Answer ans : allAnswers)
            if (ans.getAnswer().equals(answer)) {
                ans.setCorrect(newValue);
                return true;
            }
        return false;
    }
    
    public boolean isCorrect(String answer) {
        for (Answer ans : allAnswers)
            if (ans.getAnswer().equals(answer))
                return ans.isCorrect();
        
        reportError("Target answer not found. Unable to determine correctness.");
        return false;
    }
    
    public boolean setAnswer(String newAnswer) {
        if (newAnswer == null)
            return false;
        
        if (this.problemType == DatabaseInterface.MULTIPLECHOICE_PROBLEM) {
             reportError("Method setAnswer(String) should not be called for a multiple choice poblem. Use setAnswer(String,String) instead.");
             return false;
        }
        
        this.allAnswers.clear();
        return this.allAnswers.add(new Answer(newAnswer, true));
    }
    public boolean setAnswer(String oldAnswer, String newAnswer) {
        boolean success = false;
        
        if (newAnswer == null || newAnswer.length() == 0) {
            reportError("The new answer cannot be empty.");
            return false;
        }
        
        if (Objects.equals(oldAnswer, newAnswer)) {
            reportError("Old and new answer are the same. Nothing was changed.");
            return false;
        }
        
        for (Answer ans :allAnswers) {
            if (Objects.equals(ans.getAnswer(), oldAnswer)) {
                ans.setAnswer(newAnswer);
                success = true;
            }
        }
        
        if (!success)
            reportError("The target answer to update was not found.");

        return success;
    }
    
    public void setQuestion(String newQuestion) {
        if (newQuestion != null && newQuestion.length() > 0)
            this.question = newQuestion;
        else
            System.err.println("Cannot set question to a null or empty value.");
    }
    
    public boolean deleteAnswer(String targetAnswer) {
             
        if (targetAnswer == null || targetAnswer.length() == 0) {
            reportError("Cannot delete: no answer was given.");
            return false;
        }
        
        // is it the last correct answer
        if (isCorrect(targetAnswer) && getCorrectAnswers().size() == 1) {
            reportError(LAST_CORRECT_ANSWER);
            return false;
        }
        
        // delete it
        if ( !this.allAnswers.remove(new Answer(targetAnswer, true)) ) {
            reportError("Answer \"" + targetAnswer + "\" not found.");
            return false;
        }
        
        return true;
    }
    
    public boolean deleteTags(Collection<String> targetTags) {
        if (targetTags == null || targetTags.isEmpty())
            return false;
        
        if (!tags.removeAll(targetTags)) {
            reportError("One or more tags failed to delete.");
            return false;
        }
        
        return true;
    }
    
    public void removeCourse() {
        course.deleteProblem(this);
    }
    
    public void removeImage(int imageID) {
        ImageIconInfo targetImage = null;
        
        // find target image
        for (ImageIconInfo icon : images)
            if (icon.getImageID() == imageID)
                targetImage = icon;
        
        // remove association between image and problem
        if (targetImage != null) {
            if (images.remove(targetImage))             // from problem
                targetImage.disassociateProblem(this);  // from image
        }
    }
    
    public void removeImage(ImageIconInfo targetIcon) {
        images.remove(targetIcon);
    }

    public String getQuestion() {
        return this.question;
    }
    
    public void setProblemType(int newType) {
        if (newType >= 0 && newType <= 3)
            this.problemType = newType;
        else
            reportError("Problem type \"" + newType + "\" invalid.");
    }
    
    public boolean isMultipleChoice() {
        return allAnswers.size() > 1;
    }
    
    public boolean isTextProblem() {
        if (isMultipleChoice())
            return false;
        return problemType == DatabaseInterface.TEXT_PROBLEM;
    }
    
    public boolean isIntegerProblem() {
        if (isMultipleChoice())
            return false;
        return problemType == DatabaseInterface.INTEGER_PROBLEM;
    }
    
    public boolean isFloatProblem() {
        if (isMultipleChoice())
            return false;
        return problemType == DatabaseInterface.FLOAT_PROBLEM;
    }
    
    public static boolean problemTypesEqual(String answer1, String answer2) {
        int oldType, newType;
        
        oldType = determineProblemType(Collections.singleton(answer1));
        newType = determineProblemType(Collections.singleton(answer2));
        
        return oldType == newType;
    }
    
    public static int determineProblemType(Set<String> answers) {
        return determineProblemType(answers, null);
    }
    
    public static int determineProblemType(Set<String> answers
            , Set<String> nonAnswerChoices) {
        int problemType;
        
        if (answers.size() > 1
                || (nonAnswerChoices != null && !nonAnswerChoices.isEmpty()))
            problemType = DatabaseInterface.MULTIPLECHOICE_PROBLEM;
        else if (isInteger(answers.iterator().next()))
            problemType = DatabaseInterface.INTEGER_PROBLEM;
        else if (isFloat(answers.iterator().next()))
            problemType = DatabaseInterface.FLOAT_PROBLEM;
        else
            problemType = DatabaseInterface.TEXT_PROBLEM;
        
        return problemType;
    }
    
    private static boolean isInteger(String str) {
        if (str.indexOf('.') != -1)
            return false;
        try { Integer.parseInt(str); }
        catch (NumberFormatException e) { return false; }
        
        return true;
    }
    
    private static boolean isFloat(String str) {
        if (str.indexOf('.') == -1)
            return false;
        try { Double.parseDouble(str); }
        catch (NumberFormatException e) { return false; }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Problem other = (Problem) obj;
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        
        return Objects.equals(this.question, other.question);
    }

    @Override
    public int compareTo(Problem o) {
        if (o == null)
            return 1;
        return this.question.compareTo(o.question);
    }
    
    
}
