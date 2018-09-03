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
        
        JsonNode node = readFromFileAsJsonNode("/normal_json.json");
        
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator();
        JsonNode output = MAPPER.readValue(jsonSchemaGenerator.transformJsonToSchema(node), JsonNode.class);
        
        assertEquals("Integer", output.get("id").textValue());
        assertEquals("String", output.get("village").textValue());
        
    }
    
    @Test
    public void testComplexTransformWithArray() throws IOException {
        JsonNode node = readFromFileAsJsonNode("/complex_data.json");
        
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator();
        
        JsonNode output = MAPPER.readValue(jsonSchemaGenerator.transformJsonToSchema(node), JsonNode.class);
        
        assertEquals("String", output.at("/payload/items/0/product/code").textValue());
        assertEquals("Integer", output.at("/payload/items/0/id").textValue());
    }
    
    @Test
    public void testTopLevelArray() throws IOException {
        JsonNode node = readFromFileAsJsonNode("/array_parent.json");
        
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator();
        
        JsonNode output = MAPPER.readValue(jsonSchemaGenerator.transformJsonToSchema(node), JsonNode.class);
        
        assertEquals("String", output.at("/0/product/code").textValue());
        assertEquals("Integer", output.at("/0/id").textValue());
    }
    
    private JsonNode readFromFileAsJsonNode(String path) throws IOException {
        return MAPPER.readValue(IOUtils.toString(JsonSchemaGenerator.class.getResourceAsStream(path),
                Charset.defaultCharset()), JsonNode.class);
    }
}