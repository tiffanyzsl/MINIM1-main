package models;

//No hace falta porque estan en la misma carpeta.
//import models.LP;

import java.util.*;

public class Order {

    private String dni;
    private List<LP> lps;
    private User user;

    public Order(){
    }
    public Order(String dni) {
        this.dni=dni;
        this.lps= new LinkedList<>(); //se crea una lista para cada dni
    }

    public void addLP(int q, String p) {
        //crea otra clase para poder juntar la cantidad y el producto en una lista
       LP nuevolp = new LP(q,p);
       this.lps.add(nuevolp);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDni(){
        return dni;
    }

    public List<LP>getLps(){
        return lps;
    }
}
