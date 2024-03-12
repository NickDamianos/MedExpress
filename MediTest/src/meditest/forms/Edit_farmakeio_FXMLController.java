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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import meditest.db;

/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class Edit_farmakeio_FXMLController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private TextField odos;
    @FXML
    private TextField tk;
    @FXML
    private TextField periox;
    @FXML
    private TextField afm;
    @FXML
    private TextField city;
    @FXML
    private TextField dimos;
    @FXML
    private Label label_id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        label_id.setText("ID : "+String.valueOf(Customer_FXMLController.id_farmakeiou));
    
        db DB = new db();
               
        String sql = "SELECT * from farmakeia WHERE id="+Customer_FXMLController.id_farmakeiou+";";
        
        ResultSet RS= DB.SelectQuery(sql);
        
        try {
            name.setText(RS.getString("onoma"));
            phone.setText(RS.getString("tilefono"));
            odos.setText(RS.getString("odos"));
            tk.setText(RS.getString("t_k"));
            periox.setText(RS.getString("perioxi"));
            afm.setText(RS.getString("afm"));
            city.setText(RS.getString("poli"));
            dimos.setText(RS.getString("dimos"));
            
        } catch (SQLException ex) {
            Logger.getLogger(Edit_farmakeio_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DB.close();
    }    

    @FXML
    private void delete(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Delete Dialog");
        a.setHeaderText("Ειστε Σίγουρος πως θα θέλατε να διαγράψεται αυτο το φάρμακείο");

        a.showAndWait();

        if (a.getResult() == ButtonType.OK) {
            db DB = new db();

            String sql = "DELETE FROM farmakeia WHERE id=" + Customer_FXMLController.id_farmakeiou + ";";

            DB.updateDB(sql);

            DB.close();

            Stage stage = (Stage) odos.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    private void edit(ActionEvent event) {
        if (!name.getText().isEmpty() && !phone.getText().isEmpty() && !odos.getText().isEmpty() && !tk.getText().isEmpty()
                && !periox.getText().isEmpty() && !city.getText().isEmpty() && !dimos.getText().isEmpty()
                && !afm.getText().isEmpty()) {
            db DB = new db();

            String query = "UPDATE farmakeia SET onoma='" + name.getText() + "', tilefono='" + phone.getText()
                    + "', odos ='" + odos.getText() + "',t_k ='" + tk.getText()
                    + "',perioxi = '" + periox.getText() + "',afm='" + afm.getText() + "',poli='" + city.getText()
                    + "',dimos ='" + dimos.getText() + "' WHERE id =" + Customer_FXMLController.id_farmakeiou + ";";

            DB.updateDB(query);
            DB.close();

            Stage stage = (Stage) odos.getScene().getWindow();

            stage.close();
        } else {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information Dialog");
            a.setHeaderText("Υπάρχη Κενό Κελή");
            a.setContentText("Για να Προσθέσουμε Φαρμακείο χρειάζονται όλα τα κελιά");

            a.showAndWait();
        }
    }

}
