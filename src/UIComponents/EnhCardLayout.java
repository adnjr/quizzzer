/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents;

import java.awt.CardLayout;
import java.awt.Container;

/**
 *
 * @author Armando
 */
public class EnhCardLayout extends CardLayout {
    private int myindex = 0;
    public static int NEXT = 1;
    public static int PREV = -1;
    
    public int getIndex() {
        return myindex;
    }
    
    @Override
    public void next(Container parent) {
        super.next(parent);
        myindex++;
    }
    
    @Override
    public void previous(Container parent) {
        super.previous(parent);
        myindex--;
    }
    
    @Override
    public void first(Container parent) {
        super.first(parent);
        myindex = 0;
    }
    
    @Override
    public void last(Container parent) {
        super.last(parent);
        myindex = parent.getComponentCount() - 1;
    }
    
}
