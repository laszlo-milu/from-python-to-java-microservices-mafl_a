package postal_label_generator_service;


import org.json.JSONException;
import org.json.JSONObject;
import spark.Response;
import spark.Request;
import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static postal_label_generator_service.PdfCreator.convertPostalDataToPdf;

/**
 * @author      László Milu hockeylaci@gmail.com
 * @version     1.0
 */
public class PostalLabelServiceController {
    private static PostalLabelServiceController INSTANCE;

    public static PostalLabelServiceController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostalLabelServiceController();
        }
        return INSTANCE;
    }

    private PostalLabelServiceController() {}

    /**
     * This method gets the data from request body, calls convertPostalDataToPdf
     * @see PdfCreator
     * which creates a pdf from it, and sends it in HTTP response as byte[]
     * @param request HTTP request from a user of our API (contains the postal data in request body)
     * @param response HTTP response contains the pdf in byte[] format
     * @return it returns the standard response for successful HTTP requests (200)
     * @throws JSONException
     * @throws IOException
     */
    public int getPostalData(Request request, Response response) throws JSONException, IOException {
        JSONObject json = new JSONObject(request.body());
        List<String> postalData = new ArrayList<>();
        postalData.add(json.getString("name"));
        postalData.add(json.getString("country"));
        postalData.add(json.getString("city"));
        postalData.add(json.getString("address"));
        postalData.add(json.getString("zipcode"));

        String fileName = convertPostalDataToPdf(postalData);
        response.header("Content-Type", "application/pdf");
        response.header("Content-Disposition", "attachment;filename="+ fileName);
        ServletOutputStream outputStream = response.raw().getOutputStream();
        outputStream.write(PdfCreator.convertToBytes(fileName));
        outputStream.flush();
        outputStream.close();
        return 200;
    }

}
