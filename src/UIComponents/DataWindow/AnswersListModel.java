package UIComponents.DataWindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.swing.AbstractListModel;

/**
 *
 * @author Armando
 */
public class AnswersListModel extends AbstractListModel {
    private List<String> answers;
    
    public AnswersListModel(Collection<String> answers) {
        super();
        this.answers = new ArrayList<>();
        if (answers != null)
            this.answers.addAll(answers);
    }
    
    public AnswersListModel() {
        super();
        this.answers = new ArrayList<>();
    }
    
    public void addAnswer(String answer) {
        if (answer != null)
            this.answers.add(answer);
        this.fireIntervalAdded(this, this.answers.size()-1, this.answers.size()-1);
    }
    
    public void addAnswers(Collection<String> moreAnswers) {
        if (moreAnswers == null)
            return;
        
        for (String ans : moreAnswers)
            addAnswer(ans);
    }
    
    public boolean removeAnswer(String answer) {
        int targetIndex;
        
        if ((targetIndex = answers.indexOf(answer)) < 0)
            return false;
        
        if (answers.remove(answer))
            this.fireIntervalRemoved(this, targetIndex, targetIndex);
        
        return true;
    }
    
    /**
     * This method searches for the target answer. If found, the target answer
     * is refreshed in the list.
     * @param targetAnswer The caption as it currently exists in the list.
     *        Note that this will have to be the newly-edited caption.
     * @param newAnswer
     */
    public void updateAnswer(String targetAnswer, String newAnswer) {
        int targetIndex;
        
        if ((targetIndex = answers.indexOf(targetAnswer)) < 0)
            return;
        
        answers.set(targetIndex, newAnswer);
        
        if (targetIndex >= 0)
            this.fireContentsChanged(this, targetIndex, targetIndex);
    }
    
    public void removeAllAnswers() {
        int size;
        
        if (answers == null || answers.isEmpty())
            return;
        
        size = getSize();
        answers.clear();
        this.fireIntervalRemoved(this, 0, size-1);
    }
    
    public void setAnswers(Collection<String> newAnswers) {
        removeAllAnswers();
        addAnswers(newAnswers);
    }

    @Override
    public int getSize() {
        return answers.size();
    }

    @Override
    public Object getElementAt(int index) {
        return answers.get(index);
    }
    
}
