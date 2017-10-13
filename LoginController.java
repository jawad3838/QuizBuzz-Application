package quizapp;


import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import quizapp.User;
import javafx.stage.Stage;
import quizapp.database;
/**
 * FXML Controller class
 *
 * @author Syed Jawwad Hamdani
 */

public class LoginController implements Initializable{

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private PasswordField pass;
    @FXML
    private TextField user;
    @FXML
    private TextField prompt;
    @FXML
    private Button loginBtn;
    
    private database DB = new database();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    @FXML
    public void login(ActionEvent event) throws Exception {
        String username = user.getText();
        String password = pass.getText();
        String Role = null;
        Stage primaryStage=new Stage();
        prompt.setText("Login Successful");
        
        User Student = new User("jawwad", "1", "Student", 0);
        User Instructor = new User("shamyl", "1", "Instructor", 0);
        
        if(username.equals(Student.getUserName()) && password.equals(Student.getPassword()))
        {
            Role = Student.getRole();
            prompt.setVisible(true);
        }
        
        else if(username.equals(Instructor.getUserName()) && password.equals(Instructor.getPassword()))
        {
            Role = Instructor.getRole();
            prompt.setVisible(true);                                    
        }
        
        else
        {
            prompt.setText("Login Failed");
            prompt.setVisible(true);
        }
        
        if(Role.equals("Student"))
        {
            DB.createCurrentUser(username);
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.close();            
            Parent stFile = FXMLLoader.load(getClass().getResource("/quizapp/student.fxml"));            
            Scene scene = new Scene(stFile,600,502);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        else if(Role.equals("Instructor"))
        {
            DB.createCurrentUser(username);
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.close();
            Parent instFile = FXMLLoader.load(getClass().getResource("/quizapp/instructor.fxml"));
            Scene scene = new Scene(instFile,618,564);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        else
        {
            prompt.setText("Failed to load next scene");
            prompt.setVisible(true);
        }
    }


}
