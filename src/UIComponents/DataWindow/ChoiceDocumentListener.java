/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents.DataWindow;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Armando
 */
public class ChoiceDocumentListener implements DocumentListener {
    private JCheckBox checkbox;
    private JTextField textfield;
    
    public ChoiceDocumentListener(JCheckBox cb, JTextField tf) {
        super();
        this.checkbox = cb;
        this.textfield = tf;
    }
    
    public void setCheckBoxAndTextField(JCheckBox cb, JTextField tf) {
        this.checkbox = cb;
        this.textfield = tf;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (textfield.getText().trim().length() == 0) {
            checkbox.setSelected(false);
            checkbox.setEnabled(false);
        } else {
            checkbox.setEnabled(true);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (textfield.getText().trim().length() == 0) {
            checkbox.setSelected(false);
            checkbox.setEnabled(false);
        } else {
            checkbox.setEnabled(true);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        
    }
    
}
