package edu.knoldus;

public enum DataType {
    
    ARRAY("array"),
    OBJECT("object"),
    INTEGER("Integer"),
    STRING("String"),
    BOOLEAN("Boolean");
    
    private String type;
    
    DataType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return type;
    }
}
