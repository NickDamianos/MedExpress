/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class Pop_up_user_editiorController implements Initializable {

    static users_content data;

    public static void takeData(users_content edit_user) {
        Pop_up_user_editiorController.data = edit_user;
    }
    @FXML
    private TextField user_text_edit;
    @FXML
    private TextField pass_text_edit;
    @FXML
    private TextField name_text_edit;
    @FXML
    private TextField sur_text_edit;
    @FXML
    private TextField address_text_edit;
    @FXML
    private TextField city_text_edit;
    @FXML
    private TextField phone_text_edit;
    @FXML
    private TextField afm_text_edit;
    @FXML
    private TextField amka_text_edit;
    @FXML
    private TextField email_text_edit;
    @FXML
    private ChoiceBox<String> roles_edit;
    @FXML
    private Button Edit_user;
    @FXML
    private Label error_label;
    @FXML
    private Label id_label;
    @FXML
    private Button delete_user1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db DB = new db();
        String sql = "SELECT * FROM users WHERE id =" + MediTest.getWorking_id() + ";";
        ResultSet rs = DB.SelectQuery(sql);

        

        try {
            if (rs.next() == false) {
                //System.out.println("Error password or username is wrong"); 
                error_label.setText("Kati pige strava");
            } else {
                //username, password,onoma,epitheto,dieuthinsi,city,tilefono,afm,AMKA,email,rolos
                id_label.setText("ID = " + Integer.toString(rs.getInt("id")));
                user_text_edit.setText(rs.getString("username"));
                System.out.println(rs.getString("username"));
                pass_text_edit.setText(rs.getString("password"));
                name_text_edit.setText(rs.getString("onoma"));
                sur_text_edit.setText(rs.getString("epitheto"));
                address_text_edit.setText(rs.getString("dieuthinsi"));
                city_text_edit.setText(rs.getString("city"));
                phone_text_edit.setText(rs.getString("tilefono"));
                afm_text_edit.setText(rs.getString("afm"));

                amka_text_edit.setText(rs.getString("AMKA"));
                email_text_edit.setText(rs.getString("email"));

                roles_edit.setValue(rs.getString("rolos"));

                roles_edit.getItems().add("admin");
                roles_edit.getItems().add("apothikarios");
                roles_edit.getItems().add("dianomeas");
                roles_edit.getItems().add("logistis");
                roles_edit.getItems().add("ypalilos");

            }
        } catch (SQLException ex) {
            Logger.getLogger(Pop_up_user_editiorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        DB.close();
        
    }

    @FXML
    private void edit_user_(ActionEvent event) {
        db DB = new db();

        String sql = "UPDATE users SET username='" + user_text_edit.getText() + "', password='" + pass_text_edit.getText() + "',onoma='" + name_text_edit.getText()
                + "',epitheto='" + sur_text_edit.getText() + "',dieuthinsi='" + address_text_edit.getText() + "',city='" + city_text_edit.getText()
                + "',tilefono='" + phone_text_edit.getText() + "',afm='" + afm_text_edit.getText() + "',AMKA='" + amka_text_edit.getText()
                + "',email='" + email_text_edit.getText() + "',rolos='" + roles_edit.getValue() + "'WHERE id =" + MediTest.getWorking_id() + ";";
        System.out.println(sql);

        DB.updateDB(sql);

        DB.close();

        Stage stage = (Stage) Edit_user.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void delete_user_(ActionEvent event) {

        db DB = new db();
        String sql = "DELETE FROM users WHERE id=" + MediTest.getWorking_id() + ";";
        DB.updateDB(sql);

        DB.close();

        Stage stage = (Stage) Edit_user.getScene().getWindow();
        stage.close();

    }
}
