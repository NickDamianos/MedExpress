/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest;

/**
 *
 * @author Alexandros
 */
public class DianomeasTableContent {
    
    String id,numberdeliveries,critic,regdate,payment;

    public DianomeasTableContent(String id, String numberdeliveries, String critic, String regdate, String payment) {
        this.id = id;
        this.numberdeliveries = numberdeliveries;
        this.critic = critic;
        this.regdate = regdate;
        this.payment = payment;
    }

    public String getId() {
        return id;
    }

    public String getNumberdeliveries() {
        return numberdeliveries;
    }

    public String getCritic() {
        return critic;
    }

    public String getRegdate() {
        return regdate;
    }

    public String getPayment() {
        return payment;
    }
    
    
}
