package quizzer;

import DBObjects.Course;
import DBObjects.Problem;
import ErrorService.AbstractErrorReporter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Armando
 */
public class ManageCoursesHandler extends AbstractErrorReporter  {
    private Set<Course> courses;
    private final QuizzerDB qDB;
    
    public ManageCoursesHandler(QuizzerDB quizzerDB) {
        this.courses = null;
        this.qDB = quizzerDB;
    }
    
    public void initCourses() {
        courses = new HashSet<>();
        for (Map<String,Object> result : qDB.getCourses())
            courses.add(new Course((String)result.get(QuizzerDB.COURSE_TITLE)));
    }
    
    public boolean addCourse(String courseTitle) {
        if (courseExists(courseTitle)) {
            reportError("Course \"" +courseTitle+ "\" already exists.");
            return false;
        }
        
        // add to database
        if (!qDB.addCourse(courseTitle))
            return false;
        
        // add to memory;
        return courses.add(new Course(courseTitle));
    }
    
    public boolean deleteCourse(String courseTitle) {
        Course course;
        
        // does course exist
        if ((course = getCourse(courseTitle)) == null)
            return false;
        
        // delete from database
        if (!qDB.deleteCourse(courseTitle))
            return false;
        
        // delete from memory
        return courses.remove(course);
    }
    
    public Set<String> getCourseTitles() {
        Set<String> result = new TreeSet<>();
        for (Course course : courses)
            result.add(course.getTitle());
        
        return result;
    }
    
    public boolean setCourseTitle(String oldTitle, String newTitle) {
        Course course;
        
        // does course exist
        if ((course = getCourse(oldTitle)) == null)
            return false;
        
        // update database
        if (!qDB.setCourseTitle(oldTitle, newTitle))
            return false;
        
        // update memory
        course.setTitle(newTitle);
        return true;
    }
    
    /**
     * @param courseTitle The title of the target course.
     * @return The course object that has a matching title, or null if not
     *         found.
     */
    public Course getCourse(String courseTitle) {
        for (Course course : courses)
            if (course.getTitle().equalsIgnoreCase(courseTitle))
                return course;
        
        reportError("Course \"" + courseTitle + "\" not found.");
        return null;
    }
    
    public Set<String> getQuestions(String courseTitle) {
        Course course;
        
        // does course exist
        if ((course = getCourse(courseTitle)) == null)
            return null;
        
        return course.getQuestions();
    }
    
    public boolean associateProblemWithCourse(String courseTitle, Problem problem) {
        /* Note: this action only needs to be done on the data in memory, and not in the
         * database as well, since the problem is associated with the course in
         * the database at the time the problem is added to the database.*/
        Course course;
        
        // does course exist
        if ((course = getCourse(courseTitle)) == null)
            return false;
        
        // create association in memory
        return course.addProblem(problem);
    }
    
    public Map<String,String> getProblems(String courseTitle) {
        Map<String,String> questionsToCourse;
        Course course;
        
        // does course exist
        if ((course = getCourse(courseTitle)) == null)
            return null;
        
        // add problem info to map
        questionsToCourse = new HashMap<>();
        for (Problem prob : course.getProblems())
            questionsToCourse.put(prob.getQuestion(), courseTitle);
        
        return questionsToCourse;
    }
    
    public boolean courseExists(String courseTitle) {
        for (Course course : courses)
            if (course.getTitle().equalsIgnoreCase(courseTitle))
                return true;
        
        return false;
    }
}
