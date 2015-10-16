/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBObjects;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import quizzer.DatabaseInterface;

/**
 *
 * @author Armando
 */
public class Course implements Comparable<Course> {
    //private SortedSet<String> questions;
    private final SortedSet<Problem> problems;
    private String title;
    
    public Course(String title) {
        this.title = title;
        this.problems = new TreeSet<>();
    }
    
    public SortedSet<String> getQuestions() {
        SortedSet<String> questions = new TreeSet<>();
        
        for (Problem problem : this.problems)
            questions.add(problem.getQuestion());
        
        return questions;
    }
    
    public Set<String> getAnswers(String targetQuestion) {
        Problem problem;
        if ( (problem = getProblem(targetQuestion)) == null)
            throw new Error("Target problem not found. The database and local data seem to be out of sync.");
        
        return problem.getCorrectAnswers();
    }
    
    public Set<String> getAllAnswers(String targetQuestion) {
        Problem problem;
        if ( (problem = getProblem(targetQuestion)) == null)
            throw new Error("Target problem not found. The database and local data seem to be out of sync.");
        
        return problem.getAllAnswers();
    }
    
     public String getExplanation(String targetQuestion) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         return problem.getExplanation();
     }
     
     public int getProblemType(String targetQuestion) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         return problem.getProblemType();
     }
     
     public Set<String> getTags(String targetQuestion) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         return problem.getTags();
     }
     
     public Set<ImageIconInfo> getImages(String targetQuestion) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         return problem.getImages();
     }
     
     public void deleteImage(String targetQuestion, int imageID) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         problem.removeImage(imageID);
     }
     
//     public void deleteImage(ImageIconInfo targetIcon) {
//         for (Problem problem : problems)
//             problem.removeImage(targetIcon);
//     }
     
     public void addAnswer(String targetQuestion, String answer) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         if (problem.getProblemType() != DatabaseInterface.MULTIPLECHOICE_PROBLEM)
             throw new IllegalArgumentException("Course.addAnswer: The specified problem is not multiple choice.");
         
         problem.addAnswer(answer);
     }
     
     public void addChoice(String targetQuestion, String choice) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         if (problem.getProblemType() != DatabaseInterface.MULTIPLECHOICE_PROBLEM)
             throw new IllegalArgumentException("Course.addChoice: The specified problem is not multiple choice.");
         
         problem.addIncorrectAnswer(choice);
     }
     
     public void addTag(String targetQuestion, String tag) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         problem.addTags(Collections.singleton(tag));
     }
     
     public void addImage(String targetQuestion, ImageIconInfo image) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         problem.addImage(image);
     }
     
    public void addImages(String targetQuestion, Set<ImageIconInfo> images) {
        if (images != null)
            for (ImageIconInfo img : images)
                addImage(targetQuestion, img);
     }
    
     public void setExplanation(String targetQuestion, String explanation) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         problem.setExplanation(explanation);
     }
     
//     public void addProblem(int problemType, String question, SortedSet<Object> answers
//             , SortedSet<String> tags, String explanation, SortedSet<String> choices) {
//         
//         problems.add(new Problem(this, problemType, question, answers, tags, explanation, choices));
//         
//     }
     
     public void setAnswer(String targetQuestion, String newAnswer) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         problem.setAnswer(newAnswer);
     }
     public void setAnswer(String targetQuestion, String oldAnswer, String newAnswer) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         problem.setAnswer(oldAnswer, newAnswer);
     }
     
     public void setQuestion(String targetQuestion, String newQuestion) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         problem.setQuestion(newQuestion);
     }
     
     public void setTitle(String newTitle) {
         if (newTitle != null && newTitle.length() > 0)
             this.title = newTitle;
         else
             System.err.println("Cannot change title to an empty value.");
     }
     
     public boolean addProblem(Problem problem) {
         if (problem != null)
             return problems.add(problem);
         System.err.println("Cannot add null problem to course: " + title);
         return false;
     }
     
     public void addProblem(int problemType, String question, Set<String> answers
             , SortedSet<String> tags, String explanation, Set<ImageIconInfo> images, Set<String> choices) {
         
         this.addProblem(new Problem(this, problemType, question, answers, tags, explanation, images, choices));
     }
     
     public void deleteAnswer(String targetQuestion, String targetAnswer) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         problem.deleteAnswer(targetAnswer);
     }
     
     public boolean deleteProblem(Problem targetProblem) {
         return problems.remove(targetProblem);
     }
     
     public void deleteTags(String targetQuestion, Collection<String> targetTags) {
         Problem problem;
         if ( (problem = getProblem(targetQuestion)) == null)
             throw new Error("Target problem not found. The database and local data seem to be out of sync.");
         
         problem.deleteTags(targetTags);
     }
     
    /** @return The problem object that matches the target question, or null if not found. **/
    public Problem getProblem(String targetQuestion) {
        for (Problem problem : this.problems)
            if (problem.getQuestion().equals(targetQuestion))
                return problem;
        
        return null;
    }
    
    public Set<Problem> getProblems() {
        return problems;
    }
    
    public String getTitle() {
        return this.title;
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
        
        final Course other = (Course) obj;
        return this.title.equalsIgnoreCase(other.title);
    }

    @Override
    public int compareTo(Course o) {
        if (o == null)
            return 1;
        return this.title.compareTo(o.title);
    }
}
