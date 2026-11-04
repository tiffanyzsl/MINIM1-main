package models;

import java.util.*;

public class User {

    private String nif;
    private String name;
    private String surname;
    private String mail;
    private List<Order> orderList;

    public User(){

    }
    public User (String nif, String name, String surname, String mail) {
        this.nif=nif;
        this.name=name;
        this.surname=surname;
        this.mail=mail;
        this.orderList= new LinkedList<>();
    }

    public int numOrders() {
        return orderList.size();
    }
    public String getNif (){
            return nif;
    }

    public void setNif (String nif){
        this.nif=nif;
    }
    public String getName(){
            return name;
    }

    public void setName (String name){
        this.name=name;
    }

    public String getSurname(){
            return surname;
    }

    public void setSurname (String surname){
        this.surname=surname;
    }

    public String getMail(){
            return mail;
    }

    public void setMail (String mail){
        this.mail=mail;
    }
    public List<Order> orders() {
        return orderList;
    }
}
