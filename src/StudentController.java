package quizapp;

import static java.lang.System.exit;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import quizapp.database;

/**
 * FXML Controller class
 *
 * @author Syed Jawwad Hamdani
 */
public class StudentController implements Initializable {

    @FXML
    private Pane selectQuizPanel;
    @FXML
    private ComboBox<String> quizComboBox;
    @FXML
    private Pane mainQuestionPanel;
    @FXML
    private TextField questionNum;
    @FXML
    private TextArea question;
    @FXML
    private TextField maxMarks;
    @FXML
    private Pane mcqPanel;
    @FXML
    private RadioButton mcqOp1;
    @FXML
    private ToggleGroup mcqOptions;
    @FXML
    private RadioButton mcqOp2;
    @FXML
    private RadioButton mcqOp3;
    @FXML
    private RadioButton mcqOp4;
    @FXML
    private Pane trueFalsePanel;
    @FXML
    private RadioButton tru;
    @FXML
    private ToggleGroup trueFalse;
    @FXML
    private RadioButton fals;
    @FXML
    private Pane numericPanel;
    @FXML
    private TextField numericAns;
    @FXML
    private Button nextButton;
    @FXML
    private Pane scorePanel;
    @FXML
    private TextField totalScore;

    private database DB = new database();
    private String[] questions;
    private String[] answers;
    private int[] marks;
    private String[] type;
    private String[] previousScore = null;
    private int iterator = 0;
    private int studentScore = 0;
    private int totalQuizScore = 0;
    private int quizLength = 0;
    @FXML
    private TextField lastScore;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){ 
        List<String> list = new ArrayList<String>();
        String[] quizList =  DB.getQuizList();
        for(String s: quizList)
        {
            list.add(s);            
        }
        previousScore = DB.getLastScore(DB.getCurrentUser());
        if(previousScore[0] == null)
        {
            lastScore.setText("Last Score: NA");
        }
        else
        {
            lastScore.setText("Last Score: "+previousScore[1]+" in "+previousScore[0]);
        }
            
        ObservableList obList = FXCollections.observableList(list);
        quizComboBox.getItems().clear();
        quizComboBox.setItems(obList);  
    }    


    @FXML
    private void startQuiz(ActionEvent event) {
        selectQuiz();
    }

    @FXML
    private void nextQuestion(ActionEvent event) {
        if((nextButton.getText()).equals("Finish"))
        {
            checkAnswer();
            iterator = 0;
            totalScore.setText("Your Score : "+studentScore+"/"+totalQuizScore);
            DB.setStudentScore(DB.getCurrentUser(), quizComboBox.getValue(), studentScore+"/"
                                +totalQuizScore);
            mainQuestionPanel.setVisible(false);
            scorePanel.setVisible(true);
        }
        
        else
        {
            checkAnswer();
            getNextQuestion();
        }
    }

    @FXML
    private void takeAnotherQuiz(ActionEvent event) {
        switchToQuizWindow();
    }

    @FXML
    private void logOut(ActionEvent event) throws Exception {
        signOut();
    }

    public void selectQuiz()
    {
        if(quizComboBox.getValue() == null)
        {
            alert("Invalid Quiz", "You must select a quiz in order to proceed");
        }
        
        else
        {
            getQuizInfo();
            setQuestionPanel();
            selectQuizPanel.setVisible(false);
            mainQuestionPanel.setVisible(true);
        }
    }
    
    public void alert(String title, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
        alert = null;        
    }

    public void getQuizInfo()
    {
        questions = DB.getQuestions(quizComboBox.getValue());
        answers = DB.getAnswers();
        marks = DB.getmaxMarks();
        type = DB.getType();
        quizLength = DB.getQuizLength();
    }
    
    public void setQuestionPanel()
    {        
        if(type[iterator].equals("MCQ"))
        {
            mcqOptions.selectToggle(null);
            String[] options = DB.getMcqOptions(questions[iterator]);
            mcqOp1.setText(options[0]);
            mcqOp2.setText(options[1]);
            mcqOp3.setText(options[2]);
            mcqOp4.setText(options[3]);
            maxMarks.setText("Max Marks: "+"("+marks[iterator]+")");
            question.setText(questions[iterator]);
            numericPanel.setVisible(false);
            trueFalsePanel.setVisible(false);  
            mcqPanel.setVisible(true);
        }        
        
        else if(type[iterator].equals("True/False"))
        {
            trueFalse.selectToggle(null);
            question.setText(questions[iterator]);
            maxMarks.setText("Max Marks: "+"("+marks[iterator]+")");
            mcqPanel.setVisible(false);
            numericPanel.setVisible(false);
            trueFalsePanel.setVisible(true);
        }
        
        else if(type[iterator].equals("Numeric"))
        {
            numericAns.setText("");
            question.setText(questions[iterator]);
            maxMarks.setText("Max Marks: "+"("+marks[iterator]+")");
            numericAns.setText("");
            mcqPanel.setVisible(false);
            trueFalsePanel.setVisible(false);
            numericPanel.setVisible(true);
        }
    
        
    }
    
    public void checkAnswer()
    {
        RadioButton mcqSelected = (RadioButton) mcqOptions.getSelectedToggle();
        RadioButton tFSelected = (RadioButton) trueFalse.getSelectedToggle();
        if(type[iterator].equals("Numeric"))
        {  
            if(numericAns.getText().equals(""))
            {
                alert("Answer Field Needed", "You must type your answer\n"
                      + "Type NA if you don't know the answer");
                iterator--;
                exit(1);
            }
            else if(numericAns.getText().equals(answers[iterator]))
            {
                studentScore += (marks[iterator]);
                numericAns.setText("");
            }
        }
        else if(type[iterator].equals("MCQ"))
        {
            if(mcqSelected.getText().equals(answers[iterator]))
            {
                studentScore += (marks[iterator]);
            }
        }
        else if(type[iterator].equals("True/False"))
        {
            if(tFSelected.getText().equals(answers[iterator]))
            {
                studentScore += (marks[iterator]);
            }
        }
        numericAns.setText("");
        totalQuizScore += (marks[iterator]);
        iterator++;
    }
    
    public void getNextQuestion()
    {
        if(iterator+1 == quizLength)
        {
            nextButton.setText("Finish");
            setQuestionPanel();
        }
        
        else
        {
            setQuestionPanel();
        }
    }
    
    public void switchToQuizWindow()
    {
        iterator = 0;
        studentScore = 0;
        totalQuizScore = 0;
        quizLength = 0;
        questions = null;
        answers = null;
        type = null;
        marks = null;
        DB = null;
        DB = new database();
        previousScore = DB.getLastScore(DB.getCurrentUser());
        lastScore.setText("Last Score: "+previousScore[1]+" in "+previousScore[0]);
        quizComboBox.valueProperty().set(null);
        nextButton.setText("Next Question");
        scorePanel.setVisible(false);
        selectQuizPanel.setVisible(true);
    }
    
    public void signOut() throws Exception
    {
        totalScore.setText("");
        Stage stage = (Stage) nextButton.getScene().getWindow();
        stage.close();
        Stage primaryStage=new Stage();
        Parent loginFile = FXMLLoader.load(getClass().getResource("/quizapp/login.fxml"));
        Scene scene = new Scene(loginFile);
        primaryStage.close();
        primaryStage.setScene(scene);
        primaryStage.show();        
    }
}
