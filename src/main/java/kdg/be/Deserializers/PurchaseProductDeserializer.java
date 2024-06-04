package kdg.be.Deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import kdg.be.Models.PurchaseOrder.PurchaseProduct;

import java.io.IOException;

public class PurchaseProductDeserializer extends JsonDeserializer<PurchaseProduct> {
    @Override
    public PurchaseProduct deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String productNumber = node.get("productNumber").asText();
        String productName = node.get("productName").asText();
        String specialInstruction = node.get("specialInstruction").asText();
        PurchaseProduct purchaseProduct = new PurchaseProduct();
        purchaseProduct.setName(productName);
        purchaseProduct.setProductNumber(productNumber);
        purchaseProduct.setSpecialInstruction(specialInstruction);
        return purchaseProduct;
    }
}
