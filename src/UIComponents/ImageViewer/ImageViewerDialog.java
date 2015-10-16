/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents.ImageViewer;

import DBObjects.ImageIconInfo;
import java.awt.Frame;
import java.util.Set;
import java.util.SortedSet;
import javax.swing.JDialog;

/**
 * This is a <code>JDialog</code> that shows a {@link ViewImagesPanel}. The
 * set of images this panel is intended to display can be accessed via setters
 * and getters directly through this <code>ImageViewerDialog</code> object.
 * @author Armando
 * @see ViewImagesPanel
 */
public class ImageViewerDialog extends JDialog {
    private final ViewImagesPanel panel;
    
    public ImageViewerDialog(Frame aFrame, Set<ImageIconInfo> imageIcons) {
        super(aFrame, true);
        
        panel = new ViewImagesPanel(this, imageIcons);
        setContentPane(panel);
        setModal(false);
        setTitle("Image Viewer");
    }
    
    public void setImages(Set<ImageIconInfo> imageIcons) {
        panel.setImages(imageIcons);
    }
    
    
    public Set<ImageIconInfo> getImages() {
        return panel.getImages();
    }
}
