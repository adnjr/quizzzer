package UIComponents;

/**
 * A class implementing this interface represents a director that publishes
 * events to subscribers.
 * @author Armando
 */
public interface DirectorPublisher {
    public static int FAILURE = 0;
    public static int SUCCESS = 1;
    
    public void addSubscriber(DirectorSubscriber subscriber);
    
    /**
     * Notifies any <code>DirectorSubscriber</code>s of an event. An integer
     * constant is passed along to indicate the nature of the event. The
     * integer may be ignored by implementing methods.
     * @param eventIndicator An integer constant defined by the
     * <code>DirectorPublisher</code>. It can indicate the nature of the
     * triggering event. <code>DirectorPublisher.FAILURE</code> and
     * <code>DirectorPublisher.SUCCESS</code> are defined by default.
     */
    public void notifySubscribers(int eventIndicator);
}
