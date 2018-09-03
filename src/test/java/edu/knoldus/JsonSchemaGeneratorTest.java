package edu.knoldus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;


public class JsonSchemaGeneratorTest {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Test
    public void testSimpleTransform() throws IOException {
        
        JsonNode node = MAPPER.readValue(IOUtils.toString(JsonSchemaGenerator.class.getResourceAsStream("/normal_json.json"),
                Charset.defaultCharset()), JsonNode.class);
        
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator();
        JsonNode output = MAPPER.readValue(jsonSchemaGenerator.transformJsonToSchema(node), JsonNode.class);
        
        assertEquals("Integer", output.get("id").textValue());
        assertEquals("String", output.get("village").textValue());
        
    }
    
    @Test
    public void testComplexTransformWithArray() throws IOException {
        JsonNode node = MAPPER.readValue(IOUtils.toString(JsonSchemaGenerator.class.getResourceAsStream("/complex_data.json"),
                Charset.defaultCharset()), JsonNode.class);
        
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator();
        
        JsonNode output = MAPPER.readValue(jsonSchemaGenerator.transformJsonToSchema(node), JsonNode.class);
        
        assertEquals("String", output.at("/payload/items/0/product/code").textValue());
        assertEquals("Integer", output.at("/payload/items/0/id").textValue());
    }
}