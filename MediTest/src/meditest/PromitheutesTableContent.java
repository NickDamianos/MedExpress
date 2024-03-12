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
public class PromitheutesTableContent {
    
    String id,name,telephone,address,product,cost;

    public PromitheutesTableContent(String id, String name, String telephone, String address, String product, String cost) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.product = product;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public String getProduct() {
        return product;
    }

    public String getCost() {
        return cost;
    }
    
}
