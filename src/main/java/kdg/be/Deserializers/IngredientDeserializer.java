package kdg.be.Deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import kdg.be.Models.Ingredient;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;

import java.io.IOException;

public class IngredientDeserializer extends JsonDeserializer<Ingredient> {


    @Override
    public Ingredient deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        System.out.println(ctxt);
        return null;
    }
}
