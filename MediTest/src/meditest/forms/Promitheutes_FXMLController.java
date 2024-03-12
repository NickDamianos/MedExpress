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
import meditest.PromitheutesTableContent;
import meditest.db;

/**
 * FXML Controller class
 *
 * @author Alexandros
 */
public class Promitheutes_FXMLController implements Initializable {

    @FXML
    private Button insertBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private TextField idTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField telephoneTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private TextField productTxt;
    @FXML
    private TextField costTxt;
    @FXML
    private TableView<PromitheutesTableContent> dbPromitheutesTable;
    @FXML
    private TableColumn<PromitheutesTableContent, String> dbPromitheutesTableId;
    @FXML
    private TableColumn<PromitheutesTableContent, String> dbPromitheutesTableName;
    @FXML
    private TableColumn<PromitheutesTableContent, String> dbPromitheutesTableTelephone;
    @FXML
    private TableColumn<PromitheutesTableContent, String> dbPromitheutesTableAddress;
    @FXML
    private TableColumn<PromitheutesTableContent, String> dbPromitheutesTableProduct;
    @FXML
    private TableColumn<PromitheutesTableContent, String> dbPromitheutesTableCost;
    @FXML 
    private TextField search_user;
    @FXML
    private Button refresh;
    
    ObservableList<PromitheutesTableContent> search = FXCollections.observableArrayList();

    
    public static ObservableList<PromitheutesTableContent>oblist = FXCollections.observableArrayList();
    
    
    
    
    
    @FXML
    public void buttonStatus(){
        String id = idTxt.getText(), name = nameTxt.getText(), telephone = telephoneTxt.getText(),
                address = addressTxt.getText(), product = productTxt.getText(), cost = costTxt.getText();
        boolean isDeleteDisabled = (id.isEmpty() || id.trim().isEmpty());
        boolean isDisabled = (id.isEmpty() || id.trim().isEmpty() ||
                name.isEmpty() || name.trim().isEmpty() ||
                telephone.isEmpty() || telephone.trim().isEmpty() ||
                address.isEmpty() || address.trim().isEmpty() ||
                product.isEmpty() || product.trim().isEmpty()) ||
                cost.isEmpty() || cost.trim().isEmpty();
        deleteBtn.setDisable(isDeleteDisabled);
        updateBtn.setDisable(isDisabled);
        insertBtn.setDisable(isDisabled);
    }
    
    public void refresh() {

        db database = new db();

        String sql = "SELECT * FROM promith ;"; 
        ResultSet rs = database.SelectQuery(sql);

        oblist.clear();

        try {
            while (rs.next()) {
                oblist.add(new PromitheutesTableContent(rs.getString("id"),
                rs.getString("name"),rs.getString("telephone"),rs.getString("address"),
                rs.getString("product"), rs.getString("cost")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Promitheutes_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        dbPromitheutesTable.setItems(oblist);
        database.close();
        search_user.textProperty().addListener((observable, oldValue, newValue) -> {  
            
            search.clear();
            
            if(newValue.equals("")){
                dbPromitheutesTable.setItems(oblist);
            } else{
                String txt = newValue.toLowerCase(); 
                for(PromitheutesTableContent provider:oblist){  

                    if(String.valueOf(provider.getId()).equals(txt)){ 
                        search.add(provider);
                        
                        break;
                    } else if(provider.getId().contains(txt)||provider.getName().contains(txt)
                                ||provider.getTelephone().contains(txt)||provider.getAddress().contains(txt)
                                ||provider.getProduct().contains(txt) ||provider.getCost().contains(txt)){ 
                        search.add(provider);
                    }  
                }
                dbPromitheutesTable.setItems(search);
            }
        });
        search_user.setText("");
    }
    
    @FXML
    private void refresh_button_handler(ActionEvent event) {
        refresh();
    }
    
    @FXML
    private void insertBtnClicked(){
        String miniform = "INSERT INTO promith(id,name,telephone,address,product,cost) "+
                "VALUES(" + idTxt.getText() + "," + "'" + nameTxt.getText() + "'," +
                "'" + telephoneTxt.getText() + "'," +
                "'" + addressTxt.getText() + "'," +
                "'" + productTxt.getText() + "'," +
                "'" + costTxt.getText() + "')";
        
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
        String miniform="UPDATE promith "+
                "SET name='" + nameTxt.getText() +"',"+
                "telephone='"+telephoneTxt.getText()+"',"+
                "address='"+addressTxt.getText()+"',"+
                "product='"+productTxt.getText()+"',"+
                "cost='"+costTxt.getText()+"' "+
                "WHERE id='"+idTxt.getText()+"'";
        
        String db_url = "jdbc:sqlite:" + System.getProperty("user.dir") + "//test_log.db";
        
        Statement updateStmt;
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
        String miniform = "DELETE FROM promith WHERE id=" + idTxt.getText();
        
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
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dbPromitheutesTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        dbPromitheutesTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        dbPromitheutesTableTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone")); 
        dbPromitheutesTableAddress.setCellValueFactory(new PropertyValueFactory<>("address")); 
        dbPromitheutesTableProduct.setCellValueFactory(new PropertyValueFactory<>("product")); 
        dbPromitheutesTableCost.setCellValueFactory(new PropertyValueFactory<>("cost")); 
        refresh();
    }    
    
}
