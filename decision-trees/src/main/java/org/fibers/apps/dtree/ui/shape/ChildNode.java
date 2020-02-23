package org.fibers.apps.dtree.ui.shape;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;

public class ChildNode extends Pane {
    private final Ellipse shape = new Ellipse();
    private final Text text = new Text();

    public ChildNode(String label) {
        getParent().getChildrenUnmodifiable().add(shape);
        text.setText(label);
    }

}
