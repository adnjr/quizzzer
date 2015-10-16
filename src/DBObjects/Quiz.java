/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBObjects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Armando
 */
public class Quiz {
    /** The collection of QuizProblem objects this Quiz object handles. **/
    private List<QuizProblem> quizProblems;
    /** Keeps track of which problem is currently be displayed (worked). **/
    private int index;
    
    public Quiz(List<Problem> problems) {
        if (problems == null)
            throw new IllegalArgumentException(
                    "Quiz: cannot create new Quiz with null list of problems");
        
        index = 0;
        
        // create QuizProblem objects for this Quiz
        quizProblems = new ArrayList<>();
        for (Problem problem : problems)
            quizProblems.add(new QuizProblem(problem));
    }
    
    /** Information for answerCurrentProblem **/
    public boolean answerCurrentProblem(List<String> userAnswers)
            throws NumberFormatException {
        return getCurrentProblem().setAnswer(userAnswers);
    }
    
    public int getNumberCorrect() {
        Boolean isCorrect;
        int count = 0;
        
        for (QuizProblem qProblem : quizProblems) {
            isCorrect = qProblem.isCorrect();
            if(isCorrect != null && isCorrect)
                count++;
        }
        
        return count;
    }
    
    /** Returns the number of wrong answers for this quiz. The current quiz
     * must have been graded already.
     * @return The number of problems answered incorrectly, or -1 if the quiz
     * has not yet been graded.
     */
    public int getNumberWrong() {
        Boolean isCorrect;
        int count = 0;
        
        for (QuizProblem qProblem : quizProblems) {
            isCorrect = qProblem.isCorrect();
            if(isCorrect != null && !isCorrect)
                count++;
        }
        
        return count;
    }
    
    /** Returns the next QuizProblem, or null if there isn't one
     * @return the next QuizProblem, or null if there isn't one **/
    public QuizProblem getNextProblem() {
        if (index < quizProblems.size() - 1)
            return quizProblems.get(++index);
        
        return null;
    }
    
    /** Returns the previous QuizProblem, or null if there isn't one
     * @return the previous QuizProblem, or null if there isn't one */
    public QuizProblem getPreviousProblem() {
        if (index > 0)
            return quizProblems.get(--index);
        
        return null;
    }
    
    public void resetTraversal() {
        index = 0;
    }
    
    public QuizProblem getCurrentProblem() {
        return quizProblems.get(index);
    }
    
    public int getIndex() {
        return index;
    }
    
    public int getSize() {
        return quizProblems.size();
    }
    
    public List<QuizProblem> getProblems() {
        return quizProblems;
    }
    
    public void clearAnswers() {
        for (QuizProblem problem : quizProblems)
            problem.clearAnswers();
    }
    
}
