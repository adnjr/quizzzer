package UIComponents;

import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Armando
 */
public class UISettings {
    private static final Color borderEnabledColor = new Color(51, 153, 255);
    private static final Color borderDisabledColor = new Color(153, 153, 153);
    public static final Border enabledBorder = new LineBorder(borderEnabledColor, 1, true);
    public static final Border disabledBorder = new LineBorder(borderDisabledColor, 1, true);
}
