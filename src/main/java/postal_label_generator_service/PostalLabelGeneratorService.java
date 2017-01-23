package postal_label_generator_service;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * @author      László Milu hockeylaci@gmail.com
 * @version     1.0
 */
public class PostalLabelGeneratorService {

    /**
     * <p>In the main method we create a default Spark webserver with a post request on route "/"
     * and we call the getPostalData() method
     * @see PostalLabelServiceController
     * @param args
     */
    public static void main(String[] args) {
        post("/", PostalLabelServiceController.getInstance()::getPostalData);
        enableDebugScreen();
    }
}
