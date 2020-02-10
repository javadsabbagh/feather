package org.fibers.apps.dtree.ui;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LabelButton extends Label {

    private static final Color NORMAL_COLOR = Color.valueOf("#74B5CC");
    private static final Color CLICKED_COLOR = Color.BLACK;

    public LabelButton(String text) {
        super(text);
        setFont(Font.font("Verdana", 12));
        setTextFill(NORMAL_COLOR);
        setCursor(Cursor.HAND);
        setOnMouseEntered(e -> hovered(true));
        setOnMouseExited(e -> hovered(false));
        setOnMousePressed(e -> bold(true));
        setOnMouseReleased(e -> bold(false));
    }

    public void hovered(boolean b) {
        setTextFill(b ? CLICKED_COLOR : NORMAL_COLOR);
        this.setUnderline(b);
    }

    public void bold(boolean b) {
        Font f = this.getFont();
        this.setFont(Font.font(f.getFamily(), b ? FontWeight.BOLD : FontWeight.NORMAL, f.getSize()));
    }
}
