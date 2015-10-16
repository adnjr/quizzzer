package UIComponents.DataWindow;

import java.awt.Component;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import quizzer.HandlerFactory;
import quizzer.ManageProblemsHandler;

/**
 * Renders a cell showing answers to a problem.
 * @author Armando
 */
public class AnswersCellRenderer extends JLabel implements ListCellRenderer {
    private final ManageProblemsHandler pHandler;
    private final String course;
    private final String question;

    public AnswersCellRenderer (String courseTitle, String question) {
        pHandler = HandlerFactory.getHandlerFactory().getProblemsHandler();
        this.course = courseTitle;
        this.question = question;
        setOpaque(true);
        this.setHorizontalTextPosition(SwingConstants.TRAILING);
        this.setIconTextGap(15);
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index
            , boolean isSelected, boolean cellHasFocus) {
        
        Set<String> answers;
                
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        
        answers = pHandler.getCorrectAnswers(course, question);
        
        if (answers.contains((String)value))
            setIcon(new ImageIcon(getClass().getResource("/images/check-mark_small_blue.gif")));
        else
            setIcon(new ImageIcon(getClass().getResource("/images/blank-mark_small.gif")));
        
        setText((String)value);
        setFont(list.getFont());

        return this;
    }
    
}
