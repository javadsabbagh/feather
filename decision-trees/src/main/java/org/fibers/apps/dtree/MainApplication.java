package org.fibers.apps.dtree;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.fibers.apps.dtree.ui.LabelButton;

import javax.swing.*;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        createSwingContent();

        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label sysLabel = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        sysLabel.setLayoutX(100);
        sysLabel.setLayoutY(10);

        LabelButton l1 = new LabelButton("Result");
        l1.setLayoutX(10);
        l1.setLayoutY(10);

        LabelButton l2 = new LabelButton("Body");
        l2.setLayoutX(60);
        l2.setLayoutY(10);

        //stage.setAlwaysOnTop(true); // Note: it has no effect after calling stage.show()

        Scene scene = new Scene(new Pane(l1, l2, sysLabel), 640, 480);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to exit?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                System.exit(0);
            } else {
                event.consume(); // prevent closing window
            }
        });
    }

    private SwingNode createSwingContent() {
        final SwingNode swingNode = new SwingNode();
        swingNode.setContent(new JButton("Click me!"));

        return swingNode;
    }

}
