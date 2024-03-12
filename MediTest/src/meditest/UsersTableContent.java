/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meditest;


public class UsersTableContent {
    String id,username,password,firstname,lastname,role,address,town,phone,afm,amka,email;

    public UsersTableContent(String id, String username, String password, String firstname, String lastname, String role, String address, String town, String phone, String afm, String amka, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.address = address;
        this.town = town;
        this.phone = phone;
        this.afm = afm;
        this.amka = amka;
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getRole() {
        return role;
    }

    public String getAddress() {
        return address;
    }

    public String getTown() {
        return town;
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
    
}
