/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest.forms;


import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import meditest.db;

/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class Sales_FXMLController implements Initializable {

    static ObservableList<Farmaka_Items> oblist_Ready_Items = FXCollections.observableArrayList();//oi paraggelies pou einai gia na tis etoimasoun oi apo8ikarioi
    static ObservableList<Farmaka_Items> oblist_For_Send_Items = FXCollections.observableArrayList();//oi paraggelies pou einai gia na Stal8oun
    String id_pelati;
    
    
    @FXML
    private Tab Prepare;
    @FXML
    private Tab for_send;
    @FXML
    private Tab history;
    @FXML
    private ChoiceBox<String> parg_for_apothiki_choice_box;
    @FXML
    private TableView<Farmaka_Items> apothiki_ready_table;
    @FXML
    private Button ready_Button;
    @FXML
    private ChoiceBox<String> etimes_parag_gia_send;
    @FXML
    private TableView<Farmaka_Items> for_send_table;
    @FXML
    private Button send_button;
    @FXML
    private Button cancel_button;
    
    @FXML
    private TableColumn<Farmaka_Items, String> etimo_id_col;
    @FXML
    private Label parag_for_ready_label;
    @FXML
    private Button for_etimo_timologio;

    String etimo_url;
    String ready_url;
    //ArrayList<Farmaka_Items> List_Items;
    
    private String ready_id;
    private String Send_id;
    
    @FXML
    private TableColumn<Farmaka_Items, String> etoimo_onoma_col;
    @FXML
    private TableColumn<Farmaka_Items, String> etoimo_ousia_col;
    @FXML
    private TableColumn<Farmaka_Items, String> etoimo_temaxia_col;
    
    
    @FXML
    private TableColumn<Farmaka_Items, String> for_send_ID_col;
    @FXML
    private TableColumn<Farmaka_Items, String> for_send_ONOMA_col;
    @FXML
    private TableColumn<Farmaka_Items, String> for_send_ousia_col;
    @FXML
    private TableColumn<Farmaka_Items, String> for_send_temaxia_col;
    
    
    //history tab
    @FXML
    private TextField history_Search;
    @FXML
    private TableView<History_Items> history_table;
    @FXML
    private TableColumn<History_Items, Integer> history_table_col;
    @FXML
    private TableColumn<History_Items, String> farmakopoios_name_table_col;
    @FXML
    private TableColumn<History_Items, String> farmakeio_history_table_col;
    @FXML
    private TableColumn<History_Items, String> wra_history_table_col;
    @FXML
    private TableColumn<History_Items, String> address_history_table_col;
    @FXML
    private TableColumn<History_Items, String> dianomeas_history_table_col;
    @FXML
    private TableColumn<History_Items, Integer> meres_history_table_col;
    @FXML
    private TableColumn<History_Items, Double> kostos_history_table_col;
    @FXML
    private TableColumn<History_Items, Button> timologio_history_table_col;
    
    static ObservableList<History_Items> History_oblist_Items = FXCollections.observableArrayList();//olo to istoriko
    static ObservableList<History_Items> Search_oblist_history = FXCollections.observableArrayList();//auto pou psaxnoume
    @FXML
    private Button for_send_timologio;
    @FXML
    private Button etoimasia_button;
    @FXML
    private Button for_send_refresh_buuton;
    @FXML
    private Button history_refresh_button;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // etimo tab
        
        etimo_id_col.setCellValueFactory(new PropertyValueFactory<>("Med_id"));

        etoimo_onoma_col.setCellValueFactory(new PropertyValueFactory<>("onoma"));
        etoimo_ousia_col.setCellValueFactory(new PropertyValueFactory<>("ousia"));
        etoimo_temaxia_col.setCellValueFactory(new PropertyValueFactory<>("temaxia"));
        
        
        
        apothiki_ready_table.setItems(oblist_Ready_Items);
        
        db DB = new db();
        
        ChangeListener<String> changeListener = new ChangeListener<String>() {
 
            public void changed(ObservableValue<? extends String> observable, //
                    String oldValue, String newValue) {
                
                if (newValue != null) {
                    
                    ready_Button.setDisable(false);
                    for_etimo_timologio.setDisable(false);
                    
                    oblist_Ready_Items.clear();
                    
                    db DB = new db();
                    String querry = "SELECT * from parag_before_receive where id ="+newValue.split(":")[0]+ ";";
                    ready_id = newValue.split(":")[0];
                    System.out.println("Timologio Querry : "+querry);
                    
                    ResultSet rs = DB.SelectQuery(querry);
                    try {
                        System.out.println(rs.getString("onoma_timologiou"));
                        etimo_url = rs.getString("onoma_timologiou");
                        
                        String items = rs.getString("Proionta");
                        for(String item : items.split("\\n")){//System.getProperty("line.separator"))){
                            String ousia = "";
                            
                            String [] splited = item.split(":");
                            db DB_ousia = new db();
                            String querry2 ="SELECT ousia from farmaka WHERE id= "+splited[0]+";";
                            //System.out.println(querry2);
                            ResultSet ousia_rs=DB_ousia.SelectQuery(querry2);
                            ousia = ousia_rs.getString("ousia");
                            
                            oblist_Ready_Items.add(new Farmaka_Items(splited[0],splited[1],ousia,splited[2]));
                            DB_ousia.close();
                        }
                        
                        //apothiki_ready_table
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    DB.close();
                } else {
                    ready_Button.setDisable(true);
                    for_etimo_timologio.setDisable(true);
                    
                }
            
            
            }
        };
        
        parg_for_apothiki_choice_box.getSelectionModel().selectedItemProperty().addListener(changeListener);
        
        
        
        
        
        String querry = "SELECT * from parag_before_receive where ready = false;";
        ResultSet rs = DB.SelectQuery(querry);
        try {
            while(rs.next()){
                parg_for_apothiki_choice_box.getItems().add(rs.getInt("id")+":"+rs.getString("onoma_farmakeiou"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        DB.close();
        
        parag_for_ready_label.setText("Παραγγελίες για ετοιμασία : \n"+parg_for_apothiki_choice_box.getItems().size());
        
        
        //                                                       ready tab
        //#####################################################################################################################################
        //#####################################################################################################################################
        //#####################################################################################################################################
        
        
        for_send_ID_col.setCellValueFactory(new PropertyValueFactory<>("Med_id"));

        for_send_ONOMA_col.setCellValueFactory(new PropertyValueFactory<>("onoma"));
        for_send_ousia_col.setCellValueFactory(new PropertyValueFactory<>("ousia"));
        for_send_temaxia_col.setCellValueFactory(new PropertyValueFactory<>("temaxia"));
        //for_send_Hmer_Parag_col.setCellValueFactory(new PropertyValueFactory<>("Hmer"));
        
        
        
        
         
        
        for_send_table.setItems(oblist_For_Send_Items);
        
        
        db DB2 = new db();
        
        ChangeListener<String> changeListenerReady = new ChangeListener<String>() {
 
            public void changed(ObservableValue<? extends String> observable, //
                    String oldValue, String newValue) {
                
                if (newValue != null) {
                    send_button.setDisable(false);
                    for_send_timologio.setDisable(false);
                    
                    String date = new SimpleDateFormat("dd_MM_yyyy#HH_mm").format(Calendar.getInstance().getTime());
                    String hour = date.split("#")[1];
                    String day = date.split("#")[0];
                    int day_current = Integer.parseInt(day.split("_")[0]);
                    
                    
                    
                    
                    oblist_For_Send_Items.clear();
                    
                    db DB = new db();
                    Send_id = newValue.split(":")[0];
                    String querry = "SELECT * from parag_before_receive where id ="+Send_id+ ";";
                    
                    //System.out.println("Timologio Querry : "+querry);
                    
                    ResultSet rs = DB.SelectQuery(querry);
                    try {
                        
                        id_pelati = rs.getString("id_farmak");
                        
                        System.out.println(rs.getString("onoma_timologiou"));
                        
                        
                        ready_url = rs.getString("onoma_timologiou");
                        
                        String items = rs.getString("Proionta");
                        for(String item : items.split("\\n")){//System.getProperty("line.separator"))){
                            String ousia = "";
                            
                            String [] splited = item.split(":");
                            db DB_ousia = new db();
                            String querry2 ="SELECT ousia from farmaka WHERE id= "+splited[0]+";";
                            //System.out.println(querry2);
                            ResultSet ousia_rs=DB_ousia.SelectQuery(querry2);
                            ousia = ousia_rs.getString("ousia");
                            

                            
                            oblist_For_Send_Items.add(new Farmaka_Items(splited[0], splited[1], ousia, splited[2]));//yufgg
                            DB_ousia.close();
                        }
                        String hmer_parag = rs.getString("hmerominia");
                        int day_Paragkelias = Integer.parseInt(hmer_parag.split("_")[0]);
                        
                        if ((day_Paragkelias+3)>=day_current) {
                            cancel_button.setDisable(false);
                        }
                        //apothiki_ready_table

                    } catch (SQLException ex) {
                        Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    DB.close();
                } else {
                    cancel_button.setDisable(true);
                    send_button.setDisable(true);
                    for_send_timologio.setDisable(true);
                    
                    oblist_For_Send_Items.clear();
                }
            }
        };
        
        etimes_parag_gia_send.getSelectionModel().selectedItemProperty().addListener(changeListenerReady);
        
        
        
        
        
        String querry3 = "SELECT * from parag_before_receive WHERE ready = true;";
        ResultSet rs1 = DB2.SelectQuery(querry3);
        try {
            while(rs1.next()){
                etimes_parag_gia_send.getItems().add(rs1.getInt("id")+":"+rs1.getString("onoma_farmakeiou")+":"+rs1.getString("hmerominia")+"_"+rs1.getString("wra_parag"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        DB2.close();
        
        
        //                                                       history tab
        //#####################################################################################################################################
        //#####################################################################################################################################
        //#####################################################################################################################################
        
        History_oblist_Items.clear();
        
        
        history_table_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        farmakopoios_name_table_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        farmakeio_history_table_col.setCellValueFactory(new PropertyValueFactory<>("onoma_farmakeiou"));
        wra_history_table_col.setCellValueFactory(new PropertyValueFactory<>("Hmer"));        
        address_history_table_col.setCellValueFactory(new PropertyValueFactory<>("dieuth"));     
        dianomeas_history_table_col.setCellValueFactory(new PropertyValueFactory<>("dianomeas"));        
        meres_history_table_col.setCellValueFactory(new PropertyValueFactory<>("days"));        
        kostos_history_table_col.setCellValueFactory(new PropertyValueFactory<>("kostos"));        
        timologio_history_table_col.setCellValueFactory(new PropertyValueFactory<>("open_Timologio"));
        //timologio_history_table_col.setCellValueFactory(new PropertyValueFactory<History_Items, ImageView>("open_Timologio"));
        
        
        
        
        
         /*History_oblist_Items //olo to istoriko
            Search_oblist_history*/       
                
        history_Search.textProperty().addListener((observable, oldValue, newValue) -> { //otan allaksi to textfield tote kane: 
            
            Search_oblist_history.clear();//einai h lista pou 8a apothikrutoun oi times pou vriskei 
            
            
            
            
            if(newValue.equals("")){//an den exei timi sto textfield tote dikse olous tous 
                history_table.setItems(History_oblist_Items);
            } else{
                String txt = newValue.toLowerCase();//ta kanei mikra 
                for(History_Items parag:History_oblist_Items){ // gia ka8e paraggelia 
                    String id="#"+String.valueOf(parag.getId());
                    
                    String days = String.valueOf(parag.getDays());
                    String kostos = String.valueOf(parag.getKostos());
                    
                    if(id.equals(txt)){//ama to noumero einai iso me to id tote vale stin lista ton parag 
                        Search_oblist_history.add(parag);
                        
                        break;
                    } else if(days.contains(txt)||parag.getDianomeas().contains(txt)
                                ||parag.getDieuth().contains(txt)||parag.getHmer().contains(txt)
                                ||kostos.contains(txt)||parag.getName().contains(txt)
                                ||parag.getOnoma_farmakeiou().contains(txt)){ // ama yparxei mesa sta parapanw tote pros8eseta sthn lista 
                        
                        Search_oblist_history.add(parag);
                        
                    }  
                    
                }
    
                history_table.setItems(Search_oblist_history);
                 
            }
        });
        
        db DB3 = new db();
        
        String sql ="SELECT * from istoriko ;";  
        
        ResultSet history_RS = DB3.SelectQuery(sql);
        
        
        
        
        try {
            while(history_RS.next()){
                String sql_farmakeio ="SELECT * from farmakeia WHERE id ="+history_RS.getInt("id_farmak")+";";  
                String onoma_farmakeiou = " ";
                
                db DB_farmakeio = new db();
                 
                ResultSet onoma_RS = DB_farmakeio.SelectQuery(sql_farmakeio);
                onoma_farmakeiou = onoma_RS.getString("onoma");
                
                DB_farmakeio.close();
                
                
                
                String onoma_dianomea = " ";//id_dianomea
                String sql_onoma_dianomea ="SELECT * from users WHERE id ="+history_RS.getInt("id_dianomea")+";"; 
                
                
                db DB_dianomea = new db();
                 
                ResultSet onoma_dianomea_RS = DB_dianomea.SelectQuery(sql_onoma_dianomea);
                onoma_dianomea = onoma_dianomea_RS.getString("firstname")+ " " + onoma_dianomea_RS.getString("firstname");
                
                DB_dianomea.close();
                
                
                History_oblist_Items.add(new History_Items(history_RS.getInt("id"),history_RS.getString("onoma")+" "+history_RS.getString("epitheto"),
                        onoma_farmakeiou,history_RS.getString("hmerominia")+" "+history_RS.getString("wra"),history_RS.getString("address"),
                onoma_dianomea,history_RS.getString("onoma_timologioy"),history_RS.getInt("meres_parad"),history_RS.getDouble("kostos"),
                       new Button(""))); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
 
        DB3.close();
        
        
        history_table.setItems(History_oblist_Items);
        
    }    
    
    
    
    @FXML
    private void open_timologio(ActionEvent event) {
    //anoigi me to default programma to pdf tou timologiou    
            try {
                
                Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+ etimo_url); 
            } catch (IOException ex) {
                Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

    @FXML
    private void for_send_prep_button_click(ActionEvent event) {
        //ama patithi oti einai raedy h paragelia apo tin apothiki tote tha kanei update tin vasei oti einai ready kai tin stelnoume ston pelati 
        db DB = new db();
        String query = "UPDATE parag_before_receive SET ready=" + true + " WHERE id = " + ready_id+";";
        DB.updateDB(query);
        DB.close();
        parg_for_apothiki_choice_box.getItems().remove(parg_for_apothiki_choice_box.getValue());
        if(parg_for_apothiki_choice_box.getItems().size()>=1){
            parg_for_apothiki_choice_box.setValue(parg_for_apothiki_choice_box.getItems().get(0));
        }
        parag_for_ready_label.setText("Παραγγελίες για ετοιμασία : \n"+parg_for_apothiki_choice_box.getItems().size());
        
        refresh("for_send_refresh_buuton");
    }

    @FXML
    private void sended(ActionEvent event) throws IOException {
        //diagrafi apo tin vasei tin paraggelia , meta metaferh to timologio sta sended  kai meta ananeoni to table  
        db DB = new db();
        
        File f = new File(ready_url);
        String target = System.getProperty("user.dir") +"\\src\\meditest\\timologia\\sended\\"+f.getName();
        
        String sql = "DELETE FROM parag_before_receive WHERE id=" + Send_id + ";";
        
        String onoma="",epitheto="",items="",hmer="",wra="",address="",timologio="";
        int id_dianomea=1,meres_parad=1,id_farmakiou=1;
        double kostos = 0;
        
        String sql_get_pelati = "SELECT *  from farmakopoioi  WHERE id=" + id_pelati + ";";
        String sql_get_parag  = "SELECT *  from parag_before_receive  WHERE id=" + Send_id + ";";
        
        
        ResultSet rs = DB.SelectQuery(sql_get_pelati);
        ResultSet rs2 = DB.SelectQuery(sql_get_parag);
        
        try {
            onoma = rs.getString("onoma");
            epitheto=rs.getString("epitheto");
            
            items = rs2.getString("Proionta");
            hmer = rs2.getString("hmerominia");
            wra = rs2.getString("wra_parag");
            address=rs2.getString("address");
            
            
            id_dianomea=rs2.getInt("id_dianomea");
            
            
            meres_parad = getDiaf_imeron_apo_simera(hmer);
            System.out.println("Meres : " + meres_parad);
            
            id_farmakiou=getId_farmakopoiou(rs2.getString("onoma_farmakeiou"));
            System.out.println("ID_farmakiou : " + id_farmakiou);
            
            kostos = rs2.getDouble("kostos");
        } catch (SQLException ex) {
            Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int year = Integer.parseInt(hmer.split("_")[2]);
        
        String sql_save_to_base = "INSERT INTO istoriko (onoma , epitheto,proionta_posotites,kostos,hmerominia,wra,address,id_dianomea,meres_parad,onoma_timologioy,id_farmak,year) "
                + "VALUES ('"+onoma+"','"+epitheto+"','"+items+"',"+String.valueOf(kostos)+",'"+hmer+"','"+wra+"','"+address+"',"+id_dianomea+","
                +meres_parad+ ",'"+target+"',"+id_farmakiou+","+year+");";
        
        
        
        
        DB.updateDB(sql_save_to_base);//to vazoume sto history 
        
        
        String Current_date = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime());
        
        
        
        
        int nowmonth = Integer.parseInt(Current_date.split("_")[1]);
        int nowyear = Integer.parseInt(Current_date.split("_")[2]);
        
        
        for (String item : items.split("\\n")) {
            
            String id = item.split(":")[0];
            String temaxia = item.split(":")[2];

            String Farmako_sql = "SELECT * from farmaka WHERE id =" + id + ";";
            double syn_poso, timi_agoras = 0, timi = 0, Syn_timi_agoras;

            ResultSet rs_farmaka = DB.SelectQuery(Farmako_sql);
            try {
                timi_agoras = rs_farmaka.getDouble("timi_agoras");
                timi = rs_farmaka.getDouble("timi");
            } catch (SQLException ex) {
                Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            syn_poso = timi * Integer.parseInt(temaxia);
            Syn_timi_agoras = timi_agoras * Integer.parseInt(temaxia);

            String sql_statistics = "INSERT INTO Statistics (id_farmakou , id_farmakopoiou, id_farmakeiou,minas,xronos,temaxia,sun_poso,timi_agoras)"
                    + "VALUES (" + id + "," + id_pelati + "," + id_farmakiou + "," + nowmonth + "," + nowyear + "," + temaxia + "," + syn_poso + "," + Syn_timi_agoras + ");";
            DB.updateDB(sql_statistics);
            
        }
        
        
        
        
        DB.updateDB(sql);//to diagrafoume apo to temp(parag_before_receive)
        
        DB.close();
        
        
        Files.move(Paths.get(ready_url), Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
        
        etimes_parag_gia_send.getItems().remove(etimes_parag_gia_send.getValue());
        
        if(etimes_parag_gia_send.getItems().size()>=1){
            etimes_parag_gia_send.setValue(etimes_parag_gia_send.getItems().get(0));
        }
        
        refresh("history_refresh_button");
        
    }

    @FXML
    private void cancel_order(ActionEvent event) throws IOException {
        //diagrafi apo tin vasei tin paraggelia , meta metaferh to timologio sta cancelled  kai meta ananeoni to table  
        String sql = "DELETE FROM parag_before_receive WHERE id=" + Send_id + ";";
        
        db DB = new db();
        DB.updateDB(sql);
        DB.close();
        
        File f = new File(ready_url);
        
        
        String target = System.getProperty("user.dir") +"\\src\\meditest\\timologia\\canceled\\"+f.getName();

        
        Files.move(Paths.get(ready_url), Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
        
        etimes_parag_gia_send.getItems().remove(etimes_parag_gia_send.getValue());
        
        if(etimes_parag_gia_send.getItems().size()>=1){
            etimes_parag_gia_send.setValue(etimes_parag_gia_send.getItems().get(0));
        }
        
    }
    
    int getId_farmakopoiou(String name_farmakopoioy){
        int id = 1;
        db Db = new db();
        String sql = "SELECT * from farmakeia WHERE onoma = '"+name_farmakopoioy+"';";
        
        
        ResultSet rs = Db.SelectQuery(sql);
        try {
            id = rs.getInt("id");
        } catch (SQLException ex) {
            Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Db.close();
        
        return id;
    }
    
    int getDiaf_imeron_apo_simera(String date){
        String Current_date = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime());
        
        int day = Integer.parseInt(date.split("_")[0]);
        int month = Integer.parseInt(date.split("_")[1]);
        int year = Integer.parseInt(date.split("_")[2]);
        
        int nowday = Integer.parseInt(Current_date.split("_")[0]);
        int nowmonth = Integer.parseInt(Current_date.split("_")[1]);
        int nowyear = Integer.parseInt(Current_date.split("_")[2]);
        
        Date dateGiven = new Date(year,month,day);
        Date now = new Date(nowyear,nowmonth,nowday);
        
        return (int)( (now.getTime() - dateGiven.getTime()) / (1000 * 60 * 60 * 24));
    }

    @FXML
    private void open_timologio_send(ActionEvent event) {
        try {
                
                Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+ ready_url); 
            } catch (IOException ex) {
                Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    
    void refresh(String Tad) {
        String refresh_tab = Tad;

        if (refresh_tab.equals("etoimasia_button")) {
            db DB = new db();

            parg_for_apothiki_choice_box.getItems().clear();
            

            oblist_Ready_Items.clear();
            

            String querry = "SELECT * from parag_before_receive where ready = false;";
            ResultSet rs = DB.SelectQuery(querry);

            try {
                while (rs.next()) {
                    parg_for_apothiki_choice_box.getItems().add(rs.getInt("id") + ":" + rs.getString("onoma_farmakeiou"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            parag_for_ready_label.setText("Παραγγελίες για ετοιμασία : \n" + parg_for_apothiki_choice_box.getItems().size());

            DB.close();

            
            
        } else if (refresh_tab.equals("for_send_refresh_buuton")) {
            oblist_For_Send_Items.clear();
            etimes_parag_gia_send.getItems().clear();

            db DB2 = new db();
            String querry3 = "SELECT * from parag_before_receive WHERE ready = true;";
            ResultSet rs1 = DB2.SelectQuery(querry3);
            try {
                while (rs1.next()) {
                    etimes_parag_gia_send.getItems().add(rs1.getInt("id") + ":" + rs1.getString("onoma_farmakeiou") + ":" + rs1.getString("hmerominia") + "_" + rs1.getString("wra_parag"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            DB2.close();
        } else { //history_refresh_button
            history_Search.setText("");
            History_oblist_Items.clear();

            db DB3 = new db();

            String sql = "SELECT * from istoriko ;";

            ResultSet history_RS = DB3.SelectQuery(sql);

            try {
                while (history_RS.next()) {
                    String sql_farmakeio = "SELECT * from farmakeia WHERE id =" + history_RS.getInt("id_farmak") + ";";
                    String onoma_farmakeiou = " ";

                    db DB_farmakeio = new db();

                    ResultSet onoma_RS = DB_farmakeio.SelectQuery(sql_farmakeio);
                    onoma_farmakeiou = onoma_RS.getString("onoma");

                    DB_farmakeio.close();

                    String onoma_dianomea = " ";//id_dianomea
                    String sql_onoma_dianomea = "SELECT * from users WHERE id =" + history_RS.getInt("id_dianomea") + ";";

                    db DB_dianomea = new db();

                    ResultSet onoma_dianomea_RS = DB_dianomea.SelectQuery(sql_onoma_dianomea);
                    onoma_dianomea = onoma_dianomea_RS.getString("firstname") + " " + onoma_dianomea_RS.getString("firstname");

                    DB_dianomea.close();

                    History_oblist_Items.add(new History_Items(history_RS.getInt("id"), history_RS.getString("onoma") + " " + history_RS.getString("epitheto"),
                            onoma_farmakeiou, history_RS.getString("hmerominia") + " " + history_RS.getString("wra"), history_RS.getString("address"),
                            onoma_dianomea, history_RS.getString("onoma_timologioy"), history_RS.getInt("meres_parad"), history_RS.getDouble("kostos"),
                            new Button("")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            DB3.close();

            history_table.setItems(History_oblist_Items);

        }

    }

    @FXML
    private void refresh_etima_button_click(ActionEvent event) {
        refresh("etoimasia_button");
    }

    @FXML
    private void for_send_refresh_button_click(ActionEvent event) {
        refresh("for_send_refresh_buuton");
    }

    @FXML
    private void history_refresh_button_click(ActionEvent event) {
        refresh("history_refresh_button");
    }
    
    
   public class Farmaka_Items{
       
       String Med_id , onoma , ousia , temaxia,Hmer;

        public Farmaka_Items(String Med_id, String onoma, String ousia, String temaxia) {
            this.Med_id = Med_id;
            this.onoma = onoma;
            this.ousia = ousia;
            this.temaxia = temaxia;
        }

        public Farmaka_Items(String Med_id, String onoma, String ousia, String temaxia,String Hmer) {
            this.Med_id = Med_id;
            this.onoma = onoma;
            this.ousia = ousia;
            this.temaxia = temaxia;
            this.Hmer = Hmer;
        }
        
        
        
        public String getMed_id() {
            return Med_id;
        }

        public String getOnoma() {
            return onoma;
        }

        public String getOusia() {
            return ousia;
        }

        public String getTemaxia() {
            return temaxia;
        }

        public String getHmer() {
            return Hmer;
        }
        
        
   
   } 
   
   public class History_Items{
       int id;
       String name,onoma_farmakeiou,Hmer,dieuth,dianomeas,timologio;
       int days;
       double kostos;
       
       Button open_Timologio;
        public History_Items(int id, String name, String onoma_farmakeiou, String Hmer, String dieuth, String dianomeas, String timologio, int days, double kostos,Button open_Timologio){ 
            this.id = id;
            this.name = name;
            this.onoma_farmakeiou = onoma_farmakeiou;
            this.Hmer = Hmer;
            this.dieuth = dieuth;
            this.dianomeas = dianomeas;
            this.timologio = timologio;
            this.days = days;
            this.kostos = kostos;
            
            
            this.open_Timologio = open_Timologio;
            
            
            open_Timologio.setGraphic(new ImageView(new Image("file:" + System.getProperty("user.dir")+"\\src\\meditest\\images\\pdf_img.png")));
            
            this.open_Timologio.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {

                        Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + timologio);
                    } catch (IOException ex) {
                        Logger.getLogger(Sales_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getOnoma_farmakeiou() {
            return onoma_farmakeiou;
        }

        public String getHmer() {
            return Hmer;
        }

        public String getDieuth() {
            return dieuth;
        }

        public String getDianomeas() {
            return dianomeas;
        }

        public String getTimologio() {
            return timologio;
        }

        public int getDays() {
            return days;
        }

        public double getKostos() {
            return kostos;
        }
        
        
       
        
        public Button getOpen_Timologio() {
            return open_Timologio;
        }
   }

   
}