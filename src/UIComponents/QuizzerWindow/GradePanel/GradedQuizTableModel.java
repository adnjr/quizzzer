package UIComponents.QuizzerWindow.GradePanel;

import DBObjects.Quiz;
import DBObjects.QuizProblem;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import quizzer.DatabaseInterface;

/**
 * A {@link TableModel} designed for use with a table showing the results of a
 * graded quiz, such as {@link GradeReportTable}.
 * @author Armando
 * @see GradeReportTable
 * @see GradeCellRenderer
 * @see ImageCellRenderer
 */
public class GradedQuizTableModel extends AbstractTableModel {
    private final Quiz quiz;
    private static final int NUM_COLUMNS = 6;
    private static final int COL_INDEX_QUESTION = 0;
    private static final int COL_INDEX_USER_ANSWER = 1;
    private static final int COL_INDEX_CORRECT_ANSWER = 2;
    private static final int COL_INDEX_EXPLANATION = 3;
    private static final int COL_INDEX_IMAGE = 4;
    private static final int COL_INDEX_IS_CORRECT = 5;
    private static final String COL_NAME_0 = "Question";
    private static final String COL_NAME_1 = "Your Answer";
    private static final String COL_NAME_2 = "Correct Answer";
    private static final String COL_NAME_3 = "Explanation";
    private static final String COL_NAME_4 = "Img";
    private static final String COL_NAME_5 = "";
    private static final String[] COLUMN_NAMES = {
        COL_NAME_0, COL_NAME_1, COL_NAME_2, COL_NAME_3, COL_NAME_4, COL_NAME_5 };
    private static final String MULT_TEXT = "[click]";

    public GradedQuizTableModel(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public int getRowCount() {
        return quiz.getSize();
    }

    @Override
    public int getColumnCount() {
        return NUM_COLUMNS;
    }
    
    @Override
    public String getColumnName(int col) {
        return COLUMN_NAMES[col];
    }
    
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        QuizProblem qProblem = quiz.getProblems().get(rowIndex);
        
        // queston column
        if (columnIndex == COL_INDEX_QUESTION)
            return qProblem.getQuestion();
        
        // user answer column
        else if (columnIndex == COL_INDEX_USER_ANSWER) {
        
            // multiple choice problem
            if (qProblem.isMultipleChoice())
                return MULT_TEXT;
            
            // non-multiple choice
            return (qProblem.hasUserAnswer())
                    ? qProblem.getUserAnswers().get(0) : "";
        }
        
        // correct answer column
        else if (columnIndex == COL_INDEX_CORRECT_ANSWER) {
            
            // multiple choice problem
            if (qProblem.isMultipleChoice())
                return MULT_TEXT;
            
            // non-multiple choice
            return qProblem.getCorrectAnswers().iterator().next();
        }
        
        // explanation column
        else if (columnIndex == COL_INDEX_EXPLANATION)
            return qProblem.getExplanation();
        
        // image column
        else if (columnIndex == COL_INDEX_IMAGE) {
            return ! qProblem.getImages().isEmpty();
        }
        
        // grade column
        else if (columnIndex == COL_INDEX_IS_CORRECT)
            return qProblem.isCorrect();
        
        throw new IllegalArgumentException("GradedQuizTableModel.getValueAt: invalid column index of " + columnIndex);
    }
    
}
