/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quizzer;

import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author Armando
 */
public class Utils {
    
    public static void showCard(JPanel panel, String cardName) {
        CardLayout cLayout;
        
        cLayout = (CardLayout)panel.getLayout();
        cLayout.show(panel, cardName);
    }
    
}
