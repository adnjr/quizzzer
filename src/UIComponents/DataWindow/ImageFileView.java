/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents.DataWindow;

import java.io.File;
import javax.swing.Icon;
import javax.swing.filechooser.FileView;

/**
 *
 * @author Armando
 */
public class ImageFileView extends FileView {

//    @Override
//    public Boolean isTraversable(File f) {
//        return super.isTraversable(f); //To change body of generated methods, choose Tools | Templates.
//    }
    
    public ImageFileView() {
//        setFont(new java.awt.Font("Dialog", 0, 26));
    }

    @Override
    public Icon getIcon(File f) {
        return null;
    }

    @Override
    public String getTypeDescription(File f) {
        String extension = getExtension(f);
        if (extension != null) {

            if (extension.equals("jpg") || extension.equals("jpeg"))
                return "JPEG Image";
            if (extension.equals("gif"))
                return "GIF Image";
            if (extension.equals("tif") || extension.equals("tiff"))
                return "TIFF Image";
            if (extension.equals("png"))
                return "PNG Image";
        }
        
        return null;
    }

//    @Override
//    public String getDescription(File f) {
//        return super.getDescription(f); //To change body of generated methods, choose Tools | Templates.
//    }

//    @Override
//    public String getName(File f) {
//        return super.getName(f); //To change body of generated methods, choose Tools | Templates.
//    }
    
    private String getExtension(File f) {
        String result = null;
        int index;
        
        if ((index = f.getName().lastIndexOf('.')) > 0)
            result = f.getName().substring(index+1).toLowerCase();
        
        return result;
    }
    
}
