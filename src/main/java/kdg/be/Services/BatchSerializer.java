package kdg.be.Services;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import kdg.be.DTO.BatchForWarehouse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BatchSerializer extends StdSerializer<BatchForWarehouse> {
    protected BatchSerializer(Class<BatchForWarehouse> t) {
        super(t);
    }
    protected BatchSerializer() {
        super((Class<BatchForWarehouse>) null);
    }
    protected BatchSerializer(JavaType type) {
        super(type);
    }

    protected BatchSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected BatchSerializer(StdSerializer<?> src) {
        super(src);
    }

    @Override
    public void serialize(BatchForWarehouse batch, JsonGenerator gen, SerializerProvider provider) throws IOException {


        gen.writeStartObject();
        gen.writeNumberField("id", batch.getBatchId());
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      try {
          System.out.println(batch.getBatchDate().toString());
          gen.writeStringField("batchDate", String.valueOf(batch.getBatchDate()));
      }catch(Exception ex){
          gen.writeStringField("batchDate","");
       //   gen.writeStringField("batchDate", dateFormat.format(LocalDate.now().toString()));
System.out.println("eror");
      }
        List<String>ing=new ArrayList<>();
        List<Double> amounts=new ArrayList<>();
        ObjectMapper objectMapper=new ObjectMapper();
        batch.getIngredients().forEach(ingre->{
            String jsoningredient;
            try {
                jsoningredient =    objectMapper.writeValueAsString(ingre);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
ing.add(jsoningredient);
          ;
        });
        batch.getAmounts().forEach(amounts::add);

        gen.writeStringField("ingredients",ing.toString());;
        gen.writeStringField("amount",amounts.toString());;

        gen.writeEndObject();
System.out.println("test");
    }
}
