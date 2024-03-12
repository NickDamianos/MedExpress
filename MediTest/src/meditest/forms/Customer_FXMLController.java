/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest.forms;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import meditest.db;

/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class Customer_FXMLController implements Initializable {

    @FXML
    private TextField search_text;
    @FXML
    private CheckBox check_farmakopoios;
    @FXML
    private CheckBox check_farmakeio;
    @FXML
    private Button new_pelatis_button;
    @FXML
    private Button refresh_button;
    
    
    @FXML
    private TableView<F_Farmakoia_Items> farmakeia_table;
    @FXML
    private TableColumn<F_Farmakoia_Items, Integer> farmakeia_ID_col;
    @FXML
    private TableColumn<F_Farmakoia_Items, String> farmakeia_name_col;
    @FXML
    private TableColumn<F_Farmakoia_Items, String> farmakeia_phone_col;
    @FXML
    private TableColumn<F_Farmakoia_Items, String> farmakeia_odos_col;
    @FXML
    private TableColumn<F_Farmakoia_Items, String> farmakeia_TK_col;
    @FXML
    private TableColumn<F_Farmakoia_Items, String> farmakeia_PERpolDim_col;
    @FXML
    private TableColumn<F_Farmakoia_Items, String> farmakeia_AFM_col;
    @FXML
    private TableColumn<F_Farmakoia_Items, Button> farmakeia_edit_col;
    
    public static int farmak_id;
    public static int id_farmakeiou;
    
    @FXML
    private TableView<Farmakopoioi_Items> farmakopoioi_table;
    @FXML
    private TableColumn<Farmakopoioi_Items, Integer> farmakopoioi_ID_col;
    @FXML
    private TableColumn<Farmakopoioi_Items, String> farmakopoioi_name_col;
    @FXML
    private TableColumn<Farmakopoioi_Items, String> farmakopoioi_epith_col;
    @FXML
    private TableColumn<Farmakopoioi_Items, Integer> farmakopoioi_id_farmakeiou_col;
    @FXML
    private TableColumn<Farmakopoioi_Items, String> farmakopoioi_phone_col;
    @FXML
    private TableColumn<Farmakopoioi_Items, String> farmakopoioi_email_col;
    @FXML
    private TableColumn<Farmakopoioi_Items, Button> farmakopoioi_edit_col;

    
    static ObservableList<Farmakopoioi_Items> oblist_Farmakopoioi = FXCollections.observableArrayList();
    static ObservableList<F_Farmakoia_Items> Farmakoia_oblist = FXCollections.observableArrayList();
    
    
    static ObservableList<Farmakopoioi_Items> Search_oblist_Farmakopoioi = FXCollections.observableArrayList();
    static ObservableList<F_Farmakoia_Items> Search_Farmakoia_oblist = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oblist_Farmakopoioi.clear();
        Farmakoia_oblist.clear();
        
        farmakopoioi_ID_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        farmakopoioi_name_col.setCellValueFactory(new PropertyValueFactory<>("onoma"));
        farmakopoioi_epith_col.setCellValueFactory(new PropertyValueFactory<>("epitheto"));
        farmakopoioi_id_farmakeiou_col.setCellValueFactory(new PropertyValueFactory<>("id_farmakeiou"));
        farmakopoioi_phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        farmakopoioi_email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        farmakopoioi_edit_col.setCellValueFactory(new PropertyValueFactory<>("edit"));
        
        farmakeia_ID_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        farmakeia_name_col.setCellValueFactory(new PropertyValueFactory<>("onoma"));
        farmakeia_phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        farmakeia_odos_col.setCellValueFactory(new PropertyValueFactory<>("odos"));
        farmakeia_TK_col.setCellValueFactory(new PropertyValueFactory<>("tk"));
        farmakeia_PERpolDim_col.setCellValueFactory(new PropertyValueFactory<>("Perioxi_Poli_Dimos"));
        farmakeia_AFM_col.setCellValueFactory(new PropertyValueFactory<>("afm"));
        farmakeia_edit_col.setCellValueFactory(new PropertyValueFactory<>("edit"));
        
        
        check_farmakopoios.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                check_farmakeio.setSelected(oldValue);    
            }
        });
        
        check_farmakeio.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                check_farmakopoios.setSelected(oldValue);    
            }
        });
        
        
        search_text.textProperty().addListener((observable, oldValue, newValue) -> { //otan allaksi to textfield tote kane: 
            
            Search_oblist_Farmakopoioi.clear();//einai h lista pou 8a apothikrutoun oi times pou vriskei gia tous farmakopoious
            Search_Farmakoia_oblist.clear();//einai h lista pou 8a apothikrutoun oi times pou vriskei gia ta farmakeia
            
            
            
            if(newValue.equals("")){//an den exei timi sto textfield tote dikse ta ola 
                farmakopoioi_table.setItems(oblist_Farmakopoioi);
                farmakeia_table.setItems(Farmakoia_oblist);
                
            } else {
                String txt = newValue.toLowerCase();//ta kanei mikra 

                if (check_farmakopoios.isSelected()) {
                    for (Farmakopoioi_Items Farmak : oblist_Farmakopoioi) { // gia ka8e farmakopoio 
                        String id = "#" + String.valueOf(Farmak.getId());

                        if (id.equals(txt)) {//ama to noumero einai iso me to id tote vale stin lista ton farmakopoion  
                            Search_oblist_Farmakopoioi.add(Farmak);

                            break;
                        } else if (Farmak.getEmail().contains(txt) || Farmak.getEpitheto().contains(txt)
                                || Farmak.getOnoma().contains(txt) || Farmak.getPhone().contains(txt)
                                || String.valueOf(Farmak.getId_farmakeiou()).contains(txt)) { // ama yparxei mesa sta parapanw tote pros8eseta sthn lista 

                            Search_oblist_Farmakopoioi.add(Farmak);

                        }

                    }

                    farmakopoioi_table.setItems(Search_oblist_Farmakopoioi);

                } else {
                    for (F_Farmakoia_Items farmakeio : Farmakoia_oblist) { // gia ka8e farmakeio
                        String id = "#" + String.valueOf(farmakeio.getId());

                        

                        if (id.equals(txt)) {//ama to noumero einai iso me to id tote vale stin lista ton parag 
                            Search_Farmakoia_oblist.add(farmakeio);

                            break;
                        } else if (farmakeio.getAfm().contains(txt) || farmakeio.getOdos().contains(txt)
                                || farmakeio.getOnoma().contains(txt) || farmakeio.getPerioxi_Poli_Dimos().contains(txt)
                                || farmakeio.getPhone().contains(txt) || farmakeio.getTk().contains(txt)) { // ama yparxei mesa sta parapanw tote pros8eseta sthn lista 

                            Search_Farmakoia_oblist.add(farmakeio);

                        }

                    }

                    farmakeia_table.setItems(Search_Farmakoia_oblist);
                }
            }
        });
        
        refresh();
        
        
    }

    void refresh(){
        search_text.setText("");
        
        
        String SQL_Farmakeia = "SELECT * from farmakeia";
        String Farmakopoioi_SQL = "SELECT * from farmakopoioi";
        
        db DB_farmakeia = new db();
        db Farmakopoios_DB = new db();
        
        ResultSet rs_farmakeia = DB_farmakeia.SelectQuery(SQL_Farmakeia);
        ResultSet Farmakopoios_rs = Farmakopoios_DB.SelectQuery(Farmakopoioi_SQL);
        
        try {
            while(rs_farmakeia.next()){
                Farmakoia_oblist.add(new F_Farmakoia_Items(rs_farmakeia.getInt("id"),rs_farmakeia.getString("onoma"),rs_farmakeia.getString("tilefono"),
                rs_farmakeia.getString("odos"),rs_farmakeia.getString("t_k"),
                rs_farmakeia.getString("perioxi")+" "+rs_farmakeia.getString("poli")+" "+rs_farmakeia.getString("dimos"),
                rs_farmakeia.getString("afm"),new Button("Edit")));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Customer_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            while(Farmakopoios_rs.next()){
                oblist_Farmakopoioi.add(new Farmakopoioi_Items(Farmakopoios_rs.getInt("id"),Farmakopoios_rs.getString("onoma"),
                Farmakopoios_rs.getString("epitheto"),Farmakopoios_rs.getString("tilefono"),Farmakopoios_rs.getString("email"),
                Farmakopoios_rs.getInt("id_farmakeiou"),new Button("Edit")));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Customer_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DB_farmakeia.close();
        Farmakopoios_DB.close();
        
        
        farmakopoioi_table.setItems(oblist_Farmakopoioi);
        farmakeia_table.setItems(Farmakoia_oblist);
        
        
    }

    @FXML
    private void refresh_button(ActionEvent event) {
        oblist_Farmakopoioi.clear();
        Farmakoia_oblist.clear();
        refresh();
    }

    @FXML
    private void new_costomer(ActionEvent event) {
        FXMLLoader loader;
        String Title;
        
        loader = new FXMLLoader(getClass().getResource("new_costomer_add_FXML.fxml"));
        Title = "Νέος Πελατης";
       

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
        
        
    }
    

    public class Farmakopoioi_Items{
        int id;
        String onoma,epitheto,phone,email;
        int id_farmakeiou;
        Button edit;

        public Farmakopoioi_Items(int id, String onoma, String epitheto, String phone, String email, int id_farmakeiou, Button edit) {
            this.id = id;
            this.onoma = onoma;
            this.epitheto = epitheto;
            this.phone = phone;
            this.email = email;
            this.id_farmakeiou = id_farmakeiou;
            this.edit = edit;
            //Edit_farmakopoio_FXML.fxml
            
            this.edit.setOnAction(e -> {
                farmak_id = this.id;

                
                FXMLLoader loader;
                String Title;

                loader = new FXMLLoader(getClass().getResource("Edit_farmakopoio_FXML.fxml"));
                Title = "Επεξεργασία Φαρμακοποιου";

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

            });
        }

        public int getId() {
            return id;
        }

        public String getOnoma() {
            return onoma;
        }

        public String getEpitheto() {
            return epitheto;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public int getId_farmakeiou() {
            return id_farmakeiou;
        }

        public Button getEdit() {
            return edit;
        }
        
        
    }
    
    public class F_Farmakoia_Items{
        int id;
        String onoma,phone,odos,tk,Perioxi_Poli_Dimos,afm;
        Button edit;

        public F_Farmakoia_Items(int id, String onoma, String phone, String odos, String tk, String Perioxi_Poli_Dimos, String afm, Button edit) {
            this.id = id;
            this.onoma = onoma;
            this.phone = phone;
            this.odos = odos;
            this.tk = tk;
            this.Perioxi_Poli_Dimos = Perioxi_Poli_Dimos;
            this.afm = afm;
            this.edit = edit;

            this.edit.setOnAction(e -> {
                id_farmakeiou = this.id;

                //Edit_farmakeio_FXML.fxml
                FXMLLoader loader;
                String Title;

                loader = new FXMLLoader(getClass().getResource("Edit_farmakeio_FXML.fxml"));
                Title = "Επεξεργασία Φαρμακείου";

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

            });
        }

        public int getId() {
            return id;
        }

        public String getOnoma() {
            return onoma;
        }

        public String getPhone() {
            return phone;
        }

        public String getOdos() {
            return odos;
        }

        public String getTk() {
            return tk;
        }

        public String getPerioxi_Poli_Dimos() {
            return Perioxi_Poli_Dimos;
        }

        public String getAfm() {
            return afm;
        }

        public Button getEdit() {
            return edit;
        }
        
        
    }
    
}
