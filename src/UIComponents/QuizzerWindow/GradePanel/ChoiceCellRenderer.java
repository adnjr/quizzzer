/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents.QuizzerWindow.GradePanel;

import java.awt.Color;
import java.awt.Component;
import java.util.Collection;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Armando
 */
public class ChoiceCellRenderer extends JLabel implements TableCellRenderer {
    private Collection<String> correctAnswers;
    private Collection<String> userAnswers;
    
    public ChoiceCellRenderer(Collection<String> userAnswers
            , Collection<String> correctAnswers) {
        this.userAnswers = userAnswers;
        this.correctAnswers = correctAnswers;
    }

    
    
    @Override
    public Component getTableCellRendererComponent(
            JTable table
            , Object value
            , boolean isSelected
            , boolean hasFocus
            , int row
            , int column) {
        
        boolean isCorrect;
        boolean chosenByUser;
        
        String val = (String)value;
        chosenByUser = userAnswers.contains(val);
        
        isCorrect = !((chosenByUser && !correctAnswers.contains(val))
                || (!chosenByUser && correctAnswers.contains(val)));
        
        setFont(new java.awt.Font("Times New Roman", 0, 24));
        setText("  " + (String)value);
        setBackground((isCorrect) ? table.getBackground() : Color.red);
        setOpaque(true);
        return this;
        
    }
    
}
