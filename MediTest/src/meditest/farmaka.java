/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest;

import javafx.scene.control.Button;

/**
 *
 * @author nikolaos damianos
 */
public class farmaka {
    int id,temaxia,id_promyth,orio;
    String onoma,ousia,hmer_lyksis,xwra,katigoria,xrhsh,hmer_paraskevis,poiothta,eikona,etairia ;
    double timi,timi_agoras;
    
    //Button button;

    public void setId(int id) {
        this.id = id;
    }

    public void setTemaxia(int temaxia) {
        this.temaxia = temaxia;
    }

    public void setId_promyth(int id_promyth) {
        this.id_promyth = id_promyth;
    }

    public void setOnoma(String onoma) {
        this.onoma = onoma;
    }

    public void setOusia(String ousia) {
        this.ousia = ousia;
    }

    public void setHmer_lyksis(String hmer_lyksis) {
        this.hmer_lyksis = hmer_lyksis;
    }

    public void setXwra(String xwra) {
        this.xwra = xwra;
    }

    public void setKatigoria(String katigoria) {
        this.katigoria = katigoria;
    }

    public void setXrhsh(String xrhsh) {
        this.xrhsh = xrhsh;
    }

    public void setHmer_paraskevis(String hmer_paraskevis) {
        this.hmer_paraskevis = hmer_paraskevis;
    }

    public void setPoiothta(String poiothta) {
        this.poiothta = poiothta;
    }

    public void setEikona(String eikona) {
        this.eikona = eikona;
    }

    public void setTimi(double timi) {
        this.timi = timi;
    }

    public void setTimi_agoras(double timi_agoras) {
        this.timi_agoras = timi_agoras;
    }

    
    public farmaka(int id, String onoma,  String ousia, String hmer_lyksis, double timi, String xwra,int temaxia, String katigoria
            , String xrhsh, double timi_agoras, String hmer_paraskevis, String poiothta, int id_promyth,String eikona,int orio,String etairia) //,Button button
    {
        this.id = id;
        this.temaxia = temaxia;
        this.id_promyth = id_promyth;
        this.onoma = onoma;
        this.ousia = ousia;
        this.hmer_lyksis = hmer_lyksis;
        this.xwra = xwra;
        this.katigoria = katigoria;
        this.xrhsh = xrhsh;
        this.hmer_paraskevis = hmer_paraskevis;
        this.poiothta = poiothta;
        this.timi = timi;
        this.timi_agoras = timi_agoras;
        this.eikona = eikona;
        this.orio = orio;
        this.etairia = etairia;
        
        //this.button = button;
    }

    public String getEtairia() {
        return etairia;
    }

    public int getOrio() {
        return orio;
    }

    public void setOrio(int orio) {
        this.orio = orio;
    }

    public int getId() {
        return id;
    }

    public int getTemaxia() {
        return temaxia;
    }

    public int getId_promyth() {
        return id_promyth;
    }

    public String getOnoma() {
        return onoma;
    }

    public String getOusia() {
        return ousia;
    }

    public String getHmer_lyksis() {
        return hmer_lyksis;
    }

    public String getXwra() {
        return xwra;
    }

    public String getKatigoria() {
        return katigoria;
    }

    public String getXrhsh() {
        return xrhsh;
    }

    public String getHmer_paraskevis() {
        return hmer_paraskevis;
    }

    public String getPoiothta() {
        return poiothta;
    }

    public String getEikona() {
        return eikona;
    }

    public double getTimi() {
        return timi;
    }

    public double getTimi_agoras() {
        return timi_agoras;
    }
    
    /*
    public Button getButton() {
        return button;
    }*/
    
    
    
}
