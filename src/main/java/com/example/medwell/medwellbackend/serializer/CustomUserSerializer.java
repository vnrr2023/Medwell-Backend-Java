package  com.example.medwell.medwellbackend.serializer;

import com.example.medwell.medwellbackend.entity.CustomUser;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


import java.io.IOException;

public class CustomUserSerializer extends JsonSerializer<CustomUser>{

    @Override
    public void serialize(CustomUser customUser, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", String.valueOf(customUser.getId()));
        jsonGenerator.writeStringField("name", String.valueOf(customUser.getFirstName()+" "+customUser.getLastName()));
        jsonGenerator.writeStringField("email", String.valueOf(customUser.getEmail()));
        jsonGenerator.writeEndObject();
    }
}
