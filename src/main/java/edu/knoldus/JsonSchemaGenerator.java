package edu.knoldus;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class JsonSchemaGenerator {
    
    public String transformJsonToSchema(JsonNode json) {
        return transformJson(json).toString();
    }
    
    public StringBuilder transformJson(JsonNode json,
                                       Datatype parentDataType) {
        
        List<String> tokenizedKeyValues = new ArrayList<>();
        
        for (JsonNode node : json) {
            if (!node.isValueNode()) {
                Datatype datatype = findDataTypeForContainerNode(node);
                StringBuilder pojo = transformJson(node, datatype);
                tokenizedKeyValues.add(pojo.toString());
                
            } else {
                
                Datatype dataType = findDataTypeForLeafNode(node);
                String nodeKey = node.fieldNames().next();
                tokenizedKeyValues.add(buildJsonKeyValue(nodeKey, dataType.toString()));
                
            }
        }
        
        StringBuilder op = new StringBuilder();
        
        if (tokenizedKeyValues.size() > 0) {
            op.append(tokenizedKeyValues.get(0));
        }
        
        for (int i = 1; i < tokenizedKeyValues.size(); i++) {
            op.append( "," + tokenizedKeyValues.get(i));
        }
        
        return op;
    }
    
    private Datatype findDataTypeForContainerNode(JsonNode node) {
        
        if (node.isArray()) {
            return Datatype.ARRAY;
        } else {
            return Datatype.OBJECT;
        }
    }
    
    private String buildJsonKeyValue(String key, String value) {
        
        return String.format("\"%s\" : \"%s\"", key, value);
    }
    
    private Datatype findDataTypeForLeafNode(JsonNode jsonNode) {
        if (jsonNode.isInt()) {
            return Datatype.INTEGER;
        } else if (jsonNode.isBoolean()) {
            return Datatype.BOOLEAN;
        } else {
            return Datatype.STRING;
        }
    }
    
    private enum Datatype {
        
        ARRAY("array"),
        OBJECT("object"),
        INTEGER("Integer"),
        STRING("String"),
        BOOLEAN("Boolean");
        
        private String type;
        
        Datatype(String type) {
            this.type = type;
        }
        
        
        @Override
        public String toString() {
            return type;
        }
    }
}
