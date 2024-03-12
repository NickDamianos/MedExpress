/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest.forms;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import meditest.FXMLmainController;
//import static meditest.FXMLmainController.oblist;
import meditest.MediTest;
import meditest.UsersTableContent;
import meditest.db;

/**
 * FXML Controller class
 *
 * @author Alexandros
 */
public class Users_FXMLController implements Initializable {

    @FXML
    private Button insertBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private TextField idtxt;
    @FXML
    private TextField usernametxt;
    @FXML
    private TextField passwordtxt;
    @FXML
    private TextField firstnametxt;
    @FXML
    private TextField lastnametxt;
    @FXML
    private ChoiceBox<String> roles;
    @FXML
    private TextField addresstxt;
    @FXML
    private TextField towntxt;
    @FXML
    private TextField phonetxt;
    @FXML
    private TextField afmtxt;
    @FXML
    private TextField amkatxt;
    @FXML
    private TextField emailtxt;
    @FXML
    private TableView<UsersTableContent> dbUserTable;
    @FXML
    private TableColumn<UsersTableContent, String> dbUserTableId;
    @FXML
    private TableColumn<UsersTableContent, String> dbUserTableUsername;
    @FXML
    private TableColumn<UsersTableContent, String> dbUserTablePassword;
    @FXML
    private TableColumn<UsersTableContent, String> dbUserTableFirstname;
    @FXML
    private TableColumn<UsersTableContent, String> dbUserTableLastname;
    @FXML
    private TableColumn<UsersTableContent, String> dbUserTableRole;
    @FXML
    private TableColumn<UsersTableContent, String> dbUserTableAddress;
    @FXML
    private TableColumn<UsersTableContent, String> dbUserTableTown;
    @FXML
    private TableColumn<UsersTableContent, String> dbUserTablePhone;
    @FXML
    private TableColumn<UsersTableContent, String> dbUserTableAfm;
    @FXML
    private TableColumn<UsersTableContent, String> dbUserTableAmka;
    @FXML
    private TableColumn<UsersTableContent, String> dbUserTableEmail;
    @FXML 
    private TextField search_user;
    @FXML
    private Button refresh;
    
    ObservableList<UsersTableContent> search = FXCollections.observableArrayList();

    
    public static ObservableList<UsersTableContent>oblist = FXCollections.observableArrayList();
    
    
    @FXML
    public void updateAndInsertStatus(){
        String id = idtxt.getText(), username = usernametxt.getText(), password = passwordtxt.getText(),
                firstname = firstnametxt.getText(), lastname = lastnametxt.getText(),
                address = addresstxt.getText(), town = towntxt.getText(), phone = phonetxt.getText(),
                afm = afmtxt.getText(), amka = amkatxt.getText(), email = emailtxt.getText();
        boolean isDeleteDisabled = (id.isEmpty() || id.trim().isEmpty());
        boolean isDisabled = (id.isEmpty() || id.trim().isEmpty() || username.isEmpty() || username.trim().isEmpty() || password.isEmpty() || password.trim().isEmpty() ||
                firstname.isEmpty() || firstname.trim().isEmpty() || lastname.isEmpty() || lastname.trim().isEmpty() ||
                address.isEmpty() || address.trim().isEmpty() || town.isEmpty() || town.trim().isEmpty() ||
                phone.isEmpty() || phone.trim().isEmpty() || afm.isEmpty() || afm.trim().isEmpty() ||
                amka.isEmpty() || amka.trim().isEmpty() || email.isEmpty() || email.trim().isEmpty());
        deleteBtn.setDisable(isDeleteDisabled);
        updateBtn.setDisable(isDisabled);
        insertBtn.setDisable(isDisabled);
    }
    
    @FXML
    private void refresh_button_handler(ActionEvent event) {
        refresh();
    }
    
    @FXML
    private void deleteBtnClicked(){
        String miniform = "DELETE FROM users WHERE id=" + idtxt.getText();
        
        String db_url = "jdbc:sqlite:" + System.getProperty("user.dir") + "//test_log.db";
        
        Statement deleteStmt;
        try{
            Connection con = DriverManager.getConnection(db_url); 
            deleteStmt = con.createStatement();
            deleteStmt.executeQuery(miniform);
            deleteStmt.close();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    private void insertBtnClicked(){
        String miniform = "INSERT INTO users(id,username,password,firstname,lastname,role,address,town,phone,afm,amka,email) "+
                "VALUES(" + idtxt.getText() + "," + "'" + usernametxt.getText() + "'," +
                "'" + passwordtxt.getText() + "'," +
                "'" + firstnametxt.getText() + "'," +
                "'" + lastnametxt.getText() + "'," +
                "'" + roles.getValue() + "'," +
                "'" + addresstxt.getText() + "'," +
                "'" + towntxt.getText() + "'," +
                "'" + phonetxt.getText() + "'," +
                "'" + afmtxt.getText() + "'," +
                "'" + amkatxt.getText() + "'," +
                "'" + emailtxt.getText() + "')";
        
        String db_url = "jdbc:sqlite:" + System.getProperty("user.dir") + "//test_log.db";
        
        Statement insertStmt;
        try{
            Connection con = DriverManager.getConnection(db_url); 
            insertStmt = con.createStatement();
            insertStmt.executeQuery(miniform);
            insertStmt.close();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    private void updateBtnClicked(){
        String miniform="UPDATE users "+
                "SET username='" + usernametxt.getText() +"',"+
                "password='"+passwordtxt.getText()+"',"+
                "firstname='"+firstnametxt.getText()+"',"+
                "lastname='"+lastnametxt.getText()+"',"+
                "role='"+roles.getValue()+"',"+
                "address='"+addresstxt.getText()+"',"+
                "town='"+towntxt.getText()+"',"+
                "phone='"+phonetxt.getText()+"',"+
                "AFM='"+afmtxt.getText()+"',"+
                "AMKA='"+amkatxt.getText()+"',"+
                "email='"+emailtxt.getText()+"' "+
                "WHERE id='"+idtxt.getText()+"'";
        
        String db_url = "jdbc:sqlite:" + System.getProperty("user.dir") + "//test_log.db";
        
        Statement updateStmt;
        //ResultSet rs = DB.SelectQuery(sql);
        try{
            Connection con = DriverManager.getConnection(db_url); 
            updateStmt = con.createStatement();
            updateStmt.executeQuery(miniform);
            updateStmt.close();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        roles.getItems().add("admin");
        roles.getItems().add("apothikarios");
        roles.getItems().add("dianomeas");
        roles.getItems().add("Logistis");
        roles.getItems().add("ypalilos"); 
        
        dbUserTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        dbUserTableUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        dbUserTablePassword.setCellValueFactory(new PropertyValueFactory<>("password")); 
        dbUserTableFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname")); 
        dbUserTableLastname.setCellValueFactory(new PropertyValueFactory<>("lastname")); 
        dbUserTableRole.setCellValueFactory(new PropertyValueFactory<>("role")); 
        dbUserTableAddress.setCellValueFactory(new PropertyValueFactory<>("address")); 
        dbUserTableTown.setCellValueFactory(new PropertyValueFactory<>("town")); 
        dbUserTablePhone.setCellValueFactory(new PropertyValueFactory<>("phone")); 
        dbUserTableAfm.setCellValueFactory(new PropertyValueFactory<>("afm")); 
        dbUserTableAmka.setCellValueFactory(new PropertyValueFactory<>("amka")); 
        dbUserTableEmail.setCellValueFactory(new PropertyValueFactory<>("email")); 

        refresh();
    } 
    
    public void refresh() {

        db database = new db();

        String sql = "SELECT * FROM users ;"; //username, password,onoma,epitheto,dieuthinsi,city,tilefono,afm,AMKA,email,rolos
        ResultSet rs = database.SelectQuery(sql);

        oblist.clear();

        try {
            while (rs.next()) {
                //System.out.println(rs.getInt("id") + rs.getString("username") + rs.getString("password"));
                oblist.add(new UsersTableContent(rs.getString("id"),
                rs.getString("username"),rs.getString("password"),rs.getString("firstname"),
                rs.getString("lastname"),rs.getString("role"),
                rs.getString("address"),rs.getString("town"),rs.getString("phone"),
                rs.getString("afm"),rs.getString("amka"),rs.getString("email")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Users_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        dbUserTable.setItems(oblist);
        database.close();
        search_user.textProperty().addListener((observable, oldValue, newValue) -> { //otan allaksi to textfield tote kane: 
            
            search.clear();//einai h lista pou 8a apothikrutoun oi tipes pou vriskei 
            
            if(newValue.equals("")){//an den exei timi sto textfield tote dikse olous tous users
                dbUserTable.setItems(oblist);
            } else{
                String txt = newValue.toLowerCase();//ta kanei mikra 
                for(UsersTableContent user:oblist){ // gia ka8e user 

                    if(String.valueOf(user.getId()).equals(txt)){//ama to noumero einai iso me to id tote vale stin lista ton user 
                        search.add(user);
                        
                        break;
                    } else if(user.getFirstname().contains(txt)||user.getPassword().contains(txt)
                                ||user.getTown().contains(txt)||user.getRole().contains(txt)
                                ||user.getPhone().contains(txt)||user.getUsername().contains(txt)
                                ||user.getLastname().contains(txt)||user.getEmail().contains(txt)
                                ||user.getAddress().contains(txt)||user.getAmka().contains(txt)
                                ||user.getAfm().contains(txt)){ // ama yparxei mesa sta parapanw tote pros8eseta sthn lista
                        search.add(user);
                    }  
                }
                dbUserTable.setItems(search);
            }
        });
    }
    
}
