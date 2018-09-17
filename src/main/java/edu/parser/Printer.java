package edu.parser;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Printer {
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter a valid file path : ");
        String inputFilePath = "/" + reader.readLine();
        
        FileReader fileReader = new FileReader();
        JsonNode jsonNode = fileReader.readAsJsonNodeFromFile(inputFilePath);
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator();
        
        String schemaJson = jsonSchemaGenerator.transformJsonToSchema(jsonNode);
        
        FileWriter fileWriter = new FileWriter();
        fileWriter.writeToFile(schemaJson, "./src/main/resources/output/op.json");
    }
}
