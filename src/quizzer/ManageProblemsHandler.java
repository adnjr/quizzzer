package quizzer;

import DBObjects.Answer;
import DBObjects.Course;
import ErrorService.AbstractErrorReporter;
import DBObjects.ImageIconInfo;
import DBObjects.ImageInfo;
import DBObjects.Problem;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Armando
 */
public class ManageProblemsHandler extends AbstractErrorReporter {
    private Set<Problem> problems;
    private ManageImagesHandler imagesHandler;
    private ManageCoursesHandler coursesHandler;
    private final QuizzerDB qDB;
    private final String COURSE_NOT_FOUND = "Course not found in memory.";
    private final String IMAGES_NOT_FOUND = "Images not found in memory.";
    private final String IMAGES_OR_COURSE_NOT_FOUND = "Images or course not found in memory.";
    
    public ManageProblemsHandler(QuizzerDB db) {
        this.problems = null;
        this.qDB = db;
    }
    
    public void initProblems() {
        String courseTitle;
        String question;
        String explanation;
        Integer problemType;
        Set<ImageIconInfo> images;
        Set<Integer> imageIDs;
        Set<String> tags;
        Set<String> correctAnswers;
        Set<String> wrongAnswers;
        Course course;
        Problem problem;
        
        imagesHandler = HandlerFactory.getHandlerFactory().getImagesHandler();
        coursesHandler = HandlerFactory.getHandlerFactory().getCoursesHandler();
        problems = new HashSet<>();
        
        for (Map<String,Object> result : qDB.getProblems()) {
            
            // collect basic problem data
            courseTitle = (String)result.get(QuizzerDB.COURSE_TITLE);
            question = (String)result.get(QuizzerDB.PROBLEM_QUESTION);
            explanation = (String)result.get(QuizzerDB.PROBLEM_EXPLANATION);
            problemType = (Integer)result.get(QuizzerDB.PROBLEM_TYPE);
            
            // collect remaining problem data
            course = coursesHandler.getCourse(courseTitle);
            imageIDs = qDB.getImageIDs(courseTitle, question);
            images = imagesHandler.getImages(imageIDs);
            tags = qDB.getTags(courseTitle, question);
            correctAnswers = qDB.getCorrectAnswers(courseTitle, question);
            wrongAnswers = qDB.getWrongAnswers(courseTitle, question);
            
            // load problem and create associations with course/image(s)
            problem = new Problem(course, problemType, question, correctAnswers
                    , tags, explanation, images, wrongAnswers);
            coursesHandler.associateProblemWithCourse(courseTitle, problem);
            imagesHandler.associateProblemWithImages(imageIDs, problem);
            problems.add(problem);
        }
    }
    
    public boolean addProblem(String courseTitle, String question
            , Set<String> answers, Set<String> nonAnswerChoices, Set<String> tags
            , String explanation, Set<? extends ImageInfo> imageInfos) {
        
        Problem problem;
        Course course;
        Set<Integer> imageIDs;
        Set<ImageIconInfo> imageIcons;
        int problemType;
        
        // does course exist
        if ((course = coursesHandler.getCourse(courseTitle)) == null)
            return false;
        
        // make sure at least one answer was chosen
        if (answers.isEmpty()) {
            reportError("At least one answer must be chosen before the problem can be added");
            return false;
        }
        
        // make sure image info is valid, add any new images to database/memory
        imageIDs = imagesHandler.addImages(imageInfos);
        imageIcons = imagesHandler.getImages(imageIDs);
        
        // add problem to the database
        problemType = Problem.determineProblemType(answers, nonAnswerChoices);
        if (!qDB.addProblem(courseTitle, question, answers, nonAnswerChoices
                , tags, explanation, imageIDs, problemType))
            return false;
        
        // add the problem to memory
        problem = new Problem(course, problemType, question
                , answers, tags, explanation, imageIcons, nonAnswerChoices);
        coursesHandler.associateProblemWithCourse(courseTitle, problem);
        imagesHandler.associateProblemWithImages(imageIDs, problem);
        problems.add(problem);
        
        return true;
    }
    
    public boolean deleteProblem(String courseTitle, String question) {
        Problem problem;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return false;
        
        // delete from database
        if (!qDB.deleteProblem(courseTitle, question))
            return false;
        
        // delete from memory
        problem.removeCourse();          // from course
        return problems.remove(problem); // from handler
    }
    
    public boolean addAnswer(String courseTitle, String question
            , String answer, Boolean isCorrect) {
        Problem problem;
        int pType;
        
        if (answer.trim().isEmpty())
            return false;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return false;
        
        // add to database
        if (!qDB.addAnswers(courseTitle, question, Collections.singleton(answer), isCorrect))
            return false;
        
        // add to memory
        if (isCorrect)
            problem.addAnswer(answer);
        problem.addIncorrectAnswer(answer);
        
        // does problem type need to be changed?
        pType = Problem.determineProblemType(getAllAnswers(courseTitle, question));
        if (pType != problem.getProblemType()) {
            if (qDB.setProblemType(problem.getCourseTitle(), problem.getQuestion(), pType))
                problem.setProblemType(pType);
            else
                reportError("Answer added, but problem type could not be updated.");
        }
        
        return true;
    }
    
    public boolean deleteAnswers(String courseTitle, String question
            , Set<String> answers) {
        boolean success = true;
        
        if (answers != null) {
            for (String answer : answers)
                if (!deleteAnswer(courseTitle, question, answer))
                    success = false;
        }
        else {
            System.err.println("Cannot delete an empty set of target answers.");
            success = false;
        }
        
        return success;
    }
    
    private boolean deleteAnswer(String courseTitle, String question, String answer) {
        Problem problem;
        int pType;
        
        // does target problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return false;
        
        // delete from database
        if (!qDB.deleteAnswer(courseTitle, question, answer))
            return false;
        
        // delete from memory
        problem.deleteAnswer(answer);
        
        // does problem type need to be changed?
        pType = Problem.determineProblemType(getAllAnswers(courseTitle, question));
        if (pType != problem.getProblemType()) {
            if (qDB.setProblemType(problem.getCourseTitle(), problem.getQuestion(), pType))
                problem.setProblemType(pType);
            else
                reportError("Answer added, but problem type could not be updated.");
        }
        
        return true;
    }
    
    public Set<String> getCorrectAnswers(String courseTitle, String question) {
        Problem problem;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return null;
        
        return problem.getCorrectAnswers();
    }
    
    public Set<String> getAllAnswers(String courseTitle, String question) {
        Problem problem;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return null;
        
        return problem.getAllAnswers();
    }
    
    public Set<Answer> getAnswers(String courseTitle, String question) {
        Problem problem;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return null;
        
        return problem.getAnswers();
    }
    
    public boolean removeImageFromProblem(String courseTitle, String question
            , int imageID) {
        Problem problem;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return false;
        
        // remove in database
        if (!qDB.removeImageFromProblem(courseTitle, question, imageID))
            return false;
        
        // remove in memory
        problem.removeImage(imageID);
        return true;
    }
    
    public boolean addTags(String courseTitle, String question, Set<String> tags) {
        Problem problem;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return false;
        
        // add tag to database
        if (!qDB.addTags(courseTitle, question, tags))
            return false;
        
        // add to memory
        problem.addTags(tags);
        return true;
    }
    
    public boolean deleteTags(String courseTitle, String question
            , Set<String> tags) {
        Problem problem;
        
        // does probem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return false;
        
        System.err.println(courseTitle + ": " + question);
        for (String tag : tags)
            System.err.println("\t" + tag);
        
        // delete from database
        if (!qDB.deleteTags(courseTitle, question, tags))
            return false;
        
        // delete from memory
        return problem.deleteTags(tags);
    }
    
    public Set<String> getTags(String courseTitle, String question) {
        Problem problem;
        
        // does probem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return null;
        
        return problem.getTags();
    }
    
    public Set<String> getTags() {
        Set<String> tags = new HashSet<>();
        for (Problem prob : problems)
            tags.addAll(prob.getTags());
        return tags;
    }
    
    // TODO change this to private after the UI has been overhauled
    public Problem getProblem(String courseTitle, String question) {
        if (courseTitle == null || question == null)
            return null;
        
        for (Problem problem : problems)
            if (problem.getCourseTitle().equals(courseTitle)
                    && problem.getQuestion().equals(question))
                return problem;
        
        reportError("Problem from course \"" + courseTitle + "\" with question \""
                + question + "\" not found.");
        return null;
    }
    
    /** Returns a mapping of questions to course titles for problems with any
     * of the specified tags.
     * @param tags Tags to search for problems by.
     * @return A map of questions to course titles. The map will be empty if
     *         no matching problems were found.
     */
    public Map<String,String> getProblemsByTags(Set<String> tags) {
        Map<String,String> result = new HashMap<>();
        
        for (String tag : tags)
            for (Problem prob : problems)
                if (prob.getTags().contains(tag))
                    result.put(prob.getQuestion(), prob.getCourseTitle());
        
        return result;
    }
    
    public int getProblemType(String courseTitle, String question) {
        Problem problem;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return -1;
        
        return problem.getProblemType();
    }
    
    public boolean setExplanation(String courseTitle, String question
            , String newExplanation) {
        Problem problem;
        
        if (newExplanation == null || newExplanation.trim().isEmpty())
            return false;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return false;
        
        // update database
        if (!qDB.setExplanation(courseTitle, question, newExplanation))
            return false;
        
        // update data in memory
        problem.setExplanation(newExplanation);
        return true;
    }
    
    public String getExplanation(String courseTitle, String question) {
        Problem problem;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return null;
        
        return problem.getExplanation();
    }
    
    public boolean toggleIsCorrect(String courseTitle, String question, String answer) {
        Problem problem;
        boolean oldValue;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return false;
        
        // is target answer the last correct answer
        if (problem.getCorrectAnswers().size() == 1 && problem.getCorrectAnswers().contains(answer)) {
            reportError("Cannot mark the last correct answer wrong. At least one correct answer must remain.");
            return false;
        }
        oldValue = problem.isCorrect(answer);
        
        // toggle in database
        qDB.setIsCorrect(courseTitle, question, answer, !oldValue);
        
        // toggle in memory
        return problem.setIsCorrect(answer, !oldValue);
    }
    
    public boolean setAnswer(String courseTitle, String question
            , String oldAnswer, String newAnswer) {
        
        Problem problem;
        Integer newType = null;
        
        if (newAnswer == null || newAnswer.trim().isEmpty())
            return false;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return false;
        
        // does problem type need updating
        if (!problem.isMultipleChoice()
                && !Problem.problemTypesEqual(oldAnswer, newAnswer))
            newType = Problem.determineProblemType(Collections.singleton(newAnswer));
        
        // update answer in database
        if (!qDB.setAnswer(courseTitle, question, oldAnswer, newAnswer, newType))
            return false;
        
        // update answer/problem in memory
        problem.setAnswer(oldAnswer, newAnswer);
        if (newType != null)
            problem.setProblemType(newType);
        
        return true;
    }
    
    public boolean setQuestion(String courseTitle, String oldQuestion
            , String newQuestion) {
        Problem problem;
        
        if (newQuestion == null || newQuestion.equals(oldQuestion))
            return false;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, oldQuestion)) == null)
            return false;
        
        // update question in database
        if (!qDB.setQuestion(courseTitle, oldQuestion, newQuestion))
            return false;
        
        // update question in memory
        problem.setQuestion(newQuestion);
        return true;
    }
    
    public boolean associateImagesWithProblem(String courseTitle, String question
            , Set<Integer> imageIDs) {
        Problem problem;
        
        // was at least one image given
        if (imageIDs == null || imageIDs.isEmpty()) {
            System.err.println("Image ID list is null or empty.");
            return false;
        }
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return false;
        
        // update the database
        if (!qDB.associateImagesWithProblem(courseTitle, question, imageIDs))
            return false;
        
        // update data in memory
        problem.addImages(imagesHandler.getImages(imageIDs));
        
        return true;
    }
    
    public boolean problemExists(String courseTitle, String question) {
        return getProblem(courseTitle, question) != null;
    }
    
    public Set<ImageIconInfo> getImages(String courseTitle, String question) {
        Problem problem;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return null;
        
        return problem.getImages();
    }
    
    public boolean isMultipleChoice(String courseTitle, String question) {
        Problem problem;
        
        // does problem exist
        if ((problem = getProblem(courseTitle, question)) == null)
            return false;
        
        return problem.isMultipleChoice();
    }
    
}

