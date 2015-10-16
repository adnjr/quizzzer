package ErrorService;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Armando
 */
public class ErrorService {
    
    private static ErrorService errorService = null;
    private ErrorListener listener;
    
    public ErrorService() {
        listener = null;
    }
    
    public static ErrorService getService() {
        if (errorService == null)
            errorService = new ErrorService();
        return errorService;
    }
    
    public ErrorService(ErrorListener initialListener) {
        if (initialListener != null)
            listener = initialListener;
        else
            throw new NullPointerException();
    }
    
    public void setListener(ErrorListener newListener) {
        if (newListener != null)
            listener = newListener;
    }
    
    public void reportError(String errorMessage) {
        if (listener != null)
            listener.onError(Collections.singletonList(errorMessage));
        else
            System.err.println(errorMessage);
    }
    
    public void reportErrors(List<String> errorMessages) {
        listener.onError(errorMessages);
    }
    
}
