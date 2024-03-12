/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest.forms;

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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import meditest.DianomeasTableContent;
import meditest.db;


public class Dianomis_FXMLController implements Initializable {

    @FXML
    private Button insertBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private TextField idTxt;
    @FXML
    private TextField numDelTxt;
    @FXML
    private TextField criticTxt;
    @FXML
    private TextField regDateTxt;
    @FXML
    private TextField paymentTxt;
    @FXML
    private TableView<DianomeasTableContent> dbDeliveryTable;
    @FXML
    private TableColumn<DianomeasTableContent, String> dbDeliveryTableId;
    @FXML
    private TableColumn<DianomeasTableContent, String> dbDeliveryTableNumDel;
    @FXML
    private TableColumn<DianomeasTableContent, String> dbDeliveryTableCritic;
    @FXML
    private TableColumn<DianomeasTableContent, String> dbDeliveryTableRegDate;
    @FXML
    private TableColumn<DianomeasTableContent, String> dbDeliveryTablePayment;
    @FXML 
    private TextField search_user;
    @FXML
    private Button refresh;
    
    ObservableList<DianomeasTableContent> search = FXCollections.observableArrayList();

    
    public static ObservableList<DianomeasTableContent>oblist = FXCollections.observableArrayList();
    
    @FXML
    public void buttonStatus(){
        String id = idTxt.getText(), numberDel = numDelTxt.getText(), critic = criticTxt.getText(),
                regDate = regDateTxt.getText(), payment = paymentTxt.getText();
        boolean isDeleteDisabled = (id.isEmpty() || id.trim().isEmpty());
        boolean isDisabled = (id.isEmpty() || id.trim().isEmpty() ||
                numberDel.isEmpty() || numberDel.trim().isEmpty() ||
                critic.isEmpty() || critic.trim().isEmpty() ||
                regDate.isEmpty() || regDate.trim().isEmpty() ||
                payment.isEmpty() || payment.trim().isEmpty());
        deleteBtn.setDisable(isDeleteDisabled);
        updateBtn.setDisable(isDisabled);
        insertBtn.setDisable(isDisabled);
    }
    
    @FXML
    private void insertBtnClicked(){
        String miniform = "INSERT INTO dianomeas(id,numberdeliveries,critic,regdate,payment) "+
                "VALUES(" + idTxt.getText() + "," + "'" + numDelTxt.getText() + "'," +
                "'" + criticTxt.getText() + "'," +
                "'" + regDateTxt.getText() + "'," +
                "'" + paymentTxt.getText() + "')";
        
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
        refresh();
    }
    
    @FXML
    private void updateBtnClicked(){
        String miniform="UPDATE dianomeas "+
                "SET numberdeliveries='" + numDelTxt.getText() +"',"+
                "critic='"+criticTxt.getText()+"',"+
                "regdate='"+regDateTxt.getText()+"',"+
                "payment='"+paymentTxt.getText()+"' "+
                "WHERE id='"+idTxt.getText()+"'";
        
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
        refresh();
    }
    
    @FXML
    private void deleteBtnClicked(){
        String miniform = "DELETE FROM dianomeas WHERE id=" + idTxt.getText();
        
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
        refresh();
    }
    
    @FXML
    private void refresh_button_handler(ActionEvent event) {
        refresh();
    }
    
    public void refresh() {

        db database = new db();

        String sql = "SELECT * FROM dianomeas ;"; 
        ResultSet rs = database.SelectQuery(sql);

        oblist.clear();

        try {
            while (rs.next()) {
                oblist.add(new DianomeasTableContent(rs.getString("id"),
                rs.getString("numberdeliveries"),rs.getString("critic"),rs.getString("regdate"),
                rs.getString("payment")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dianomis_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        dbDeliveryTable.setItems(oblist);
        database.close();
        search_user.textProperty().addListener((observable, oldValue, newValue) -> {  
            
            search.clear();
            
            if(newValue.equals("")){
                dbDeliveryTable.setItems(oblist);
            } else{
                String txt = newValue.toLowerCase(); 
                for(DianomeasTableContent delivery:oblist){  

                    if(String.valueOf(delivery.getId()).equals(txt)){ 
                        search.add(delivery);
                        
                        break;
                    } else if(delivery.getId().contains(txt)||delivery.getNumberdeliveries().contains(txt)
                                ||delivery.getCritic().contains(txt)||delivery.getRegdate().contains(txt)
                                ||delivery.getPayment().contains(txt)){ 
                        search.add(delivery);
                    }  
                }
                dbDeliveryTable.setItems(search);
            }
        });
        search_user.setText("");
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dbDeliveryTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        dbDeliveryTableNumDel.setCellValueFactory(new PropertyValueFactory<>("numberdeliveries"));
        dbDeliveryTableCritic.setCellValueFactory(new PropertyValueFactory<>("critic")); 
        dbDeliveryTableRegDate.setCellValueFactory(new PropertyValueFactory<>("regdate")); 
        dbDeliveryTablePayment.setCellValueFactory(new PropertyValueFactory<>("payment")); 
        
        refresh();
    }    
    
}
