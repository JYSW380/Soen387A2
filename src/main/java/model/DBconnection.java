package model;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBconnection {
    private static String JDBC_DRIVER ="com.mysql.cj.jdbc.Driver";
    private static String DB_URL;
    private static String DB_NAME ="post";
    private static String DB_USER;
    private static String DB_PASSWORD;
    private static Connection conn = null;

    /**
     * to connect to database with myProperties.prop for url , user and password
     * @return
     */
    public static Connection getConnection(){
        Properties p;
        FileInputStream input = null;
        try {
            // change the path
            input = new FileInputStream("C:\\Users\\Owner\\IdeaProjects\\A2\\src\\main\\webapp\\WEB-INF\\myPorperties.prop");
            //input = new FileInputStream("C:\\Users\\Andres Vidoza\\Documents\\code\\java\\SOEN387_A2\\Soen387A2\\src\\main\\webapp\\WEB-INF\\myPorperties.prop");
            p= new Properties();
            if (input == null) {
                System.out.println("file not found");
                return null;
            }
            p.load(input);
            DB_URL= p.getProperty("db.url");
            DB_USER= p.getProperty("db.user");
            DB_PASSWORD =p.getProperty("db.pwd");
            Class.forName(JDBC_DRIVER); //Register JDBC driver
            //Open a connection
            conn = DriverManager.getConnection(DB_URL+DB_NAME,DB_USER,DB_PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;

    }
    public static void closeConnection() throws SQLException{
        //Close connection
        if(conn!=null) conn.close();
    }

}
