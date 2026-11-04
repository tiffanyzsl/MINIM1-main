package models;

public class Product {

    private  String id;
    private  String name;
    private double price;
    private int numSales;

    public Product(){
    }
    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setSales(int sales){
        this.numSales = sales;
    }

    public int getSales() {
        return numSales;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
