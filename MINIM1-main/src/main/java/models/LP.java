package models;

public class LP {

    private int q;
    private String p;

    public LP(){
    }
    public LP (int q, String p){
        this.q=q;
        this.p=p;
    }

    public int getQ(){
        return q;
    }

    public void setQ(int cantidades){
        this.q=cantidades;
    }

    public String getP(){
        return p;
    }

    public void setP(String productos){
        this.p=productos;
    }

}
