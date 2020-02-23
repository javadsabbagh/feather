package org.fibers.apps.dtree.util;

import javafx.geometry.Dimension2D;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Sabbagh
 */
public class JavafxUtil {

    /**
     * This is how to calculate a text width in JavaFX.
     */
    public static Dimension2D findTextSize(String text, Font font) {
        Text temp = new Text(text);
        temp.setFont(font);
        double textWidth = temp.getLayoutBounds().getWidth();
        double textHeight = temp.getLayoutBounds().getHeight();

        return new Dimension2D(textWidth, textHeight);
    }

}
