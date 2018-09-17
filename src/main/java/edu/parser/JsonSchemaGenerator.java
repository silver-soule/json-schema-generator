package edu.parser;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonSchemaGenerator {
    
    public static final String EMPTY_STRING = "";
    
    public String transformJsonToSchema(JsonNode json) {
        if (NonLeafNode.fromJsonNode(json).equals(NonLeafNode.OBJECT)) {
            return transformJson(json, NonLeafNode.OBJECT, EMPTY_STRING).toString();
        } else {
            return transformJson(json, NonLeafNode.ARRAY, EMPTY_STRING).toString();
        }
    }
    
    public StringBuilder transformJson(JsonNode json,
                                       NonLeafNode parentLeafNode,
                                       String parentNodeName) {
        
        List<String> tokenizedKeyValues = new ArrayList<>();
        Iterator<String> keys = json.fieldNames();
        
        if (parentLeafNode.equals(NonLeafNode.ARRAY)) {
            for (JsonNode node : json) {
                if (!node.isValueNode()) {
                    NonLeafNode datatype = NonLeafNode.fromJsonNode(node);
                    StringBuilder pojo = transformJson(node, datatype, EMPTY_STRING);
                    tokenizedKeyValues.add(pojo.toString());
                    
                } else {
                    
                    LeafNode leafNode = LeafNode.fromJsonNode(node);
                    tokenizedKeyValues.add(buildJsonKeyValue(EMPTY_STRING, leafNode.toString()));
                    
                }
            }
        } else {
            for (; keys.hasNext(); ) {
                
                String curr = keys.next();
                JsonNode node = json.get(curr);
                
                if (!node.isValueNode()) {
                    NonLeafNode datatype = NonLeafNode.fromJsonNode(node);
                    StringBuilder pojo = transformJson(node, datatype, curr);
                    tokenizedKeyValues.add(pojo.toString());
                    
                } else {
                    
                    LeafNode leafNode = LeafNode.fromJsonNode(node);
                    tokenizedKeyValues.add(buildJsonKeyValue(curr, leafNode.toString()));
                    
                }
            }
        }
        
        StringBuilder op = new StringBuilder();
        
        if (tokenizedKeyValues.size() > 0) {
            op.append(tokenizedKeyValues.get(0));
        }
        
        for (int i = 1; i < tokenizedKeyValues.size(); i++) {
            op.append("," + tokenizedKeyValues.get(i));
        }
        
        addParentData(parentLeafNode, parentNodeName, op);
        
        return op;
    }
    
    private void addParentData(NonLeafNode parentLeafNode, String parentNodeName, StringBuilder op) {
        if (parentLeafNode.equals(NonLeafNode.ARRAY)) {
    
            wrapWithParentElement(parentNodeName, op, "[", "]");
        } else {
    
            wrapWithParentElement(parentNodeName, op, "{", "}");
        }
    }
    
    private void wrapWithParentElement(String parentNodeName,
                                       StringBuilder schemaString,
                                       String openingOperator,
                                       String closingOperator) {
        
        schemaString.append(closingOperator);
        schemaString.insert(0, openingOperator);
        if (!parentNodeName.equals(EMPTY_STRING)) {
            schemaString.insert(0, String.format("\"%s\" : ", parentNodeName));
        }
    }
    
    private String buildJsonKeyValue(String key, String value) {
        
        return String.format("\"%s\" : \"%s\"", key, value);
    }
    
}
