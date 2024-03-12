/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package meditest;

import java.awt.Desktop;
import java.awt.Image;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class FXMLmainController implements Initializable {


    //public static ObservableList<users_content>oblist = FXCollections.observableArrayList();
    @FXML
    private Label Name_label;
    @FXML
    private Label role_label;
    @FXML
    private Label do_label;
    @FXML
    private Button users_button;
    @FXML
    private Button sales_button;
    @FXML
    private Button medicine_button;
    @FXML
    private Button kalathi_button;
    @FXML
    private Button dianomis_button;
    @FXML
    private Button statistics_button;
    @FXML
    private Button promitheutes_button;
    
    @FXML
    private BorderPane main;
    
    
    //public static List<kalathi_items> List_Kalathi ;
    public static ArrayList<kalathi_items> List_Kalathi = new ArrayList<kalathi_items>();
    @FXML
    private Button customer_button;
    @FXML
    private AnchorPane main_form;
    @FXML
    private Button form_button;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          Name_label.setText(MediTest.getName() + " " + MediTest.getSurname());
          role_label.setText(MediTest.getRole());
          Start_check(); 
          Starter_form();       
          
          
          
    }    

    
    
    @FXML
    private void menu_buttons_clicked(ActionEvent event) {
        Button source = (Button) event.getSource();
        label_change_text(source.getId());
        change_scene(source.getId());
        
    }

  
    
    private void change_scene(String scene_name) {
        
        Parent secondroot = null;
        try {
            String name = scene_name.substring(0,scene_name.length() -7 );
            System.out.println(".\\forms\\"+name + "_FXML.fxml");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("forms/"+name + "_FXML.fxml"));
            
            secondroot = loader.load();
            main.setCenter(secondroot);
           
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         //main.setCenter(secondroot);
    }
    
    void Start_check(){
        String role = MediTest.getRole();
        if (role.equals("admin")||role.equals("apothikarios")||role.equals("ypalilos")){
            db DB = new db();
            String sql = "SELECT * from farmaka;";
            ResultSet RS = DB.SelectQuery(sql);
            String Warnings_for_temaxia = "";
            String Warnings_for_Lyksis = "";
            
            Boolean have_Warnings = false;
            try {
                while(RS.next()){
                    if(RS.getInt("temaxia") <= RS.getInt("orio_temaxion")){
                        Warnings_for_temaxia += "Το προιόν : "+RS.getInt("id")+ " " +RS.getString("onoma")+" και με ουσία "
                                +RS.getString("ousia")+ " έχει λίγα αποθέματα ( "
                                +RS.getInt("temaxia")+"<"+RS.getInt("orio_temaxion")+" ) \n";
                        have_Warnings = true;
                    }
                    
                    int meres_gia_liksi = getDiaf_imeron_apo_simera(RS.getString("hmer_lyksis"));
                    //System.out.println("Hmeres gia liksi : "+ meres_gia_liksi);
                    
                    if(meres_gia_liksi < 7 && meres_gia_liksi >= 0){
                        Warnings_for_Lyksis += "Το προιόν : "+RS.getInt("id")+ " " +RS.getString("onoma")+" και με ουσία "
                                +RS.getString("ousia")+" λήγη σε "+ meres_gia_liksi+" μέρες !!!! \n";
                        have_Warnings = true;
                    } else if (meres_gia_liksi <= 0){
                        Warnings_for_Lyksis += "Το προιόν : "+RS.getInt("id")+ " " +RS.getString("onoma")+" και με ουσία "
                                +RS.getString("ousia")+"έχει λήξει !!! \n";
                        have_Warnings = true;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLmainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DB.close();
            
            if(have_Warnings){
                //System.out.println(Warnings_for_temaxia);
                //System.out.println("_______________");
                //System.out.println(Warnings_for_Lyksis);
                
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Warnings!!!!");
                a.setHeaderText("Προσοχή !!!!");
                a.setContentText(Warnings_for_temaxia + " ________________________________________ \n "+Warnings_for_Lyksis);

                a.showAndWait();  
            }
        } 
    }
    
    int getDiaf_imeron_apo_simera(String date){
        String Current_date = new SimpleDateFormat("dd_MM_yyyy").format(Calendar.getInstance().getTime());
        
        int day = Integer.parseInt(date.split("-")[2]);
        int month = Integer.parseInt(date.split("-")[1]);
        int year = Integer.parseInt(date.split("-")[0]);
        
        int nowday = Integer.parseInt(Current_date.split("_")[0]);
        int nowmonth = Integer.parseInt(Current_date.split("_")[1]);
        int nowyear = Integer.parseInt(Current_date.split("_")[2]);
        
        Date dateGiven = new Date(year,month,day);
        Date now = new Date(nowyear,nowmonth,nowday);
        
        return (int)( ( dateGiven.getTime()-now.getTime()) / (1000 * 60 * 60 * 24));
    }
    
    void label_change_text(String form){
        String name = form.substring(0,form.length() -7 );
        System.out.println("Forma : "+name);
        
        if (name.equals("users")) {
            do_label.setText("Χρήστες");
        } else if (name.equals("sales")) {
            do_label.setText("Παραγγελίες");
        } else if (name.equals("medicine")) {
            do_label.setText("Φάρμακα");
        }else if (name.equals("kalathi")) {
            do_label.setText("Καλάθι Αγορών");
        }else if (name.equals("dianomis")) {
            do_label.setText("Διανομής");
        }else if (name.equals("statistics")) {
            do_label.setText("Στατιστικά");
        }else if (name.equals("customer")) {
            do_label.setText("Πελάτες");
        }else if (name.equals("promitheutes")) {
            do_label.setText("Προμηθευτές");
        }
    }
    
    void Starter_form(){
        
        
        
        String role = MediTest.getRole();
        if (role.equals("admin")||role.equals("apothikarios")||role.equals("ypalilos")){
            
            change_scene("medicine_button");
            label_change_text("medicine_button");
            
        } else if(role.equals("logistis")){
            
            change_scene("statistics_button");
            label_change_text("statistics_button");
            
        } else if(role.equals("dianomeas")){
            change_scene("sales_button");
            label_change_text("sales_button");
        }
    }

    @FXML
    private void open_forma(ActionEvent event) throws URISyntaxException {
        String url = "https://docs.google.com/forms/d/e/1FAIpQLSePbii1RpmV76Npv_leT5YJ3tpINTijkVevO12ZQPiHBA_mUA/viewform?fbclid=IwAR0DhVAv8qDbjzJyx0Tl0VT8fvTaECFjloKftWxrJDqSA0BtUQYrK93c-K8";
        
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException ex) {
            Logger.getLogger(FXMLmainController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }
}
