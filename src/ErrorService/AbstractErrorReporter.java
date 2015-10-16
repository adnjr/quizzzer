package ErrorService;

import java.util.List;

/**
 *
 * @author Armando
 */
public abstract class AbstractErrorReporter implements ErrorReporter {
    private final ErrorService errorService = ErrorService.getService();
//    private final Set<ErrorListener> errorListeners = new HashSet<>();
//    private final List<String> errorMessages = new ArrayList<>();
    
//    @Override
//    public void registerErrorListener(ErrorListener listener) {
//        if (listener != null)
//            errorListeners.add(listener);
//    }
    
    public void reportError(String errorMessage) {
        errorService.reportError(errorMessage);
//        reportErrors(Collections.singletonList(errorMessage));
    }
    
    public   void reportErrors(List<String> errors) {
        errorService.reportErrors(errors);
//        errorMessages.addAll(errors);
//        for (ErrorListener listener : errorListeners)
//            listener.onError(errors);
    }
    
//    public boolean unregisterErrorListener(ErrorListener listener) {
//        if (listener != null)
//            return errorListeners.remove(listener);
//        return false;
//    }
    
//    protected void clearErrors() {
//        errorMessages.clear();
//    }
    
//    public void clearListeners() {
//        errorListeners.clear();
//    }
    
//    protected List<String> getErrors() {
//        return errorMessages;
//    }
}
