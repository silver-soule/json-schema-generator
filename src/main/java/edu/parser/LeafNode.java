package edu.parser;

import com.fasterxml.jackson.databind.JsonNode;

public enum LeafNode {
    
    INTEGER("<Integer>"),
    STRING("<String>"),
    BOOLEAN("<Boolean>");
    
    private String type;
    
    LeafNode(String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return type;
    }
    
    public static LeafNode fromJsonNode(JsonNode jsonNode) {
        if (jsonNode.isInt()) {
            return LeafNode.INTEGER;
        } else if (jsonNode.isBoolean()) {
            return LeafNode.BOOLEAN;
        } else {
            return LeafNode.STRING;
        }
    }
}
