/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest.forms;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import meditest.FXMLmainController;
import static meditest.FXMLmainController.List_Kalathi;
import meditest.MediTest;
import meditest.db;
import meditest.kalathi_items;

/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class Kalathi_FXMLController implements Initializable {

    static ObservableList<kalathi_items> oblist_Kalathi = FXCollections.observableArrayList();
    
    @FXML
    private ChoiceBox<String> farmakopoios;
    @FXML
    private TableView<kalathi_items> proionta_table;
    @FXML
    private TableColumn<kalathi_items, Integer> id_farmakou;
    @FXML
    private TableColumn<kalathi_items, String> onoma_farmakou;
    @FXML
    private TableColumn<kalathi_items, String> ousia_farmakou;
    @FXML
    private TableColumn<kalathi_items, Integer> temaxia_farmakon;
    @FXML
    private TableColumn<kalathi_items, Double> timi_farmakou;
    @FXML
    private Button paragkelia;
    @FXML
    private TextField ekptosi;
    @FXML
    private TextField sun_poso_;
    @FXML
    private TextField me_fpa;
    @FXML
    private TextField sun_timi_me_ekptosi;
    @FXML
    private TableColumn<kalathi_items, Double> all_price;

    public static String Print_Url;

    ObservableList<kalathi_items> Tmp_kalthi = FXCollections.observableArrayList();
    @FXML
    private Button delete_row_button;
    @FXML
    private ChoiceBox<String> dianomeas_sel;

    static String id_dian;
    static String id_Farmakopoiou;
    @FXML
    private TextField metaforika;
    static String metaforika_text;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        metaforika.setText("10");
        metaforika_text = "10";
        
        ekptosi.textProperty().addListener((observable, oldValue, newValue) -> {
            check_txt_if_is_num(newValue,metaforika.getText());
        });
        
        metaforika.textProperty().addListener((observable, oldValue, newValue) -> {
            check_txt_if_is_num(newValue,ekptosi.getText());
            metaforika_text = metaforika.getText();
        });
        
        
        
        
        oblist_Kalathi = FXCollections.observableList(FXMLmainController.List_Kalathi);
        
        
        id_farmakou.setCellValueFactory(new PropertyValueFactory<>("med_id"));

        onoma_farmakou.setCellValueFactory(new PropertyValueFactory<>("onoma"));
        ousia_farmakou.setCellValueFactory(new PropertyValueFactory<>("ousia"));
        temaxia_farmakon.setCellValueFactory(new PropertyValueFactory<>("temaxia"));

        timi_farmakou.setCellValueFactory(new PropertyValueFactory<>("timi"));

        all_price.setCellValueFactory(new PropertyValueFactory<>("syn_timi"));

        //del_farmakou.setCellValueFactory(new PropertyValueFactory<>("button"));
        refresh();

        if (FXMLmainController.List_Kalathi.size() > 0) {
            double poso = 0;
            double poso_fpa = 0;
            for (kalathi_items ii : FXMLmainController.List_Kalathi) {
                poso += ii.getSyn_timi();
                poso_fpa += ii.getSyn_timi() * 1.24;
            }
            sun_poso_.setText(String.valueOf(poso));
            me_fpa.setText(String.valueOf(poso_fpa));
            sun_timi_me_ekptosi.setText(String.valueOf(poso_fpa*(1-Double.parseDouble(ekptosi.getText()))+Double.parseDouble(metaforika_text)));
        }
        sun_poso_.setEditable(false);

        
    }

    void refresh() {

        proionta_table.refresh();
        /*
        for (kalathi_items tmp : FXMLmainController.List_Kalathi) {
            Tmp_kalthi.add(tmp);
        }*/
        proionta_table.setItems(oblist_Kalathi);

        db DB = new db();
        db DB2 = new db();
        db DB3 = new db();
        String query = "SELECT * from farmakopoioi;";
        String query2 = "SELECT * from dianomeas;";
        String query3 = "SELECT * from users WHERE id = ";
        try {
            ResultSet rs = DB.SelectQuery(query);
            while (rs.next()) {
                farmakopoios.getItems().add(rs.getInt("id") + "|" + rs.getString("onoma") + " " + rs.getString("epitheto"));
            }
            
            ResultSet rs2 = DB2.SelectQuery(query2);
            while (rs2.next()) {
                query3 = query3 + rs2.getInt("id")+";";
                ResultSet rs3 = DB3.SelectQuery(query3);
                dianomeas_sel.getItems().add(rs2.getInt("id") + ":" + rs3.getString("firstname") + " " + rs3.getString("lastname"));
                query3 = "SELECT * from users WHERE id = ";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Kalathi_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DB.close();
        DB2.close();
        DB3.close();
        
        if (FXMLmainController.List_Kalathi.size() > 0) {
            double poso = 0;
            for (kalathi_items ii : FXMLmainController.List_Kalathi) {
                poso += ii.getSyn_timi();
            }
            sun_poso_.setText(String.valueOf(poso));
            me_fpa.setText(String.valueOf(poso * 1.24));
        }
        
        if(dianomeas_sel.getItems().size()>0){
            dianomeas_sel.setValue(dianomeas_sel.getItems().get(0));
        }

    }


    @FXML
    private void print_timologio(ActionEvent event) throws FileNotFoundException, DocumentException {
        if (farmakopoios.getValue() != null && FXMLmainController.List_Kalathi.size()>0) {

            PDF_create n = new PDF_create(Double.parseDouble(ekptosi.getText()), farmakopoios.getValue());
            Print_Url = n.getImg();

            FXMLLoader loader;
            String Title;

            loader = new FXMLLoader(getClass().getResource("Print_Preview_FXML.fxml"));
            Title = "Εκτύπωση";
            id_dian = dianomeas_sel.getValue();
            id_Farmakopoiou = farmakopoios.getValue().split("|")[0];
            
            Parent root;
            try {
                root = loader.load();
                Stage stage = new Stage();

                Scene scene = new Scene(root);
                stage.setTitle(Title);
                stage.initModality(Modality.APPLICATION_MODAL);
                
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(Medicine_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information Dialog");
            a.setHeaderText("Δεν έχει επιλεχθει πελάτης ή Προιόντα στο καλάθι");
            
            a.showAndWait();
        }
    }

    @FXML
    private void del_row(ActionEvent event) {
        int index = proionta_table.getSelectionModel().getSelectedIndex();
        delete(index);
        
    }
    
    void delete(int index) {
        kalathi_items Del_item = proionta_table.getSelectionModel().getSelectedItem();//-1 an den exei dialeksi

        reAddTemaxia(Del_item);

        oblist_Kalathi.remove(index);

        if (index != 0) {

            index = index - 1;
        }

        proionta_table.requestFocus();
        proionta_table.getSelectionModel().select(index);
        proionta_table.getFocusModel().focus(index);

    }

    void reAddTemaxia(kalathi_items add) {
        db DB = new db();

        String query1 = "SELECT * from farmaka where id = " + add.getMed_id();
        ResultSet rs = DB.SelectQuery(query1);

        try {
            String query = "UPDATE farmaka SET temaxia = " + String.valueOf(Integer.parseInt(add.getTemaxia()) + rs.getInt("temaxia")) + " WHERE id = " + add.getMed_id();
            DB.updateDB(query);

        } catch (SQLException ex) {
            Logger.getLogger(MediTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        DB.close();

    }
    
    public static void remove_all_from_table(){
        oblist_Kalathi.clear();
    }
    
    public static String getDian(){
        if(id_dian!=null){
            
            return  id_dian.split(":")[0];
        }
        return "1";
    }
    
    public static String getFarmakopoioiData(){
        int id_farmakeioy = 1;
        db DB = new db();
        String querry  = "SELECT * from farmakopoioi where id = "+id_Farmakopoiou+";";
        ResultSet rs = DB.SelectQuery(querry);
        try {
            
            id_farmakeioy = rs.getInt("id_farmakeiou");
        } catch (SQLException ex) {
            Logger.getLogger(PDF_create.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String querry2 = "SELECT * from farmakeia where id = "+ id_farmakeioy;
        rs = DB.SelectQuery(querry2);
        
        String onoma_farmakeiou="";
        
        String dieuthinsi="";
        try {
            onoma_farmakeiou = rs.getString("onoma");
            dieuthinsi = rs.getString("odos") + "_"+rs.getString("perioxi")+"_"+rs.getString("poli")+"_"+rs.getString("dimos");
            
        } catch (SQLException ex) {
            Logger.getLogger(PDF_create.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DB.close();
        
        String ret_values = id_Farmakopoiou+":"+onoma_farmakeiou+":"+dieuthinsi;
        return ret_values;
    
    }
    
    
void check_txt_if_is_num(String value ,String value2) {
        
            if (value.matches("[0-9]*") && !value.isEmpty() && value2.matches("[0-9]*") && !value2.isEmpty()) {
                //ekptosi.setText(value);
                
                sun_timi_me_ekptosi.setText(String.valueOf((Double.parseDouble(me_fpa.getText()) * (1-(Double.parseDouble(ekptosi.getText())*0.01)))+Double.parseDouble(metaforika.getText())));

            } else {
                if(ekptosi.getText().isEmpty() || !ekptosi.getText().matches("[0-9]*")){
                    ekptosi.setText(String.valueOf("1"));
                }
                if(metaforika.getText().isEmpty() || !metaforika.getText().matches("[0-9]*")){
                    metaforika.setText(String.valueOf("10"));
                    metaforika_text = "10";
                }
            }
        
    }
    
    public static Double getMetaforika(){
        return Double.parseDouble(metaforika_text);
    
    }
}


