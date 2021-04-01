import java.sql.*;

public class ConnectionFactory
{
    private static Connection con = null;

    static {
        String dbdriver = "com.mysql.jdbc.Driver";
        String protocol = "jdbc:mysql";
        String host = "localhost";
        String port = "3306";
        String dbname = "spoho_ausleihsystem";
        String userid = "root";
        String userpwd = "";
        String url = "jdbc:mysql://localhost:3306/spoho_ausleihsystem";

        try
        {
            con = DriverManager.getConnection(url,userid,userpwd);
        } /*catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e)*/
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //close the connection
    public static void close(){
        if (con != null)
            try{
                con.close();
            }catch (SQLException e){
                System.out.println("Fehler beim Beenden der DB-Verbindung!");
                e.printStackTrace();
            }
    }

/*    private static class SingletonHolder {
        private final static ConnectionFactory INSTANCE = new ConnectionFactory();
    }*/

    public static Connection getConnection(){
        return con;
    }
}
