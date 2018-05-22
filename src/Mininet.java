import Controller.UserController;
import Model.UserModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Set;

public class Mininet extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        UserController userController = new UserController();
        if (userController.init()) {
            Parent root = FXMLLoader.load(getClass().getResource("View/MainView.fxml"));
            primaryStage.setTitle("Add friend");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No datasource found!");
            alert.setContentText("Neither people.txt nor mininet.db file was found!\nPlease create a people.txt file and launch the application again.");

            alert.showAndWait();
            System.exit(1);
        }
    }

    public void pressButton(ActionEvent event) throws Exception{
        System.out.println("Button clicked!");
        Parent addFriend = FXMLLoader.load(getClass().getResource("View/AddFriend.fxml"));
        Stage mainWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
        mainWindow.setScene(new Scene(addFriend));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
