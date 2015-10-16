/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBObjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import quizzer.DatabaseInterface;

/**
 *
 * @author Armando
 */
public class QuizProblem {
    private final Problem problem;
    private List<String> userAnswers;
    private final List<String> errors;
    private final int intSensitivity = Settings.SENSITIVITY_INT;
    private final double floatSensitivity = Settings.SENSITIVITY_FLOAT;
    private int wrongChoices;
    
    public QuizProblem(Problem problem) {
        this.problem = problem;
        this.userAnswers = new ArrayList<>();
        this.errors = new ArrayList<>();
        this.wrongChoices = 0;
    }
    
    public boolean isRadioButtonProblem() {
        return isMultipleChoice() && getCorrectAnswers().size() == 1;
    }
    
    public boolean isCheckBoxProblem() {
        return isMultipleChoice() && getCorrectAnswers().size() > 1;
    }
    
    public boolean hasUserAnswer() {
        return ! this.userAnswers.isEmpty();
    }
    
    public Boolean isCorrect() {
        if (problem.getProblemType() == DatabaseInterface.TEXT_PROBLEM)
            return null;
        
        if (this.hasUserAnswer())
            return isCorrect(userAnswers);
        
        return false;
    }
    
    private boolean isCorrect(List<String> userAnswers) throws NumberFormatException {
        if (problem.getProblemType() == DatabaseInterface.INTEGER_PROBLEM)
            return gradeIntegerProblem(userAnswers);
        else if (problem.getProblemType() == DatabaseInterface.FLOAT_PROBLEM)
            return gradeFloatProblem(userAnswers);
        else if (problem.getProblemType() == DatabaseInterface.TEXT_PROBLEM)
            return gradeTextProblem(userAnswers);
        else if (problem.getProblemType() == DatabaseInterface.MULTIPLECHOICE_PROBLEM)
            return (wrongChoices = gradeMultipleChoiceProblem(userAnswers)) == 0;
        else
            throw new IllegalArgumentException("QuizProblem.isCorrect: invalid problem type");
    }
    
    public int getNumberWrong() {
        return wrongChoices;
    }
    
    /** 
     * Performs input validation, and stores the answers if valid.
     * @param userAnswers A List of user-inputted answers to this quiz problem.
     * @return True if the user answer was correct, false otherwise.
     * @throws NumberFormatException If input is invalid, throws exception.
     */
    public boolean setAnswer(List<String> userAnswers) throws NumberFormatException {
        boolean result;
        
        if (userAnswers == null)
            return false;
        
        result = isCorrect(userAnswers);       // also does input validation
        this.userAnswers = userAnswers;        // input valid, so store it
        return result;
    }
    
    private boolean gradeIntegerProblem(List<String> userAnswers) throws NumberFormatException {
        if (userAnswers.isEmpty())
            throw new NoAnswerException("QuizProblem: gradeIntegerProblem: the user has not yet entered an answer");
        
        int correctAnswer = Integer.parseInt(problem.getCorrectAnswers().iterator().next());
        int userAnswer = Integer.parseInt(userAnswers.get(0));
        
        if (correctAnswer > userAnswer)
            return (correctAnswer - userAnswer) <= intSensitivity;
        else if (userAnswer > correctAnswer)
            return (userAnswer - correctAnswer) <= intSensitivity;
        else if (userAnswer == correctAnswer)
            return true;
        
        throw new IllegalArgumentException("QuizProblem.gradeIntegerProblem: this line should never be reached.");
    }
    
    private boolean gradeFloatProblem(List<String> userAnswers) {
        if (userAnswers.isEmpty())
            throw new IllegalArgumentException("QuizProblem: gradeFloatProblem: the user has not yet entered an answer");
        
        double correctAnswer = Double.parseDouble(problem.getCorrectAnswers().iterator().next());
        double userAnswer = Double.parseDouble(userAnswers.get(0));
        
        if (correctAnswer > userAnswer)
            return (correctAnswer - userAnswer) <= floatSensitivity;
        else if (userAnswer > correctAnswer)
            return (userAnswer - correctAnswer) <= floatSensitivity;
        else if (userAnswer == correctAnswer)
            return true;
        
        throw new IllegalArgumentException("QuizProblem.gradeFloatProblem: this line should never be reached.");
    }
    
    // perhaps use key words or something
    private boolean gradeTextProblem(List<String> userAnswers) {
        return false;
    }
    
    /** Returns the number of incorrect answers **/
    private int gradeMultipleChoiceProblem(List<String> userAnswers) {
        if (userAnswers.isEmpty())
            throw new NoAnswerException("QuizProblem: gradeMultipleChoiceProblem: you must select at least one answer.");
        
        int numWrong = 0;
        
        for (String answer : userAnswers)
            if (! problem.getCorrectAnswers().contains(answer))
                numWrong++;
        
        for (String answer : problem.getCorrectAnswers())
            if (! userAnswers.contains(answer))
                numWrong++;
        
        return numWrong;
    }
    
    public List<String> getUserAnswers() {
        return userAnswers;
    }
    
    public String getQuestion() {
        return problem.getQuestion();
    }
    
    public String getExplanation() {
        return problem.getExplanation();
    }
    
    /** @return The correct answer(s) to this problem as Strings, no matter the problem type **/
    public Set<String> getCorrectAnswers() {
        return problem.getCorrectAnswers();
    }
    
    /** @return All possible choices for this problem (including non-answer choices) **/
    public Collection<String> getChoices() {
        return problem.getAllAnswers();
    }
    
    public Set<ImageIconInfo> getImages() {
        return problem.getImages();
    }
    
//    public int getProblemType() {
//        return problem.getProblemType();
//    }
    public boolean isMultipleChoice() {
        return problem.isMultipleChoice();
    }
    
    public boolean isTextProblem() {
        return problem.isTextProblem();
    }
    
    public boolean isIntegerProblem() {
        return problem.isIntegerProblem();
    }
    
    public boolean isFloatProblem() {
        return problem.isFloatProblem();
    }
    
    public void clearAnswers() {
        userAnswers.clear();
    }
    
}
