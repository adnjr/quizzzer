/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents.QuizzerWindow.GradePanel;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author Armando
 */
public class ChoicesMissedTable extends JTable {
    private static final Font defTableFont = new Font("Times New Roman", 1, 24);
    
    public ChoicesMissedTable(List<String> userAnswers
            , Set<String> correctAnswers, List<String> choices) {
        super(new ChoicesMissedTableModel(userAnswers, correctAnswers, choices));
        initTable(userAnswers, correctAnswers, choices);
    }

//    public ChoicesMissedTable(TableModel model) {
//        super(model);
//        initTable();
//    }

    private void initTable(List<String> usrAns, Set<String> corrAns
            , List<String> choices) {
        
        TableColumn column;
        ChoiceCellRenderer choiceRenderer;
        
        setFont(defTableFont);
//        setCellSelectionEnabled(true);
        setRowHeight(30);
        
        // set column widths
        for (int i = 0; i < getColumnModel().getColumnCount(); i++)
            if (i % 2 == 0)
                getColumnModel().getColumn(i).setMaxWidth(25);
        
//        // boolean columns preferences
//        column = this.getColumnModel().getColumn(0);
//        column.setCellRenderer(new ChosenCellRenderer());
//        column = this.getColumnModel().getColumn(2);
//        column.setCellRenderer(new ChosenCellRenderer());
//        
//        // text columns preferences
//        choiceRenderer = new ChoiceCellRenderer(usrAns, corrAns);
//        choiceRenderer.setBackground(Color.red);
//        column = this.getColumnModel().getColumn(1);
//        column.setCellRenderer(choiceRenderer);
//        column = this.getColumnModel().getColumn(3);
//        column.setCellRenderer(choiceRenderer);
        
    }
    
}
