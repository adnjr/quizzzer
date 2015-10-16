package UIComponents.ImageViewer;

import DBObjects.ImageInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.AbstractListModel;

/**
 * This class stores a sorted set of {@link ImageInfo} objects, and displays
 * the captions in the list this model belongs to.
 * @author Armando
 */
public class ImagesListModel extends AbstractListModel {
    private final List<ImageInfo> images;

    public ImagesListModel() {
        this.images = new ArrayList<>();
    }
    
    public ImagesListModel(Set<? extends ImageInfo> images) {
        this.images = new ArrayList<>(images);
    }
    
    public List<ImageInfo> getImages() {
        return this.images;
    }
    
    public ImageInfo getImageAt(int index) {
        if (images != null && index >= 0 && index < images.size())
            return images.get(index);
        
        return null;
    }
    
    public void addImage(ImageInfo imageInfo) {
        if (imageInfo != null)
            this.images.add(imageInfo);
        this.fireIntervalAdded(this, this.images.size()-1, this.images.size()-1);
    }
    
    public void addImages(Set<? extends ImageInfo> imageInfos) {
        if (imageInfos == null)
            return;
        for (ImageInfo info : imageInfos)
            addImage(info);
    }
    
    public boolean removeImage(String caption) {
        int targetIndex = -1;
        
        for (int i = 0; i < images.size(); i++)
            if (images.get(i).getCaption().equals(caption))
                targetIndex = i;
        
        if (targetIndex < 0)
            return false;
        
        if (images.remove(targetIndex) != null)
            this.fireIntervalRemoved(this, targetIndex, targetIndex);
        
        return true;
    }
    
    /**
     * This method searches for the image with the target caption. If found,
     * the target image caption is refreshed in the list.
     * @param targetCaption The caption as it currently exists in the list.
     *        Note that this will have to be the newly-edited caption.
     */
    public void updateImage(String targetCaption) {
        int targetIndex = -1;
        
        for (int i = 0; i < images.size(); i++)
            if (images.get(i).getCaption().equals(targetCaption))
                targetIndex = i;
        
        if (targetIndex >= 0)
            this.fireContentsChanged(this, targetIndex, targetIndex);
    }
    
    public void removeAllImages() {
        int size;
        
        if (images == null || images.isEmpty())
            return;
        
        size = getSize();
        images.clear();
        this.fireIntervalRemoved(this, 0, size-1);
    }

    @Override
    public int getSize() {
        return this.images.size();
    }

    @Override
    public Object getElementAt(int index) {
        Iterator<? extends ImageInfo> iter;
        ImageInfo info;
        
        iter = images.iterator();
        for (int i = 0; i < index; i++)
            iter.next();
        info = iter.next();
        
        return info.getCaption();
    }
    
    public boolean isEmpty() {
        return getSize() == 0;
    }
    
}
