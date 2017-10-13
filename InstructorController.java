package quizapp;

import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import quizapp.database;
import quizapp.User;
/**
 * FXML Controller class
 *
 * @author Syed Jawwad Hamdani
 */
public class InstructorController implements Initializable {

    @FXML
    private Pane createPanel;
    @FXML
    private TextField quizTitle;
    @FXML
    private TextArea quizDescription;
    @FXML
    private Pane questionPanel;
    @FXML
    private TextField maxMarks;
    @FXML
    private RadioButton mcq;
    @FXML
    private RadioButton trueFalse;
    @FXML
    private RadioButton numeric;
    @FXML
    private TextArea question;
    @FXML
    private TextField expectedAns;
    @FXML
    private Pane mcqPanel;
    @FXML
    private RadioButton mcqOp1;
    @FXML
    private RadioButton mcqOp2;
    @FXML
    private RadioButton mcqOp3;
    @FXML
    private RadioButton mcqOp4;
    @FXML
    private Pane trFlPanel;
    @FXML
    private RadioButton tru;
    @FXML
    private RadioButton fals;
    @FXML
    private Pane numericPanel;
    @FXML
    private TextField numericAns;
    @FXML
    private Pane successPanel;
    @FXML
    private ToggleGroup type;
    @FXML
    private ToggleGroup mcqChoice;
    @FXML
    private ToggleGroup tfChoice;

    /**
     * Initializes the controller class.
     */
    private ResultSet rs; 
    private database DB = new database();
    @FXML
    private TextField op1;
    @FXML
    private TextField op2;
    @FXML
    private TextField op3;
    @FXML
    private TextField op4;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createPanel.setVisible(true);
        questionPanel.setVisible(false);
        successPanel.setVisible(false);
    }    

    @FXML
    public void createQuiz(ActionEvent event) {
        create();
    }
    
    @FXML
    private void getQtype(ActionEvent event) {
        getQuestionType();        
    }
    
    @FXML
    private void setOption(ActionEvent event) {
        setMcqOptions();
    }
    
    @FXML
    private void next(ActionEvent event) {    
        nextButton();
    }

    @FXML
    private void submit(ActionEvent event) {
        submitQuiz();
    }

    @FXML
    private void anotherQuiz(ActionEvent event) {
        createAnotherQuiz();
    }

    @FXML
    private void logOut(ActionEvent event) throws Exception{
         signOut();
    }
    
    public void create()
    {

        
        String title = quizTitle.getText();
        String description = quizDescription.getText();
        
        
        if(title.equals("") || description.equals(""))
        {
            alert("Title Or Description Needed", "You must enter a Quiz Title/Description");
        }
        
        else
        {
            DB.createQuizId(DB.getCurrentUser(), title, description);
            quizTitle.setText("");
            quizDescription.setText("");
            createPanel.setVisible(false);
            questionPanel.setVisible(true);
        }   
    }
    
    public void getQuestionType()
    {
        if(mcq.isSelected())
        {
            trFlPanel.setVisible(false);
            numericPanel.setVisible(false);
            mcqPanel.setVisible(true);

        }
        else if(trueFalse.isSelected())
        {
            mcqPanel.setVisible(false);
            numericPanel.setVisible(false);   
            trFlPanel.setVisible(true);            
        }
        else if(numeric.isSelected())
        {
            mcqPanel.setVisible(false); 
            trFlPanel.setVisible(false); 
            numericPanel.setVisible(true);  
        }
    }
    
    public void setMcqOptions()
    {
        if(op1.getText().equals("") || op2.getText().equals("") || op3.getText().equals("") || 
           op4.getText().equals(""))
        {
            alert("Option fields needed", "One or more option fields are empty"
                    + "\nYou must fill these fields");
        }
        
        else
        {
            mcqOp1.setText(op1.getText());
            mcqOp2.setText(op2.getText());
            mcqOp3.setText(op3.getText());
            mcqOp4.setText(op4.getText());
        }
    
    }
    
    public void nextButton()
    {
        String quest = question.getText();
        int marks = Integer.parseInt(maxMarks.getText());
        String ans = expectedAns.getText();
        
        if(mcq.isSelected())
        {
            if(mcqOp1.getText().equals("Option 1"))
            {
                alert("Option fields needed", "One or more Option fields are "
                      + "empty\nYou must fill these fields.");
            }
        }
        
        if(quest.equals("") || marks == 0 || ans.equals(""))
        {
            alert("Question/MaxMarks/ExpectedAnswer fields needed", "\nQuestion or maxMarks or Expected"
                    + "Answer fields are Empty\nYou must fill these fields");
          
        }
        
        else
        {
            if(mcq.isSelected())
            {
                DB.insertData(quest,ans,marks,"MCQ", mcqOp1.getText(),mcqOp2.getText(),
                              mcqOp3.getText(),mcqOp4.getText() );
                clearFields();
            }
            else
            {
                RadioButton selected = (RadioButton) type.getSelectedToggle();
                DB.insertData(quest,ans, marks, selected.getText(),null,null,null,null);
                clearFields();
            }
        }       
    
    }

    public void clearFields()
    {
        question.setText("");
        maxMarks.setText("");
        expectedAns.setText("");
        op1.setText("");
        op2.setText("");
        op3.setText("");
        op4.setText("");
        mcqOp1.setText("Option 1");
        mcqOp2.setText("Option 2");
        mcqOp3.setText("Option 3");
        mcqOp4.setText("Option 4");
    }
    
    public void submitQuiz()
    {
        clearFields();
        questionPanel.setVisible(false);
        successPanel.setVisible(true);   
    }
    
    public void createAnotherQuiz()
    {
        successPanel.setVisible(false);
        createPanel.setVisible(true);
    }
    
    public void signOut() throws Exception
    {
        DB = null;
        Stage stage = (Stage) mcq.getScene().getWindow();
        stage.close();
        Stage primaryStage=new Stage();
        Parent loginFile = FXMLLoader.load(getClass().getResource("/quizapp/login.fxml"));
        Scene scene = new Scene(loginFile);
        primaryStage.close();
        primaryStage.setScene(scene);
        primaryStage.show();     
    }
    
    public void alert(String title, String content)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
        alert = null;
    }
}
