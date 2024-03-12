/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest.forms;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import meditest.db;

/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class Farmaka_Add_to_base_FXMLController implements Initializable {

    @FXML
    private ImageView image;
    @FXML
    private DatePicker hmer_L;
    @FXML
    private DatePicker P_hmer_P;
    @FXML
    private ChoiceBox<String> promyth;
    @FXML
    private TextField temaxia;
    @FXML
    private TextField timi;
    @FXML
    private TextField timi_Agoras;
    @FXML
    private Button Add_Button;
    @FXML
    private TextField name_txt;
    @FXML
    private TextField ousia_txt;
    @FXML
    private TextField xwra_txt;
    @FXML
    private TextField category;
    @FXML
    private TextField poiothta;
    @FXML
    private TextArea usage;
    @FXML
    private AnchorPane pane;

    String Im_url;
    @FXML
    private TextField orio_temaxion;//orio_temaxion

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db DB = new db();

        ResultSet rs = DB.SelectQuery("SELECT id,name from promith");

        try {
            while (rs.next()) {
                promyth.getItems().add(String.valueOf(rs.getInt("id")) + "|" + rs.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Farmaka_Add_to_base_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        DB.close();
        
        temaxia.setText("1");
        timi.setText("1.0");
        timi_Agoras.setText("1.0");
        orio_temaxion.setText("20");
        
        temaxia.textProperty().addListener((observable, oldValue, newValue) -> {
            check_txt_if_is_num(newValue, 1);
        });
        timi.textProperty().addListener((observable, oldValue, newValue) -> {
            check_txt_if_is_num(newValue, 2);
        });
        timi_Agoras.textProperty().addListener((observable, oldValue, newValue) -> {
            check_txt_if_is_num(newValue, 3);
        });
        orio_temaxion.textProperty().addListener((observable, oldValue, newValue) -> {
            check_txt_if_is_num(newValue, 4);
        });

    }

    @FXML
    private void add_image(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File f = fileChooser.showOpenDialog((Stage) pane.getScene().getWindow());
        if (f != null) {
            String url = f.toURI().toString();
            image.setImage(new Image(url));

            Im_url = url;
            System.out.println(url);

        }

    }

    @FXML
    private void To_DataBase(ActionEvent event) {

        if (check_keno()) {
            String img = "/src/meditest/images/default_img.jpg";

            if (Im_url != null) {
                try {
                    

                    Path src = Paths.get(URI.create(Im_url));//5

                    String[] File_Name = Im_url.split("/");

                    String name = File_Name[File_Name.length - 1];
                    String To;//= System.getProperty("user.dir") +"/src/meditest/images/"+name;

                    while (check_if_img_exists(name)) {
                        name = name.split("\\.")[0] + "_new." + name.split("\\.")[1];
                        System.out.println(name);
                    }
                    To = System.getProperty("user.dir") + "/src/meditest/images/" + name;

                    img = "/src/meditest/images/" + name;

                    Path dest = Paths.get(To);
                    Files.copy(src, dest);

                } catch (IOException ex) {
                    //Logger.getLogger(Farmaka_Add_to_base_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //onoma,ousia,hmer_lyksis,timi,xwra,temaxia,katigoria,xrhsh,timi_agoras,hmer_paraskevis,poiothta,id_promyth,img
            db DB = new db();
            String query = "INSERT INTO farmaka (onoma,ousia,hmer_lyksis,timi,xwra,temaxia,katigoria,xrhsh,timi_agoras,hmer_paraskevis,poiothta,id_promyth,img,orio_temaxion)"
                    + " VALUES ('" + name_txt.getText() + "','" + ousia_txt.getText() + "','" + hmer_L.getValue().toString() + "'," + timi.getText()
                    + ",'" + xwra_txt.getText() + "'," + temaxia.getText() + ",'" + category.getText() + "','" + usage.getText() + "'," + timi_Agoras.getText()
                    + ",'" + P_hmer_P.getValue().toString() + "','" + poiothta.getText() + "'," + promyth.getValue().split("|")[0] + ",'" + img +"',"+orio_temaxion.getText()+ ");";

            System.out.println(query);
            DB.updateDB(query);
            DB.close();

            Stage stage = (Stage) pane.getScene().getWindow();

            stage.close();
        } else {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("Information Dialog");
            a.setHeaderText("Υπάρχη Κενό Κελή");
            a.setContentText("Για να προσθεσουμε φάρμακο χρειάζονται όλα τα κελιά");

            a.showAndWait();
        }
    }

    Boolean check_keno() {

        if (hmer_L.getValue() == null || P_hmer_P.getValue() == null || promyth.getValue() == null || temaxia.getText().isEmpty() || timi.getText().isEmpty()
                || timi_Agoras.getText().isEmpty() || name_txt.getText().isEmpty() || ousia_txt.getText().isEmpty() || xwra_txt.getText().isEmpty() || category.getText().isEmpty()
                || poiothta.getText().isEmpty() || usage.getText().isEmpty() || orio_temaxion.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    Boolean check_if_img_exists(String name) {
        /*
            sinartisi pou elenxi ama yparxi to arxeio
            kai epistrefi tru ama yparxei kai false an oxi
         */
        File folder = new File(System.getProperty("user.dir") + "/src/meditest/images/");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                if (file.getName().equals(name)) {
                    System.out.println(file.getName());
                    return true;
                }
            }
        }

        return false;
    }

    void check_txt_if_is_num(String value, int _txt_) {//_txt_ : 1 gia temaxia 2 gia timi , 3 gia timi_agoras , orio_temaxion
        if (_txt_ == 1) {
            if (value.matches("[0-9]*")) {
                temaxia.setText(value);

            } else {
                temaxia.setText(String.valueOf("1"));
            }
        } else if (_txt_ == 2) {
            if (!timi.getText().isEmpty()) {
                try {
                    double is_double = Double.parseDouble(value);
                    timi.setText(value);
                } catch (NumberFormatException e) {
                    timi.setText("1.0");
                }
            }

        }else if (_txt_ == 3) {
            if (!timi_Agoras.getText().isEmpty()) {
                try {
                    double is_double = Double.parseDouble(value);
                    timi_Agoras.setText(value);
                } catch (NumberFormatException e) {
                    timi_Agoras.setText("1.0");
                }
            }

        }else if(_txt_==4){   //orio_temaxion
            if (value.matches("[0-9]*")) {
                orio_temaxion.setText(value);

            } else {
                orio_temaxion.setText(String.valueOf("10"));
            } 
        }
    }
}
