package kdg.be.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class OrdersFromClientDTO {

    public OrdersFromClientDTO(Map<String, Integer> products) {
        this.products = products;
    }

    public OrdersFromClientDTO(){

    }

    @JsonProperty("producs")
    private Map<String,Integer> products=new HashMap<>();

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }
}
