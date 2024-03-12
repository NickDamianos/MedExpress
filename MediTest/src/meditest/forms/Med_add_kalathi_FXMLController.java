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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import meditest.FXMLmainController;
import meditest.MediTest;
import meditest.db;
import meditest.farmaka;
import meditest.kalathi_items;

/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class Med_add_kalathi_FXMLController implements Initializable {

    @FXML
    private ImageView med_img;
    @FXML
    private Label name_label;
    @FXML
    private Button minus_button;
    @FXML
    private TextField num_txt;
    @FXML
    private Label ousia_label;
    @FXML
    private Label temaxia_label;
    @FXML
    private Button plus_button;
    @FXML
    private Button to_card;

    int Tem;
    farmaka farmako;
    @FXML
    private Pane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tem = 1;
        db DB = new db();
        String query = "SELECT * from farmaka where id = " + MediTest.getWorking_id();
        ResultSet rs = DB.SelectQuery(query);

        try {
            farmako = new farmaka(rs.getInt("id"), rs.getString("onoma"), rs.getString("ousia"), rs.getString("hmer_lyksis"),
                    rs.getDouble("timi"), rs.getString("xwra"),
                    rs.getInt("temaxia"), rs.getString("katigoria"),
                    rs.getString("xrhsh"), rs.getInt("timi_agoras"),
                    rs.getString("hmer_paraskevis"), rs.getString("poiothta"), rs.getInt("id_promyth"), rs.getString("img"),rs.getInt("orio_temaxion") , rs.getString("etairia"));
        } catch (SQLException ex) {
            Logger.getLogger(Med_add_kalathi_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DB.close();

        Image image = new Image("file:" + System.getProperty("user.dir") + farmako.getEikona());

        //med_img.setFitWidth(457);
        //med_img.setFitHeight(274);
        med_img.fitWidthProperty().bind(pane.widthProperty());
        med_img.setPreserveRatio(true);

        med_img.setImage(image);

        num_txt.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.isEmpty() || newValue.equals("") || newValue.equals("0")){
                num_txt.setText(String.valueOf(Tem));
            }
            
            if (newValue.matches("[0-9]*") && Integer.parseInt(newValue) <= farmako.getTemaxia()  ) {
                Tem = Integer.parseInt(newValue);
                
            } else {
                num_txt.setText(String.valueOf(Tem));
            }
            
            

        });

        name_label.setText("Όνομα : " + farmako.getOnoma());
        ousia_label.setText("Ουσία : " + farmako.getOusia());
        temaxia_label.setText("Τεμάχια : " + String.valueOf(farmako.getTemaxia()));
    }

    @FXML
    private void minus_tem(ActionEvent event) {
        if (Tem > 1) {
            Tem--;
            num_txt.setText(String.valueOf(Tem));
        } else {
            num_txt.setText(String.valueOf(Tem));
        }
    }

    @FXML
    private void plus_tem(ActionEvent event) {
        if (Tem < farmako.getTemaxia()) {
            Tem++;
            num_txt.setText(String.valueOf(Tem));
        } else {
            num_txt.setText(String.valueOf(Tem));
        }

    }

    @FXML
    private void add_to_cart(ActionEvent event) {
        db DB = new db();
        
        
        String query = "UPDATE farmaka SET temaxia = " + String.valueOf(farmako.getTemaxia() - Tem) + " WHERE id = "+ farmako.getId();
        System.out.println(query);

        DB.updateDB(query);
        DB.close();
        //int med_id, String onoma, String ousia, String temaxia, String timi, String timi_agoras
        
        if((farmako.getTemaxia() - Tem) <= farmako.getOrio()){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information Dialog");
            a.setHeaderText("Χαμηλό Όριο ");
            a.setContentText("Το ελάχιστο όριο για το Φάρμακο "+farmako.getOnoma() + " είναι " + farmako.getOrio() + " τώρα τα τεμάχεια είναι " + (farmako.getTemaxia() - Tem));

            a.showAndWait();

        }
        
        
        /*
        FXMLmainController.oblist_Kalathi.add(new kalathi_items(farmako.getId(), farmako.getOnoma(), farmako.getOusia(),
                 num_txt.getText(), String.valueOf(farmako.getTimi()), String.valueOf(farmako.getTimi_agoras())));//,new Button("Del")));
        */
        
        FXMLmainController.List_Kalathi.add(new kalathi_items(farmako.getId(), farmako.getOnoma(), farmako.getOusia(),
                 num_txt.getText(), String.valueOf(farmako.getTimi()), String.valueOf(farmako.getTimi_agoras())));//,new Button("Del")));
        
        
        
        
        Medicine_FXMLController.refreshPublic();
        
        
        Stage stage = (Stage) name_label.getScene().getWindow();

        stage.close();
    }

}
