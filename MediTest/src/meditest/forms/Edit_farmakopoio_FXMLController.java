/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest.forms;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
public class Edit_farmakopoio_FXMLController implements Initializable {

    @FXML
    private Label id_label;
    @FXML
    private TextField farmak_name;
    @FXML
    private TextField farmak_surname;
    @FXML
    private TextField farmak_phone;
    @FXML
    private TextField farmak_email;
    @FXML
    private ChoiceBox<String> farmak_farmakeio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_label.setText("ID : " + String.valueOf(Customer_FXMLController.farmak_id));

        int id_farmakeiou=1;
         
        db DB = new db();

        String sql = "SELECT * from farmakopoioi WHERE id= " + Customer_FXMLController.farmak_id + ";";

        ResultSet RS = DB.SelectQuery(sql);
        
        try {
            farmak_name.setText(RS.getString("onoma"));
            farmak_surname.setText(RS.getString("epitheto"));
            farmak_phone.setText(RS.getString("tilefono"));
            farmak_email.setText(RS.getString("email"));
            //farmak_farmakeio.setText(RS.getString("perioxi"));
            id_farmakeiou = RS.getInt("id_farmakeiou");

        } catch (SQLException ex) {
            Logger.getLogger(Edit_farmakeio_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sql2 = "SELECT * from farmakeia;";
        
        ResultSet RS2 = DB.SelectQuery(sql2);
        
        try {
            while(RS2.next()){
                farmak_farmakeio.getItems().add(RS2.getInt("id")+":"+RS2.getString("onoma"));
                if(RS2.getInt("id") == id_farmakeiou){
                    farmak_farmakeio.setValue(RS2.getInt("id")+":"+RS2.getString("onoma"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(New_costomer_add_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        
        DB.close();
    }

    @FXML
    private void DELETE(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Delete Dialog");
        a.setHeaderText("Ειστε Σίγουρος πως θα θέλατε να διαγράψεται αυτο τον φάρμακοποιό");

        a.showAndWait();

        if (a.getResult() == ButtonType.OK) {
            db DB = new db();

            String sql = "DELETE FROM farmakopoioi WHERE id=" + Customer_FXMLController.farmak_id + ";";

            DB.updateDB(sql);

            DB.close();

            Stage stage = (Stage) id_label.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    private void EDIT(ActionEvent event) {
        if(!farmak_name.getText().isEmpty()&& !farmak_surname.getText().isEmpty() && !farmak_phone.getText().isEmpty() && !farmak_email.getText().isEmpty()
                && farmak_farmakeio.getValue()!=null){
            
            db DB =new db();
            
            String id_farmakeiou = farmak_farmakeio.getValue().split(":")[0];
            
            /*String query = "UPDATE farmakeia SET onoma='" + name.getText() + "', tilefono='" + phone.getText()
                    + "', odos ='" + odos.getText() + "',t_k ='" + tk.getText()
                    + "',perioxi = '" + periox.getText() + "',afm='" + afm.getText() + "',poli='" + city.getText()
                    + "',dimos ='" + dimos.getText() + "' WHERE id =" + Customer_FXMLController.id_farmakeiou + ";";*/
            
            String sql = "UPDATE farmakopoioi SET onoma='"+farmak_name.getText()+"',epitheto='"+farmak_surname.getText()+
                    "',tilefono='"+farmak_phone.getText()+"',email='"+farmak_email.getText()+"',id_farmakeiou ="+id_farmakeiou
                    + " WHERE id ="+Customer_FXMLController.farmak_id+";"; 
                    
            DB.updateDB(sql);
            DB.close();
        
            Stage stage = (Stage) id_label.getScene().getWindow();

            stage.close();
        }else{
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information Dialog");
            a.setHeaderText("Υπάρχη Κενό Κελή");
            a.setContentText("Για να Επεξεργαστούμε Φαρμακοποιό χρειάζονται όλα τα κελιά");

            a.showAndWait();
        }
    }
}
    
    

