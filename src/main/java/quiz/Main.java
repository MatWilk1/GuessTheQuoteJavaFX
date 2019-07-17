package quiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

//    private static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/MenuScreen.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

    }

//    // Method for different way of switching screens
//    public static FXMLLoader setRoot(String fxml) throws IOException {
//        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
//        scene.setRoot(loader.load());
//        return loader;
//    }

}
