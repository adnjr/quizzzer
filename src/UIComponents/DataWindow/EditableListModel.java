package UIComponents.DataWindow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author Armando
 */
public class EditableListModel extends AbstractListModel {
    private final List<String> items;
    
    public EditableListModel() {
        super();
        items = new ArrayList<>();
    }
    
    public EditableListModel(Collection<String> items) {
        super();
        this.items = new ArrayList<>(items);
    }
    
    public List<String> getItems() {
        return items;
    }
    
    public void addItem(String newItem) {
        if (newItem == null || items.contains(newItem))
            return;
        
        items.add(newItem);
        fireIntervalAdded(this, items.size()-1, items.size()-1);
    }
    
    public void addItems(Collection<String> newItem) {
        if (newItem == null || newItem.isEmpty())
            return;
        
        for (String tag : newItem)
            addItem(tag);
    }
    
    public void removeItem(String targetItem) {
        int targetIndex;
        if (targetItem == null)
            return;
        
        targetIndex = items.indexOf(targetItem);
        if (targetIndex == -1)
            return;
        
        if (items.remove(targetItem))
            fireIntervalRemoved(this, targetIndex, targetIndex);
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public Object getElementAt(int index) {
        return items.get(index);
    }
}
