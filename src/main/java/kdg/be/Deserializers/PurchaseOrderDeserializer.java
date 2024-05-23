package kdg.be.Services;

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
import java.util.*;

public class PurchaseOrderDeserializer extends JsonDeserializer<PurchaseOrder> {
    @Override
    public PurchaseOrder deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);

     String items=   node.get("items").asText();
        TypeReference<HashMap<String, Integer>> typeRef
                = new TypeReference<>() {};


     System.out.println(items);
        ObjectMapper mapper=new ObjectMapper();

     PurchaseOrder purchaseOrder=new PurchaseOrder();
        List<PurchaseProduct>purchaseProducts=new ArrayList<>();

        HashMap<String,Integer> p=    mapper.readValue(items,typeRef);


        p.forEach((k,v)->{

            try {
         PurchaseProduct purchaseProduct=       mapper.readValue(k,PurchaseProduct.class);
         purchaseProduct.setQuantity(v);
         purchaseProducts.add(purchaseProduct);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }


        });





        try {
            String orderDate=  node.get("orderDate").asText();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE MMM d H:mm:ss zzz yyyy", Locale.ENGLISH);
            LocalDate localDate= LocalDate.parse(simpleDateFormat.format(orderDate));
            purchaseOrder.setOrderdate(localDate);
        }catch (Exception ex){
            System.out.println(ex.getMessage());

            System.out.println("niet dus");
        }


        Long purchaseOrdeNumber= node.get("purchaseOrderNumber").asLong();
        purchaseOrder.setPurchaseOrderNumber(purchaseOrdeNumber);
purchaseOrder.setProducts(purchaseProducts);
                return purchaseOrder;




    }
}
