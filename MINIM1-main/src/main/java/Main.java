import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import java.io.IOException;
import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    public static HttpServer startServer() {
        io.swagger.jaxrs.config.BeanConfig beanConfig = new io.swagger.jaxrs.config.BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/myapp");
        beanConfig.setResourcePackage("services"); // Aquí es donde buscará tus clases REST
        beanConfig.setScan(true);

        final ResourceConfig rc = new ResourceConfig().packages("services");
        rc.register(io.swagger.jaxrs.listing.ApiListingResource.class);
        rc.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();

        java.io.File carpetaPublic = new java.io.File("public");
        if (!carpetaPublic.exists()) {
            carpetaPublic = new java.io.File("MINIM1-main/public");
        }

        StaticHttpHandler staticHttpHandler = new StaticHttpHandler(carpetaPublic.getAbsolutePath());
        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/");

        System.out.println("Servidor encendido en: " + BASE_URI);
        System.out.println("Abre Swagger en tu navegador: http://localhost:8080/swagger/");
        System.out.println("Presiona ENTER para apagarlo...");
        System.in.read();
        server.shutdownNow();
    }
}