package UIComponents;

/**
 * A class that implements this interface subscribes to the events of a
 * {@link DirectorPublisher}. It may or may not be a director and/or a
 * publisher.
 * @author Armando
 */
public interface DirectorSubscriber {
    
    /**
     * Called by a {@link DirectorPublisher} to notify this subscriber of an
     * event. A pointer to the triggering publisher is passed to the subscriber,
     * to let it know the source of the event.  An integer constant is also
     * passed along, which can indicate the nature of the event.
     * @param publisher
     * @param eventIndicator An integer constant specified by the
     * <code>DirectorPublisher</code>. Publisher classes should define their
     * own constants. However, DirectorPublisher.SUCCESS and
     * DirectorPublisher.FAILURE are defined by default.
     */
    public void onEvent(DirectorPublisher publisher, int eventIndicator);
}
