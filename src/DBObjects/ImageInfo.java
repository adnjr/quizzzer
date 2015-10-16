/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBObjects;

/**
 *
 * @author Armando
 */
public interface ImageInfo {
    public int getImageID();
    public String getCaption();
    public void setImageID(int newID);
    public void setCaption(String newCaption);
}
