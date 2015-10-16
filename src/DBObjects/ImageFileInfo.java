/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBObjects;

import DBObjects.ImageInfo;
import java.io.File;
import quizzer.HandlerFactory;
import quizzer.ManageImagesHandler;

/** This ImageFileData object simply holds a File object, a String caption, and an integer
 * ID. The File should be an image file. All three fields are intended to be required fields.
 * However, due to the way the DatabaseInterface works, this ImageFileData object can be
 * created with the ID missing. The ID must be added before this ImageFileData object can be
 * successfully passed to a DatabaseInterface method, e.g. to add an image to the
 * memory/database.
 *
 * @author Armando
 */
public class ImageFileInfo implements Comparable<ImageInfo>, ImageInfo {
    private File imageFile;
    private String caption;
    private int id;
    
    /** Initializes ImageFileData object with all of its properties. **/
    public ImageFileInfo(File imageFile, int id, String caption) {
        this.imageFile = imageFile;
        this.caption = caption;
        this.id = id;
    }
    
    /** Initializes ImageFileData with it's ID property missing. The ID property should be
     * added before passing the ImageFileData object to any DatabaseInterface methods.
     * Use ImageFileData.setImageID method to add the ID. **/
    public ImageFileInfo(File imageFile, String caption) {
        this(imageFile, -1, caption);
    }

    public File getImageFile() {
        return this.imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public String getCaption() {
        return this.caption;
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public int getImageID() {
        return this.id;
    }

    @Override
    public void setImageID(int id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final ImageFileInfo other = (ImageFileInfo) obj;
        return this.caption.equals(other.getCaption());
    }

    @Override
    public int compareTo(ImageInfo o) {
        if (o == null)
            return 1;
        
        return this.getCaption().compareToIgnoreCase(o.getCaption());
    }
    
    
}
