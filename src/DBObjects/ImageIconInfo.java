package DBObjects;

import java.awt.Image;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;

/**
 *
 * @author Armando
 */
public class ImageIconInfo extends ImageIcon implements Comparable<ImageInfo>, ImageInfo {
    private int id;
    private String caption;
    private Set<Problem> associatedProblems;
    private final ImageIconInfo scaledImage;
    
    /**
     * Creates new ImageIconInfo object, which extends ImageIcon.
     * @param image The image used to create this ImageIconInfo object.
     * @param imageID The ID of the image obtained from database, or -2 if this
     * object is meant to represent a scaled image derived from another image
     * (prevents infinite loop).
     * @param caption The caption for this ImageIconInfo object.
     */
    public ImageIconInfo(Image image, int imageID, String caption) {
        super(image);
        if (imageID != -2 && imageID != -1) {
            int maxHeight = 345;
            int maxWidth = 506;
            int imageWidth = this.getIconWidth();
            int imageHeight = this.getIconHeight();
            int scaledWidth;
            int scaledHeight;
            
            double dividend = imageHeight / maxHeight;
            if (maxHeight < imageHeight) {
                if (imageWidth / dividend < maxWidth) {
                    scaledWidth = (int)Math.round(imageWidth / dividend);
                    scaledHeight = maxHeight;
                } else {
                    dividend = imageWidth / maxWidth;
                    scaledWidth = maxWidth;
                    scaledHeight = (int)Math.round(imageHeight / dividend);
                }
                scaledImage = new ImageIconInfo(
                        image.getScaledInstance(scaledWidth, scaledHeight, 0)
                        , -2, caption);
            }
            
            else if (maxWidth < imageWidth) {
                dividend = imageWidth / maxWidth;
                if (imageHeight / dividend < maxHeight) {
                    scaledWidth = maxWidth;
                    scaledHeight = (int)Math.round(imageHeight / dividend);
                } else {
                    dividend = imageHeight / maxHeight;
                    scaledWidth = (int)Math.round(imageWidth / dividend);
                    scaledHeight = maxHeight;
                }
                scaledImage = new ImageIconInfo(
                        image.getScaledInstance(scaledWidth, scaledHeight, 0)
                        , -2, caption);
            }
            
            else {
                scaledImage = null;
            }
            
//            if (this.getIconWidth() / dividend < minWidth) {
//                scaledWidth = (int)Math.round(this.getIconWidth() / dividend);
//                scaledHeight = minHeight;
//                
//            }
//            
//            else {
//                dividend = this.getIconWidth() / minWidth;
//                scaledHeight = (int)Math.round(this.getIconHeight() / dividend);
//                scaledWidth = minWidth;
//            }
            
//            scaledImage = new ImageIconInfo(
//                    image.getScaledInstance(-1, 506, 0), -2, caption);
//                        image.getScaledInstance(scaledWidth, scaledHeight, 0), -2, caption);
        }
        else
            scaledImage = null;
        
        if (caption == null || caption.trim().length() == 0)
            throw new IllegalArgumentException("A DBImage object must have non-empty caption.");
        
        this.id = imageID;
        this.caption = caption.trim();
        this.associatedProblems = new HashSet<>();
    }
    
    public ImageIconInfo(Image image, String caption) {
        this(image, -1, caption);
    }
    
//    public void setScaled(boolean bool) {
//        if (bool)
//            super(this.getImage().g);
//    }
    
    public Set<Problem> getAssociatedProblems() {
        return associatedProblems;
    }
    
    public int getNumAssociatedProblems() {
        return this.associatedProblems.size();
    }
    
    public void associateProblem(Problem problem) {
        if (problem != null)
            associatedProblems.add(problem);
        else
            System.err.println("Cannot associate empty problem with an image.");
    }
    
    /** removes image from all problems and all problems from this image **/
    public void disassociateProblems() {
        for (Problem problem : associatedProblems)
            problem.removeImage(this);
        associatedProblems.clear();
    }
    
    /** Note that this method does not remove the image from the problem
     * itself.
     * @param problem The target problem will be removed from this image's list
     * of associated problems. **/
    public void disassociateProblem(Problem problem) {
        associatedProblems.remove(problem);
    }
    
    @Override
    public int getImageID() {
        return this.id;
    }
    
    @Override
    public String getCaption() {
        return this.caption;
    }
    
    public ImageIconInfo getScaledImage() {
        return scaledImage;
    }
    
    @Override
    public void setCaption(String newCaption) {
        if (newCaption != null && newCaption.length() > 0)
            this.caption = newCaption;
        else
            System.err.println("Caption cannot be empty or null.");
    }
    
    @Override
    public void setImageID(int id) {
        if (id < 1)
            throw new IllegalArgumentException("DBImage.setImageID: invalid id: it must be > 0");
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final ImageIconInfo other = (ImageIconInfo) obj;
        return this.caption.equals(other.getCaption());
    }

    @Override
    public int compareTo(ImageInfo o) {
        if (o == null)
            return 1;
//        return this.id - o.id;
        return this.getCaption().compareToIgnoreCase(o.getCaption());
    }
    
}
