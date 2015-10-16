package UIComponents.QuizzerWindow;

import DBObjects.ImageIconInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;

/**
 * This <code>MutableComboBoxModel</code> contains a list of image captions.
 * Selecting a caption triggers the loading of the corresponding image into an
 * image viewer label.
 * @author Armando
 */
public class ImagesComboBoxModel extends AbstractListModel implements MutableComboBoxModel {
    private List<ImageIconInfo> imageIcons;
    private ImageIconInfo selectedItem;
    
    public ImagesComboBoxModel(SortedSet<ImageIconInfo> imageIcons) {
        if (imageIcons == null)
            throw new IllegalArgumentException("ImagesComboBoxModel constructor: imageIcons cannot be null.");
        
        this.imageIcons = new ArrayList<>();
        this.selectedItem = null;
        
        for (ImageIconInfo icon : imageIcons)
            this.imageIcons.add(icon);
    }
    
    @Override
    public int getSize() {
        return imageIcons.size();
    }

    @Override
    public Object getElementAt(int index) {
        return imageIcons.get(index).getCaption();
    }

    @Override
    public void addElement(Object item) {
        if (item != null && item instanceof ImageIconInfo) {
            imageIcons.add((ImageIconInfo)item);
            this.fireIntervalAdded(this, imageIcons.size()-1, imageIcons.size()-1);
        }
    }

    @Override
    public void removeElement(Object obj) {
        if (obj != null && obj instanceof ImageIconInfo)
            imageIcons.remove((ImageIconInfo)obj);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem == null)
            return;
        for (ImageIconInfo icon : imageIcons)
            if (icon.getCaption().equals((String)anItem))
                this.selectedItem = icon;
    }

    @Override
    public Object getSelectedItem() {
        if (selectedItem != null)
            return selectedItem.getCaption();
        
        return null;
    }
    
    public ImageIconInfo getSelectedImage() {
        return selectedItem;
    }
    
    @Override
    public void insertElementAt(Object item, int index) {
        imageIcons.add(index, (ImageIconInfo)item);
    }

    @Override
    public void removeElementAt(int index) {
        imageIcons.remove(index);
    }
    
    public void removeAllItems() {
        int lastIndex = imageIcons.size() - 1;
        imageIcons.clear();
        this.fireIntervalRemoved(this, 0, lastIndex);
    }
    
}
