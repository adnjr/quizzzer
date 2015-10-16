package UIComponents.QuizzerWindow;

import UIComponents.QuizzerWindow.GradePanel.GradeReportPanel;
import DBObjects.Problem;
import DBObjects.Quiz;
import java.awt.CardLayout;
import java.util.List;

/**
 *
 * @author Armando
 */
public class QuizzerFrame extends javax.swing.JFrame {
    private QuizzerPanel quizzerPanel;
    private final Quiz quiz;
    private GradeReportPanel gradeReportPanel;
    
    private static final String CARD_GRADE_REPORT = "Grade Report Panel";
    private static final String CARD_QUIZZER = "Quizzer Panel";
//    private final Font defFont = new Font("Dialog", 0, 18);

    
    /** Creates new form QuizzerFrame
     * @param problems **/
    public QuizzerFrame(List<Problem> problems) {
        this.quiz = new Quiz(problems);
        this.quizzerPanel = new QuizzerPanel(quiz, this);
        
       
        initComponents(); // note: this does: panelAnswer = cardsPanel
        initQuizStuff();
    }

    private QuizzerFrame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void initQuizStuff() {
        CardLayout cLayout;
        
        panelMain.add(quizzerPanel, CARD_QUIZZER);
//        panelMain.add(gradeReportPanel, CARD_GRADE_REPORT);
        cLayout = (CardLayout)panelMain.getLayout();
        cLayout.show(panelMain, CARD_QUIZZER);
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMain = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelMain.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void showGradeReport() {
        gradeReportPanel = new GradeReportPanel(this, quiz);
        panelMain.add(gradeReportPanel, CARD_GRADE_REPORT);
        CardLayout cLayout = (CardLayout)panelMain.getLayout();
        cLayout.show(panelMain, CARD_GRADE_REPORT);
    }
    
    public void retakeQuiz() {
        quizzerPanel.clearAnswers();
        quizzerPanel.firstProblem();
        CardLayout cLayout = (CardLayout)panelMain.getLayout();
        cLayout.show(panelMain, CARD_QUIZZER);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuizzerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuizzerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuizzerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuizzerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuizzerFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelMain;
    // End of variables declaration//GEN-END:variables
}
