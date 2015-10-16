package UIComponents.DataWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractListModel;

/**
 *
 * @author Armando
 */
public class QuestionsListModel extends AbstractListModel {
    private final Map<String,String> questionsToCourse;
    private final List<String> questions;
    
    public QuestionsListModel() {
        super();
        this.questions = new ArrayList<>();
        this.questionsToCourse = new HashMap<>();
    }
    
//    public QuestionsListModel(Collection<Problem> problems) {
//        super();
//        this.questions = new ArrayList<>(problems);
//    }
    
    public void addQuestions(Map<String,String> questionsToCourse) {
        if (questionsToCourse == null || questionsToCourse.isEmpty())
            return;
        int oldSize = questions.size();
        this.questionsToCourse.putAll(questionsToCourse);
        this.questions.addAll(questionsToCourse.keySet());
        fireIntervalAdded(this, oldSize, questions.size()-1);
    }
    
    public void addQuestion(String question, String courseTitle) {
        if (question == null || question.trim().isEmpty())
            return;
        
        questionsToCourse.put(question, courseTitle);
        questions.add(question);
        
        fireIntervalAdded(this, questions.size()-1, questions.size()-1);
        Collections.sort(questions);
        fireContentsChanged(this, 0, questions.size()-1);
    }
    
    public boolean removeQuestion(String question) {
        int targetIndex;
        
        // find index of target question
        if ((targetIndex = questions.indexOf(question)) == -1)
            return false;
        
        if (!questions.remove(question))
            return false;
        questionsToCourse.remove(question);
        this.fireIntervalRemoved(this, targetIndex, targetIndex);
        
        return true;
    }
    
    /**
     * This method searches for the problem with the target question. If found,
     * the target question is refreshed in the list.
     * @param targetQuestion The question as it currently exists in the list.
     *        Note that this will have to be the newly-edited question.
     * @param newQuestion
     */
    public void updateQuestion(String targetQuestion, String newQuestion) {
        int targetIndex = -1;
        
        // does anything really need to be done
        if (targetQuestion.equals(newQuestion))
            return;
        
        // find index of target
        for (int i = 0; i < questions.size(); i++)
            if (questions.get(i).equals(targetQuestion))
                targetIndex = i;
        if (targetIndex < 0)
            return;
        
        questions.set(targetIndex, newQuestion);
        questionsToCourse.put(newQuestion, questionsToCourse.get(targetQuestion));
        questionsToCourse.remove(targetQuestion);
        Collections.sort(questions);
        fireContentsChanged(this, 0, questions.size()-1);
    }

    @Override
    public int getSize() {
        return questions.size();
    }

    @Override
    public Object getElementAt(int index) {
        return questions.get(index);
    }
    
    public String getCourseTitle(String question) {
        return questionsToCourse.get(question);
    }
    
    public Map<String,String> getQuestions() {
        return questionsToCourse;
    }
    
    public void removeAll() {
        if (questions.isEmpty())
            return;

        int oldSize = questions.size();
        questions.clear();
        questionsToCourse.clear();
        fireIntervalRemoved(this, 0, oldSize-1);
    }
    
    
    public boolean isEmpty() {
        return questions.isEmpty();
    }
    
//    public Problem getProblemAt(int index) {
//        if (questions != null)
//            return questions.get(index);
//        
//        return null;
//    }
    
//    public List<Problem> getProblems() {
//        return questions;
//    }
    
}
