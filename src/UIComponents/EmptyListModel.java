/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents;

import javax.swing.AbstractListModel;

/**
 *
 * @author Armando
 */
public class EmptyListModel extends AbstractListModel {
    
    String[] strings = { };
    
    @Override
    public int getSize() {
        return strings.length;
    }
    
    @Override
    public Object getElementAt(int i) {
        return strings[i];
    }
    
}
