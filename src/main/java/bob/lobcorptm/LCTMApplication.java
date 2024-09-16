package bob.lobcorptm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LCTMApplication extends Application {
    public static String styleSheets = Objects.requireNonNull(LCTMApplication.class.getResource("/bob/lobcorptm/style/lc-stylesheet.css")).toExternalForm();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LCTMApplication.class.getResource("pomodoro-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        scene.getStylesheets().add(LCTMApplication.styleSheets);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            logout(stage);
        });
    }
    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to exit?");
        alert.setContentText("- Angela -");

        alert.showAndWait()
            .filter(response -> response == ButtonType.OK)
            .ifPresent(response -> stage.close());
    }

    public static void main(String[] args) {
        launch();
    }
}