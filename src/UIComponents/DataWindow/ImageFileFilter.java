/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents.DataWindow;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Armando
 */
public class ImageFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        String extension;
        
        if (f.isDirectory())
            return true;
        
        extension = getExtension(f);
        if (extension == null)
            return false;
        
        if (extension.equals("jpg") || extension.equals("jpeg")
                || extension.equals("tiff") || extension.equals("tif")
                || extension.equals("gif") || extension.equals("png")) {
            
            return true;
            
        }
        
        return false;
    }
    
    private String getExtension(File f) {
        String result = null;
        int index;
        
        if ((index = f.getName().lastIndexOf('.')) > 0)
            result = f.getName().substring(index+1).toLowerCase();
        
        return result;
    }

    @Override
    public String getDescription() {
        return "Images Only";
    }
    
}
