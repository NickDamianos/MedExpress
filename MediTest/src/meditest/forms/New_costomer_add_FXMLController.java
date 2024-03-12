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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import meditest.db;

/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class New_costomer_add_FXMLController implements Initializable {

    @FXML
    private TextField farmak_name;
    @FXML
    private TextField farmak_surname;
    @FXML
    private TextField farmak_phone;
    @FXML
    private TextField farmak_email;
    @FXML
    private TextField farmakeio_name;
    @FXML
    private TextField farmakeio_phone;
    @FXML
    private TextField farmakeio_odos;
    @FXML
    private TextField farmakeio_tk;
    @FXML
    private TextField farmakeio_perioxi;
    @FXML
    private TextField farmakeio_poli;
    @FXML
    private TextField farmakeio_dimos;
    @FXML
    private TextField farmakeio_afm;
    @FXML
    private ChoiceBox<String> farmak_farmakeio;
    @FXML
    private Button Add_farmak_button;
    @FXML
    private Button farmakeio_Add_button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db DB =new db();
        String query="SELECT * from farmakeia;";
        
        ResultSet RS = DB.SelectQuery(query);
        
        try {
            while(RS.next()){
                farmak_farmakeio.getItems().add(RS.getInt("id")+":"+RS.getString("onoma"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(New_costomer_add_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        DB.close();
        
    }    

    @FXML
    private void Add_farmak(ActionEvent event) {
        if(!farmak_name.getText().isEmpty()&& !farmak_surname.getText().isEmpty() && !farmak_phone.getText().isEmpty() && !farmak_email.getText().isEmpty()
                && farmak_farmakeio.getValue()!=null){
            
            db DB =new db();
            String id_farmakeiou = farmak_farmakeio.getValue().split(":")[0];
            String sql = "INSERT INTO farmakopoioi (onoma,epitheto,tilefono,email,id_farmakeiou)"
                    + "VALUES ('"+farmak_name.getText()+"','"+farmak_surname.getText()+"','"+farmak_phone.getText()+"','"+farmak_email.getText()
                    +"',"+id_farmakeiou+");";
            DB.updateDB(sql);
            DB.close();
        
            Stage stage = (Stage) farmakeio_Add_button.getScene().getWindow();

            stage.close();
        }else{
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information Dialog");
            a.setHeaderText("Υπάρχη Κενό Κελή");
            a.setContentText("Για να Προσθέσουμε Φαρμακοποιό χρειάζονται όλα τα κελιά");

            a.showAndWait();
        }
    }

    @FXML
    private void farmakeio_Add(ActionEvent event) {
        if(!farmakeio_name.getText().isEmpty()&& !farmakeio_phone.getText().isEmpty() && !farmakeio_odos.getText().isEmpty() && !farmakeio_tk.getText().isEmpty()
                && !farmakeio_perioxi.getText().isEmpty() && !farmakeio_poli.getText().isEmpty() && !farmakeio_dimos.getText().isEmpty()
                && !farmakeio_afm.getText().isEmpty()){
            
            db DB =new db();
            
            
            String sql = "INSERT INTO farmakeia (onoma,tilefono,odos,t_k,perioxi,poli,dimos,afm)"
                    + "VALUES ('"+farmakeio_name.getText()+"','"+farmakeio_phone.getText()+"','"+farmakeio_odos.getText()+"','"+farmakeio_tk.getText()
                    +"','"+farmakeio_perioxi.getText()+"','"+farmakeio_poli.getText()+"','"+farmakeio_dimos.getText()+"','"+farmakeio_afm.getText()+"');";
            
            DB.updateDB(sql);
            DB.close();
        
            refresh();
        }else{
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information Dialog");
            a.setHeaderText("Υπάρχη Κενό Κελή");
            a.setContentText("Για να Προσθέσουμε Φαρμακείο χρειάζονται όλα τα κελιά");

            a.showAndWait();
        }
    }
    
    void refresh() {
        farmakeio_name.setText("");
        farmakeio_phone.setText("");
        farmakeio_odos.setText("");
        farmakeio_tk.setText("");
        farmakeio_perioxi.setText("");
        farmakeio_poli.setText("");
        farmakeio_dimos.setText("");
        farmakeio_afm.setText("");
        
        farmak_farmakeio.getItems().clear();
        
        db DB =new db();
        String query="SELECT * from farmakeia;";
        
        ResultSet RS = DB.SelectQuery(query);
        
        try {
            while(RS.next()){
                farmak_farmakeio.getItems().add(RS.getInt("id")+":"+RS.getString("onoma"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(New_costomer_add_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        DB.close();
        
    }
    
}
