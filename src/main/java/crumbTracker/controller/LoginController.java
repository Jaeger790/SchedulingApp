package crumbTracker.controller;

import crumbTracker.DAO.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import crumbTracker.model.CalendarModel;
import crumbTracker.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label loginHeader, timeZoneLabel, userLabel, passwordLabel;
    @FXML
    private TextField userNameTF;
    @FXML
    private PasswordField passwordTF;
    public Button loginButton, exitButton;
    private static String userInput;
    private String passwordText;
    @FXML
    private Label errorLabel;
    ResourceBundle rb = ResourceBundle.getBundle("/crumbTracker/lang", Locale.getDefault());
    String locale = Locale.getDefault().getLanguage();

    public static User currentUser() {
        return UserQuery.getUser(userInput);
    }

    /**
     * takes user input from textFields and compares them to a successful login parameters.  Adds error styling to
     * textFields and Labels when an incorrect value has been tried.
     */
    private boolean validateLogin(String userInput, String passwordText) {
        String userName = currentUser().getUserName();
        String password = currentUser().getPassword();
        if (!userInput.equals(userName) || !passwordText.equals(password)) {
            if (!userInput.equals(userName)) {
                userLabel.setTextFill(Color.RED);
                userNameTF.getStyleClass().add("wrongField");
                userNameTF.getStyleClass().remove("correctField");
                errorLabel.setText(rb.getString("errorText"));
                errorLabel.setDisable(false);
            } else {
                userLabel.setTextFill(Color.BLACK);
                userNameTF.getStyleClass().add("correctField");
                userNameTF.getStyleClass().remove("wrongField");
                errorLabel.setDisable(true);
            }

            if (!passwordText.equals(password)) {
                passwordLabel.setTextFill(Color.RED);
                passwordTF.getStyleClass().add("wrongField");
                errorLabel.setText(rb.getString("errorText"));
                errorLabel.setDisable(false);

            }
            return false;
        }
        return true;
    }

    /**
     * records login attempts, the data input in the attempt, and the localDateTime of the attempt and saves it to
     * login_activity.txt.
     */
    private void recordLoginAttempt() {
        try {
            FileWriter loginWriter = new FileWriter("login_activity.txt", true);
            PrintWriter loginPrinter = new PrintWriter(loginWriter);
            String userNameInput = "Username: " + userNameTF.getText();
            String passwordInput = "Password: " + passwordTF.getText();
            String dateFormat = LocalDateTime.now().format(CalendarModel.getDateDTF());

            if (!validateLogin(userNameTF.getText(), passwordTF.getText())) {
                loginPrinter.println("|| Login failed  |   " + dateFormat + " ||");
                loginPrinter.println(userNameInput + "\n" + passwordInput);
                loginPrinter.close();
            }
            loginPrinter.println("|| Login successful  |   " + dateFormat + " ||");
            loginPrinter.println(userNameInput + "\n" + passwordInput);
            loginPrinter.close();
        } catch(IOException o) {
            o.printStackTrace();
        }
    }

    /**
     * handler for login button
     */

    private void onLoginClick() {
        loginButton.setOnAction(actionEvent -> {
            userInput = userNameTF.getText();
            passwordText = passwordTF.getText();
            recordLoginAttempt();
            if (!validateLogin(userInput, passwordText)) {
                return;
            }
            openMainScreen(actionEvent);
        });
    }

    /**
     * Opens main screen after a successful login attempt has been detected.
     */
    private void openMainScreen(ActionEvent actionEvent) {
        try {
            Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/crumbTracker/layout/mainScreen.fxml"));
            Parent root = loader.load();
            Scene mainScene = new Scene(root);
            loginStage.setScene(mainScene);
            loginStage.setTitle("Welcome to Scheduling");
            loginStage.show();
        } catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Exits application
     */
    private void onExitClick() {
        exitButton.setOnAction(actionEvent -> {
            Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION);
            confirmExit.setTitle(rb.getString("cancelTitle"));
            confirmExit.setHeaderText(rb.getString("cancelHeader"));
            confirmExit.setContentText(rb.getString("cancelText"));
            Optional<ButtonType> result = confirmExit.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }
        });
    }

    /**
     * translates login page to users current local machine locale language setting.
     */
    private void translateLogin() {
        if (locale.equals("de") || locale.equals("fr")) {
            exitButton.setText(rb.getString("exit"));
            loginButton.setText(rb.getString("login"));
            userLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            loginHeader.setText(rb.getString("scheduling") + " " + rb.getString("login"));
            loginHeader.setMaxWidth(350);
        }
        if (locale.equals("fr")) {
            userLabel.setStyle("-fx-padding:0 0 0 40");
            passwordLabel.setStyle("-fx-padding: 0 0 0 30");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userNameTF.setText("CrumbAdmin");
        passwordTF.setText("Rebelution");
        loginButton.setDefaultButton(true);
        exitButton.setCancelButton(true);
        errorLabel.setDisable(true);
        timeZoneLabel.setText(String.valueOf(CalendarModel.getLocalZoneId()));
        translateLogin();

        onLoginClick();
        onExitClick();
    }
}