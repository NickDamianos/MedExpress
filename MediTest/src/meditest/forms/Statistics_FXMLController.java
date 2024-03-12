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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import meditest.MediTest;
import meditest.db;

/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class Statistics_FXMLController implements Initializable {
    static ObservableList<Tziros_ana_farmaka> oblist_Tziros_ana_farmaka = FXCollections.observableArrayList();//gia Tab 1
    static ObservableList<Tziros_ana_Farmakopoio> oblist_Tziros_ana_FarmaKopoio = FXCollections.observableArrayList();//gia Tab 2
    static ObservableList<Etisies_poliseis_ana_farmakeio_kai_proion> oblist_Etisies_poliseis_ana_farmakeio_kai_proion = FXCollections.observableArrayList();
    static ObservableList<Kerdi_analoga_mepos_kerdon_ana_farmako> oblist_Kerdi_analoga_mepos_kerdon_ana_farmako = FXCollections.observableArrayList();
    static ObservableList<Tziros_ANA_Farmakeio> oblist_Tziros_ANA_Farmakeio = FXCollections.observableArrayList();
    static ObservableList<Kerdofora_Farmaka> oblist_Kerdofora_Farmaka = FXCollections.observableArrayList();
    static ObservableList<Plirothike> oblist_Plirothike = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    
    static double Eteisios_Tziros = 0;
    @FXML
    private TableView<Tziros_ana_farmaka> Table_TZ_PR;
    @FXML
    private TableColumn<Tziros_ana_farmaka, Integer> Table_TZ_PR_ID_col;
    @FXML
    private TableColumn<Tziros_ana_farmaka, String> Table_TZ_PR_onoms_col;
    @FXML
    private TableColumn<Tziros_ana_farmaka, Integer> Table_TZ_PR_minas_col;
    @FXML
    private TableColumn<Tziros_ana_farmaka, Double> Table_TZ_PR_tz_ana_mina_col;
    @FXML
    private TableColumn<Tziros_ana_farmaka, Integer> Table_TZ_PR_temaxia_ana_mina_col;
    @FXML
    private TableColumn<Tziros_ana_farmaka, Integer> Table_TZ_PR_year_col;
    @FXML
    private TableColumn<Tziros_ana_farmaka, Double> Table_TZ_PR_tziros_ana_xrono_col;
   
    @FXML
    private TableColumn<Tziros_ana_farmaka, Integer> Table_TZ_PR_temaxia_ana_xrono_col;
    
    @FXML
    private Tab Tab_1;
    @FXML
    private Tab Tab_2;
    @FXML
    private TableView<Tziros_ana_Farmakopoio> Table_TZ_FARM;
    @FXML
    private TableColumn<Tziros_ana_Farmakopoio, Integer> Table_TZ_FARM_ID_col;
    @FXML
    private TableColumn<Tziros_ana_Farmakopoio, String> Table_TZ_FARM_Onoma_farm_col;
    @FXML
    private TableColumn<Tziros_ana_Farmakopoio, Integer> Table_TZ_FARM_Minas_col;
    @FXML
    private TableColumn<Tziros_ana_Farmakopoio, Double> Table_TZ_FARM_TZiros_ana_mina_col;
    @FXML
    private TableColumn<Tziros_ana_Farmakopoio, Integer> Table_TZ_FARM_Temaxeia_ana_mina_col;
    @FXML
    private TableColumn<Tziros_ana_Farmakopoio, Integer> Table_TZ_FARM_Xronos_col;
    @FXML
    private TableColumn<Tziros_ana_Farmakopoio, Double> Table_TZ_FARM_TZIROS_ana_XRONO_col;
    @FXML
    private TableColumn<Tziros_ana_Farmakopoio, Integer> Table_TZ_FARM_Temaxeia_ana_xrono_col;
    @FXML
    private Tab Tab_3;
    @FXML
    private Tab Tab_4;
    @FXML
    private Tab Tab_5;
    @FXML
    private Tab Tab_6;
    @FXML
    private Tab Tab_7;
    @FXML
    private TabPane Tabs_Stats;
    @FXML
    private TableView<Etisies_poliseis_ana_farmakeio_kai_proion> Table_POL_FARM_Proion;
    @FXML
    private TableColumn<Etisies_poliseis_ana_farmakeio_kai_proion, Integer> Table_POL_FARM_Proion_ID_col;
    @FXML
    private TableColumn<Etisies_poliseis_ana_farmakeio_kai_proion, String> Table_POL_FARM_Proion_Onoma_FARMAKEIOU_col;
    @FXML
    private TableColumn<Etisies_poliseis_ana_farmakeio_kai_proion, String> Table_POL_FARM_Proion_onoma_proion_col;
    @FXML
    private TableColumn<Etisies_poliseis_ana_farmakeio_kai_proion, Integer> Table_POL_FARM_Proion_year_col;
    @FXML
    private TableColumn<Etisies_poliseis_ana_farmakeio_kai_proion, Integer> Table_POL_FARM_Proion_temaxia_xrono_col;
    @FXML
    private TableColumn<Etisies_poliseis_ana_farmakeio_kai_proion, Double> Table_POL_FARM_Proion_tziros_xronos_col;
    @FXML
    private TableColumn<Etisies_poliseis_ana_farmakeio_kai_proion, Integer> Table_POL_FARM_Proion_ID_proiontos_col;
    @FXML
    private TextField etisios_txt;
    @FXML
    private TableView<Kerdi_analoga_mepos_kerdon_ana_farmako> TABLE_Pososta_proion;
    @FXML
    private TableColumn<Kerdi_analoga_mepos_kerdon_ana_farmako, Integer> TABLE_Pososta_proion_Farmako_ID_col;
    @FXML
    private TableColumn<Kerdi_analoga_mepos_kerdon_ana_farmako, String> TABLE_Pososta_proion_ONOMA_col;
    @FXML
    private TableColumn<Kerdi_analoga_mepos_kerdon_ana_farmako, Integer> TABLE_Pososta_proion_XRONOS_Col;
    @FXML
    private TableColumn<Kerdi_analoga_mepos_kerdon_ana_farmako, Double> TABLE_Pososta_proion_SYN_Poso_col;
    @FXML
    private TableColumn<Kerdi_analoga_mepos_kerdon_ana_farmako, Double> TABLE_Pososta_proion_TIMI_AGORAS_COL;
    @FXML
    private TableColumn<Kerdi_analoga_mepos_kerdon_ana_farmako,Double> TABLE_Pososta_proion_Kerdi_col;
    @FXML
    private TableColumn<Kerdi_analoga_mepos_kerdon_ana_farmako, Double> TABLE_Pososta_proion_POSOSTO_COL;
    
    
    @FXML
    private TableView<Tziros_ANA_Farmakeio> Table_ETISIOS_ana_FARMAKEIO;
    @FXML
    private TableColumn<Tziros_ANA_Farmakeio, Integer> Table_ETISIOS_ana_FARMAKEIO_ID_col;
    @FXML
    private TableColumn<Tziros_ANA_Farmakeio, String> Table_ETISIOS_ana_FARMAKEIO_Onoma_col;
    @FXML
    private TableColumn<Tziros_ANA_Farmakeio, Integer> Table_ETISIOS_ana_FARMAKEIO_Xronos_col;
    @FXML
    private TableColumn<Tziros_ANA_Farmakeio, Double> Table_ETISIOS_ana_FARMAKEIO_TZIROS_ANA_Xrono_col;
    @FXML
    private TableColumn<Tziros_ANA_Farmakeio, Integer> Table_ETISIOS_ana_FARMAKEIO_PLiromena_col;
    @FXML
    private TableColumn<Tziros_ANA_Farmakeio, Integer> Table_ETISIOS_ana_FARMAKEIO_MI_Pliromena_col;
    @FXML
    private TableColumn<Tziros_ANA_Farmakeio, Integer> Table_ETISIOS_ana_FARMAKEIO_ALL_Timologia_col;
    @FXML
    private TableColumn<Tziros_ANA_Farmakeio, Double> Table_ETISIOS_ana_FARMAKEIO_RITHMOS_Pliromis_Timologion_col;
    
    @FXML
    private TableView<Kerdofora_Farmaka> Kerd_Table;
    @FXML
    private TableColumn<Kerdofora_Farmaka, Integer> Kerd_Table_ID_col;
    @FXML
    private TableColumn<Kerdofora_Farmaka, String> Kerd_Table_Onoma;
    @FXML
    private TableColumn<Kerdofora_Farmaka, Integer> Kerd_Table_minas_col;
    @FXML
    private TableColumn<Kerdofora_Farmaka,Integer> Kerd_Table_temaxia_col;
    @FXML
    private TableColumn<Kerdofora_Farmaka, Double> Kerd_Table_Tziros_col;
    @FXML
    private TableColumn<Kerdofora_Farmaka, Integer> Kerd_Table_Prev_month_col;
    @FXML
    private TableColumn<Kerdofora_Farmaka, Integer> Kerd_Table_Prev_month_Temaxia_col;
    @FXML
    private TableColumn<Kerdofora_Farmaka, Double> Kerd_Table_Prev_month_Tziros_col;
    @FXML
    private TableColumn<Kerdofora_Farmaka, CheckBox> Kerd_Table_KERDOFORW_col;
    
    @FXML
    private Button Kerd_Table_Button;
    @FXML
    private Tab Tab_8;
    @FXML
    private TableView<Plirothike> Gia_Pliromi_TABLE;
    @FXML
    private TableColumn<Plirothike, Integer> Gia_Pliromi_TABLE_ID_Parag_Col;
    @FXML
    private TableColumn<Plirothike, String> Gia_Pliromi_TABLE_Onoma_Farmakopoiou_col;
    @FXML
    private TableColumn<Plirothike, String> Gia_Pliromi_TABLE_EPitheto_Farmakopoiou_Col;
    @FXML
    private TableColumn<Plirothike, Double> Gia_Pliromi_TABLE_Timi_col;
    @FXML
    private TableColumn<Plirothike, String> Gia_Pliromi_TABLE_hmer_col;
    @FXML
    private TableColumn<Plirothike, Button> Gia_Pliromi_TABLE_Timologio_col;
    @FXML
    private TableColumn<Plirothike, CheckBox> Gia_Pliromi_TABLE_Plirothike_col;
    @FXML
    private Button UPDATE_PLIROMI_button;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // statistics table column id_farmakou,id_farmakopoiou, minas,xronos,temaxia,timi,id_farmakiou,timi_agoras,pliromi
        Tabs_Stats.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            refresh(Integer.parseInt(newTab.getId().split("_")[1]));
            System.err.println("changed to :" + newTab.getId() + "   dfdfd   " + Eteisios_Tziros);
        });
        
        //Tab 1
        Table_TZ_PR_ID_col.setCellValueFactory(new PropertyValueFactory<>("id_farmakou"));
        Table_TZ_PR_onoms_col.setCellValueFactory(new PropertyValueFactory<>("onoma_farmakou"));
        Table_TZ_PR_minas_col.setCellValueFactory(new PropertyValueFactory<>("nowmonth"));
        Table_TZ_PR_tz_ana_mina_col.setCellValueFactory(new PropertyValueFactory<>("tziros_ana_mina"));
        Table_TZ_PR_temaxia_ana_mina_col.setCellValueFactory(new PropertyValueFactory<>("Syn_temaxia_mina"));
        Table_TZ_PR_year_col.setCellValueFactory(new PropertyValueFactory<>("nowyear"));
        Table_TZ_PR_tziros_ana_xrono_col.setCellValueFactory(new PropertyValueFactory<>("tziros_ana_xrono"));
        Table_TZ_PR_temaxia_ana_xrono_col.setCellValueFactory(new PropertyValueFactory<>("Syn_temaxia_xronos"));
        refresh(1);
        
        //Tab 2 
        Table_TZ_FARM_ID_col.setCellValueFactory(new PropertyValueFactory<>("id_Farmakopoiou"));
        Table_TZ_FARM_Onoma_farm_col.setCellValueFactory(new PropertyValueFactory<>("onoma_Farmakopoiou"));
        Table_TZ_FARM_Minas_col.setCellValueFactory(new PropertyValueFactory<>("nowmonth"));
        Table_TZ_FARM_TZiros_ana_mina_col.setCellValueFactory(new PropertyValueFactory<>("tziros_ana_mina"));
        Table_TZ_FARM_Temaxeia_ana_mina_col.setCellValueFactory(new PropertyValueFactory<>("Syn_temaxia_mina"));
        Table_TZ_FARM_Xronos_col.setCellValueFactory(new PropertyValueFactory<>("nowyear"));
        Table_TZ_FARM_TZIROS_ana_XRONO_col.setCellValueFactory(new PropertyValueFactory<>("tziros_ana_xrono"));
        Table_TZ_FARM_Temaxeia_ana_xrono_col.setCellValueFactory(new PropertyValueFactory<>("Syn_temaxia_xronos"));
        
        //Tab 3
        Table_ETISIOS_ana_FARMAKEIO_ID_col.setCellValueFactory(new PropertyValueFactory<>("id_Farmakeiou"));
        Table_ETISIOS_ana_FARMAKEIO_Onoma_col.setCellValueFactory(new PropertyValueFactory<>("onoma_Farmakeiou"));
        Table_ETISIOS_ana_FARMAKEIO_Xronos_col.setCellValueFactory(new PropertyValueFactory<>("nowyear"));
        Table_ETISIOS_ana_FARMAKEIO_TZIROS_ANA_Xrono_col.setCellValueFactory(new PropertyValueFactory<>("tziros_ana_xrono"));
        Table_ETISIOS_ana_FARMAKEIO_PLiromena_col.setCellValueFactory(new PropertyValueFactory<>("pliromena_timologia"));
        Table_ETISIOS_ana_FARMAKEIO_MI_Pliromena_col.setCellValueFactory(new PropertyValueFactory<>("Mi_pliromena_timologia"));
        Table_ETISIOS_ana_FARMAKEIO_ALL_Timologia_col.setCellValueFactory(new PropertyValueFactory<>("ola_ta_timologia"));
        Table_ETISIOS_ana_FARMAKEIO_RITHMOS_Pliromis_Timologion_col.setCellValueFactory(new PropertyValueFactory<>("rithmos_pliromis_timologion"));
        
        
        
        
        //Tab 4
        Table_POL_FARM_Proion_ID_col.setCellValueFactory(new PropertyValueFactory<>("id_Farmakeiou"));
        Table_POL_FARM_Proion_Onoma_FARMAKEIOU_col.setCellValueFactory(new PropertyValueFactory<>("onoma_Farmakeiou"));
        Table_POL_FARM_Proion_onoma_proion_col.setCellValueFactory(new PropertyValueFactory<>("onoma_Farmakou"));
        Table_POL_FARM_Proion_year_col.setCellValueFactory(new PropertyValueFactory<>("nowyear"));
        Table_POL_FARM_Proion_temaxia_xrono_col.setCellValueFactory(new PropertyValueFactory<>("etisia_temaxia"));
        Table_POL_FARM_Proion_tziros_xronos_col.setCellValueFactory(new PropertyValueFactory<>("Tziros"));
        Table_POL_FARM_Proion_ID_proiontos_col.setCellValueFactory(new PropertyValueFactory<>("id_Farmakou"));
        
        //Tab 5
        TABLE_Pososta_proion_Farmako_ID_col.setCellValueFactory(new PropertyValueFactory<>("id_Farmakou"));
        TABLE_Pososta_proion_ONOMA_col.setCellValueFactory(new PropertyValueFactory<>("onoma_Farmakou"));
        TABLE_Pososta_proion_XRONOS_Col.setCellValueFactory(new PropertyValueFactory<>("nowyear"));
        TABLE_Pososta_proion_SYN_Poso_col.setCellValueFactory(new PropertyValueFactory<>("poso"));
        TABLE_Pososta_proion_TIMI_AGORAS_COL.setCellValueFactory(new PropertyValueFactory<>("timi_agoras"));
        TABLE_Pososta_proion_Kerdi_col.setCellValueFactory(new PropertyValueFactory<>("kerdi_yearly"));
        TABLE_Pososta_proion_POSOSTO_COL.setCellValueFactory(new PropertyValueFactory<>("tis_ekato_kerdos"));
        
        //Tab 6

        //Tab 7 
        Kerd_Table_ID_col.setCellValueFactory(new PropertyValueFactory<>("id_Farmakou"));
        Kerd_Table_Onoma.setCellValueFactory(new PropertyValueFactory<>("onoma_Farmakou"));
        Kerd_Table_minas_col.setCellValueFactory(new PropertyValueFactory<>("Current_month"));
        Kerd_Table_temaxia_col.setCellValueFactory(new PropertyValueFactory<>("Current_month_temaxeia"));
        Kerd_Table_Tziros_col.setCellValueFactory(new PropertyValueFactory<>("Current_month_Tziros"));
        Kerd_Table_Prev_month_col.setCellValueFactory(new PropertyValueFactory<>("Previus_month"));
        Kerd_Table_Prev_month_Temaxia_col.setCellValueFactory(new PropertyValueFactory<>("Previus_month_temaxeia"));
        Kerd_Table_Prev_month_Tziros_col.setCellValueFactory(new PropertyValueFactory<>("Previus_month_Tziros"));
        Kerd_Table_KERDOFORW_col.setCellValueFactory(new PropertyValueFactory<>("Kerdoforo"));
        
        //Tab 8
        Gia_Pliromi_TABLE_ID_Parag_Col.setCellValueFactory(new PropertyValueFactory<>("Id_parag"));
        Gia_Pliromi_TABLE_Onoma_Farmakopoiou_col.setCellValueFactory(new PropertyValueFactory<>("onoma"));
        Gia_Pliromi_TABLE_EPitheto_Farmakopoiou_Col.setCellValueFactory(new PropertyValueFactory<>("epitheto"));
        Gia_Pliromi_TABLE_Timi_col.setCellValueFactory(new PropertyValueFactory<>("timi"));
        Gia_Pliromi_TABLE_hmer_col.setCellValueFactory(new PropertyValueFactory<>("hmer"));
        Gia_Pliromi_TABLE_Timologio_col.setCellValueFactory(new PropertyValueFactory<>("timologio"));
        Gia_Pliromi_TABLE_Plirothike_col.setCellValueFactory(new PropertyValueFactory<>("plhrothike"));
        
        
        
        //end of tabs 
        
        if (MediTest.getRole().equals("Logistis")){
            Kerd_Table_Button.setDisable(false);
            UPDATE_PLIROMI_button.setDisable(false);
        }
    }  
    
    private void set_Eteisios_Tziros(int xronos) {//5
        String sql = "SELECT * from Statistics WHERE xronos = " + xronos + ";";

        db DB = new db();
        ResultSet RS = DB.SelectQuery(sql);

        try {
            while (RS.next()) {
                Eteisios_Tziros += RS.getDouble("sun_poso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        DB.close();
    }

    @FXML
    private void Kerd_Table_Button_click(ActionEvent event) {
        db DB = new db();
        
        for(Kerdofora_Farmaka kerd:oblist_Kerdofora_Farmaka){
            DB.updateDB("UPDATE farmaka SET kerdoforo = "+kerd.getCheck()+" WHERE id = "+kerd.getId_Farmakou()+";");
        }
        
        
        DB.close();
        
    }

    @FXML
    private void UPDATE_PLIROMI(ActionEvent event) {
        db DB = new db();
        
        for(Plirothike plir:oblist_Plirothike){
            DB.updateDB("UPDATE istoriko SET plirothike = "+plir.getCheck()+" WHERE id = "+plir.getId_parag()+";");
        }
        
        
        DB.close();
    }
    
    
    public class Tziros_ana_farmaka{//1
        int id_farmakou;
        String onoma_farmakou;
        double tziros_ana_mina = 0;
        double tziros_ana_xrono = 0;
        int Syn_temaxia_mina = 0;
        int Syn_temaxia_xronos = 0;
        int nowmonth;
        int nowyear;

        public Tziros_ana_farmaka(int id_farmakou, int nowmonth, int nowyear) {
            this.id_farmakou = id_farmakou;
            this.nowmonth = nowmonth;
            this.nowyear = nowyear;
            
            setOnoma();
            setTziros_minas();
            setTziros_xronos();
            
        }
 
        private void setOnoma(){
            String sql = "SELECT * from farmaka WHERE id = "+this.id_farmakou+";";
            db DB = new db();
            ResultSet RS = DB.SelectQuery(sql);
            try {
                this.onoma_farmakou = RS.getString("onoma");
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DB.close();
        }
        
        private void setTziros_minas(){
            
            String sql = "SELECT * from Statistics WHERE id_farmakou = "+this.id_farmakou+" AND minas = "+nowmonth+";";
            
            db DB = new db();
            ResultSet RS = DB.SelectQuery(sql);
            
            try {
                while(RS.next()){
                    tziros_ana_mina += RS.getDouble("sun_poso");
                    Syn_temaxia_mina += RS.getInt("temaxia");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DB.close();
            
        }
        
        private void setTziros_xronos(){
            String sql = "SELECT * from Statistics WHERE id_farmakou = "+this.id_farmakou+" AND xronos = "+nowyear+";";
            
            db DB = new db();
            ResultSet RS = DB.SelectQuery(sql);
            
            try {
                while(RS.next()){
                     tziros_ana_xrono += RS.getDouble("sun_poso");
                     Syn_temaxia_xronos += RS.getInt("temaxia");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DB.close();
        }
        
        

        public int getId_farmakou() {
            return id_farmakou;
        }

        public String getOnoma_farmakou() {
            return onoma_farmakou;
        }

        public double getTziros_ana_mina() {
            return tziros_ana_mina;
        }

        public double getTziros_ana_xrono() {
            return tziros_ana_xrono;
        }

        public int getSyn_temaxia_mina() {
            return Syn_temaxia_mina;
        }

        public int getSyn_temaxia_xronos() {
            return Syn_temaxia_xronos;
        }

        public int getNowmonth() {
            return nowmonth;
        }

        public int getNowyear() {
            return nowyear;
        }

        
    }
    
    public class Tziros_ana_Farmakopoio{//2
        int id_Farmakopoiou;
        String onoma_Farmakopoiou;
        double tziros_ana_mina=0;
        double tziros_ana_xrono=0;
        
        int Syn_temaxia_mina = 0;
        int Syn_temaxia_xronos = 0;
        
        int nowmonth;
        int nowyear;

        public Tziros_ana_Farmakopoio(int id_Farmakopoiou, int nowmonth, int nowyear) {
            this.id_Farmakopoiou = id_Farmakopoiou;
            this.nowmonth = nowmonth;
            this.nowyear = nowyear;
            
            setOnoma();
            setTziros_minas();
            setTziros_xronos();
        }

        private void setOnoma(){
            String sql = "SELECT * from farmakopoioi WHERE id = "+this.id_Farmakopoiou+";";
            db DB = new db();
            ResultSet RS = DB.SelectQuery(sql);
            try {
                this.onoma_Farmakopoiou = RS.getString("onoma") + " " + RS.getString("epitheto");
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DB.close();
        }
        
        private void setTziros_minas(){
            
            String sql = "SELECT * from Statistics WHERE id_farmakopoiou = "+this.id_Farmakopoiou+" AND minas = "+nowmonth+";";
            
            db DB = new db();
            ResultSet RS = DB.SelectQuery(sql);
            
            try {
                while(RS.next()){
                    tziros_ana_mina += RS.getDouble("sun_poso");
                    Syn_temaxia_mina += RS.getInt("temaxia");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DB.close();
            
        }
        
        private void setTziros_xronos(){
            String sql = "SELECT * from Statistics WHERE id_farmakopoiou = "+this.id_Farmakopoiou+" AND xronos = "+nowyear+";";
            
            db DB = new db();
            ResultSet RS = DB.SelectQuery(sql);
            
            try {
                while(RS.next()){
                     tziros_ana_xrono += RS.getDouble("sun_poso");
                     Syn_temaxia_xronos += RS.getInt("temaxia");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DB.close();
        }

        public int getId_Farmakopoiou() {
            return id_Farmakopoiou;
        }

        public String getOnoma_Farmakopoiou() {
            return onoma_Farmakopoiou;
        }

        public double getTziros_ana_mina() {
            return tziros_ana_mina;
        }

        public double getTziros_ana_xrono() {
            return tziros_ana_xrono;
        }

        public int getSyn_temaxia_mina() {
            return Syn_temaxia_mina;
        }

        public int getSyn_temaxia_xronos() {
            return Syn_temaxia_xronos;
        }

        public int getNowmonth() {
            return nowmonth;
        }

        public int getNowyear() {
            return nowyear;
        }
        
        
        
        
    }
    
    public class Tziros_ANA_Farmakeio{//3
        double rithmos_pliromis_timologion;
        int id_Farmakeiou;
        String onoma_Farmakeiou;
        double tziros_ana_xrono = 0;
        int pliromena_timologia=0;
        int Mi_pliromena_timologia=0;
        int ola_ta_timologia=0;
        int nowyear;

        public Tziros_ANA_Farmakeio(int id_Farmakeiou, int nowyear) {
            this.id_Farmakeiou = id_Farmakeiou;
            this.nowyear = nowyear;
            setOnoma();
            setRithmo();
            
        }
        
        private void setOnoma(){
            String sql = "SELECT * from farmakeia WHERE id = " + this.id_Farmakeiou + ";";
            
            db DB = new db();
            ResultSet RS = DB.SelectQuery(sql);
            try {
                this.onoma_Farmakeiou = RS.getString("onoma") ;
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DB.close();
        }
        
        private void setRithmo(){
            String sql = "SELECT * from istoriko WHERE id_farmak = " + this.id_Farmakeiou +" AND plirothike= true AND year = " + nowyear + ";";
            String sql2 = "SELECT * from istoriko WHERE id_farmak = " + this.id_Farmakeiou +" AND plirothike= false AND year = " + nowyear + ";";
            db DB = new db();
            
            ResultSet RS = DB.SelectQuery(sql);
            try {
                while(RS.next()){
                    pliromena_timologia += 1;
                    tziros_ana_xrono += RS.getDouble("kostos");
                }
                
                ResultSet RS2 = DB.SelectQuery(sql2);
                
                while(RS2.next()){
                    Mi_pliromena_timologia += 1;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DB.close();
            
            ola_ta_timologia= pliromena_timologia+Mi_pliromena_timologia;
            if(ola_ta_timologia !=0){
            
                rithmos_pliromis_timologion = (ola_ta_timologia-Mi_pliromena_timologia)/ola_ta_timologia;
            } else{
                rithmos_pliromis_timologion =0 ;
            }
        }

        public double getRithmos_pliromis_timologion() {
            return rithmos_pliromis_timologion;
        }

        public int getId_Farmakeiou() {
            return id_Farmakeiou;
        }

        public String getOnoma_Farmakeiou() {
            return onoma_Farmakeiou;
        }

        public double getTziros_ana_xrono() {
            return tziros_ana_xrono;
        }

        public int getPliromena_timologia() {
            return pliromena_timologia;
        }

        public int getMi_pliromena_timologia() {
            return Mi_pliromena_timologia;
        }

        public int getOla_ta_timologia() {
            return ola_ta_timologia;
        }

        public int getNowyear() {
            return nowyear;
        }
        
        
        
    }
    
    public class Etisies_poliseis_ana_farmakeio_kai_proion{//4
        int id_Farmakeiou;
        String onoma_Farmakeiou;
        int id_Farmakou;
        String onoma_Farmakou; 
        int etisia_temaxia=0;
        double Tziros=0;
        int nowyear;

        public Etisies_poliseis_ana_farmakeio_kai_proion(int id_Farmakeiou, int id_Farmakou, int nowyear) {
            this.id_Farmakeiou = id_Farmakeiou;
            this.id_Farmakou = id_Farmakou;
            this.nowyear = nowyear;
            
            setOnoma();
            setTziro();
            
        }
        
        private void setOnoma() {
            String sql = "SELECT * from farmaka WHERE id = " + this.id_Farmakou + ";";
            String sql2 = "SELECT * from farmakeia WHERE id = " + this.id_Farmakeiou + ";";
            
            db DB = new db();
            
            ResultSet RS = DB.SelectQuery(sql);
            
            try {
                this.onoma_Farmakou = RS.getString("onoma");
                
                ResultSet RS2 = DB.SelectQuery(sql2);
                this.onoma_Farmakeiou = RS2.getString("onoma");
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            DB.close();

        }
        
        private void setTziro() {
            String sql = "SELECT * from Statistics WHERE id_farmakeiou = " + this.id_Farmakeiou + " AND id_farmakou = " + this.id_Farmakou +" AND xronos = "+nowyear+ ";";

            db DB = new db();
            ResultSet RS = DB.SelectQuery(sql);

            try {
                while (RS.next()) {
                    Tziros += RS.getDouble("sun_poso");
                    etisia_temaxia += RS.getInt("temaxia");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            DB.close();

        }

        public int getId_Farmakeiou() {
            return id_Farmakeiou;
        }

        public String getOnoma_Farmakeiou() {
            return onoma_Farmakeiou;
        }

        public int getId_Farmakou() {
            return id_Farmakou;
        }

        public String getOnoma_Farmakou() {
            return onoma_Farmakou;
        }

        public int getEtisia_temaxia() {
            return etisia_temaxia;
        }

        public double getTziros() {
            return Tziros;
        }

        public int getNowyear() {
            return nowyear;
        }
        
        
        
        
    }
    
    public class Kerdi_analoga_mepos_kerdon_ana_farmako{//6 // pososto kerdous = sun_poso - timi agoras; kai gia tis % tote (sun_poso - timi)*0.01
        double poso=0;
        double timi_agoras=0;
        double kerdi_yearly;
        int id_Farmakou;
        String onoma_Farmakou;
        double tis_ekato_kerdos;
        
        int nowyear;

        public Kerdi_analoga_mepos_kerdon_ana_farmako(int id_Farmakou, int nowyear) {
            this.id_Farmakou = id_Farmakou;
            this.nowyear = nowyear;
            
            setOnoma();
            setPoso_kai_timi_agoras();
            kerdi_yearly = poso-timi_agoras;
            if(poso != 0){
                tis_ekato_kerdos = ((kerdi_yearly)/poso)*100;//gia na min kano dieresi kai yparxi provlima ama einai 0
            } else{
               tis_ekato_kerdos =0;
            }
            
        }
        
        private void setOnoma(){
            String sql = "SELECT * from farmaka WHERE id = "+this.id_Farmakou+";";
            db DB = new db();
            ResultSet RS = DB.SelectQuery(sql);
            try {
                this.onoma_Farmakou = RS.getString("onoma");
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DB.close();
        }
        
        private void setPoso_kai_timi_agoras() {

            String sql = "SELECT * from Statistics WHERE id_farmakou = " + this.id_Farmakou + " AND xronos = " + nowyear + ";";

            db DB = new db();
            ResultSet RS = DB.SelectQuery(sql);

            try {
                while (RS.next()) {
                    poso += RS.getDouble("sun_poso");
                    timi_agoras += RS.getDouble("timi_agoras");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            DB.close();

        }

        public double getPoso() {
            return poso;
        }

        public double getTimi_agoras() {
            return timi_agoras;
        }

        public double getKerdi_yearly() {
            return kerdi_yearly;
        }

        public int getId_Farmakou() {
            return id_Farmakou;
        }

        public String getOnoma_Farmakou() {
            return onoma_Farmakou;
        }

        public double getTis_ekato_kerdos() {
            return tis_ekato_kerdos;
        }

        public int getNowyear() {
            return nowyear;
        }
        
        
    }
    
    public class Kerdofora_Farmaka{
        int id_Farmakou;
        String onoma_Farmakou;
        int Current_month;
        int Current_month_temaxeia;
        double Current_month_Tziros;
        
        int Previus_month;
        int Previus_month_temaxeia;
        double Previus_month_Tziros;
        
        CheckBox Kerdoforo;

        public Kerdofora_Farmaka(int id_Farmakou, int Current_month) {
            this.id_Farmakou = id_Farmakou;
            this.Current_month = Current_month;
            Kerdoforo = new CheckBox();

            
            Kerdoforo.setDisable(true);
            
            if(MediTest.getRole().equals("Logistis")){
                Kerdoforo.setDisable(false);
            }
            
            
            
            if(Current_month == 1){
                Previus_month = 12; 
            } else {
                Previus_month = Current_month-1; 
            }
            
            setName();
            setCurrentTziro_kai_Temaxeia();
            setPreviusTziro_kai_Temaxeia();
        }
        
        private void setName() {
            String sql = "SELECT * from farmaka WHERE id = " + this.id_Farmakou + ";";
            db DB = new db();
            ResultSet RS = DB.SelectQuery(sql);
            try {
                this.onoma_Farmakou = RS.getString("onoma");
                Kerdoforo.setSelected(RS.getBoolean("kerdoforo"));
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            DB.close();

        }
        
        private void setCurrentTziro_kai_Temaxeia(){
            String sql = "SELECT * from Statistics WHERE id_farmakou = "+this.id_Farmakou+" AND minas = "+Current_month+";";
            
            db DB = new db();
            ResultSet RS = DB.SelectQuery(sql);
            
            try {
                while(RS.next()){
                     Current_month_Tziros += RS.getDouble("sun_poso");
                     Current_month_temaxeia += RS.getInt("temaxia");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DB.close();
            
        }
        
        private void setPreviusTziro_kai_Temaxeia(){
            String sql = "SELECT * from Statistics WHERE id_farmakou = "+this.id_Farmakou+" AND minas = "+Previus_month+";";
            
            db DB = new db();
            ResultSet RS = DB.SelectQuery(sql);
            
            try {
                while(RS.next()){
                     Previus_month_Tziros += RS.getDouble("sun_poso");
                     Previus_month_temaxeia += RS.getInt("temaxia");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DB.close();
            
        }

        public int getId_Farmakou() {
            return id_Farmakou;
        }

        public String getOnoma_Farmakou() {
            return onoma_Farmakou;
        }

        public int getCurrent_month() {
            return Current_month;
        }

        public int getCurrent_month_temaxeia() {
            return Current_month_temaxeia;
        }

        public double getCurrent_month_Tziros() {
            return Current_month_Tziros;
        }

        public int getPrevius_month() {
            return Previus_month;
        }

        public int getPrevius_month_temaxeia() {
            return Previus_month_temaxeia;
        }

        public double getPrevius_month_Tziros() {
            return Previus_month_Tziros;
        }

        public CheckBox getKerdoforo() {
            return Kerdoforo;
        }
        
        public Boolean getCheck(){
            return Kerdoforo.isSelected();
        }

}
    
    
    public class Plirothike{
        String timologio_path;
        
        int Id_parag;
        String onoma;
        String epitheto;
        double timi;
        String hmer;
        
        Button timologio;
        CheckBox plhrothike;
        
        int year;

        public Plirothike(int Id_parag, int year) {
            this.Id_parag = Id_parag;
            this.year = year;
            
            timologio = new Button();
            plhrothike= new CheckBox();
            plhrothike.setDisable(true);
            
            
            timologio.setGraphic(new ImageView(new Image("file:" + System.getProperty("user.dir")+"\\src\\meditest\\images\\pdf_img.png")));
            
            setAll();
            
            this.timologio.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {

                        Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + timologio_path);
                    } catch (IOException ex) {
                        Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            //plhrothike
            if(MediTest.getRole().equals("Logistis")){
                plhrothike.setDisable(false);
            }
            
            
            
        }
        
        private void setAll(){
            String SQL = "SELECT * from istoriko WHERE id ="+Id_parag+" AND year = "+ year +";";
            db DB = new db();
            ResultSet RS = DB.SelectQuery(SQL);
            
            try {
                timologio_path = RS.getString("onoma_timologioy");
                onoma = RS.getString("onoma");
                epitheto = RS.getString("epitheto");
                timi = RS.getDouble("kostos");
                hmer = RS.getString("hmerominia");
                plhrothike.setSelected(RS.getBoolean("plirothike"));
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DB.close();
            
        }

        public String getTimologio_path() {
            return timologio_path;
        }

        public int getId_parag() {
            return Id_parag;
        }

        public String getOnoma() {
            return onoma;
        }

        public String getEpitheto() {
            return epitheto;
        }

        public double getTimi() {
            return timi;
        }

        public String getHmer() {
            return hmer;
        }

        public Button getTimologio() {
            return timologio;
        }

        public CheckBox getPlhrothike() {
            return plhrothike;
        }

        public int getYear() {
            return year;
        }
        
        public Boolean getCheck() {
            return this.plhrothike.isSelected();
        }
        
    }
    
    private void refresh(int tab) {
        Eteisios_Tziros = 0;
        String Current_date = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime());

        int nowmonth = Integer.parseInt(Current_date.split("_")[1]);
        int nowyear = Integer.parseInt(Current_date.split("_")[2]);
        
        
        oblist_Tziros_ana_farmaka.clear();
        oblist_Tziros_ana_FarmaKopoio.clear();
        oblist_Etisies_poliseis_ana_farmakeio_kai_proion.clear();
        oblist_Kerdi_analoga_mepos_kerdon_ana_farmako.clear();
        oblist_Tziros_ANA_Farmakeio.clear();
        oblist_Kerdofora_Farmaka.clear();
        oblist_Plirothike.clear();
        
        
        if (tab == 1) {
            db DB = new db();//oblist_Tziros_ana_farmaka
            ResultSet Rs = DB.SelectQuery("SELECT * from farmaka;");

            try {
                while (Rs.next()) {
                    oblist_Tziros_ana_farmaka.add(new Tziros_ana_farmaka(Rs.getInt("id"), nowmonth, nowyear));
                }

            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            DB.close();

            Table_TZ_PR.setItems(oblist_Tziros_ana_farmaka);

        } else if (tab == 2) {

            db DB = new db();//oblist_Tziros_ana_FarmaKopoio
            ResultSet Rs = DB.SelectQuery("SELECT * from farmakopoioi;");

            try {
                while (Rs.next()) {
                    oblist_Tziros_ana_FarmaKopoio.add(new Tziros_ana_Farmakopoio(Rs.getInt("id"), nowmonth, nowyear));
                }

            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            DB.close();

            Table_TZ_FARM.setItems(oblist_Tziros_ana_FarmaKopoio);

        }else if (tab == 3) {
            db DB = new db();
            ResultSet Rs = DB.SelectQuery("SELECT * from farmakeia;");
            
            try {
                while (Rs.next()) {
                    oblist_Tziros_ANA_Farmakeio.add(new Tziros_ANA_Farmakeio(Rs.getInt("id"), nowyear));
                }

            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            DB.close();
            
            Table_ETISIOS_ana_FARMAKEIO.setItems(oblist_Tziros_ANA_Farmakeio);
            
        }else if (tab == 4) {

            db DB = new db();//oblist_Etisies_poliseis_ana_farmakeio_kai_proion
            ResultSet Rs = DB.SelectQuery("SELECT * from farmakeia;");
            ResultSet Rs_farmako = DB.SelectQuery("SELECT * from farmaka;");
            
            ArrayList<Integer> id_farmakon = new ArrayList<Integer>();
            
            try {
                while (Rs_farmako.next()) {
                    id_farmakon.add(Rs_farmako.getInt("id"));
                }
                
                while (Rs.next()) {
                    for (int id : id_farmakon) {
                        oblist_Etisies_poliseis_ana_farmakeio_kai_proion.add(new Etisies_poliseis_ana_farmakeio_kai_proion(Rs.getInt("id"), id , nowyear));
                    }
                }
                
                
                
            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            DB.close();

            Table_POL_FARM_Proion.setItems(oblist_Etisies_poliseis_ana_farmakeio_kai_proion);
            set_Eteisios_Tziros(nowyear);
            
            etisios_txt.setText(Eteisios_Tziros+"€");
        } else if(tab ==5){
            //oblist_Kerdi_analoga_mepos_kerdon_ana_farmako
            //TABLE_Pososta_proion
            db DB = new db();//oblist_Tziros_ana_farmaka
            ResultSet Rs = DB.SelectQuery("SELECT * from farmaka;");

            try {
                while (Rs.next()) {
                    oblist_Kerdi_analoga_mepos_kerdon_ana_farmako.add(new Kerdi_analoga_mepos_kerdon_ana_farmako(Rs.getInt("id"), nowyear));
                }

            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            DB.close();

            TABLE_Pososta_proion.setItems(oblist_Kerdi_analoga_mepos_kerdon_ana_farmako);
        
        
        } else if (tab == 6){
        
        }else if (tab == 7){
            
            db DB = new db();
            ResultSet Rs = DB.SelectQuery("SELECT * from farmaka;");

            try {
                while (Rs.next()) {
                    oblist_Kerdofora_Farmaka.add(new Kerdofora_Farmaka(Rs.getInt("id"), nowmonth));
                    //System.out.println(Rs.getInt("id")+" ghg "+ nowmonth);
                }

            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            DB.close();

            Kerd_Table.setItems(oblist_Kerdofora_Farmaka);
        }else if (tab == 8){
            
            db DB = new db();//oblist_Tziros_ana_farmaka
            ResultSet Rs = DB.SelectQuery("SELECT * from istoriko WHERE year = "+nowyear+";");

            try {
                while (Rs.next()) {
                    oblist_Plirothike.add(new Plirothike(Rs.getInt("id"), nowyear));
                    
                }

            } catch (SQLException ex) {
                Logger.getLogger(Statistics_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            DB.close();

            Gia_Pliromi_TABLE.setItems(oblist_Plirothike);
        }

        
        
    }
    
    public class Rithmos_ana_catigoria{
        
        String category;
        Double rythmos_poliseis;

        public Rithmos_ana_catigoria(String category) {
            this.category = category;
            setRithmo();
        }
        
        private void setRithmo(){
            String sql = "SELECT * from farmaka WHERE katigoria = "+category+" ; ";
            db DB = new db();
            
            
            
            DB.close();
            
        }
        
    
    }
    
    public class Rithmos_ana_ETAIRIA{
        String etairia;
        Double rythmos_poliseis;

        public Rithmos_ana_ETAIRIA(String etairia) {
            this.etairia = etairia;
            setRithmo();
        }
        
        private void setRithmo(){
            String sql = "SELECT * from farmaka WHERE etairia = "+etairia+"; ";
            db DB = new db();
            
            
            
            
            DB.close();
        }
    }
}
