package manager;

import models.LP;
import models.Order;
import models.Product;
import models.User;

import java.util.*;

//2.4:LOG4J
import org.apache.log4j.Logger;

public class ProductManagerImpl implements ProductManager {
    //Lista ideal para reordenar elementos
    private List<Product> productList; //ordenarlos por precio o ventas
    private Queue<Order> orderQueue;
    //Diccionario (pareja de datos)
    private HashMap<String, User> users;

    //LOG4J
    final static Logger logger = Logger.getLogger(ProductManagerImpl.class);

    //singleton
    private static ProductManagerImpl instance;

        //constructor
    private ProductManagerImpl() {
        this.productList = new ArrayList<>();
        this.orderQueue = new LinkedList<>();
        this.users=new HashMap<>();
    }

    public static ProductManagerImpl getInstance() {
        if (instance == null) {
            instance = new ProductManagerImpl();
        }
        return instance;
    }

    @Override
    public void addProduct(String id, String name, double price) {
        logger.info("Iniciando addProduct. Añadiendo producto con ID: " + id + ", Nombre: " + name + "y Precio:" +price);
        productList.add(new Product(id, name, price));
        logger.info("Finalizando addProduct. Producto añadido a la lista.");
    }

    @Override
    public List<Product> getProductsByPrice() {
        logger.info("Iniciando getProductsByPrice. Se va a ordenar ascendentemente y devolver la lista de productos.");
        productList.sort(Comparator.comparingDouble(Product::getPrice).reversed()); //ascendentemente
        logger.info("Finalizando getProductsByPrice. Lista ordenada y lista para devolver.");
        return productList;
    }

    @Override
    public List<Product> getProductsBySales() {
        logger.info("Iniciando getProductsBySales. Se va a ordenar descendentemente y devolver la lista de productos.");
        productList.sort(Comparator.comparingInt(Product::getSales).reversed()); //descendentemente
        logger.info("Finalizando getProductsBySales. Lista ordenada y lista para devolver.");
        return productList;
    }

    @Override
    public List<Order> getOrdersByUser(String idUser){
        if(idUser==null){
            logger.error("No se ha dado un idUser");
            return null;
        }else{
            logger.info("Iniciando getOrdersByUser. Con un id:"+" "+idUser);
            User user=this.getUser(idUser);
            if (user==null){
                logger.error("No se ha encontrado al usuario con id:"+" "+idUser);
                return null;
            }else{
                List<Order> historial=user.orders();
                logger.info("Finalizando getOrdersByUser."+" "+historial);
                return historial;
            }
        }
    }


    @Override
    public void addOrder(Order order) {
        logger.info("Iniciando addOrder. Se ha recibido el pedido: " + order);
        orderQueue.add(order);
        logger.info("Finalizando addOrder. Pedido añadido a la cola correctamente.");
    }

    @Override
    public int numOrders() {
        logger.info("Iniciando numOrders.");
        int cantidad = orderQueue.size();
        logger.info("Finalizando numOrders. La cantidad de pedidos es" + " " + cantidad);
        return cantidad;
    }

    @Override
    public int numUsers() {
        logger.info("Iniciando numUsers.");
        int user= users.size();
        logger.info("Finalizando numUsers. La cantidad de usurarios es"+" "+user);
        return user;
    }

    @Override
    public Order deliverOrder() {
        logger.info("Iniciando deliverOrder");
        Order order = orderQueue.poll(); //.poll() -> es para eliminar el primer elemento de una cola
        if(order==null){
            logger.error("La cola de pedidos está vacía. No hay pedidos para servir.");
        }else {
            User comprador =this.getUser(order.getDni());
            if(comprador!=null){
                order.setUser(comprador);
                comprador.orders().add(order);
                for (LP lp: order.getLps()){
                    Product producto=this.getProduct(lp.getP());
                    if (producto!=null){
                        producto.setSales(producto.getSales()+lp.getQ());
                    }
                }
            }
            logger.info("Finalizando deliverOrder."+ " "+ order);
        }
        return order;
    }

    public Product getProduct(String id) {

        if(id==null){
            logger.error("Error: No se ha introducido ningún id");
            return null; //si no lo freno aquí, Java intentará seguir buscando un id
        }else{
            logger.info("Iniciando getProduct. Buscando ID:"+" "+id);
            /*
            for (Product product : productsList) {
             if (product.getName().equals(name)) {
                return product;
              }
             }
            return null;
            */
            //.stream() forma moderna de hacer un bucle for
            Product p= productList.stream()
                    .filter(product -> product.getId().equals(id))
                    .findFirst()
                    .orElse(null);
            if(p==null){
                logger.error("No se ha encontrado ningun producto con este id:"+" "+id);
                return null;
            }else {
                logger.info("Se ha encontrado el producto"+" "+p +""+"con el id"+""+id);
                return p;
            }
        }
    }

    @Override
    public User getUser(String number) {
        if(number==null){
            logger.error("No se ha podido inicializar getUser");
            return null;
        }else{
            logger.info("Se ha inicializado correctamente getUser con numero"+" "+number);
            User u=users.get(number);
            if(u==null){
                logger.error("No se ha encontrado ningun usuario con este numero"+" "+number);
                return null;
            }else{
                logger.info("Se ha encontrado el usuario"+" "+u+" "+"con numero"+" "+number);
                return u;
            }
        }
    }

    @Override
    public int numProducts() {
        logger.info("Iniciando numProducts");
        int cantidad = productList.size();
        logger.info("Finalizando numProducts. La cantidad de producto en el sistema es:"+" "+cantidad);
        return cantidad;

    }

    @Override
    public void addUser(String nif, String name, String surname, String mail) {
        if(nif==null || name==null || surname ==null || mail==null){
            logger.error("No se puede iniciar addUser");
            return;
        }else{
            logger.info("Se ha iniciado correctamente addUser con el siguiente nif:"+" "+nif+" "+",nombre:"+" "+name+" "+",surname:"+" "+surname+" "+"y mail:"+" "+mail);
            User u= new User(nif,name,surname,mail);
            users.put(nif,u);
            logger.info("Se ha finalizado correctamente el addUser");
        }
    }

    @Override
    public void clear(){
        this.productList.clear();
        this.orderQueue.clear();
        this.users.clear();
    }
}
