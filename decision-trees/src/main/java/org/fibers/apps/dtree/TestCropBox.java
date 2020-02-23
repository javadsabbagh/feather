package org.fibers.apps.dtree;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.fibers.apps.dtree.ui.shape.AbstractNode;

/**
 *
 * @author Sabbagh
 */
public class TestCropBox extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Pane root;

    @Override
    public void start(Stage primaryStage) {
        createComponents();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Testing cop box");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createComponents() {
        SimpleObjectProperty<AbstractNode> selectedProperty = new SimpleObjectProperty<>();

        AbstractNode box1 = new AbstractNode(selectedProperty);
        box1.setLayoutX(270);
        box1.setLayoutY(40);
        box1.setPrefWidth(200);
        box1.setPrefHeight(200);
        
        box1.setSelected(true);

        AbstractNode box2 = new AbstractNode(selectedProperty);
        box2.setLayoutX(40);
        box2.setLayoutY(270);
        box2.setPrefWidth(200);
        box2.setPrefHeight(200);        
        
        box2.setSelected(false);

        AbstractNode box3 = new AbstractNode(selectedProperty);
        box3.setLayoutX(270);
        box3.setLayoutY(270);
        box3.setPrefWidth(200);
        box3.setPrefHeight(200);

        AbstractNode box4 = new AbstractNode(selectedProperty);
        box4.setLayoutX(33);
        box4.setLayoutY(28);
        box4.setPrefWidth(200);
        box4.setPrefHeight(200);

        root = new Pane();
        root.setPrefWidth(650);
        root.setPrefHeight(650);

        root.getChildren().addAll(box1, box2, box3, box4);

        root.setOnMousePressed(event -> {
            selectedProperty.set(null);
        });
    }

}
