package edu.parser;

import com.fasterxml.jackson.databind.JsonNode;

public enum NonLeafNode {
    ARRAY,
    OBJECT;
    
    public static NonLeafNode fromJsonNode(JsonNode node) {
        if (node.isArray()) {
            return NonLeafNode.ARRAY;
        }
        
        return NonLeafNode.OBJECT;
        
    }
}
