/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import static meditest.FXMLmainController.List_Kalathi;

/**
 *
 * @author nikolaos damianos
 */
public class MediTest extends Application {
    static int id;
    static int working_id;//auto einai to id gia na kseroume pio einai sto pop up window
    
    @Override
    public void stop() {
        
        if(!List_Kalathi.isEmpty()){
            db DB = new db();
            
            for(kalathi_items kalathi : List_Kalathi){
                String query1 = "SELECT * from farmaka where id = " + kalathi.getMed_id();
                ResultSet rs = DB.SelectQuery(query1);
                
                try {
                    String query = "UPDATE farmaka SET temaxia = " + String.valueOf(Integer.parseInt(kalathi.getTemaxia()) + rs.getInt("temaxia")) + " WHERE id = "+ kalathi.getMed_id();
                    DB.updateDB(query);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(MediTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            DB.close();
        }
        
                
    }

    
    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        MediTest.id = id;
    }

    public static int getWorking_id() {
        return working_id; // epistrefi to id gia to opoio theloume na kanoume edit se ena pop up 
    }

    public static void setWorking_id(int working_id) {
        MediTest.working_id = working_id;
    }
    static String  name , surname,role;
    //public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        
        
        stage.setResizable(false);
        stage.sizeToScene();
        
        System.out.println(System.getProperty("user.dir")+"\\src\\images\\meditest_icon.png");
        //stage.getIcons().add(new Image("file:"+MediTest.class.getResourceAsStream(System.getProperty("user.dir")+"\\src\\images\\meditest_icon.png")));
        stage.setTitle("MedExpress");
        
        stage.show();
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        MediTest.name = name;
    }

    public static String getSurname() {
        return surname;
    }

    public static void setSurname(String surname) {
        MediTest.surname = surname;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        MediTest.role = role;
    }

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        launch(args);
    }
    
}
