package UIComponents.DataWindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author Armando
 */
public class TagsListModel extends AbstractListModel {
    private final List<String> tags;
    
    public TagsListModel(Collection<String> tags) {
        super();
        this.tags = new ArrayList<>(tags);
    }
    
    public void addTag(String newTag) {
        if (newTag == null || tags.contains(newTag))
            return;
        
        tags.add(newTag);
        fireIntervalAdded(this, tags.size()-1, tags.size()-1);
    }
    
    public void removeTag(String targetTag) {
        int targetIndex;
        if (targetTag == null)
            return;
        
        targetIndex = tags.indexOf(targetTag);
        if (targetIndex == -1)
            return;
        
        if (tags.remove(targetTag))
            fireIntervalRemoved(this, targetIndex, targetIndex);
    }

    @Override
    public int getSize() {
        return tags.size();
    }

    @Override
    public Object getElementAt(int index) {
        return tags.get(index);
    }
}
