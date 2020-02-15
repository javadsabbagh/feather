package org.fibers.apps.dtree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DrawController {

    @FXML
    private AnchorPane drawPane;

    @FXML
    private ToggleGroup shapeGroup;

    @FXML
    private ToggleButton ellipseBtn;
    @FXML
    private ToggleButton rectBtn;
    @FXML
    private ToggleButton lineBtn;

    @FXML
    protected void onHelpAction(ActionEvent event) {
        shapeGroup.getSelectedToggle();

        System.out.println("gholi");
    }

    @FXML
    protected void onSystemInfoAction(ActionEvent event) {
        System.out.println("gholi");
    }

    @FXML
    protected void onDrawPaneClicked(MouseEvent event) {
        System.out.println("gholi");
    }

    @FXML
    protected void onDrawPaneDragged(MouseEvent event) {
        System.out.println("gholi");
    }

}
