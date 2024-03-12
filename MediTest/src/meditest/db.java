/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest;

//import meditest.forms.Add_User_FXMLController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikolaos damianos
 */
public class db {

    String db_url;

    
    Connection conn;

    public db() {
        db_url = "jdbc:sqlite:" + System.getProperty("user.dir") + "//test_log.db";
        conn = null;

        try {

            conn = DriverManager.getConnection(db_url);

            //System.out.println("Class");

        } catch (SQLException ex) {
            //Logger.getLogger(Add_User_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void close() {
        try {
            if (conn != null) {
                System.out.println("telos syndesis");
                conn.close();
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void updateDB(String query) {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            //System.out.println("OK !!!!");
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ResultSet SelectQuery(String query) {
        ResultSet rs;
        try {
            Statement stmt = conn.createStatement();
            
            
            
            rs = stmt.executeQuery(query);
            //System.out.println(rs.getString("username"));
            
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
            rs = null;
        }

        
        return rs;
    }
        
}
