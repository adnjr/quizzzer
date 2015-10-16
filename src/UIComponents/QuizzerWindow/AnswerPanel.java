/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents.QuizzerWindow;

import java.util.List;

/**
 *
 * @author Armando
 */
public abstract class AnswerPanel extends javax.swing.JPanel {
    public abstract boolean hasAnswer();
    public abstract List<String> getAnswers();
    public abstract void clearAnswers();
}
