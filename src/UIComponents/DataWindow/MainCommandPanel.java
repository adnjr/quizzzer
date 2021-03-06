package UIComponents.DataWindow;

import UIComponents.DirectorPublisher;
import UIComponents.DirectorSubscriber;
import UIComponents.UISettings;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import quizzer.HandlerFactory;
import quizzer.ManageCoursesHandler;
import quizzer.ManageProblemsHandler;
import utilities.ui.UIUtils;

/**
 *
 * @author Armando
 */
public class MainCommandPanel extends javax.swing.JPanel implements DirectorPublisher {
    private final PanelListener panelListener;
    private final Set<DirectorSubscriber> subscribers;
    private final ManageCoursesHandler cHandler;
    private final ManageProblemsHandler pHandler;
    private final EditableListModel tagsModel;
    
    private final Border enabledBorder = UISettings.enabledBorder;
    private final Border disabledBorder = UISettings.disabledBorder;
    
    private static final String ADD_NEW_COURSE = "    [-- Add New Course --]";
    private static final String EXISTING_TAGS = " existing tags";
    
    public static final int SHOW_ALL_IMAGES = 0;
    public static final int TAKE_QUIZ = 1;
    public static final int SEARCH_BY_TAGS = 2;
    public static final int COURSE_SELECTED = 3;
    
    /**
     * Creates new form MainCommandPanel
     * @param subscriber
     */
    public MainCommandPanel(DirectorSubscriber subscriber) {
        panelListener = new PanelListener();
        subscribers = new HashSet<>();
        if (subscriber == null)
            throw new IllegalArgumentException("This main command panel must be initialized with a subscriber.");
        subscribers.add(subscriber);
        cHandler = HandlerFactory.getHandlerFactory().getCoursesHandler();
        pHandler = HandlerFactory.getHandlerFactory().getProblemsHandler();
        tagsModel = new EditableListModel();
        initComponents();
        
        
        
        // set up combo box for courses
        comboBoxCourses.addActionListener(panelListener);
        comboBoxCourses.addItem(ADD_NEW_COURSE);
        for (String course : cHandler.getCourseTitles())
            comboBoxCourses.addItem(course);
        comboBoxCourses.setEditable(false);
        comboBoxCourses.getEditor().addActionListener(
                (ActionEvent e) -> coursesChanged());
        
        // set up combo box for tags
        comboboxTags.addItem(EXISTING_TAGS);
        for (String tag : pHandler.getTags())
            comboboxTags.addItem(tag);
        comboboxTags.setEditable(false);
        fieldTagFilter.setText("");
        
    }

    /** Returns the currently selected course title, or null if nothing selected
     * @return  **/
    public String getCourse() {
        String selection = (String)comboBoxCourses.getSelectedItem();
        
        if (ADD_NEW_COURSE.equals(selection))
            return null;
        
        return selection;
    }
    
    public Set<String> getTags() {
        return new HashSet<>(tagsModel.getItems());
    }
    
    public void setQuizEnabled(boolean bool) {
        buttonQuiz.setEnabled(bool);
    }
    
    public boolean isTagSearchOn() {
        return checkboxFilterByTag.isSelected();
    }
    
    public boolean isCourseFilterOn() {
        return checkboxCourseFilter.isSelected();
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
        panelTopRight = new javax.swing.JPanel();
        buttonQuiz = new javax.swing.JButton();
        buttonAllImages = new javax.swing.JButton();
        labelQuestions = new javax.swing.JLabel();
        panelFilter = new javax.swing.JPanel();
        fieldTagFilter = new javax.swing.JTextField();
        comboboxTags = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        listTags = new javax.swing.JList();
        buttonRemoveTagFilter = new javax.swing.JButton();
        checkboxCourseFilter = new javax.swing.JCheckBox();
        checkboxFilterByTag = new javax.swing.JCheckBox();
        panelCourses = new javax.swing.JPanel();
        comboBoxCourses = new javax.swing.JComboBox();
        labelCourses = new javax.swing.JLabel();
        buttonEditCourse = new javax.swing.JButton();
        buttonDeleteCourse = new javax.swing.JButton();

        panelTopRight.setBorder(enabledBorder);

        buttonQuiz.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        buttonQuiz.setText("Take Quiz");
        buttonQuiz.setEnabled(false);
        buttonQuiz.addActionListener(panelListener);

        buttonAllImages.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        buttonAllImages.setText("All Images");
        buttonAllImages.setActionCommand("<html>View All Images\nin the Database</html>");
        buttonAllImages.addActionListener(panelListener);

        javax.swing.GroupLayout panelTopRightLayout = new javax.swing.GroupLayout(panelTopRight);
        panelTopRight.setLayout(panelTopRightLayout);
        panelTopRightLayout.setHorizontalGroup(
            panelTopRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTopRightLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelTopRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonAllImages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonQuiz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelTopRightLayout.setVerticalGroup(
            panelTopRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTopRightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonQuiz, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAllImages)
                .addContainerGap())
        );

        labelQuestions.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        labelQuestions.setText("Questions");
        labelQuestions.setEnabled(false);

        panelFilter.setBorder(disabledBorder);

        fieldTagFilter.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        fieldTagFilter.setToolTipText("");
        fieldTagFilter.addActionListener(panelListener);

        comboboxTags.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        comboboxTags.setEnabled(false);
        comboboxTags.addActionListener(panelListener);

        listTags.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        listTags.setModel(tagsModel);
        listTags.addListSelectionListener(panelListener);
        jScrollPane2.setViewportView(listTags);

        buttonRemoveTagFilter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/x_12.png"))); // NOI18N
        buttonRemoveTagFilter.setEnabled(false);
        buttonRemoveTagFilter.addActionListener(panelListener);

        checkboxCourseFilter.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        checkboxCourseFilter.setText("Filter Course");
        checkboxCourseFilter.setEnabled(false);
        checkboxCourseFilter.setMargin(new java.awt.Insets(4, 4, 4, 4));
        checkboxCourseFilter.addActionListener(panelListener);

        checkboxFilterByTag.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        checkboxFilterByTag.setText("Search by tag");
        checkboxFilterByTag.addActionListener(panelListener);

        javax.swing.GroupLayout panelFilterLayout = new javax.swing.GroupLayout(panelFilter);
        panelFilter.setLayout(panelFilterLayout);
        panelFilterLayout.setHorizontalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboboxTags, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(checkboxCourseFilter, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addComponent(checkboxFilterByTag)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fieldTagFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonRemoveTagFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelFilterLayout.setVerticalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldTagFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(checkboxFilterByTag, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buttonRemoveTagFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panelFilterLayout.createSequentialGroup()
                        .addComponent(comboboxTags)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(checkboxCourseFilter))))
        );

        panelCourses.setBorder(enabledBorder);

        comboBoxCourses.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N

        labelCourses.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        labelCourses.setText("Courses");

        buttonEditCourse.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        buttonEditCourse.setText("Edit");
        buttonEditCourse.setEnabled(false);
        buttonEditCourse.addActionListener(panelListener);

        buttonDeleteCourse.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        buttonDeleteCourse.setText("Delete");
        buttonDeleteCourse.setEnabled(false);
        buttonDeleteCourse.addActionListener(panelListener);

        javax.swing.GroupLayout panelCoursesLayout = new javax.swing.GroupLayout(panelCourses);
        panelCourses.setLayout(panelCoursesLayout);
        panelCoursesLayout.setHorizontalGroup(
            panelCoursesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCoursesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCoursesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboBoxCourses, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelCoursesLayout.createSequentialGroup()
                        .addComponent(labelCourses)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonEditCourse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonDeleteCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCoursesLayout.setVerticalGroup(
            panelCoursesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCoursesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCoursesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCourses)
                    .addComponent(buttonDeleteCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonEditCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBoxCourses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelMainLayout = new javax.swing.GroupLayout(panelMain);
        panelMain.setLayout(panelMainLayout);
        panelMainLayout.setHorizontalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainLayout.createSequentialGroup()
                .addGroup(panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCourses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelQuestions))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTopRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelMainLayout.setVerticalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMainLayout.createSequentialGroup()
                .addComponent(panelCourses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelQuestions))
            .addComponent(panelFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTopRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editCourse() {
        String oldTitle;
        String newCourseTitle;
        
        oldTitle = (String)comboBoxCourses.getSelectedItem();
        newCourseTitle = UIUtils.inputDialog(this
                , "Enter a new title for this course."
                , "Edit Course Title", oldTitle);
        
        if (newCourseTitle == null)
            return;
        
        // send request to change the course title; update ui on success
        if (cHandler.setCourseTitle(oldTitle, newCourseTitle)) {
            comboBoxCourses.removeItem(oldTitle);
            comboBoxCourses.addItem(newCourseTitle);
            comboBoxCourses.setSelectedItem(newCourseTitle);
        }
    }
    
    private void addCourse() {
        buttonEditCourse.setEnabled(false);
//        buttonDeleteCourse.setEnabled(false);
        if (!comboBoxCourses.isEditable()) {
            comboBoxCourses.setEditable(true);
            comboBoxCourses.getEditor().setItem(ADD_NEW_COURSE);
            comboBoxCourses.getEditor().selectAll();
            comboBoxCourses.requestFocusInWindow();
        } else
            comboBoxCourses.setEditable(false);
    }
    
    private void deleteCourse() {
        String selectedCourse;
        String msg;
        String title;
        int confirmation;
        
        selectedCourse  = (String)comboBoxCourses.getSelectedItem();
        title = "Delete Course";
        msg = "Are you sure you want to delete the course  \"" +selectedCourse
                + "\"<br>and all of its associated questions?<br>This action "
                + "cannot be undone.";
        
        // prompt user for confirmation
        confirmation = UIUtils.optionDialog(this, msg, title, title, "Cancel");
        if (confirmation == 1)
            return;
        
        // send deletion request and update ui on success
        if (cHandler.deleteCourse(selectedCourse))
            comboBoxCourses.removeItem(selectedCourse);
    }
    
    private void searchByTagsToggle() {
        setTagsFilterEnabled(isTagSearchOn());
        
        if (isTagSearchOn()) {
            notifySubscribers(SEARCH_BY_TAGS);
            setCoursesEnabled(isCourseFilterOn());
        } else {
            notifySubscribers(COURSE_SELECTED);
            setCoursesEnabled(true);
        }
    }
    
    private void setCoursesEnabled(boolean bool) {
        buttonEditCourse.setEnabled(bool);
        buttonDeleteCourse.setEnabled(bool);
        comboBoxCourses.setEnabled(bool);
        labelCourses.setEnabled(bool);
        panelCourses.setBorder((bool) ? enabledBorder : disabledBorder);
    }
    
    private void coursesChanged() {
        
        // add-new-course was selected
        if (getCourse() == null) {
            addCourse();
            return;
        }
        
        // a new course was entered
        else if (!cHandler.courseExists(getCourse()) && cHandler.addCourse(getCourse()))
            comboBoxCourses.addItem(getCourse());
        
        // update ui components
        setCoursesEnabled(true);
        labelQuestions.setEnabled(true);
        comboBoxCourses.setEditable(false);
        
        // notify subscribers of event
        notifySubscribers((isTagSearchOn()) ? SEARCH_BY_TAGS : COURSE_SELECTED);
    }
    
    private void setTagsFilterEnabled(boolean toEnable) {
        checkboxCourseFilter.setEnabled(toEnable);
        comboboxTags.setEnabled(toEnable);
        listTags.setEnabled(toEnable);
        fieldTagFilter.setEnabled(toEnable);
        buttonRemoveTagFilter.setEnabled(toEnable && !listTags.isSelectionEmpty());
        panelFilter.setBorder((toEnable) ? enabledBorder : disabledBorder);
    }
    
    private void removeTagFromFilter() {
        int selIndex;
        int lastIndex;
        
        selIndex = listTags.getSelectedIndex();
        tagsModel.removeItem((String)listTags.getSelectedValue());
        
        lastIndex = tagsModel.getSize()-1;
        listTags.setSelectedIndex((selIndex > lastIndex) ? lastIndex : selIndex);
        
        notifySubscribers(SEARCH_BY_TAGS);
    }
    
    private void addTagToFilter() {
        String tag;
        
        tag = (String)comboboxTags.getSelectedItem();
        if (EXISTING_TAGS.equals(tag))
            return;
        
        tagsModel.addItem(tag);
        
        notifySubscribers(SEARCH_BY_TAGS);
    }
    
    private void addCustomTag() {
        Set<String> tags;
                
        tags = new HashSet<>();
        for (String tag : fieldTagFilter.getText().trim().split(","))
            if (!tag.trim().isEmpty())
                tags.add(tag.trim());
                
        tagsModel.addItems(tags);
        fieldTagFilter.setText("");
        
        notifySubscribers(SEARCH_BY_TAGS);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAllImages;
    private javax.swing.JButton buttonDeleteCourse;
    private javax.swing.JButton buttonEditCourse;
    private javax.swing.JButton buttonQuiz;
    private javax.swing.JButton buttonRemoveTagFilter;
    private javax.swing.JCheckBox checkboxCourseFilter;
    private javax.swing.JCheckBox checkboxFilterByTag;
    private javax.swing.JComboBox comboBoxCourses;
    private javax.swing.JComboBox comboboxTags;
    private javax.swing.JTextField fieldTagFilter;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelCourses;
    private javax.swing.JLabel labelQuestions;
    private javax.swing.JList listTags;
    private javax.swing.JPanel panelCourses;
    private javax.swing.JPanel panelFilter;
    private javax.swing.JPanel panelMain;
    private javax.swing.JPanel panelTopRight;
    // End of variables declaration//GEN-END:variables

    @Override
    public void addSubscriber(DirectorSubscriber subscriber) {
        if (subscriber != null)
            subscribers.add(subscriber);
    }

    @Override
    public void notifySubscribers(int eventIndicator) {
        for (DirectorSubscriber subber : subscribers)
            subber.onEvent(this, eventIndicator);
    }
    
    // --------------------------- UI Events ----------------------------------

    private class PanelListener implements ActionListener, ListSelectionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            
            if (src == buttonEditCourse)
                editCourse();
            
            else if (src == buttonAllImages)
                notifySubscribers(SHOW_ALL_IMAGES);
        
            else if (src == buttonDeleteCourse)
                deleteCourse();
            
            else if (src == buttonQuiz)
                notifySubscribers(TAKE_QUIZ);
            
            else if (src == checkboxFilterByTag
                    || src == checkboxCourseFilter)
                searchByTagsToggle();
            
            else if (src == buttonRemoveTagFilter)
                removeTagFromFilter();
            
            else if (src == comboboxTags)
                addTagToFilter();
            
            else if (src == comboBoxCourses || src == comboBoxCourses.getEditor())
                coursesChanged();
            
            else if (src == fieldTagFilter)
                addCustomTag();
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getSource() == listTags)
                buttonRemoveTagFilter.setEnabled(!listTags.isSelectionEmpty());
            else if (e.getSource() == comboBoxCourses.getEditor())
                coursesChanged();
        }
        
    }
    
}
