/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quizzer;

import UIComponents.DataWindow.DatabaseFrame;
import static java.lang.System.exit;

/**
 *
 * @author Armando
 */
public class Quizzer {
    public static QuizzerDB qDB;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/quizzer";
        String user = "root";
        String password = "";
        
        javax.swing.UIManager.put("TextField.font", new javax.swing.plaf.FontUIResource(
            new java.awt.Font("Dialog", 0, 18) ) );
        javax.swing.UIManager.put("OptionPane.buttonFont", new javax.swing.plaf.FontUIResource(
            new java.awt.Font("Dialog", 0, 16) ) );
        javax.swing.UIManager.put("OptionPane.font", new javax.swing.plaf.FontUIResource(
            new java.awt.Font("Dialog", 0, 18) ) );
        
        // fun colors!
//        javax.swing.UIManager.put("Panel.background", new java.awt.Color(
//            0, 0, 0 ) );
//        javax.swing.UIManager.put("List.background", new java.awt.Color(
//            0, 0, 0 ) );
//        javax.swing.UIManager.put("List.foreground", new java.awt.Color(
//            215, 215, 215 ) );
//        javax.swing.UIManager.put("TextField.background", new java.awt.Color(
//            0, 0, 0 ) );
//        javax.swing.UIManager.put("TextField.foreground", new java.awt.Color(
//            215, 215, 215 ) );
//        javax.swing.UIManager.put("TextArea.background", new java.awt.Color(
//            0, 0, 0 ) );
//        javax.swing.UIManager.put("TextArea.foreground", new java.awt.Color(
//            215, 215, 215 ) );
//        javax.swing.UIManager.put("Label.foreground", new java.awt.Color(
//            215, 215, 215 ) );
//        javax.swing.UIManager.put("Button.background", new java.awt.Color(
//            0, 0, 0 ) );
//        javax.swing.UIManager.put("Button.foreground", new java.awt.Color(
//            215, 215, 215 ) );
//        javax.swing.UIManager.put("CheckBox.background", new java.awt.Color(
//            0, 0, 0 ) );
//        javax.swing.UIManager.put("CheckBox.foreground", new java.awt.Color(
//            215, 215, 215 ) );
//        javax.swing.UIManager.put("OptionPane.background", new java.awt.Color(
//            0, 0, 0 ) );
//        javax.swing.UIManager.put("OptionPane.foreground", new java.awt.Color(
//            215, 215, 215 ) );
//        javax.swing.UIManager.put("ScrollBar.background", new java.awt.Color(
//            0, 0, 0 ) );
//        javax.swing.UIManager.put("ScrollPane.background", new java.awt.Color(
//            0, 0, 0 ) );
//        javax.swing.UIManager.put("ScrollPaneUI.background", new java.awt.Color(
//            0, 0, 0 ) );
//        javax.swing.JScrollPane pane = new javax.swing.JScrollPane();
//        
        
        
//        Database db = new Database(url, user, password);
//        QuizzerDB qDB;
//        db.openConnection();
//        db.initData();
//        HandlerFactory hFact = HandlerFactory.getHandlerFactory(qDB);
        
        HandlerFactory hFact;
        DatabaseFrame ui;
        
        qDB = new QuizzerDB(url, user, password);
        
//        db.openConnection();
        hFact = HandlerFactory.getHandlerFactory(qDB);
        hFact.getCoursesHandler().initCourses();
        hFact.getImagesHandler().initImages();
        hFact.getProblemsHandler().initProblems();
        
        ui = new DatabaseFrame();
        ui.pack();
        ui.setVisible(true);
        
//        ImageTester testFrame = new ImageTester(db);
        
        
//        TestingLists testFrame = new TestingLists(db);
//        testFrame.pack();
//        testFrame.setVisible(true);
        //db.closeConnection();
        
//        Connection con = null;
//        String url = "jdbc:mysql://localhost:3306/quizzer";
//        String user = "root";
//        String password = "";
//        String sql = "select * from Courses";
//        
//        try {
//            con = DriverManager.getConnection(url, user, password); // open connection to database
//            Statement stmt = con.createStatement(); // get a statement object from connection
//            ResultSet rs = stmt.executeQuery(sql); // tell stmt what to execute and get result
//            PreparedStatement pstmt = con.prepareStatement(sql);
//            pstmt.executeUpdate();
//            while (rs.next()) {                    // iterate through results
//                System.out.println(rs.getString("cid") + "\t" +
//                        rs.getString("title"));
//            }
//            rs.close();          // close result set
//            stmt.close();        // close statement
//        }
//        catch (SQLException ex) { System.out.println(ex.getMessage());/*Logger.getLogger(Quizzer.class.getName()).log(Level.SEVERE, null, ex);*/ }
//        finally {
//            if (con != null)
//                try { con.close(); } // close connection
//                catch (SQLException ex) { System.out.println(ex.getMessage());/*Logger.getLogger(Quizzer.class.getName()).log(Level.SEVERE, null, ex);*/ }
//        }
    }
    
    public static void exitQuizzer() {
        qDB.closeConnection();
        exit(0);
    }
    
}
