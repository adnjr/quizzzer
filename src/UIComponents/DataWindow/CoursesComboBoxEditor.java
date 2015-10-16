package UIComponents.DataWindow;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxEditor;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 *
 * @author Armando
 */
public class CoursesComboBoxEditor extends BasicComboBoxEditor implements ComboBoxEditor {
    private JTextField myEditor;
    
    @Override
    public Component getEditorComponent() {
        return myEditor;
    }

    @Override
    public void setItem(Object anObject) {
        myEditor.setText((String)anObject);
    }

    @Override
    public Object getItem() {
        return myEditor.getText();
    }

    @Override
    public void selectAll() {
        myEditor.selectAll();
        myEditor.requestFocusInWindow();
    }

    @Override
    public void addActionListener(ActionListener l) {
        myEditor.addActionListener(l);
    }

    @Override
    public void removeActionListener(ActionListener l) {
        myEditor.removeActionListener(l);
    }
    
}
