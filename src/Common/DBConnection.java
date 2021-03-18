/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ahmad Ahkam
 */
public class DBConnection {
    
    
    private static Connection connection;

	// private DBConnection() {}
    public static int userid;
    public static String usertype;

	public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
                
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/time", "root", "");
                
                return connection;
	}
        
        public static void iud(String sql) throws Exception {

        if (connection == null) {
            getDBConnection();
        }
        connection.createStatement().executeUpdate(sql);
    }

    public static ResultSet search(String sql) throws Exception {

        if (connection == null) {
            getDBConnection();
        }
        return connection.createStatement().executeQuery(sql);
    }
}
