package UIComponents.DataWindow;

import DBObjects.ImageFileInfo;
import UIComponents.QuizzerWindow.QuizzerFrame;
import DBObjects.ImageIconInfo;
import DBObjects.Problem;
import ErrorService.ErrorListener;
import ErrorService.ErrorService;
import UIComponents.DataWindow.AddQuestionPanel.AddQuestionPanel;
import UIComponents.DirectorPublisher;
import UIComponents.DirectorSubscriber;
import UIComponents.ImageViewer.ImageViewerPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultCaret;
import quizzer.HandlerFactory;
import quizzer.ManageCoursesHandler;
import quizzer.ManageImagesHandler;
import quizzer.ManageProblemsHandler;
import quizzer.Quizzer;
import utilities.ui.UIUtils;

/**
 *
 * @author Armando
 */
public class DatabaseFrame extends JFrame implements ErrorListener, DirectorSubscriber {
    // controllers
    private final ManageCoursesHandler cHandler;
    private final ManageImagesHandler iHandler;
    private final ManageProblemsHandler pHandler;
    
    // children panels
    private final AddQuestionPanel addQuestionPanelDirector;
    private final ImageViewerPanel imageViewerPanelDirector;
    private final ProblemReportPanel problemDetailsPanelDirector;
    private final StatsPanel statsPanelDirector;
    private final QuestionsPanel questionsPanelDirector;
    private final MainCommandPanel mainCommandPanelDirector;
    
    private int storedCard;
    private int cardShowing;
    private final int defaultCard;
    private boolean isViewingAllImages;
    private final JFileChooser fileChooser;
    private String imgsViewProblemCourse;
    private String imgsViewProblemQuestion;
    
    private static final int CARD_DEFAULT = -1;
    private static final int CARD_STATS = 0;
    private static final int CARD_ADD_QUESTION = 1;
    private static final int CARD_PROBLEM_REPORT = 2;
    private static final int CARD_IMAGE_VIEWER = 3;
    private static final Dimension FILE_CHOOSER_DIMENSION =
                                                        new Dimension(650, 700);
    private static final String PANEL_STATS = "stats";
    private static final String PANEL_ADD_QUESTION = "addQuestion";
    private static final String PANEL_PROBLEM_REPORT = "problemReport";
    private static final String PANEL_IMAGE_VIEWER = "imageViewer";
    private static final String MSG_SELECT_EXISTING = "<html><b>Select an existing image from the database</b></html>";
    private static final String MSG_SELECTED_QUESTION = "Image(s) belonging to the selected question above";
    private static final String MSG_DATABASE_IMAGES = "These are all of the existing images in the database.";
    private static final String DELETE_IMAGE = "This will delete the image from"
            + "the database completely, including from every associated problem"
            + ".<br> Are you sure you want to delete this image?";
    private static final String REMOVE_IMAGE = "This image will no longer be "
            + "associated with any problems.<br> Would you like to delete the "
            + "image from the database as well?";
    
    public DatabaseFrame() {
        cHandler = HandlerFactory.getHandlerFactory().getCoursesHandler();
        iHandler = HandlerFactory.getHandlerFactory().getImagesHandler();
        pHandler = HandlerFactory.getHandlerFactory().getProblemsHandler();
        isViewingAllImages = false;
        imgsViewProblemCourse = null;
        imgsViewProblemQuestion = null;
        initComponents();
        panelMid.setLayout(new CardLayout());
        CardLayout cLayout = (CardLayout)panelMid.getLayout();
        questionsPanelDirector = new QuestionsPanel(this);
        panelMid.add(questionsPanelDirector);
        cLayout.first(panelMid);
        panelTop.setLayout(new CardLayout());
        mainCommandPanelDirector = new MainCommandPanel(this);
        cLayout = (CardLayout)panelTop.getLayout();
        panelTop.add(mainCommandPanelDirector);
        cLayout.first(panelTop);
        
        ErrorService.getService().setListener(this);

        // set up file chooser
        fileChooser = new JFileChooser("C:/Users/Armando/Pictures/");
        fileChooser.setFileFilter(new ImageFileFilter());
        fileChooser.setPreferredSize(FILE_CHOOSER_DIMENSION);
        UIUtils.setComponentFont(fileChooser.getComponents());
        
        // this ensures error area will scroll to bottom on each update
        ( (DefaultCaret)areaError.getCaret() )
                .setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        addQuestionPanelDirector = new AddQuestionPanel(this);
        imageViewerPanelDirector = new ImageViewerPanel(this);
        problemDetailsPanelDirector = new ProblemReportPanel();
        statsPanelDirector = new StatsPanel();
        cardShowing = CARD_STATS;
        defaultCard = CARD_PROBLEM_REPORT;
        
        panelBottom.removeAll();
        panelBottom.setLayout(new CardLayout());
        panelBottom.add(statsPanelDirector, PANEL_STATS);
        panelBottom.add(addQuestionPanelDirector, PANEL_ADD_QUESTION);
        panelBottom.add(imageViewerPanelDirector, PANEL_IMAGE_VIEWER);
        panelBottom.add(problemDetailsPanelDirector, PANEL_PROBLEM_REPORT);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMain = new javax.swing.JPanel();
        panelMid = new javax.swing.JPanel();
        panelBottom = new javax.swing.JPanel();
        scrollPaneError = new javax.swing.JScrollPane();
        areaError = new javax.swing.JTextArea();
        panelTop = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Quizzer");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panelMain.setPreferredSize(new java.awt.Dimension(600, 475));

        panelMid.setBackground(new java.awt.Color(0, 153, 0));

        javax.swing.GroupLayout panelMidLayout = new javax.swing.GroupLayout(panelMid);
        panelMid.setLayout(panelMidLayout);
        panelMidLayout.setHorizontalGroup(
            panelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelMidLayout.setVerticalGroup(
            panelMidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 266, Short.MAX_VALUE)
        );

        panelBottom.setBackground(new java.awt.Color(153, 153, 0));

        javax.swing.GroupLayout panelBottomLayout = new javax.swing.GroupLayout(panelBottom);
        panelBottom.setLayout(panelBottomLayout);
        panelBottomLayout.setHorizontalGroup(
            panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelBottomLayout.setVerticalGroup(
            panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );

        scrollPaneError.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        areaError.setEditable(false);
        areaError.setColumns(20);
        areaError.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        areaError.setLineWrap(true);
        areaError.setRows(5);
        areaError.setTabSize(4);
        areaError.setWrapStyleWord(true);
        scrollPaneError.setViewportView(areaError);

        panelTop.setBackground(new java.awt.Color(0, 0, 204));

        javax.swing.GroupLayout panelTopLayout = new javax.swing.GroupLayout(panelTop);
        panelTop.setLayout(panelTopLayout);
        panelTopLayout.setHorizontalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );
        panelTopLayout.setVerticalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 119, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelMainLayout = new javax.swing.GroupLayout(panelMain);
        panelMain.setLayout(panelMainLayout);
        panelMainLayout.setHorizontalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainLayout.createSequentialGroup()
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelMid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPaneError)
                    .addComponent(panelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelMainLayout.createSequentialGroup()
                        .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelMainLayout.setVerticalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainLayout.createSequentialGroup()
                .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPaneError, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, 878, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Quizzer.exitQuizzer();
    }//GEN-LAST:event_formWindowClosing

    @Override
    public void onError(List<String> errorMessages) {
        if (errorMessages == null)
            return;
        
        for (String msg : errorMessages)
            areaError.append(msg + "\n");
    }
    
    // ------------------- Messages from Child Panels -------------------------

    @Override
    public void onEvent(DirectorPublisher source, int eventIndicator) {
        
        if (source == mainCommandPanelDirector) {
            if (eventIndicator == MainCommandPanel.SHOW_ALL_IMAGES)
                showAllImages();
            
            else if (eventIndicator == MainCommandPanel.TAKE_QUIZ)
                takeQuiz();
            
            else if (eventIndicator == MainCommandPanel.COURSE_SELECTED)
                courseSelected();
            
            else if (eventIndicator == MainCommandPanel.SEARCH_BY_TAGS)
                searchByTags();
        }
        
        else if (source == addQuestionPanelDirector) {
            
            if (eventIndicator == AddQuestionPanel.ADD_EXISTING_IMAGE)
                selectExistingImage();

            else if (eventIndicator == AddQuestionPanel.SUCCESS)
                addQuestion();

            else if (eventIndicator == AddQuestionPanel.FAILURE)
                showCard(CARD_DEFAULT);
        }
        
        else if (source == imageViewerPanelDirector) {
                
            if (eventIndicator == ImageViewerPanel.EVENT_CANCEL)
                cancelImageSelection();

            else if (eventIndicator == ImageViewerPanel.EVENT_IMAGE_CHOSEN)
                imageSelected();
            
            else if (eventIndicator == ImageViewerPanel.EVENT_DELETE_IMAGE) {
                if (isViewingAllImages)
                    deleteImage();
                else
                    removeImageFromProblem();
            }
            
            else if (eventIndicator == ImageViewerPanel.EVENT_EDIT_IMAGE) {
                if (isViewingAllImages)
                    editImage();
                else
                    editImageForProblem();
            }
            
            else if (eventIndicator == ImageViewerPanel.EVENT_ADD_IMAGE) {
                if (isViewingAllImages)
                    addImage();
                else
                    addImageToProblem();
            }
        }
        
        else if (source == questionsPanelDirector) {
            if (eventIndicator == QuestionsPanel.ADD_QUESTION)
                showAddQuestionPanel();
            
            else if (eventIndicator == QuestionsPanel.SELECTION_CHANGED)
                questionSelectionChanged();
            
            else if (eventIndicator == QuestionsPanel.SHOW_DETAILS)
                showDetails();
            
            else if (eventIndicator == QuestionsPanel.SHOW_IMAGES)
                showImages();
            
            else if (eventIndicator == QuestionsPanel.SHOW_STATS)
                showStats();
        }
    }
    
    /** A new course selection was made **/
    private void courseSelected() {
        String course;
        Map<String,String> questions;

        // collect data
        course = mainCommandPanelDirector.getCourse();
        questions = cHandler.getProblems(course);
        
        // get questions; update ui
        questionsPanelDirector.setAddEnabled(course != null);
        questionsPanelDirector.setQuestions(questions);
        addQuestionPanelDirector.setCourse(course);
    }
    
    /** The user canceled out of the existing-image selection process **/
    private void cancelImageSelection() {
        showCard(storedCard);
        
        if (imgsViewProblemCourse == null)
            return;
        
        setImageViewer(pHandler.getImages(
                imgsViewProblemCourse, imgsViewProblemQuestion)
                , MSG_SELECTED_QUESTION, imgsViewProblemCourse
                , imgsViewProblemQuestion, false);
        imgsViewProblemCourse = null;
        imgsViewProblemQuestion = null;
    }
    
    /** An existing image was chosen at the request of some panel **/
    private void imageSelected() {
        
        ImageIconInfo image = imageViewerPanelDirector.getImage();
        
        showCard(storedCard);
        
        // request is meant for add question panel director
        if (imgsViewProblemCourse == null)
            addQuestionPanelDirector.addImage(image);
        
        // request is meant for images viewer panel director itself
        else {
            
            pHandler.associateImagesWithProblem(imgsViewProblemCourse
                    , imgsViewProblemQuestion
                    , Collections.singleton(image.getImageID()));
            
            setImageViewer(pHandler.getImages(
                    imgsViewProblemCourse, imgsViewProblemQuestion)
                    , MSG_SELECTED_QUESTION, imgsViewProblemCourse
                    , imgsViewProblemQuestion, false);
            imgsViewProblemCourse = null;
            imgsViewProblemQuestion = null;
//            imageViewerPanelDirector.imageAdded(image);
        }
    }
    
    /** Initiate existing-image selection process **/
    private void selectExistingImage() {
        storedCard = cardShowing;
        imageViewerPanelDirector.chooseAnImage(
                MSG_SELECT_EXISTING, iHandler.getImages());
        showCard(CARD_IMAGE_VIEWER);
    }
    
    /** A wrapper for a similar method from image viewer panel that updates
     * the value returned by <code>isViewingAllImages()</code>.
     * @param imageIcons The images to view.
     * @param viewerMessage An optional message to show in the viewer.
     * @param courseTitle If viewing images of a problem, this is the course the
     *        problem belongs to.
     * @param questionText If viewing images of a problem, this is the question
     *        of the problem.
     * @param choosingImage A <code>true</code> value results in the image
     *        viewer being configured to allow the selection of an existing
     *        image. Certain buttons will be hidden/shown depending on this
     *        setting.
     */
    private void setImageViewer(Set<ImageIconInfo> imageIcons
            , String viewerMessage, String courseTitle, String questionText
            , boolean choosingImage) {
        
        imageViewerPanelDirector.setImages(imageIcons, viewerMessage
                , courseTitle, questionText, choosingImage);
        
        isViewingAllImages = MSG_DATABASE_IMAGES.equals(viewerMessage);
    }
    
    /** A question was added to the current course **/
    private void addQuestion() {
        String course = mainCommandPanelDirector.getCourse();
        String question = addQuestionPanelDirector.getQuestion();
        
        questionsPanelDirector.addQuestion(question, course);
        showCard(storedCard);
    }
    
    /** Initiates a quiz using the questions in the question list **/
    private void takeQuiz() {
        Map<String,String> questions;
        List<Problem> probList;
        QuizzerFrame qFrame;
        
        probList = new ArrayList<>();
        questions = questionsPanelDirector.getQuestions();
        for (String key : questions.keySet())
            probList.add(pHandler.getProblem(questions.get(key), key));
        
        qFrame = new QuizzerFrame(probList);
        qFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        qFrame.pack();
        qFrame.setLocationRelativeTo(this);
        qFrame.setVisible(true);
    }
    
    /** Switches the bottom panel to display the add-question panel **/
    private void showAddQuestionPanel() {
        storedCard = cardShowing;
        showCard(CARD_ADD_QUESTION);
    }
    
    /** Switches the bottom panel to the image viewer panel and passes it all
     * images known to the program. **/
    private void showAllImages() {
        setImageViewer(iHandler.getImages(), MSG_DATABASE_IMAGES, null, null, false);
        questionsPanelDirector.unclickLast();
        showCard(CARD_IMAGE_VIEWER);
    }
    
    /** Search for problems by tag, w/ or w/o filtering by current course. **/
    private void searchByTags() {
        Map<String,String> questions;
        Set<String> tags;
        String course;
        
        // collect data from ui components
        tags = mainCommandPanelDirector.getTags();
        questions = pHandler.getProblemsByTags(tags);
        course = mainCommandPanelDirector.getCourse();
        
        // filter problems by course if that setting is turned on
        if (mainCommandPanelDirector.isCourseFilterOn())
            questions.values().retainAll(Collections.singleton(course));
        
        questionsPanelDirector.setQuestions(questions);
//        buttonQuiz.setEnabled(!questions.isEmpty());
    }

    /** Switches the bottom panel between various panel cards **/
    private void showCard(int cardConstant) {
        CardLayout cLayout = (CardLayout)panelBottom.getLayout();
        String panelName = null;
        
        switch (cardConstant) {
            case CARD_STATS:
                panelName = PANEL_STATS; break;
            case CARD_PROBLEM_REPORT:
                panelName = PANEL_PROBLEM_REPORT;
                break;
            case CARD_ADD_QUESTION:
                panelName = PANEL_ADD_QUESTION; break;
            case CARD_IMAGE_VIEWER:
                panelName = PANEL_IMAGE_VIEWER; break;
            case CARD_DEFAULT:
                showCard(defaultCard);
        }
        cLayout.show(panelBottom, panelName);
        cardShowing = cardConstant;
    }
    
    /** A request to delete the currently selected image was sent from the
     * image viewer panel. The image will be deleted from the entire program,
     * including from all associated problems. **/
    private void deleteImage() {
        int imageID;
        boolean success;
        
        imageID = imageViewerPanelDirector.getImage().getImageID();
        
        // ask user to confirm, if problems are still associated with the image
        if (iHandler.getNumAssociated(imageID) > 0)
            if (!UIUtils.confirmDialog(this, DELETE_IMAGE, "Delete Image?"))
                return;
        
        // send image deletion request
        success = iHandler.deleteImage(imageID);    
        imageViewerPanelDirector.imageDeleted(success);
    }
    
    /** A request to remove the currently selected image from a problem was sent
     * from the image viewer panel. **/
    private void removeImageFromProblem() {
        boolean success;
        String courseTitle;
        String question;
        int imageID;
        int choice;
        
        courseTitle = imageViewerPanelDirector.getCourseTitle();
        question = imageViewerPanelDirector.getQuestion();
        imageID = imageViewerPanelDirector.getImage().getImageID();
        
        /* if this is the last problem associated with the image, ask user
         * if they would like to delete image from the program entirely. */
        if (iHandler.getNumAssociated(imageID) <= 1) {
            choice = UIUtils.choiceDialog(this, REMOVE_IMAGE, "Delete Image from Program?");
            
            if (choice == JOptionPane.YES_OPTION)
                success = iHandler.deleteImage(imageID);
            
            else if (choice == JOptionPane.NO_OPTION)
                success = pHandler.removeImageFromProblem(courseTitle, question
                        , imageID);
            
            else
                return;
        }

        // send request to remove image from problem only
        else
            success = pHandler.removeImageFromProblem(courseTitle, question
                    , imageID);
        
        imageViewerPanelDirector.imageDeleted(success);
    }
    
    /** Edits the main image caption for an image. **/
    private void editImage() {
        ImageIconInfo image;
        String newCaption;

        image = imageViewerPanelDirector.getImage();
        
        // prompt the user
        newCaption = (String)JOptionPane.showInputDialog
                (this
                , "<html><span style='font-size: 1.7em'>Enter a new caption for"
                        + " the selected image</span></html>"
                , "Edit Image Caption", JOptionPane.PLAIN_MESSAGE
                , null, null, image.getCaption());

        // send caption edit request and notify image viewer if it worked
        if (iHandler.setCaption(image, newCaption))
            imageViewerPanelDirector.imageEdited(newCaption);
    }
    
    // TODO implement this for real to edit/add a custom caption for an image
    private void editImageForProblem() {
        editImage();
    }
    
    /** Let's the user choose a new image file and adds image to program.
     * @return A unique image ID for the newly added image. */
    private int addImage() {
        File file;
        int imageID;
        
        // prompt user to choose an image file
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return -1;
        file = fileChooser.getSelectedFile();
        
        // send request to add the image to the program; update ui on success
        imageID = iHandler.addImage(new ImageFileInfo(file, file.getName()));
        
        // notify image viewer panel if it was added successfully
        if (imageID != -1)
            imageViewerPanelDirector.imageAdded(iHandler.getImage(imageID));
        
        return imageID;
    }
    
    /** Let's the user choose a new image or an existing image already known
     * to the program, then adds the chosen image to the current problem. */
    private void addImageToProblem() {
        int choice;
        String course;
        String question;
        int imageID;
        
        course = imageViewerPanelDirector.getCourseTitle();
        question = imageViewerPanelDirector.getQuestion();
        choice = UIUtils.choiceDialog(this
                , null, "Add Image"
                , new String[] { "New Image", "Existing Image", "Cancel" }
                , "Cancel");
        
        // new image
        if (choice == 0) {
            imageID = addImage();
            pHandler.associateImagesWithProblem(
                    course, question, Collections.singleton(imageID));
        }
        
        // existing image
        else if (choice == 1) {
            imgsViewProblemCourse = course;
            imgsViewProblemQuestion = question;
            selectExistingImage();
        }
    }
    
    /** A new question was selected. Updates various info panels. **/
    private void questionSelectionChanged() {
        String course;
        String question;
        Set<ImageIconInfo> images;
        boolean empty;
        
        // collect selected question info
        course = questionsPanelDirector.getCourse();
        question = questionsPanelDirector.getQuestion();
        images = pHandler.getImages(course, question);
        empty = (course == null || question == null);
        
        // update ui components with new info
        statsPanelDirector.setQuestion(course, question);
        problemDetailsPanelDirector.setQuestion(course, question);
        setImageViewer(images, MSG_SELECTED_QUESTION, course, question, false);
        
        // update top panel ui components
        mainCommandPanelDirector.setQuizEnabled(!empty);
    }
    
    /** Displays the panel showing detailed info on the current problem. **/
    private void showDetails() {
        showCard(CARD_PROBLEM_REPORT);
    }
    
    /** Displays the panel showing images associated w/the current problem. **/
    private void showImages() {
        String course;
        String question;
        
        question = questionsPanelDirector.getQuestion();
        course = questionsPanelDirector.getCourse();
        imageViewerPanelDirector.setAddEnabled(true);
        
        if (course != null && question != null)
            setImageViewer(pHandler.getImages(course, question)
                    , MSG_SELECTED_QUESTION, course, question, false);
        showCard(CARD_IMAGE_VIEWER);
    }
    
    /** Displays the panel showing statistics of the current problem.. **/
    private void showStats() {
        showCard(CARD_STATS);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaError;
    private javax.swing.JPanel panelBottom;
    private javax.swing.JPanel panelMain;
    private javax.swing.JPanel panelMid;
    private javax.swing.JPanel panelTop;
    private javax.swing.JScrollPane scrollPaneError;
    // End of variables declaration//GEN-END:variables
    
}
