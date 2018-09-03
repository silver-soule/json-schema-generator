package edu.knoldus;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonSchemaGenerator {
    
    public String transformJsonToSchema(JsonNode json) {
        return transformJson(json, DataType.OBJECT, "").toString();
    }
    
    public StringBuilder transformJson(JsonNode json,
                                       DataType parentDataType,
                                       String parentNodeName) {
        
        List<String> tokenizedKeyValues = new ArrayList<>();
        Iterator<String> keys = json.fieldNames();
        
        if (parentDataType.equals(DataType.ARRAY)) {
            for (JsonNode node : json) {
                if (!node.isValueNode()) {
                    DataType datatype = findDataTypeForContainerNode(node);
                    StringBuilder pojo = transformJson(node, datatype, "");
                    tokenizedKeyValues.add(pojo.toString());
                    
                } else {
                    
                    DataType dataType = findDataTypeForLeafNode(node);
                    tokenizedKeyValues.add(buildJsonKeyValue("", dataType.toString()));
                    
                }
            }
        } else {
            for (String curr = ""; keys.hasNext(); ) {
                
                curr = keys.next();
                JsonNode node = json.get(curr);
                
                if (!node.isValueNode()) {
                    DataType datatype = findDataTypeForContainerNode(node);
                    StringBuilder pojo = transformJson(node, datatype, curr);
                    tokenizedKeyValues.add(pojo.toString());
                    
                } else {
                    
                    DataType dataType = findDataTypeForLeafNode(node);
                    tokenizedKeyValues.add(buildJsonKeyValue(curr, dataType.toString()));
                    
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
        
        addParentData(parentDataType, parentNodeName, op);
        
        return op;
    }
    
    private void addParentData(DataType parentDataType, String parentNodeName, StringBuilder op) {
        if (parentDataType.equals(DataType.ARRAY)) {
            
            op.append("]");
            op.insert(0, "[");
            if (!parentNodeName.equals("")) {
                op.insert(0, String.format("\"%s\" : ", parentNodeName));
            }
        } else {
            
            op.append("}");
            op.insert(0, "{");
            if (!parentNodeName.equals("")) {
                op.insert(0, String.format("\"%s\" : ", parentNodeName));
            }
        }
    }
    
    private DataType findDataTypeForContainerNode(JsonNode node) {
        
        if (node.isArray()) {
            return DataType.ARRAY;
        } else {
            return DataType.OBJECT;
        }
    }
    
    private String buildJsonKeyValue(String key, String value) {
        
        return String.format("\"%s\" : \"%s\"", key, value);
    }
    
    private DataType findDataTypeForLeafNode(JsonNode jsonNode) {
        if (jsonNode.isInt()) {
            return DataType.INTEGER;
        } else if (jsonNode.isBoolean()) {
            return DataType.BOOLEAN;
        } else {
            return DataType.STRING;
        }
    }
    
}
