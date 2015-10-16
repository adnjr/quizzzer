package quizzer;

/**
 *
 * @author Armando
 */
public class HandlerFactory {
    private static HandlerFactory handlerFactory = null;
    private final QuizzerDB database;
    private ManageCoursesHandler coursesHandler;
    private ManageImagesHandler imagesHandler;
    private ManageProblemsHandler problemsHandler;
    
    private HandlerFactory(QuizzerDB db) {
        this.database = db;
    }
    
    public static HandlerFactory getHandlerFactory(QuizzerDB db) {
        if (handlerFactory == null)
            handlerFactory = new HandlerFactory(db);
        return handlerFactory;
    }
    
    public static HandlerFactory getHandlerFactory() {
        if (handlerFactory == null)
            throw new RuntimeException("Handler factory must be initialized "
                    + "with getHandlerFactory(QuizzerDB) first.");
        return handlerFactory;
    }
    
    public ManageCoursesHandler getCoursesHandler() {
        if (coursesHandler == null)
            coursesHandler = new ManageCoursesHandler(database);
        return coursesHandler;
    }
    
    public ManageImagesHandler getImagesHandler() {
        if (imagesHandler == null)
            imagesHandler = new ManageImagesHandler(database);
        return imagesHandler;
    }
    
    public ManageProblemsHandler getProblemsHandler() {
        if (problemsHandler == null)
            problemsHandler = new ManageProblemsHandler(database);
        return problemsHandler;
    }
    
}
