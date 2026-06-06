package miu.rahul.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtility {

    private final ObjectMapper objectMapper;

    public JsonUtility() {
        this.objectMapper = new ObjectMapper();
    }

    public String toPrettyJson(Object data) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Unable to convert data to JSON.", e);
        }
    }
}
