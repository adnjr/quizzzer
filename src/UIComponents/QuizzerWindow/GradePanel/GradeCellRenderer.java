/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents.QuizzerWindow.GradePanel;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 * This is designed to render boolean values with a green check-mark icon for
 * <code>true</code>, and a red x icon for <code>false</code>.
 * @author Armando
 */
public class GradeCellRenderer extends JLabel implements TableCellRenderer {
    public ImageIcon correct = new ImageIcon(getClass().getResource(
            "/images/check-mark_16_green.png"));
    public ImageIcon wrong = new ImageIcon(getClass().getResource(
            "/images/x_16_red.png"));
    public ImageIcon unknown = new ImageIcon(getClass().getResource(
            "/images/question-mark_16_1.png"));
    
    @Override
    public Component getTableCellRendererComponent(JTable table
            , Object value
            , boolean isSelected
            , boolean hasFocus
            , int row
            , int column) {
        
        Boolean isCorrect = (Boolean)value;
        
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        
        if (isCorrect == null)
            this.setIcon(unknown);
        else
            this.setIcon( (isCorrect) ? correct : wrong );
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setOpaque(true);
        
        return this;
    }
    
}
