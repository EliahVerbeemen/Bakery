package kdg.be.Deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kdg.be.Models.PurchaseOrder.PurchaseOrder;
import kdg.be.Models.PurchaseOrder.PurchaseProduct;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PurchaseOrderDeserializer extends JsonDeserializer<PurchaseOrder> {
    @Override
    public PurchaseOrder deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String items = node.get("items").asText();
        TypeReference<HashMap<String, Integer>> typeRef
                = new TypeReference<>() {
        };

        ObjectMapper mapper = new ObjectMapper();

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        List<PurchaseProduct> purchaseProducts = new ArrayList<>();

        HashMap<String, Integer> p = mapper.readValue(items, typeRef);

        p.forEach((k, v) -> {
            try {
                PurchaseProduct purchaseProduct = mapper.readValue(k, PurchaseProduct.class);
                purchaseProduct.setQuantity(v);
                purchaseProducts.add(purchaseProduct);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });



        Long purchaseOrdeNumber = node.get("purchaseOrderNumber").asLong();
        purchaseOrder.setPurchaseOrderNumber(purchaseOrdeNumber);
        purchaseOrder.setProducts(purchaseProducts);
        return purchaseOrder;
    }
}
