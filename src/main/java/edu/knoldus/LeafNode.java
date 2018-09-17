package edu.knoldus;

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
}
