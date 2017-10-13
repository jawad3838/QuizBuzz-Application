package quizapp;

/**
 *
 * @author Syed Jawwad Hamdani
 */
public class User {
    
    private
            String userName;
            String password;
            String role;
            int score;
            User obj;
            
    public
            User()
            {
                userName = null;
                password = null;
                role = null;
                score = 0;
                obj = null;
            }
            User(String u, String p, String r, int s)
            {
                userName = u;
                password = p;
                role = r;
                score = s;
            }
            
            void setUserObj()
            {
                obj = this;
            }
            void setUserName(String usrname)
            {
                userName = usrname;
            }
            void setPassword(String pass)
            {
                password = pass;
            }
            void setRole(String ro)
            {
                role = ro;
            }
            void setScore(int scr)
            {
                score = scr;
            }
            
            String getUserName()
            {
                return userName;
            }
            String getPassword()
            {
                return password;
            }
            String getRole()
            {
                return role;
            }
            int getScore()
            {
                return score;
            }
            User getUserobj()
            {
                return obj;
            }
}
