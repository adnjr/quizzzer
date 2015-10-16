package UIComponents.QuizzerWindow.GradePanel;

import static UIComponents.QuizzerWindow.GradePanel.GradeReportPanel.COLUMN_GRADE;
import static UIComponents.QuizzerWindow.GradePanel.GradeReportPanel.COLUMN_IMAGE;
import java.awt.Font;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * This {@link JTable} is for displaying the basic results of a graded quiz.
 * @author Armando
 * @see UIComponents.Models.GradedQuizTableModel
 */
public class GradeReportTable extends JTable {
    private static final Font defTableFont = new Font("Times New Roman", 1, 24);

    public GradeReportTable(TableModel model, GradeReportPanel parentPanel) {
        super(model);
        initTable(parentPanel);
    }
    
    private void initTable(GradeReportPanel parentPanel) {
        TableColumn gradeColumn;
        TableColumn imageColumn;
        ListSelectionModel rowSelModel;
        ListSelectionModel colSelModel;
        ListSelectionListener listener;
        
        // table-wide preferences
        this.setRowHeight(30);
        
        // table header preferences
        this.getTableHeader().setFont(defTableFont);
        
        // image column preferences
        imageColumn = this.getColumnModel().getColumn(COLUMN_IMAGE);
        imageColumn.setCellRenderer(new ImageCellRenderer());
        imageColumn.setMaxWidth(60);
        
        // grade column preferences
        gradeColumn = this.getColumnModel().getColumn(COLUMN_GRADE);
        gradeColumn.setCellRenderer(new GradeCellRenderer());
        gradeColumn.setMaxWidth(50);
        
        // add selection listeners
        rowSelModel = this.getSelectionModel();
        colSelModel = this.getColumnModel().getSelectionModel();
        listener = (ListSelectionEvent e) -> parentPanel.showMoreInfo();
        rowSelModel.addListSelectionListener(listener);
        colSelModel.addListSelectionListener(listener);
    }

    @Override
    protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel) {
            @Override
            public String getToolTipText(MouseEvent e) {
                int index = columnModel.getColumnIndexAtX(e.getPoint().x);
                if (index == GradeReportPanel.COLUMN_GRADE)
                    return "Grade";
                else
                    return null;
            }
        };
    }
    
}
