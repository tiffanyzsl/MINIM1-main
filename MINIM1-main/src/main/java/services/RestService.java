package services;

import manager.ProductManager;
import manager.ProductManagerImpl;
import models.Order;
import models.Product;
import models.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "Sistema de Gestión", description = "Servicios REST para gestionar la tienda")
@Path("/") // Ruta base para esta clase
public class RestService {
    // Evita crear una tienda vacía y recupera los datos que ya existen
    private ProductManager pm = ProductManagerImpl.getInstance();

    @POST
    @Path("/users")
    @ApiOperation(value = "Añadir un nuevo usuario")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        pm.addUser(user.getNif(), user.getName(), user.getSurname(), user.getMail());
        return Response.status(201).build();
    }

    @POST
    @Path("/products")
    @ApiOperation(value = "Añadir un nuevo producto")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) {
        pm.addProduct(product.getId(), product.getName(), product.getPrice());
        return Response.status(201).build();
    }

    @POST
    @Path("/orders")
    @ApiOperation(value = "Realizar un pedido")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOrder(Order order) {
        pm.addOrder(order);
        return Response.status(201).build();
    }

    @GET
    @Path("/products/price")
    @ApiOperation(value = "Obtener productos ordenados por precio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByPrice() {
        List<Product> lista = pm.getProductsByPrice();
        return Response.status(200).entity(lista).build();
    }

    @GET
    @Path("/products/sales")
    @ApiOperation(value = "Obtener productos ordenados por ventas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsBySales() {
        List<Product> lista = pm.getProductsBySales();
        return Response.status(200).entity(lista).build();
    }

    @POST
    @Path("/orders/deliver")
    @ApiOperation(value = "Entregar el primer pedido de la cola")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deliverOrder(){
        Order pedidoEntregado = pm.deliverOrder();
        return Response.status(200).entity(pedidoEntregado).build();
    }

    @GET
    @Path("/products/{id}")
    @ApiOperation(value = "Obtener un producto por su ID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") String id) {
        Product producto = pm.getProduct(id);
        return Response.status(200).entity(producto).build();
    }

    @GET
    @Path("/users/{number}")
    @ApiOperation(value = "Obtener un usuario por su número/ID")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("number") String number) {
        User user = pm.getUser(number);
        return Response.status(200).entity(user).build();
    }

    @GET
    @Path("/orders/{idUser}")
    @ApiOperation(value = "Obtener historial de pedidos de un usuario")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersByUser(@PathParam("idUser") String idUser) {
        List<Order> historial=pm.getOrdersByUser(idUser);
        return Response.status(200).entity(historial).build();
    }
}