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
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import meditest.MediTest;
import meditest.db;
import meditest.farmaka;

/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class More_info_med_FXMLController implements Initializable {

    @FXML
    private ImageView img_holder;
    @FXML
    private CheckBox editaable_check;
    @FXML
    private Label name_label;
    @FXML
    private Label id_label;
    @FXML
    private Label ousia_label;
    @FXML
    private TextField name_txt;
    @FXML
    private TextField ousia_txt;
    @FXML
    private TextField country_txt;
    @FXML
    private TextField price_txt_agoras_edit;
    @FXML
    private TextField price_txt;
    @FXML
    private TextField temaxia_edit_txt;
    @FXML
    private TextField poiotita_txt;
    @FXML
    private TextField category_txt;
    @FXML
    private TextArea usage_txt;
    @FXML
    private DatePicker date_liksi_edit;
    @FXML
    private DatePicker Date_parag_edit;
    @FXML
    private ChoiceBox<String> promith_edit;
    @FXML
    private Button edit_button;
    @FXML
    private Button del_button;
    @FXML
    private TextField temaxia_txt_agoras;
    @FXML
    private DatePicker Date_liksi_Agora;
    @FXML
    private DatePicker Date_Parag_Agora;
    @FXML
    private ChoiceBox<String> Promith_choice_Agora;
    @FXML
    private Button Agora_button;
    @FXML
    private TextField price_txt_agoras;
    
    farmaka farmako;
    @FXML
    private VBox pane;
    
    String new_Img;
    Boolean Image_changed;
    @FXML
    private TextField orio_edit_txt1;
    @FXML
    private TextField Etairia_txt;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orio_edit_txt1.setEditable(false);
        Image_changed = false;
        db DB = new db();
        String query = "SELECT * from farmaka where id = " + MediTest.getWorking_id();
        ResultSet rs = DB.SelectQuery(query);

        try {
            farmako = new farmaka(rs.getInt("id"), rs.getString("onoma"), rs.getString("ousia"), rs.getString("hmer_lyksis"),
                    rs.getDouble("timi"), rs.getString("xwra"),
                    rs.getInt("temaxia"), rs.getString("katigoria"),
                    rs.getString("xrhsh"), rs.getInt("timi_agoras"),
                    rs.getString("hmer_paraskevis"), rs.getString("poiothta"), rs.getInt("id_promyth"), rs.getString("img"),rs.getInt("orio_temaxion"),rs.getString("etairia"));//
        } catch (SQLException ex) {
            Logger.getLogger(Med_add_kalathi_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DB.close();
        
        new_Img = farmako.getEikona();
        
        
        pane.setAlignment(Pos.CENTER);
        
        img_holder.fitWidthProperty().bind(pane.widthProperty());
        img_holder.setPreserveRatio(true);
        Image image = new Image("file:" + System.getProperty("user.dir") + farmako.getEikona());
        img_holder.setImage(image);
        
        
        
        
        
        
        
        
        editaable_check.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //editaable_check.setSelected(!newValue);
                if (newValue) {

                    name_txt.setEditable(true);
                    ousia_txt.setEditable(true);
                    country_txt.setEditable(true);
                    price_txt_agoras_edit.setEditable(true);
                    price_txt.setEditable(true);
                    //temaxia_edit_txt.setEditable(true);
                    poiotita_txt.setEditable(true);
                    category_txt.setEditable(true);
                    usage_txt.setEditable(true);
                    date_liksi_edit.setEditable(true);
                    Date_parag_edit.setEditable(true);
                    promith_edit.setDisable(false);
                    edit_button.setDisable(false);
                    del_button.setDisable(false);
                    orio_edit_txt1.setEditable(true);
                } else {
                    name_txt.setEditable(false);
                    ousia_txt.setEditable(false);
                    country_txt.setEditable(false);
                    price_txt_agoras_edit.setEditable(false);
                    price_txt.setEditable(false);
                    //temaxia_edit_txt.setEditable(false);
                    poiotita_txt.setEditable(false);
                    category_txt.setEditable(false);
                    usage_txt.setEditable(false);
                    date_liksi_edit.setEditable(false);
                    Date_parag_edit.setEditable(false);
                    promith_edit.setDisable(true);//
                    edit_button.setDisable(true);
                    del_button.setDisable(true);
                    orio_edit_txt1.setEditable(false);
 
                }
                
                
            }
        });
        
        refresh();
        
        
    }    

    Boolean check_for_agora(){
        /*  Promith_choice_Agora;*/
        
        if(temaxia_txt_agoras.getText().isEmpty() || Date_liksi_Agora.getValue() == null ||Date_Parag_Agora.getValue() == null ||Promith_choice_Agora.getValue().isEmpty()||price_txt_agoras.getText().isEmpty()){
            return false;
        }
        return true;
    }
    
    
    Boolean check_for_edit(){
        
        if(name_txt.getText().isEmpty() || ousia_txt.getText().isEmpty() ||country_txt.getText().isEmpty() ||price_txt_agoras_edit.getText().isEmpty() 
                || price_txt.getText().isEmpty() ||temaxia_edit_txt.getText().isEmpty() ||poiotita_txt.getText().isEmpty() 
                ||category_txt.getText().isEmpty() ||usage_txt.getText().isEmpty()||date_liksi_edit.getValue() == null 
                ||Date_parag_edit.getValue() == null || promith_edit.getSelectionModel().isEmpty() || orio_edit_txt1.getText().isEmpty()){
            
            return false;
        }
        return true;
    }

    @FXML
    private void Edit_Med(ActionEvent event) throws IOException {
        if (check_for_edit()) {
            if (Image_changed) {
                
                try {
                    

                    Path src = Paths.get(URI.create(new_Img));

                    String[] File_Name = new_Img.split("/");

                    String name = File_Name[File_Name.length - 1];
                    String To;//= System.getProperty("user.dir") +"/src/meditest/images/"+name;
                    System.out.println(name);
                    //File temp = File.createTempFile(System.getProperty("user.dir") + "/src/meditest/images/" + name, ".txt");
                    while(check_if_img_exists(name)){
                        name=name.split("\\.")[0]+"_new."+name.split("\\.")[1];
                        System.out.println(name);
                    }
                    To = System.getProperty("user.dir") + "/src/meditest/images/" + name;
                    
                    Path dest = Paths.get(To);
                    Files.copy(src, dest);
                    System.out.println(To);
                    new_Img = "/src/meditest/images/" +name;
                } catch (IOException ex) {
                    Logger.getLogger(Farmaka_Add_to_base_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            db DB = new db();

            String query = "UPDATE farmaka SET onoma='" + name_txt.getText() + "', ousia='" + ousia_txt.getText()
                    + "', hmer_lyksis ='" + date_liksi_edit.getValue().toString() + "',timi = " + price_txt.getText() + ",xwra ='" + country_txt.getText()
                    + "',temaxia = " + temaxia_edit_txt.getText() + ",katigoria='" + category_txt.getText() + "',xrhsh='" + usage_txt.getText()
                    + "',timi_agoras =" + price_txt_agoras_edit.getText() + ",hmer_paraskevis='" + Date_parag_edit.getValue().toString() + "',poiothta ='" + poiotita_txt.getText()
                    + "' ,id_promyth =" + promith_edit.getValue().split("|")[0] + " ,img ='" + new_Img +"', orio_temaxion ="+orio_edit_txt1.getText() +" WHERE id =" + farmako.getId() + ";";

            DB.updateDB(query);
            DB.close();
            
            Stage stage = (Stage) pane.getScene().getWindow();
            
            stage.close();

        } else {
            
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information Dialog");
            a.setHeaderText("Υπάρχη Κενό Κελή");
            a.setContentText("Για να προσθεσουμε φάρμακο χρειάζονται όλα τα κελιά");

            a.showAndWait();

        }
    }

    @FXML
    private void Del_Med(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Delete Dialog");
        a.setHeaderText("Ειστε Σίγουρος πως θα θέλατε να διαγράψεται αυτο το φάρμακο");

        a.showAndWait();

        if (a.getResult() == ButtonType.OK) {
            db DB = new db();
            String sql = "DELETE FROM farmaka WHERE id=" + farmako.getId() + ";";
            DB.updateDB(sql);
            DB.close();
            
            Stage stage = (Stage) pane.getScene().getWindow();
            
            stage.close();
        }
    }

    @FXML
    private void Buy_Med(ActionEvent event) {
        if (check_for_agora()) {
            //Date_Parag_Agora.getValue() == null ||Promith_choice_Agora.getValue().isEmpty() 
            db DB = new db();
            String query = "UPDATE farmaka SET  hmer_lyksis ='" + Date_liksi_Agora.getValue().toString() + "',temaxia = " + temaxia_txt_agoras.getText() + ",timi_agoras =" + price_txt_agoras.getText()
                    + ",hmer_paraskevis='" + Date_Parag_Agora.getValue().toString() + "',id_promyth =" + Promith_choice_Agora.getValue().split("|")[0] + " WHERE id =" + farmako.getId() + ";";
            DB.updateDB(query);
            DB.close();
            
            farmako.setHmer_lyksis(Date_liksi_Agora.getValue().toString());
            farmako.setHmer_paraskevis(Date_Parag_Agora.getValue().toString());
            farmako.setTemaxia(Integer.parseInt(temaxia_txt_agoras.getText()) );
            farmako.setTimi_agoras(Double.parseDouble(price_txt_agoras.getText()));
            farmako.setId_promyth(Integer.parseInt(Promith_choice_Agora.getValue().split("|")[0]));
            
            refresh();
        } else {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information Dialog");
            a.setHeaderText("Υπάρχη Κενό Κελή");
            a.setContentText("Για να αγοράσουμε φάρμακο χρειάζονται όλα τα κελιά");

            a.showAndWait();
        }
    }

    @FXML
    private void add_new_img_edit(MouseEvent event) throws IOException {
        if (editaable_check.isSelected()) {
            FileChooser fileChooser = new FileChooser();
            File f = fileChooser.showOpenDialog((Stage) pane.getScene().getWindow());
            if (f != null) {
                String url = f.toURI().toString();
                img_holder.setImage(new Image(url));

                new_Img = url;
                Image_changed = true;
                   

            }
        }
    }

    
    
    void refresh(){//synartisi h opoia ananeoni tis times ton keliwn
        id_label.setText("ID : " + farmako.getId());
        name_txt.setText(farmako.getOnoma());
        ousia_txt.setText(farmako.getOusia());
        country_txt.setText(farmako.getXwra());
        price_txt_agoras_edit.setText(String.valueOf(farmako.getTimi_agoras()));
        price_txt.setText(String.valueOf(farmako.getTimi()));
        temaxia_edit_txt.setText(String.valueOf(farmako.getTemaxia()));
        poiotita_txt.setText(farmako.getPoiothta());
        category_txt.setText(farmako.getKatigoria());
        usage_txt.setText(farmako.getXrhsh());
        orio_edit_txt1.setText(String.valueOf(farmako.getOrio()));
        Etairia_txt.setText(farmako.getEtairia());
        
        String[] liksi = farmako.getHmer_lyksis().split("-");
        date_liksi_edit.setValue(LocalDate.of(Integer.parseInt(liksi[0]),Integer.parseInt(liksi[1]),Integer.parseInt(liksi[2])));
        
        String[] parag = farmako.getHmer_paraskevis().split("-");
        Date_parag_edit.setValue(LocalDate.of(Integer.parseInt(parag[0]),Integer.parseInt(parag[1]),Integer.parseInt(parag[2])));
        
        //promith_edit.setValue(farmako.);
        db DB = new db();
        
        ResultSet rs = DB.SelectQuery("SELECT id,name from promith");
        
        try {
            while(rs.next()){
                promith_edit.getItems().add(String.valueOf(rs.getInt("id"))+"|"+rs.getString("name"));
                Promith_choice_Agora.getItems().add(String.valueOf(rs.getInt("id"))+"|"+rs.getString("name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Farmaka_Add_to_base_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DB.close();
        
        for (String id : promith_edit.getItems()) {//gia na vali default value to id tou promitheuti
                    if (id.split("|")[0].equals(String.valueOf(farmako.getId_promyth()))) {
                        promith_edit.setValue(id);
                        Promith_choice_Agora.setValue(id);
                    }
                }
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
                if(file.getName().equals(name)){
                    System.out.println(file.getName());
                    return true;
                }
            }
        }
        
        return false;
    }

}
