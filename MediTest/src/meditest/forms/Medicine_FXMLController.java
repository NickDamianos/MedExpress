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
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import meditest.MediTest;
import meditest.db;
import meditest.farmaka;



/**
 * FXML Controller class
 *
 * @author nikolaos damianos
 */
public class Medicine_FXMLController implements Initializable {

    public static ObservableList<farmaka> oblist_Farmaka = FXCollections.observableArrayList();

    ObservableList<farmaka> search = FXCollections.observableArrayList();

    @FXML
    private AnchorPane main;
    @FXML
    private FlowPane flow;
    @FXML
    private Button ADD_MED;
    @FXML
    private TextField search_farmaka;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refresh();//fortoni ta farmaka stin lista oblist_Farmaka

        flow.setHgap(10);

        //int num_farmaka = oblist_Farmaka.size();
        AddAll();

        search_farmaka.textProperty().addListener((observable, oldValue, newValue) -> { // gia na kanei anazitisi
            clear();//ka8arizei to flowpane
            search.clear();//ka8arizei to search array 

            if (newValue.equals("")) {//an den exei timi sto textfield tote dikse ola farmaka
                AddAll();
            } else {
                String txt = newValue.toLowerCase();//ta kanei mikra 

                for (farmaka med : oblist_Farmaka) {//gia ola ta farmaka
                    Boolean ousia_search = false;
                    //System.out.println(med.getId() + " == " + txt);
                    String promyth = "gjh";//getPromitheuti(med.getId_promyth());
                    String id = "#" + String.valueOf(med.getId());

                    if (id.equals(txt)) { // gia na psaksi gia id 8a prepei prwta na valei #
                        //System.out.println(med.getId() + " == " + txt);
                        search.add(med);//valtw ston search array
                        String ousia = med.getOusia().toLowerCase();
                        //System.out.println(med.getId() + " == " + txt);
                        for (farmaka idia_ousia : oblist_Farmaka) { // auto to kanw gia na mpoun ola ta farmaka me tin idia ousia
                            if (idia_ousia.getOusia().toLowerCase().equals(ousia) && idia_ousia != med) {
                                search.add(idia_ousia);
                            }
                        }

                        break;
                    } else if (med.getOusia().toLowerCase().equals(txt)) { //ama psaxnei gia ousia 
                        search.add(med);
                        //System.out.println("I Am in");
                        ousia_search = true;
                    } else if ((med.getKatigoria().toLowerCase().contains(txt) || med.getOnoma().toLowerCase().contains(txt) || med.getPoiothta().toLowerCase().contains(txt)
                            || String.valueOf(med.getTemaxia()).toLowerCase().contains(txt) || String.valueOf(med.getTimi()).toLowerCase().contains(txt)
                            || String.valueOf(med.getTimi_agoras()).toLowerCase().contains(txt) || med.getXrhsh().toLowerCase().contains(txt) || med.getXwra().toLowerCase().contains(txt)
                            || med.getHmer_lyksis().contains(txt) || med.getHmer_paraskevis().contains(txt) || promyth.contains(txt)) && ousia_search == false) { // aliws an yparxei se auta 
                        search.add(med);
                        String ousia = med.getOusia().toLowerCase();
                        //System.out.println(med.getId()+" == "+txt);

                        for (farmaka idia_ousia : oblist_Farmaka) {
                            if (idia_ousia.getOusia().toLowerCase().equals(ousia) && idia_ousia != med) {
                                search.add(idia_ousia);
                            }

                        }
                        break;
                    }
                }
                AddSearced();
                //add sto flow to search
            }

        });

    }

    void AddAll() {
        for (farmaka farmako : oblist_Farmaka) {
            VBox med = newFarmako(farmako);

            flow.setMargin(med, new Insets(35, 25, 20, 10));

            flow.getChildren().add(med);//newFarmako(farmako));
        }

    }

    void AddSearced() {

        for (farmaka farmako : search) {
            VBox med = newFarmako(farmako);

            flow.setMargin(med, new Insets(25, 25, 20, 10));

            flow.getChildren().add(med);//newFarmako(farmako));
        }

    }

    VBox newFarmako(farmaka med) {// auto einai giana vazei ta farmaka san kartes 
        VBox v = new VBox();//kane 1 vbox | width : 162| heigdth : 238

        v.setStyle("-fx-background-color: #d3ebe4 ;"//me xrwma #d3ebe4
                + "-fx-background-radius: 5.0 ;"
                + "-fx-padding: 8 ;"//auta pou exei mesa na apexwn 8 //padding
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" // gia na valw skia 
        );

        v.setPrefWidth(162);//width : 162| heigdth : 238
        v.setPrefHeight(238);

        v.setId(String.valueOf(med.getId()) + "_vbox");
        ImageView img_Holder = new ImageView();

        //System.out.println(System.getProperty("user.dir") +med.getEikona());
        Image image = new Image("file:" + System.getProperty("user.dir") + med.getEikona());

        //System.out.println(image.getHeight());
        img_Holder.setImage(image);

        img_Holder.setFitWidth(162);
        img_Holder.setFitHeight(94);

        Label ID = new Label("ID : " + med.getId());
        Label name = new Label("Όνομα : " + med.getOnoma());
        Label ousia = new Label("Ουσία : " + med.getOusia());
        Label timi = new Label("Τιμή : " + String.valueOf(med.getTimi()) + " €");
        Label temaxia = new Label("Τεμάχια : " + String.valueOf(med.getTemaxia()));
        Label timi_agoras = new Label("Τιμή Αγοράς : " + String.valueOf(med.getTimi_agoras()) + " €");

        Button more = new Button("Περισσότερα");
        Button Add = new Button("Στο Καλάθι");

        Add.getStyleClass().add("button1");
        more.getStyleClass().add("button3");

        more.setOnAction(e -> {
            //System.out.println(v.getId());
            MediTest.setWorking_id(med.getId());
            open(1);
        });

        Add.setOnAction(e -> {
            MediTest.setWorking_id(med.getId());
            open(2);

        });

        v.setAlignment(Pos.CENTER);

        v.getChildren().addAll(img_Holder,ID, name, ousia, timi, temaxia, timi_agoras, more, Add);

        
        return v;
    }

    void refresh() {
        //flow.getChildren().clear();
        db DB = new db();

        String sql = "SELECT * FROM farmaka ;"; //onoma,ousia,hmer_lyksis,timi,xwra,temaxia,katigoria,xrhsh,timi_agoras,hmer_paraskevis,poiothta,id_promyth,img

        ResultSet rs = DB.SelectQuery(sql);

        oblist_Farmaka.clear();

        try {
            while (rs.next()) {
                //System.out.println(rs.getInt("id") + rs.getString("username") + rs.getString("password"));
                oblist_Farmaka.add(new farmaka(rs.getInt("id"), rs.getString("onoma"), rs.getString("ousia"), rs.getString("hmer_lyksis"),
                        rs.getDouble("timi"), rs.getString("xwra"),
                        rs.getInt("temaxia"), rs.getString("katigoria"),
                        rs.getString("xrhsh"), rs.getInt("timi_agoras"),
                        rs.getString("hmer_paraskevis"), rs.getString("poiothta"), rs.getInt("id_promyth"), rs.getString("img"),rs.getInt("orio_temaxion"),rs.getString("etairia")));
                System.out.println(rs.getString("onoma"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        DB.close();

    }

    void open(int _scene_) {//1 gia more / 2 gia add sto kalathi /3 gia add stin basei
        FXMLLoader loader;
        String Title;
        if (_scene_ == 1) {
            loader = new FXMLLoader(getClass().getResource("more_info_med_FXML.fxml"));
            Title = "Πληροφορίες";
        } else if (_scene_ == 2) {
            loader = new FXMLLoader(getClass().getResource("Med_add_kalathi_FXML.fxml"));
            Title = "Προσθήκη στο καλάθι";
        } else {
            loader = new FXMLLoader(getClass().getResource("farmaka_Add_to_base_FXML.fxml"));
            Title = "Προσθήκη Νέου Φαρμάκου";
        }

        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();

            Scene scene = new Scene(root);
            stage.setTitle(Title);;
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(Medicine_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void add_base(ActionEvent event) {
        open(3);
    }

    @FXML
    private void ananeosi_for_button(ActionEvent event) {
        clear();
        refresh();
        AddAll();

        search_farmaka.setText("");

    }

    void clear() {
        int[] ids = new int[oblist_Farmaka.size()];

        int i = 0;

        for (Node x : flow.getChildren()) {
            //System.out.println(x.getId());  
            if (x.getId() != null && x.getId().contains("_vbox")) {
                ids[i] = flow.getChildren().indexOf(x);

                //System.out.println(x.getId() + "  :: :: " + ids[i]);
                //flow.getChildren().remove(flow.getChildren().indexOf(x));
                i++;
            }
        }

        for (int ii = 0; ii < ids.length / 2; ii++) {
            int temp = ids[ii];
            ids[ii] = ids[ids.length - ii - 1];
            ids[ids.length - ii - 1] = temp;
        }

        for (int ii = 0; ii < oblist_Farmaka.size(); ii++) {
            //System.out.println("dffdfdf : tghgh : " + ids[ii]);
            if (ids[ii] != 0) {
                flow.getChildren().remove(ids[ii]);
            }
            //System.out.println("Bruh");
        }

    }

    String getPromitheuti(int id) {
        String name = "";

        db DB = new db();
        String query = "SELECT onoma from promith where id= " + id + " ;";
        ResultSet rs = DB.SelectQuery(query);
        try {
            name = rs.getString("onoma");
        } catch (SQLException ex) {
            Logger.getLogger(Medicine_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DB.close();
        return name;
    }


    public static void refreshPublic() {
        //flow.getChildren().clear();
        db DB = new db();

        String sql = "SELECT * FROM farmaka ;"; //onoma,ousia,hmer_lyksis,timi,xwra,temaxia,katigoria,xrhsh,timi_agoras,hmer_paraskevis,poiothta,id_promyth,img

        ResultSet rs = DB.SelectQuery(sql);

        oblist_Farmaka.clear();

        try {
            while (rs.next()) {
                //System.out.println(rs.getInt("id") + rs.getString("username") + rs.getString("password"));
                oblist_Farmaka.add(new farmaka(rs.getInt("id"), rs.getString("onoma"), rs.getString("ousia"), rs.getString("hmer_lyksis"),
                        rs.getDouble("timi"), rs.getString("xwra"),
                        rs.getInt("temaxia"), rs.getString("katigoria"),
                        rs.getString("xrhsh"), rs.getInt("timi_agoras"),
                        rs.getString("hmer_paraskevis"), rs.getString("poiothta"), rs.getInt("id_promyth"), rs.getString("img"),rs.getInt("orio_temaxion"),rs.getString("etairia")));
                System.out.println(rs.getString("onoma"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        DB.close();

    }




}


