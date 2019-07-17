package quiz;

import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

// Class used to describe dialog windows
public class DialogWindows {

    // If user want to exit application
    public static Optional<ButtonType> dialogExit() {
        Alert alertExit = new Alert(AlertType.CONFIRMATION);
        alertExit.setTitle("Zamykanie");
        alertExit.setHeaderText("Zamykanie aplikacji");
        alertExit.setContentText("Czy na pewno chcesz zakończyć grę?");

        Optional<ButtonType> result = alertExit.showAndWait();

        if(result.get() == ButtonType.OK) {
            Platform.exit();
        }

        return result;
    }

    // For set user name
    public static Optional<String> dialogSetName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Imię");
        dialog.setHeaderText("Podaj imię");
        dialog.setContentText("Podaj swoje imię:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your name: " + result.get());
        }

        return result;
    }

    // If user achieves best score
    public static void dialogBestScore() {
        Alert alertExit = new Alert(AlertType.INFORMATION);
        alertExit.setTitle("Najlepszy wynik :)");
        alertExit.setHeaderText("Brawo, to jest najlepszy wynik :)");
        alertExit.setContentText("Twoja wiedza jest ogromna :)");

        alertExit.showAndWait();
    }
}