package edu.knoldus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileReader {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    public JsonNode readJsonFromFile(String resourcePath) {
        
        try {
            String content = IOUtils.toString(this.getClass().getResourceAsStream(resourcePath), UTF_8);
            return MAPPER.readValue(content, JsonNode.class);
            
        } catch (IOException e) {
            throw new FileReadException("failed to read file/ create json", e);
        }
    }
}
