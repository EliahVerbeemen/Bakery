package kdg.be.Deserializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kdg.be.Models.Product;
import org.springframework.web.bind.annotation.RestController;

//@Service
@RestController
public class JsonSerializer {
    public String productToJson(Product product) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProduct = objectMapper.writeValueAsString(product);
        System.out.println(jsonProduct);
        return jsonProduct;

    }


}
