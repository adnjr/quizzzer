package utilities.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JOptionPane;

/**
 *
 * @author Armando
 */
public class UIUtils {
    
    private static final String HEAD = "<html><span style='font-size: 20pt'>"; 
    private static final String TAIL = "</span></html>";
    
    public static boolean confirmDialog(Component parentComponent
            , String message, String title) {
        return 0 == JOptionPane.showOptionDialog
                    (parentComponent
                    , HEAD + message + TAIL
                    , title
                    , JOptionPane.YES_NO_OPTION
                    , JOptionPane.QUESTION_MESSAGE
                    , null
                    , null
                    , null);
    }
    
    public static boolean confirmDialog(Component parentComponent
            , String message, String title, String[] buttonMsgs) {
        return 0 == JOptionPane.showOptionDialog
                    (parentComponent
                    , HEAD + message + TAIL
                    , title
                    , JOptionPane.YES_NO_OPTION
                    , JOptionPane.QUESTION_MESSAGE
                    , null
                    , buttonMsgs
                    , null);
    }
    
    public static String inputDialog(Component parentComponent, String msg
            , String title, String defaultText) {
        
        return (String)JOptionPane.showInputDialog(parentComponent
            , HEAD + msg + TAIL
            , title, JOptionPane.PLAIN_MESSAGE, null, null, defaultText);
    }
    
    public static String inputDialog(Component parentComponent, String msg
            , String title) {
        
        return inputDialog(parentComponent, msg, title, null);
    }
    
    public static int optionDialog(Component parentComponent, String msg
            , String title, String option0, String option1) {
        return JOptionPane.showOptionDialog(
                parentComponent
                , HEAD + msg + TAIL 
                , title
                , JOptionPane.YES_NO_OPTION
                , JOptionPane.WARNING_MESSAGE
                , null
                , new String[] { option0, option1 }
                , null);
    }
    
    public static int choiceDialog(Component parentComponent
            , String message, String title) {
        return JOptionPane.showOptionDialog
                    (parentComponent
                    , (message==null) ? null : HEAD + message + TAIL
                    , title
                    , JOptionPane.YES_NO_CANCEL_OPTION
                    , JOptionPane.QUESTION_MESSAGE
                    , null
                    , null
                    , null);
    }
    
    public static int choiceDialog(Component parentComponent
            , String message, String title, String[] buttonMsgs
            , String defaultSelection) {
        return JOptionPane.showOptionDialog
                    (parentComponent
                    , (message==null) ? null : HEAD + message + TAIL
                    , title
                    , JOptionPane.YES_NO_CANCEL_OPTION
                    , JOptionPane.QUESTION_MESSAGE
                    , null
                    , buttonMsgs
                    , defaultSelection);
    }
    
    public static void setComponentFont(Component[] components) {
        Font font = new Font("Dialog", 0, 18);
        
        for (Component comp : components) {
            if (comp instanceof Container) {
                Container cont = (Container)comp;
                setComponentFont(cont.getComponents());
            }
            comp.setFont(font);
        }
    }
    
}
