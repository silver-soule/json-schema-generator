package edu.knoldus;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonSchemaGenrator {
    
    public String transformJsonToSchema(JsonNode json) {
        return transformJson(json).toString();
    }
    
    public StringBuilder transformJson(JsonNode json) {
        for (JsonNode node : json) {
            if (node.isValueNode()) {
                transformJson(node);
            } else {
                String dataType = findDataType(node);
                String nodeKey = node.fieldNames().next();
                node.
            }
        }
    }
    
    private String buildJsonKeyValue(String key, String value){
        return String.format("\"%s\" : \"%s\"")
    }
    
    private String findDataType(JsonNode jsonNode) {
        if (jsonNode.isInt()) {
            return "Integer";
        } else if (jsonNode.isBoolean()) {
            return "Boolean";
        } else {
            return "String";
        }
    }
}
