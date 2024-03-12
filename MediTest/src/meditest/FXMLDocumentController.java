/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest;



import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 *
 * @author nikolaos damianos
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private TextField user;
    @FXML
    private TextField pass;
    @FXML
    private AnchorPane log_scene;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException, IOException {

        db DB = new db(); // kanei connect stin vasei dedomenwn
        String sql = "SELECT id,firstname,lastname,role FROM users WHERE username='"+user.getText()+"' AND password='"+pass.getText()+"';"; 
        // to erwtima sql gia na doume an einai swsta ta stixia tou 
        ResultSet rs  = DB.SelectQuery(sql);//ektelesi erwtimatos         
        
        
        
        try {

            if (rs.next() == false) { 
                //System.out.println("Error password or username is wrong"); 
                label.setText("password h username einai lathos");
            }else{
                System.out.println(rs.getInt("id"));
                Stage stage = (Stage) button.getScene().getWindow();
                
                //einai global metavlites gia na mas voithisoun me tis formes 
                
                MediTest.setName(rs.getString("firstname"));
                MediTest.setId(rs.getInt("id"));
                MediTest.setSurname(rs.getString("lastname"));
                MediTest.setRole(rs.getString("role"));
                
                
                String role = MediTest.getRole();
                String fxml_open = "FXMLmain.fxml";
                
                if(role.equals("apothikarios")){
                    fxml_open = "Apothikarios_main_FXML.fxml";
                } else if(role.equals("dianomeas")){
                    fxml_open = "Dianomis_main_FXML.fxml";
                } if(role.equals("Logistis")){
                    fxml_open = "Logistis_main_FXML.fxml";
                } if(role.equals("ypalilos")){
                    fxml_open = "Ypalilos_main_FXML.fxml";
                }
                
                label.setText("einai Swsto !!!!!!");
                Parent root=FXMLLoader.load(getClass().getResource(fxml_open));
                
                
                
                
                Scene scene = new Scene(root);
                stage.setScene(scene);
                
                //stage.getIcons().add(new Image(MediTest.class.getResourceAsStream("file:"+System.getProperty("user.dir")+"\\src\\images\\meditest_icon.png")));
                stage.show();
            }
            
           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }         
        
        DB.close(); // kleisimo tis vaseis //(tou conection)
        
    }
        
       
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
    }    
    
}
