/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIComponents.DataWindow;

import java.util.Iterator;
import javax.swing.AbstractListModel;
import quizzer.DatabaseInterface;
import quizzer.HandlerFactory;
import quizzer.ManageCoursesHandler;

/**
 *
 * @author Armando
 */
public class CoursesListModel extends AbstractListModel {
    
    public CoursesListModel() {
        super();
    }
    
    public void refreshModel_Add() {
        int size = this.getSize();
        this.fireIntervalAdded(this, size-1, size-1);
    }
    
    public void refreshModel_Delete() {
        int size = this.getSize();
        if (size > 0)
            this.fireIntervalRemoved(this, size-1, size-1);
        else
            this.fireIntervalRemoved(this, 0, 0);
    }

    @Override
    public int getSize() {
        return HandlerFactory.getHandlerFactory()
                .getCoursesHandler().getCourseTitles().size();
    }

    @Override
    public Object getElementAt(int index) {
        Iterator<String> courses = HandlerFactory.getHandlerFactory()
                .getCoursesHandler().getCourseTitles().iterator();
        
        for (int i = 0; i < index; i++)
            courses.next();
        
//        return db.getCourses().get(index);
        return courses.next();
    }
    
}
