package kdg.be.Services;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kdg.be.DTO.OrdersFromClientDTO;

import java.io.IOException;
import java.util.HashMap;

public class OrderFromClientDeserializer extends JsonDeserializer<OrdersFromClientDTO> {

    @Override
    public OrdersFromClientDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);

        TypeReference<HashMap<Long, Integer>> typeRef
                = new TypeReference<>() {};
String pr=node.get("products").asText();
        ObjectMapper objectMapper=new ObjectMapper();
        HashMap<Long, Integer> map = objectMapper.readValue(pr, typeRef);

        return  new OrdersFromClientDTO(map);

    }
}
