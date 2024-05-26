package kdg.be.DTO;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.ElementCollection;
import kdg.be.Deserializers.OrderFromClientDeserializer;
import kdg.be.Enums.OrderState;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonDeserialize(using = OrderFromClientDeserializer.class)
public class OrdersFromClientDTO {
    private OrderState orderState;
    private Map<Long, Integer> products = new HashMap<>();
    @ElementCollection
    private List<String> Remarks = new ArrayList<>();
    // Constructors
    public OrdersFromClientDTO(LocalDate orderDate, Map<Long, Integer> producten, OrderState orderState) {
        products = producten;
        this.orderState = orderState;
    }

    public OrdersFromClientDTO(Map<Long, Integer> producten) {
        products = producten;
    }

    public OrdersFromClientDTO() {
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public Map<Long, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, Integer> producs) {
        this.products = producs;
    }

    public List<String> getRemarks() {
        return Remarks;
    }

    public void setRemarks(List<String> remarks) {
        Remarks = remarks;
    }

}
