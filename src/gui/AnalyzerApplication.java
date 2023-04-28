package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AnalyzerApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AnalyzerApplication.class.getResource("AnalyzerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1009, 606);
        primaryStage.setTitle("Analizador Léxico");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
