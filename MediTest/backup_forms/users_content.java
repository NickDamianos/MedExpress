/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import meditest.forms.Users_FXMLController;

/**
 *
 * @author nikolaos damianos
 */
public class users_content {
    String username,password,onoma,eponumo,dieuthinsi,poli,tilefono,afm,amka,email,rolos;
    int id;
    Button button;
    
    users_content send;
    
    public users_content(int id,String username, String password, String onoma, String eponumo, String dieuthinsi, String poli, String tilefono, String afm, String amka, String email, String rolos, Button button) {
        this.username = username;
        this.password = password;
        this.onoma = onoma;
        this.eponumo = eponumo;
        this.dieuthinsi = dieuthinsi;
        this.poli = poli;
        this.tilefono = tilefono;
        this.afm = afm;
        this.amka = amka;
        this.email = email;
        this.rolos = rolos;
        this.id = id;
        this.button = button;
        
        this.button.setOnAction(e->{
            for(users_content user : Users_FXMLController.oblist){//FXMLmainController.oblist){
                if(user.getButton() == button){
                    try {
                        this.send = user;
                        System.out.println("id = " + send.getId());
                        MediTest.setWorking_id(user.getId());
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("pop_up_user_editior.fxml"));
                        Parent root = loader.load();
                        
                        
                        
                        Stage stage = new Stage();
                        
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        //
                        
                        
                        
                    } catch (IOException ex) {
                        Logger.getLogger(users_content.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        });
    }
    
    
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getOnoma() {
        return onoma;
    }

    public String getEponumo() {
        return eponumo;
    }

    public String getDieuthinsi() {
        return dieuthinsi;
    }

    public String getPoli() {
        return poli;
    }

    public String getTilefono() {
        return tilefono;
    }

    public String getAfm() {
        return afm;
    }

    public String getAmka() {
        return amka;
    }

    public String getEmail() {
        return email;
    }

    public String getRolos() {
        return rolos;
    }

    public int getId() {
        return id;
    }

    public Button getButton() {
        return button;
    }

    

    
    
    
    
}
