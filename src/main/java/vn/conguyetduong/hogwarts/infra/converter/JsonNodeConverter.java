package vn.conguyetduong.hogwarts.infra.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

//@Converter(autoApply = true)
public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JsonNode attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            String result = objectMapper.writeValueAsString(attribute);
            return result;
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JsonNode to String", e);
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return objectMapper.readTree(dbData);  // Deserialize the JSON string back to JsonNode
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting String to JsonNode", e);
        }
    }
}
