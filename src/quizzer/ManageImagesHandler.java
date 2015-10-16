package quizzer;

import ErrorService.AbstractErrorReporter;
import DBObjects.ImageFileInfo;
import DBObjects.ImageIconInfo;
import DBObjects.ImageInfo;
import DBObjects.Problem;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 *
 * @author Armando
 */
public class ManageImagesHandler extends AbstractErrorReporter {
    private Set<ImageIconInfo> images;
    private final QuizzerDB qDB;
    
    private final String IMAGE_NOT_FOUND = "Unable to find target image with id: ";
    private final String INVALID_ID = "Image ID is invalid: ";
    private final String NO_ICON = "Image icon is null.";
    
    public ManageImagesHandler(QuizzerDB quizzerDB) {
        this.images = null;
        this.qDB = quizzerDB;
    }
    
    public void initImages() {
        BufferedImage bufferedImage = null;
        int imageID;
        String caption;
        byte[] imageBytes;
        
        images = new HashSet<>();
        for (Map<String,Object> result : qDB.getImages()) {
            imageID = (Integer)result.get(QuizzerDB.IMG_ID);
            caption = (String)result.get(QuizzerDB.IMG_CAPTION);
            imageBytes = (byte[])result.get(QuizzerDB.IMG_CONTENT);
            
            try { bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes)); }
            catch (IOException e) { reportError("Unable to create image: " + caption); }
            
            if (bufferedImage != null)
                images.add(new ImageIconInfo(bufferedImage, imageID, caption));
        }
    }
    
    /**
     * Processes the specified <code>infos</code> by adding each image to the
     * database if it is an image file or confirming it exists in memory if it
     * is an image icon. In either case, the IDs of the images are returned.
     * @param infos Must either be <code>ImageIconInfo</code> or
     * <code>ImageFileInfo</code> objects. A mix of both is fine.
     * @return The image IDs of the newly added or existing images.
     * @see ImageIconInfo
     * @see ImageFileInfo
     */
    public Set<Integer> addImages(Collection<? extends ImageInfo> infos) {
        Set<Integer> ids = new HashSet<>();
        int id;
        
        if (infos != null)
            for (ImageInfo info : infos) {
                id = addImage(info);
                if (id != -1)
                    ids.add(id);
                else
                    System.err.println("invalid image id: " + id);
            }
        
        return ids;
    }
    
    public int addImage(ImageInfo info) {
        int id = info.getImageID();
        
        // new image: add it to database, then collect ID
        if (info instanceof ImageFileInfo)
            return this.addImage((ImageFileInfo)info);
        
        // existingimage: make sure ID matches existing image, then collect ID
        if (info instanceof ImageIconInfo)
            return this.getImage(id).getImageID();
        
        reportError("ManageImagesHandler.addImage: info is not an ImageInfo object.");
        return -1;
    }
    
    private int addImage(ImageFileInfo fileInfo) {
        ImageIconInfo imageIcon;
        File imageFile;
        String caption;
        int imageID;
        
        if (fileInfo == null)
            return -1;
        
        imageFile = fileInfo.getImageFile();
        caption = fileInfo.getCaption();
        
        if (imageFile == null || caption == null || caption.trim().length() == 0) {
            reportError("Image file and caption cannot be empty.");
            return -1;
        }
        
        // add to database and get image ID
        if (( imageID = qDB.addImage(imageFile, caption) ) < 1)
            return -1;
        
        // convert image file to an image object and add it to memory
        imageIcon = convertImage(imageFile, imageID, caption);
        if (imageIcon == null)
            return -1;
        if (!this.images.add(imageIcon))
            return -1;
        
        return imageID;
    }
    
    public boolean deleteImage(int imageID) {
        ImageIconInfo image;
        
        // does image exist
        if ((image = getImage(imageID)) == null)
            return false;
        
        // delete from database
        if (!qDB.deleteImage(imageID))
            return false;
        
        // delete from memory
        image.disassociateProblems();
        return images.remove(image);
    }
    
    public Set<ImageIconInfo> getImages() {
        return images;
    }
    
    /**
     * Returns the image icons that have the corresponding image IDs.
     * @param imageIDs The IDs of the images to obtain.
     * @return A set of <code>ImageIconInfo</code> objects with the matching
     *         image IDs, or an empty set if no matches were found.
     */
    public Set<ImageIconInfo> getImages(Collection<Integer> imageIDs) {
        Set<ImageIconInfo> imageIcons = new HashSet<>();
        
        for (Integer id : imageIDs) {
            ImageIconInfo img = getImage(id);
            if (img != null)
                imageIcons.add(img);
            else
                reportError(IMAGE_NOT_FOUND + id);
        }
            
        return imageIcons;
    }
    
    /** Looks for the image with the specified ID.
     * @param targetImageID The ID of the target image.
     * @return The <code>ImageIconInfo</code> object with the matching ID, or
     *         null if not found. **/
    public ImageIconInfo getImage(int targetImageID) {
        for (ImageIconInfo image : images)
            if (image.getImageID() == targetImageID)
                return image;
        
        reportError(IMAGE_NOT_FOUND + targetImageID);
        return null;
    }
    
    public boolean setCaption(ImageInfo image, String newCaption) {
        
        // image exists; update existing image
        if (image.getImageID() > 0)
            return setCaption(image.getImageID(), newCaption);
        
        // image does not yet exist; update the image icon itself
        image.setCaption(newCaption);
        return true;
    }
    
    public boolean setCaption(int imageID, String newCaption) {
        ImageIconInfo image;
        
        // does image exist
        if ( (image = getImage(imageID)) == null)
            return false;
        
        if (image.getCaption().equals(newCaption))
            return false;
            
        // change caption in database
        if (!qDB.setCaption(imageID, newCaption))
            return false;

        // change caption in memory
        image.setCaption(newCaption);
        return true;
    }
    
    public int getNumAssociated(int imageID) {
        ImageIconInfo image;
        
        // does image exist
        if ((image = getImage(imageID)) == null)
            return -1;
        
        return image.getNumAssociatedProblems();
    }
    
    public boolean associateProblemWithImages(Set<Integer> imageIDs, Problem problem) {
        /* Note: this action only needs to be done on the data in memory, and not in the
         * database as well, since the problem is associated with the images in
         * the database at the time the problem is added to the database.*/
        ImageIconInfo img;
        boolean success = true;
        
        for (Integer id : imageIDs) {
            
            if ((img = getImage(id)) == null)
                success = false;
            
            else
                img.associateProblem(problem);
            
        }
        return success;
    }
    
    private ImageIconInfo convertImage(File imageFile, int imageID, String caption) {
        BufferedImage bufferedImage;
        
        try { bufferedImage = ImageIO.read(imageFile); }
        catch (IOException e) {
            reportError(e.getMessage());
            return null;
        }
        
        return new ImageIconInfo(bufferedImage, imageID, caption);
    }
}
