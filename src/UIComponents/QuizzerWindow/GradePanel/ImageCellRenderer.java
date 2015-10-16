/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents.QuizzerWindow.GradePanel;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 * This {@link TableCellRenderer} displays an "image" icon as a {@link JLabel}
 * if the component's value is <code>true</code>, or a blank label if it is
 * <code>false</code>.
 * @author Armando
 */
public class ImageCellRenderer extends JLabel implements TableCellRenderer {
    
    public ImageIcon img = new ImageIcon(getClass().getResource(
            "/images/image_16.png"));

    @Override
    public Component getTableCellRendererComponent(
            JTable table
            , Object value
            , boolean isSelected
            , boolean hasFocus
            , int row
            , int column) {
        
        boolean hasImage = (Boolean)value;
        
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        
        this.setIcon((hasImage) ? img : null);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setOpaque(true);
        
        return this;
    }
    
}
