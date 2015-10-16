/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents.QuizzerWindow.GradePanel;

import java.awt.Color;
import java.awt.Component;
import java.util.Collection;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Armando
 */
public class ChosenCellRenderer extends JLabel implements TableCellRenderer {
    private final ImageIcon icon = new ImageIcon(
            getClass().getResource("/images/black_16.png"));
    private Collection<String> correctAnswers;
    private List<String> choices;
    
    public ChosenCellRenderer(Collection<String> correctAnswers
            , List<String> choices) {
        this.correctAnswers = correctAnswers;
        this.choices = choices;
    }
    
    @Override
    public Component getTableCellRendererComponent(
            JTable table
            , Object value
            , boolean isSelected
            , boolean hasFocus
            , int row
            , int column) {
        
        boolean isChosen = (Boolean)value;
        int choiceIndex = -1;
        
        if (column == 0)
            choiceIndex = row;
        else if (column == 2)
            choiceIndex = row+3;
        
        if (choiceIndex >= 0 && choiceIndex < choices.size()) {
            if (isChosen && !correctAnswers.contains(choices.get(choiceIndex)))
                    setBackground(Color.red);
            else if (!isChosen && correctAnswers.contains(choices.get(choiceIndex)))
                    setBackground(Color.red);
            else
                setBackground(table.getBackground());
        } else
            setBackground(table.getBackground());
        
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setOpaque(true);
//        this.getInsets().set(4, 4, 4, 4);
        if (isChosen)
            this.setIcon(icon);
        else
            this.setIcon(null);
        
        return this;
    }
    
}
