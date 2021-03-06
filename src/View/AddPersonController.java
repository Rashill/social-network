/**
 * @author John Michael Tubera
 */

package View;

import Model.AdultUserModel;
import Model.ChildUserModel;
import Model.UserModel;
import Model.YoungChildUserModel;
import Controller.Driver;
import Exception.NoParentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;


public class AddPersonController {
    @FXML
    private GridPane parentsGridPane;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_photo;

    @FXML
    private TextField txt_status;

    @FXML
    private RadioButton radio_male;

    @FXML
    private RadioButton radio_female;

    @FXML
    private TextField txt_age;

    @FXML
    private TextField txt_state;

    @FXML
    private ToggleGroup genderToggle;

    @FXML
    private TextField txt_parent1;

    @FXML
    private TextField txt_parent2;

    @FXML
    public void savePerson(ActionEvent event) throws Exception {
        Driver driver = new Driver();
        Set<String> parents = new HashSet<>();

        if (txt_name.getText().length() == 0 || txt_state.getText().length() == 0 || txt_status.getText().length() == 0 || txt_age.getText().length() == 0 || genderToggle.getSelectedToggle() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missing Information!");
            alert.setHeaderText("");
            alert.setContentText("Please fill all inputs!");
            alert.showAndWait();
            return;
        }

        try{
            Integer.valueOf(txt_age.getText());
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Information!");
            alert.setHeaderText("");
            alert.setContentText("Age must be a number");
            alert.showAndWait();
            return;
        }

        String name = txt_name.getText();
        String photo = txt_photo.getText();
        String status = txt_photo.getText();
        char gender = (char) 0;
        if (genderToggle.getSelectedToggle() == radio_male)
            gender = 'M';
        else if (genderToggle.getSelectedToggle() == radio_female)
            gender = 'F';
        String state = txt_state.getText();
        int age = Integer.valueOf(txt_age.getText());

        UserModel userModel;

        try {
            if (age >= 16) {
                userModel = new AdultUserModel(name, age, status, photo, gender, state);
                driver.addUser(userModel);
            }
            else {
                if (txt_parent1.getText().length() > 0)
                    parents.add(txt_parent1.getText());
                if (txt_parent2.getText().length() > 0)
                    parents.add(txt_parent2.getText());
                if (driver.verifyIfCouple(txt_parent1.getText(), txt_parent2.getText())) {
                    if (age > 2) {
                        userModel = new ChildUserModel(name, age, status, photo, gender, state, parents);
                        driver.addUser(userModel);
                    }
                    else {
                        userModel = new YoungChildUserModel(name, age, status, photo, gender, state, parents);
                        driver.addUser(userModel);
                    }
                }
                else
                    throw new NoParentException("Parents are not a couple!");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        System.out.println("Name: " + name);
        System.out.println("Name: " + photo);
        System.out.println("Name: " + status);
        System.out.println("Name: " + gender);
        System.out.println("Name: " + age);
        System.out.println("Name: " + state);

        goToMainMenu(event);
    }

    public void toggleParentsGridPane(){
        int age;
        try{
            age = Integer.valueOf(txt_age.getText());
        }
        catch (Exception e){
            return;
        }
        if (age <= 16)
            parentsGridPane.setVisible(true);
        else
            parentsGridPane.setVisible(false);
    }

    public void goToMainMenu(ActionEvent event) throws Exception{
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage mainWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainWindow.setScene(new Scene(parent));
    }

}
