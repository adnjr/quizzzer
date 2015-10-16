/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBObjects;

import java.util.List;
import quizzer.DatabaseInterface;

/**
 *
 * @author Armando
 */
public class Data {
    private int dataType;
    private Object value;
//    private int intValue;
//    private double floatValue;
//    private String textValue;
//    private List<String> multValue;
    
    public Data(int dataType) {
        this.dataType = dataType;
    }
    
    public int getDataType() {
        return dataType;
    }
    
    public Object getValue() {
//        switch (dataType) {
//            case DatabaseInterface.INTEGER_PROBLEM:
//                return intValue;
//            case DatabaseInterface.FLOAT_PROBLEM:
//                return floatValue;
//            case DatabaseInterface.TEXT_PROBLEM:
//                return textValue;
//            case DatabaseInterface.MULTIPLECHOICE_PROBLEM:
//                return multValue;
//        }
        return value;
    }
    
}
