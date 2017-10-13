package quizapp;

import java.sql.*;
/**
 *
 * @author Syed Jawwad Hamdani
 */

/*
All database operations placed in try/catch block as database fetch/insert operations throw 
unknown exceptions that can be traced through these try/catch blocks
*/
public class database{
    
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private int id = 0;
    private String currentUser = null;
    private int currentQuizId = 0;
    private String[] questions = new String[10];
    private String[] answers = new String[10];
    private int[] maxMarks = new int[10];
    private String[] type = new String[10];
    private String[] mcqOptions = new String[4];
    private int quizLength = 0;
    
    public database(){
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizDB", "root", "");
            
            st = con.createStatement();
        
        }catch(Exception ex){
            System.out.println("Error :"+ ex);
        }
    
    }
     
    public void createQuizId(String instructor, String quizName, String description)
    {
            id = getNextId();
            try{
            String query = " insert into quiz values (?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, instructor);
            preparedStatement.setString(3, quizName);
            preparedStatement.setString(4, description);
            preparedStatement.executeUpdate(); 
        } catch(Exception ex)
            {
              System.out.println("Error :"+ ex);  
            }         
         
    }
    
    public void insertData(String ques, String ans, int max, String type, String op1, 
                           String op2, String op3, String op4)
    {
            try{
            String query = " insert into questions values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, ques);
            preparedStatement.setString(2, ans);
            preparedStatement.setInt(3, max);
            preparedStatement.setInt(4, id);
            preparedStatement.setString(5, type);
            preparedStatement.setString(6, op1);
            preparedStatement.setString(7, op2);
            preparedStatement.setString(8, op3);
            preparedStatement.setString(9, op4);
            preparedStatement.executeUpdate(); 
        } catch(Exception ex)
            {
              System.out.println("Error :"+ ex);  
            }   
    }
    
    public int getCurrentId()
    {
        try{
            String query = "select quizID from quiz";
            
            rs = st.executeQuery(query);
            while(rs.next())
            {
                id = rs.getInt("quizID");
            }
        } catch(Exception ex)
            {
                System.out.println("Error in currentID");
              System.out.println("Error :"+ ex);  
            }        
        return id;            
    }
    
    public int getNextId()
    {
        
        try{
            String query = "select quizID from quiz";
            
            rs = st.executeQuery(query);
            while(rs.next())
            {
                id = rs.getInt("quizID");
            }
        } catch(Exception ex)
            {
              System.out.println("Error :"+ ex);
              System.out.println("Error in nextID");
            }        
        return id+1;
    }
    
    public String getCurrentUser()
    {
        
        try{
            String query = "select user from current";
            
            rs = st.executeQuery(query);
            while(rs.next())
            {
                currentUser = rs.getString("user");
            }
        } catch(Exception ex)
            {
              System.out.println("Error :"+ ex);  
            }    
        return currentUser;
    }
    
    public void createCurrentUser(String usr)
    {
        try{
            String query = "insert into current values (?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, usr);
            preparedStatement.executeUpdate(); 
        } catch(Exception ex)
            {
              System.out.println("Error :"+ ex);  
            }            
    }
    
    public String[] getQuizList()
    {
        String names[] = new String[10];
        try{
            String query = "select quizName from quiz where quizID != 0";
            
            rs = st.executeQuery(query);
            int iterator = 0;
            while(rs.next())
            {
                names[iterator] = rs.getString("quizName");
                iterator += 1;
            }
        }catch(Exception ex)
            {
              System.out.println("Error :"+ ex);  
            }
        return names;
    }
    
    public String[] getLastScore(String student)
    {
        String[] score = new String[2];
        try{
            String query = "select quizName,score from student where userName = "+"\""+student+"\"";
            
            rs = st.executeQuery(query);
            while(rs.next())
            {
                score[0] = rs.getString("quizName");
                score[1] = rs.getString("score");
            }
            return score;
        }catch(Exception ex)
            {
              System.out.println("Error :"+ ex);  
            }
        return score;
    }        
    
    
    public String[] getQuestions(String currName)
    {      
        try{
            String query = "select * from quiz where quizName = "+"\""+currName+"\"";
            rs = st.executeQuery(query);
            while(rs.next())
            {
                currentQuizId = rs.getInt("quizID");
            }
            query = "select * from questions where ID = "+currentQuizId;
            rs = st.executeQuery(query);
            int iterator = 0;
            while(rs.next())
            {
                questions[iterator] = rs.getString("question");
                answers[iterator] = rs.getString("answer");
                maxMarks[iterator] = rs.getInt("maxMarks");
                type[iterator] = rs.getString("type");
                iterator++;
            }
            quizLength = iterator;
        }catch(Exception ex)
            {
              System.out.println("Error :"+ ex);  
            }
        return questions;
    }  
    
    public String[] getAnswers()
    {
        return answers;
    }
    public int[] getmaxMarks()
    {
        return maxMarks;
    }
    public String[] getType()
    {
        return type;
    }

    public String[] getMcqOptions(String question)
    {
        try{
            String query = "select * from questions where question = "+"\""+question+"\"";
            
            rs = st.executeQuery(query);
            while(rs.next())
            {
                mcqOptions[0] = rs.getString("option1");
                mcqOptions[1] = rs.getString("option2");
                mcqOptions[2] = rs.getString("option3");
                mcqOptions[3] = rs.getString("option4");
                
            }
        }catch(Exception ex)
            {
              System.out.println("Error :"+ ex);  
            }        
        return mcqOptions;
    }
    
    public int getQuizLength()
    {
        return quizLength;
    }
    
    public void setStudentScore(String name, String quiz, String marks)
    {
        try{
            String query = "insert into student values (?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, quiz);
            preparedStatement.setString(3, marks);
            preparedStatement.executeUpdate(); 
        } catch(Exception ex)
            {
              System.out.println("Error :"+ ex);  
            } 
    }
}
