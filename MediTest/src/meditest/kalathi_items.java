/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
//import static meditest.FXMLmainController.oblist_Kalathi;
import meditest.forms.Kalathi_FXMLController;
import meditest.forms.Users_FXMLController;

/**
 *
 * @author nikolaos damianos
 */
public class kalathi_items {

    int med_id;
    String onoma, ousia, temaxia, timi, timi_agoras;
    Button button;
    Double syn_timi;
    public kalathi_items(int med_id, String onoma, String ousia, String temaxia, String timi, String timi_agoras){//, Button button) {
        this.med_id = med_id;
        this.onoma = onoma;
        this.ousia = ousia;
        this.temaxia = temaxia;
        this.timi = timi;
        this.timi_agoras = timi_agoras;//kostos se emas
        /*this.button = button;

        this.button.setOnAction(e -> {
            int i = 0;
            for (kalathi_items item : FXMLmainController.List_Kalathi) {

                if (item.getButton() == button) {
                    
                    db DB = new db();

                    
                        String query1 = "SELECT * from farmaka where id = " + item.getMed_id();
                        ResultSet rs = DB.SelectQuery(query1);

                        try {
                            String query = "UPDATE farmaka SET temaxia = " + String.valueOf(Integer.parseInt(item.getTemaxia()) + rs.getInt("temaxia")) + " WHERE id = " + item.getMed_id();
                            DB.updateDB(query);

                        } catch (SQLException ex) {
                            Logger.getLogger(MediTest.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    

                    DB.close();
                    FXMLmainController.List_Kalathi.remove(i);
                    
                }
                i++;
            }
            
            
        });
        */
       syn_timi = Double.parseDouble(this.timi) * Integer.parseInt(this.temaxia); 
    }

    public Double getSyn_timi() {
        return syn_timi;
    }
    
    public Button getButton() {
        return button;
    }

    public int getMed_id() {
        return med_id;
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

    public String getTimi() {
        return timi;
    }

    public String getTimi_agoras() {
        return timi_agoras;
    }
    
    
}
