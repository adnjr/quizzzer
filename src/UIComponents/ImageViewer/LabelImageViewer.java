package UIComponents.ImageViewer;

import DBObjects.ImageIconInfo;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * This {@link JLabel} is for use as an image viewer. It works specifically with
 * {@link ImageIconInfo} objects, which extend <code>ImageIcon</code>. This
 * <code>LabelImageViewer</code> displays the thumbnail of the full-size image,
 * which it retrieves from the <code>ImageIconInfo</code> object itself. The
 * full-size icon can be retrieved using the {@link getOriginalIcon()} method.
 * @author Armando
 * @see ImageIconInfo
 */
public class LabelImageViewer extends JLabel {
    private ImageIconInfo originalIcon;

    public LabelImageViewer(Icon image) {
        super(((ImageIconInfo)image).getScaledImage());
        this.originalIcon = (ImageIconInfo) image;
    }

    public LabelImageViewer() {
        this.originalIcon = null;
    }

    @Override
    public void setIcon(Icon icon) {
        super.setIcon( (icon == null) ? null : ((ImageIconInfo)icon).getScaledImage() );
        originalIcon = (ImageIconInfo)icon;
    }
    
    /** Gets the full-size image icon for the thumbnail this
     * <code>LabelImageViewer</code> is currently displaying.
     * @return The full-size icon for the currently displayed image, or null if
     *         nothing is currently displayed. **/
    public ImageIconInfo getOriginalIcon() {
        return originalIcon;
    }
    
}
