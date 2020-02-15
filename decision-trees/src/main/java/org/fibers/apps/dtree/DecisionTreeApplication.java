package org.fibers.apps.dtree;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DecisionTreeApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/ui-template.fxml"));

        Scene scene = new Scene(root, 700, 500);

        stage.setTitle("Decision Trees ...");
        stage.setScene(scene);
        stage.show();
    }
}
