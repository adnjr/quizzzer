package UIComponents.QuizzerWindow.GradePanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Armando
 */
public class ChoicesMissedTableModel extends AbstractTableModel {
    private List<String> userAnswers;
    private List<String> correctAnswers;
    private List<String> choices;
    private final static int ROW_COUNT = 3;
    private final static int COL_COUNT = 4;
    
    public ChoicesMissedTableModel(List<String> userAnswers
            , Set<String> correctAnswers, List<String> choices) {
        this.userAnswers = userAnswers;
        this.correctAnswers = new ArrayList<>();
        if (correctAnswers != null)
            for (String ans : correctAnswers)
                this.correctAnswers.add(ans);
        this.choices = choices;
    }
    
    public void setInfo(List<String> userAnswers
            , Set<String> correctAnswers, List<String> choices) {
        
        if (choices != null)
            this.fireTableRowsDeleted(0, 2);
        this.userAnswers = userAnswers;
        this.correctAnswers = new ArrayList<>(correctAnswers);
        this.choices = choices;
        this.fireTableRowsInserted(0, 2);
    }
    
    @Override
    public int getRowCount() {
        return ROW_COUNT;
    }

    @Override
    public int getColumnCount() {
        return COL_COUNT;
    }
    
    @Override
    public String getColumnName(int col) {
        return "";
    }
    
    @Override
    public Class getColumnClass(int c) {
        if (c % 2 == 0)
            return boolean.class;
        
        return String.class;
//        return getValueAt(0, c).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        if (choices == null || userAnswers == null || correctAnswers == null)
            return "";
        
        try {
            // first column (answers)
            if (rowIndex == 0 && columnIndex == 0)
                return userAnswers.contains(choices.get(0));
            if (rowIndex == 1 && columnIndex == 0)
                return choices.get(1) != null
                        && userAnswers.contains(choices.get(1));
            if (rowIndex == 2 && columnIndex == 0)
                return choices.get(2) != null
                        && userAnswers.contains(choices.get(2));

            // second column (choices)
            if (rowIndex == 0 && columnIndex == 1)
                return choices.get(0);
            if (rowIndex == 1 && columnIndex == 1)
                return (choices.get(1) == null) ? "" : choices.get(1);
            if (rowIndex == 2 && columnIndex == 1)
                return (choices.get(2) == null) ? "" : choices.get(2);

            // third column (answers)
            if (rowIndex == 0 && columnIndex == 2)
                return choices.get(3) != null
                        && userAnswers.contains(choices.get(3));
            if (rowIndex == 1 && columnIndex == 2)
                return choices.get(4) != null
                        && userAnswers.contains(choices.get(4));
            if (rowIndex == 2 && columnIndex == 2)
                return choices.get(5) != null
                        && userAnswers.contains(choices.get(5));

            // fourth column (choices)
            if (rowIndex == 0 && columnIndex == 3)
                return (choices.get(3) == null) ? "" : choices.get(3);
            if (rowIndex == 1 && columnIndex == 3)
                return (choices.get(4) == null) ? "" : choices.get(4);
            if (rowIndex == 2 && columnIndex == 3)
                return (choices.get(5) == null) ? "" : choices.get(5);
        } catch (IndexOutOfBoundsException e) {
            return (columnIndex%2==0) ? false : "";
        }
        
        return null;
    }
    
}
