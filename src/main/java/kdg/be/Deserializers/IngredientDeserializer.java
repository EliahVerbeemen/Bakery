package kdg.be.Deserializers;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import kdg.be.Models.Ingredient;

import java.io.IOException;

public class IngredientDeserializer extends KeyDeserializer {
    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
        return new Ingredient("test", "beschrijving");
    }
}
