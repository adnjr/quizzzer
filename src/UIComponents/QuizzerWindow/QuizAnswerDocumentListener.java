/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents.QuizzerWindow;

import UIComponents.QuizzerWindow.QuizzerPanel;
import UIComponents.QuizzerWindow.SingleAnswerPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Armando
 * @see SingleAnswerPanel;
 */
public class QuizAnswerDocumentListener implements DocumentListener {
    private final JTextField textField;
    private final QuizzerPanel parentPanel;
    
    public QuizAnswerDocumentListener(JTextField textField, QuizzerPanel parentPanel) {
        this.textField = textField;
        this.parentPanel = parentPanel;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        checkSubmitButton();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        checkSubmitButton();
    }
    
    private void checkSubmitButton() {
        if (textField != null) {
            if (textField.getText().trim().length() != 0)
                parentPanel.setSubmitEnabled(true);
            else
                parentPanel.setSubmitEnabled(false);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
