/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quizapp;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Syed Jawwad Hamdani
 */

/*
All Database fetch/insert operations take place in this class therefore all unit tests
were performed for this class only.
*/
public class databaseTest {

    @Test
    public void testGetCurrentId() {
        System.out.println("Testing Current ID:");
        database obj = new database();
        int expectedId = 11;
        System.out.println("Current ID: "+expectedId);
        int testId = obj.getCurrentId();
        System.out.println("Test ID: "+testId);
        assertEquals(expectedId, testId);
        obj = null;
    
    }

    @Test
    public void testGetNextId() {
        System.out.println("Testing Next ID:");
        database obj = new database();
        int expectedId = 12;
        System.out.println("Next ID: "+expectedId);
        int testId = obj.getNextId();
        System.out.println("Test ID: "+testId);
        assertEquals(expectedId, testId);
        obj = null;
    }

    @Test
    public void testGetCurrentUser() {
        System.out.println("Testing Current User:");
        database obj = new database();
        String expectedUser = "tester";
        System.out.println("Current user: "+expectedUser);
        obj.createCurrentUser(expectedUser);
        
        String testUser = obj.getCurrentUser();
        System.out.println("Test user: "+testUser);
        assertEquals(expectedUser, testUser);
        obj = null;
    }

    @Test
    public void testGetLastScore() {
        database obj = new database();
        String name = "tester";
        String quiz = "Quiz1";
        String marks = "10/20";
        
        obj.setStudentScore(name, quiz, marks);
        
        String[] testMarks = obj.getLastScore(name);
        assertEquals(testMarks[0], quiz);
        assertEquals(testMarks[1], marks);
        obj = null;
    }

    @Test
    public void testGetQuestions() {
        database obj = new database();
        
        String[] expQuestions = {"dasassa", "dasda"};
        
        String[] testQuestions = obj.getQuestions("Quiz6");
        
        assertEquals(expQuestions[0], testQuestions[0]);
        assertEquals(expQuestions[1], testQuestions[1]);
        obj = null;
    }

    @Test
    public void testGetMcqOptions()
    {
        database obj = new database();
        String op1 = "1/6";
        String op2 = "1";
        String op3 = "1/3";
        String op4 = "1/2";
        
        String[] testOptions = obj.getMcqOptions("A dice is rolled. Probability of getting a six is?");
        
        assertEquals(op1, testOptions[0]);
        assertEquals(op2, testOptions[1]);
        assertEquals(op3, testOptions[2]);
        assertEquals(op4, testOptions[3]);

        obj = null;       
    
    }
}
