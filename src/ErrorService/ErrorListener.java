package ErrorService;

import java.util.List;

/**
 *
 * @author Armando
 */
public interface ErrorListener {
    
    /** Specifies what should happen when an error occurs **/
    public void onError(List<String> errorMessages);
    
}
