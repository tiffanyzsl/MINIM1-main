package manager;

import models.Order;
import models.Product;
import models.User;

import java.util.List;

public interface ProductManager {

    void addUser(String nif, String name, String surname, String mail);
    void addProduct(String id, String name, double price);
    void addOrder(Order order);
    void clear();

    List<Product> getProductsByPrice();
    List<Product> getProductsBySales();
    Order deliverOrder();

    Product getProduct(String id); //le pasa un id y busca en productos
    User getUser(String number);

    List<Order> getOrdersByUser(String idUser);

    //sirve para cuentos elementos tengo guardados
    //comprobar en los test JUNIT
    int numOrders();
    int numUsers();
    int numProducts();
}
