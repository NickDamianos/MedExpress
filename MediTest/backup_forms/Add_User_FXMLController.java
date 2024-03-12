/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest.forms;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import meditest.db;

/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class Add_User_FXMLController implements Initializable {

    @FXML
    private TextField user_text;
    @FXML
    private TextField pass_text;
    @FXML
    private TextField name_text;
    @FXML
    private TextField sur_text;
    @FXML
    private TextField address_text;
    @FXML
    private TextField city_text;
    @FXML
    private TextField phone_text;
    @FXML
    private TextField afm_text;
    @FXML
    private TextField amka_text;
    @FXML
    private TextField email_text;
    @FXML
    private ChoiceBox<String> roles;
    @FXML
    private Button Add_user;
    @FXML
    private Label error_label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        roles.getItems().add("admin");
        roles.getItems().add("apothikarios");
        roles.getItems().add("dianomeas");
        roles.getItems().add("logistis");
        roles.getItems().add("ypalilos");
        
        
    }    

    @FXML
    private void add_user(ActionEvent event) {
        if(user_text.getText().equals("")|| pass_text.getText().equals("")|| name_text.getText().equals("")
                || sur_text.getText().equals("") || address_text.getText().equals("")|| city_text.getText().equals("")
                || phone_text.getText().equals("")  || afm_text.getText().equals("") || amka_text.getText().equals("") || email_text.getText().equals("") )
        {
            error_label.setText("Yparxoun kena !!! ");
            
            
        } else {
            db DB = new db();
            String sql = "INSERT INTO users (username, password,onoma,epitheto,dieuthinsi,city,tilefono,afm,AMKA,email,rolos) VALUES ( '"
                        + user_text.getText()+"', '"+pass_text.getText()+"','"+name_text.getText()+"','"+sur_text.getText()
                        +"','"+address_text.getText()+"','"+city_text.getText()+"','"+phone_text.getText()+"','"+afm_text.getText()
                        +"','"+amka_text.getText()+"','"+email_text.getText()+"','"+roles.getValue()+"' );";
            
            DB.updateDB(sql);

            DB.close();
            

            Stage stage = (Stage) Add_user.getScene().getWindow();
            
            stage.close();

        }
    
    }
}
