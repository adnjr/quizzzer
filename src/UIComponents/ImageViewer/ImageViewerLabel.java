/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents.ImageViewer;

import DBObjects.ImageIconInfo;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author Armando
 */
public class ImageViewerLabel extends JLabel {
    private boolean scaleImages;
    private ImageIconInfo scaledIcon;

    public ImageViewerLabel() {
        super();
        this.scaleImages = false;
    }
    
    public ImageViewerLabel(Icon image) {
        super(image);
        ImageIconInfo info = (ImageIconInfo)image;
        Image sclImg;
        sclImg = info.getImage().getScaledInstance(-1, 265, 0);
        this.scaledIcon = new ImageIconInfo(sclImg, info.getImageID(), info.getCaption());
        this.scaleImages = false;
    }
    
    public boolean isImageScaled() {
        return this.scaleImages;
    }
    
    public void setScaledImages(boolean bool) {
        this.scaleImages = bool;
    }
    
    @Override
    public Icon getIcon() {
        if (isImageScaled())
            return this.scaledIcon;
        return super.getIcon();
    }

    @Override
    public void setIcon(Icon icon) {
        
//        if (icon instanceof ImageIconInfo && scaleImages) {
//            ImageIconInfo imageIcon = (ImageIconInfo)icon;
//            imageIcon.setImage(imageIcon.getImage().getScaledInstance(-1, 265, 0));
//            super.setIcon(imageIcon);
//        }
//        else
            super.setIcon(icon);
            if (icon != null) {
                ImageIconInfo info = (ImageIconInfo)icon;
                Image sclImg = info.getImage().getScaledInstance(-1, 265, 0);
                this.scaledIcon = new ImageIconInfo(sclImg, info.getImageID(), info.getCaption());
            }
    }
    
    
}
