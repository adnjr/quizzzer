/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ErrorService;

import java.util.List;

/**
 *
 * @author Armando
 */
public interface ErrorReporter {
    
    public void reportError(String error);
    public void reportErrors(List<String> errors);
    
}
