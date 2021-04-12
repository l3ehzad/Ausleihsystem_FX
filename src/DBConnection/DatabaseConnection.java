package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    //public static Connection getConnection;
    private String url = "jdbc:mysql://localhost:3306/spoho_ausleihsystem";
    private String username = "";
    private String password= "";
    private boolean validity=true;
    public static Connection conn;

    public DatabaseConnection(String user, String pass) {
        this.username = user;
        this.password = pass;
        /*Connection conn = null;*/

        //setup connection to database
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            //validity = true;
            System.out.print("Database is connected successfully!\n");
        }
        catch(ClassNotFoundException | SQLException e){
            validity = false;
            System.out.print("Not connected to DB - Error:"+e);
            //Logger.getLogger(SQLConnection.class.getName()).log(Level.SEVERE, null, e);
        }

    }

/*    public static Connection getConnection(){
        return conn;
    }
    */
    public boolean checkValidity() {
        return validity;
    }
}
