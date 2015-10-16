package UIComponents.DataWindow;

import ErrorService.ErrorReporter;
import ErrorService.ErrorService;
import UIComponents.EmptyListModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import quizzer.HandlerFactory;
import quizzer.ManageProblemsHandler;
import utilities.ui.UIUtils;

/**
 *
 * @author Armando
 */
public class ProblemReportPanel extends JPanel implements ErrorReporter {
    private final ManageProblemsHandler pHandler;
    private final PanelListener panelListener;
    private String course;
    private String question;
    
    private String oldAnswer;
    
    /**
     * Creates new form ProblemReportPanel
     */
    public ProblemReportPanel() {
        this.pHandler = HandlerFactory.getHandlerFactory().getProblemsHandler();
        this.panelListener = new PanelListener();
        initComponents();
    }
    
    private void tagsListChanged() {
        buttonDeleteTags.setEnabled(!listTags.isSelectionEmpty());
    }
    
    private void addTags() {
        String tagsText;
        Set<String> newTags;
        
        // prompt for new tags
        tagsText = UIUtils.inputDialog(this
                , "Enter new tags, separated by commas", "Add Tags");
        if (tagsText == null)
            return;
            
        // separate tags
        newTags = new HashSet<>();
        for (String tag : tagsText.split(","))
            newTags.add(tag.trim());
        
        // send addTags request
        pHandler.addTags(course, question, newTags);

        // update ui // TODO change how this refreshes tag list
        listTags.setModel(new TagsListModel(
                pHandler.getTags(course, question)));
        listTags.setEnabled(listTags.getModel().getSize() > 0);
    }
    
    private void deleteTags() {
        Set<String> tags = new HashSet<>(listTags.getSelectedValuesList());
        
        if (!pHandler.deleteTags(course, question, tags))
            return;

        listTags.setModel(new TagsListModel(
                pHandler.getTags(course, question)));

        if (listTags.getModel().getSize() == 0) {
            listTags.setEnabled(false);
            buttonDeleteTags.setEnabled(false); // necessary when ctrl+a -> delete used
        }
    }
    
    private void editExplanation() {
        areaExplanationReport.setEditable(true);
        areaExplanationReport.requestFocusInWindow();
        buttonEditExplanation.setEnabled(false);
        buttonSaveExplanation.setEnabled(true);
    }
    
    private void answersListChanged() {
        if (listAnswersReport.getSelectedIndex() == -1) {
            buttonEditAnswer.setEnabled(false);
            buttonDeleteAnswer.setEnabled(false);
            buttonToggleAnswer.setEnabled(false);
        }
        else {
            if (listAnswersReport.getSelectedIndices().length == 1) {
                buttonEditAnswer.setEnabled(true);
                buttonToggleAnswer.setEnabled(true);
            }
            else {
                buttonEditAnswer.setEnabled(false);
                buttonToggleAnswer.setEnabled(false);
            }
            buttonDeleteAnswer.setEnabled(true);
        }
    }
    
    private void deleteAnswer() {
        Set<String> selections;

        selections = new HashSet<>(listAnswersReport.getSelectedValuesList());
        if (pHandler.deleteAnswers(course, question, selections))
            for (String ans : selections)
                answersModel().removeAnswer(ans);
    }
    
    private void saveExplanation() {
        String newExplanation = areaExplanationReport.getText();
        
        pHandler.setExplanation(course, question, newExplanation);
        
        areaExplanationReport.setEditable(false);
        buttonEditExplanation.requestFocusInWindow();
        buttonEditExplanation.setEnabled(true);
        buttonSaveExplanation.setEnabled(false);
    }
    
    private void editAnswer() {
        String newAnswer;
        
        oldAnswer = (String)listAnswersReport.getSelectedValue();
        newAnswer = UIUtils.inputDialog(this
                , "Enter a new answer", "EditAnswer", oldAnswer);
        
        if (pHandler.setAnswer(course, question, oldAnswer, newAnswer))
            answersModel().updateAnswer(oldAnswer, newAnswer);
    }
    
    private void addAnswer() {
        String newAnswer;
        boolean isCorrect;
        
        newAnswer = UIUtils.inputDialog(this
                , "Enter a new answer", "Add Answer");
        if (newAnswer == null)
            return;
        
        isCorrect = UIUtils.confirmDialog(this
                , "Is this answer a correct or wrong answer?"
                , "Correct Answer?");
        
        if (pHandler.addAnswer(course, question, newAnswer, isCorrect))
            answersModel().addAnswer(newAnswer);
    }
    
    private void toggleAnswer() {
        String answer = (String)listAnswersReport.getSelectedValue();
        
        if (pHandler.toggleIsCorrect(course, question, answer))
            answersModel().updateAnswer(answer, answer);
    }
    
    public void setQuestion(String courseTitle, String question) {
        this.course = courseTitle;
        this.question = question;
        
        if (courseTitle == null || question == null
                || !pHandler.problemExists(courseTitle, question)) {
            listTags.setModel(new EmptyListModel());
            areaExplanationReport.setText("");
            answersModel().removeAllAnswers();
            buttonAddTags.setEnabled(false);
            buttonAddAnswer.setEnabled(false);
            buttonEditExplanation.setEnabled(false);
            
            return;
        }
        
        listTags.setModel(
                new TagsListModel(pHandler.getTags(courseTitle, question)));
        areaExplanationReport.setText(
                pHandler.getExplanation(courseTitle, question));
        answersModel().setAnswers(pHandler.getAllAnswers(courseTitle, question));
        listAnswersReport.setCellRenderer(new AnswersCellRenderer(courseTitle, question));
        

        buttonAddTags.setEnabled(true);
        buttonAddAnswer.setEnabled(true);
        buttonEditExplanation.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        scrollPaneTagsReport = new javax.swing.JScrollPane();
        listTags = new javax.swing.JList();
        buttonAddTags = new javax.swing.JButton();
        buttonDeleteTags = new javax.swing.JButton();
        buttonEditExplanation = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listAnswersReport = new javax.swing.JList();
        buttonDeleteAnswer = new javax.swing.JButton();
        labelAnswerReport = new javax.swing.JLabel();
        buttonAddAnswer = new javax.swing.JButton();
        scrollPaneExplanationReport = new javax.swing.JScrollPane();
        areaExplanationReport = new javax.swing.JTextArea();
        labelTagsReport = new javax.swing.JLabel();
        buttonSaveExplanation = new javax.swing.JButton();
        labelExplanationReport = new javax.swing.JLabel();
        buttonEditAnswer = new javax.swing.JButton();
        buttonToggleAnswer = new javax.swing.JButton();

        listTags.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        listTags.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
        listTags.addListSelectionListener(panelListener);
        scrollPaneTagsReport.setViewportView(listTags);

        buttonAddTags.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        buttonAddTags.setText("Add");
        buttonAddTags.setEnabled(false);
        buttonAddTags.addActionListener(panelListener);

        buttonDeleteTags.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        buttonDeleteTags.setText("Delete");
        buttonDeleteTags.setEnabled(false);
        buttonDeleteTags.addActionListener(panelListener);

        buttonEditExplanation.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        buttonEditExplanation.setText("Edit");
        buttonEditExplanation.setEnabled(false);
        buttonEditExplanation.addActionListener(panelListener);

        listAnswersReport.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        listAnswersReport.setModel(new AnswersListModel());
        listAnswersReport.addListSelectionListener(panelListener);
        jScrollPane3.setViewportView(listAnswersReport);

        buttonDeleteAnswer.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        buttonDeleteAnswer.setText("Delete");
        buttonDeleteAnswer.setEnabled(false);
        buttonDeleteAnswer.addActionListener(panelListener);

        labelAnswerReport.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        labelAnswerReport.setText("Answers");
        labelAnswerReport.setEnabled(false);

        buttonAddAnswer.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        buttonAddAnswer.setText("Add");
        buttonAddAnswer.setEnabled(false);
        buttonAddAnswer.addActionListener(panelListener);

        scrollPaneExplanationReport.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        areaExplanationReport.setEditable(false);
        areaExplanationReport.setColumns(13);
        areaExplanationReport.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        areaExplanationReport.setLineWrap(true);
        areaExplanationReport.setRows(4);
        areaExplanationReport.setTabSize(4);
        areaExplanationReport.setWrapStyleWord(true);
        scrollPaneExplanationReport.setViewportView(areaExplanationReport);

        labelTagsReport.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        labelTagsReport.setText("Tags");

        buttonSaveExplanation.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        buttonSaveExplanation.setText("Save");
        buttonSaveExplanation.setEnabled(false);
        buttonSaveExplanation.addActionListener(panelListener);

        labelExplanationReport.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        labelExplanationReport.setText("Explanation");

        buttonEditAnswer.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        buttonEditAnswer.setText("Edit");
        buttonEditAnswer.setEnabled(false);
        buttonEditAnswer.addActionListener(panelListener);

        buttonToggleAnswer.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        buttonToggleAnswer.setText("Toggle Answer");
        buttonToggleAnswer.setEnabled(false);
        buttonToggleAnswer.addActionListener(panelListener);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scrollPaneTagsReport)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(labelTagsReport)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonAddTags)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonDeleteTags)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(labelExplanationReport)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonSaveExplanation, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonEditExplanation)
                                .addGap(0, 226, Short.MAX_VALUE))
                            .addComponent(scrollPaneExplanationReport)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(labelAnswerReport)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonAddAnswer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonEditAnswer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonToggleAnswer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonDeleteAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAnswerReport)
                    .addComponent(buttonAddAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonEditAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonDeleteAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonToggleAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelTagsReport)
                        .addComponent(buttonDeleteTags, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonAddTags, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelExplanationReport)
                        .addComponent(buttonEditExplanation, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonSaveExplanation, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollPaneTagsReport)
                    .addComponent(scrollPaneExplanationReport, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private AnswersListModel answersModel() {
        return (AnswersListModel)listAnswersReport.getModel();
    }
    
    @Override
    public void reportError(String errorMessage) {
        ErrorService.getService().reportError(errorMessage);
    }
    
    @Override
    public void reportErrors(List<String> errorMessages) {
        ErrorService.getService().reportErrors(errorMessages);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaExplanationReport;
    private javax.swing.JButton buttonAddAnswer;
    private javax.swing.JButton buttonAddTags;
    private javax.swing.JButton buttonDeleteAnswer;
    private javax.swing.JButton buttonDeleteTags;
    private javax.swing.JButton buttonEditAnswer;
    private javax.swing.JButton buttonEditExplanation;
    private javax.swing.JButton buttonSaveExplanation;
    private javax.swing.JButton buttonToggleAnswer;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelAnswerReport;
    private javax.swing.JLabel labelExplanationReport;
    private javax.swing.JLabel labelTagsReport;
    private javax.swing.JList listAnswersReport;
    private javax.swing.JList listTags;
    private javax.swing.JScrollPane scrollPaneExplanationReport;
    private javax.swing.JScrollPane scrollPaneTagsReport;
    // End of variables declaration//GEN-END:variables

    // --------------------------- UI Events ----------------------------------
    
    private class PanelListener implements ActionListener, ListSelectionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if (src == buttonEditAnswer)
                editAnswer();
            else if (src == buttonDeleteAnswer)
                deleteAnswer();
            else if (src == buttonAddAnswer)
                addAnswer();
            else if (src == buttonAddTags)
                addTags();
            else if (src == buttonDeleteTags)
                deleteTags();
            else if (src == buttonSaveExplanation)
                saveExplanation();
            else if (src == buttonEditExplanation)
                editExplanation();
            else if (src == buttonToggleAnswer)
                toggleAnswer();
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            Object src = e.getSource();
            if (src == listAnswersReport)
                answersListChanged();
            else if (src == listTags)
                tagsListChanged();
        }
        
    }
    
}
