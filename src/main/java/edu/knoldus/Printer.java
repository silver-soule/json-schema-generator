package edu.knoldus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Printer {
    
    private final FileReader fileReader;
    
    public Printer(FileReader fileReader) {
        this.fileReader = fileReader;
    }
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter a valid filepath : ");
        String inputFilePath = "/" + reader.readLine();
        
        Printer printer = new Printer(new FileReader());
        
        JsonNode jsonNode = printer.fileReader.readJsonFromFile(inputFilePath);
        JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator();
        
        String schemaJson = jsonSchemaGenerator.transformJsonToSchema(jsonNode);
        
        FileWriter fileWriter = new FileWriter();
        fileWriter.writeToFile(schemaJson, "./src/main/resources/output/op.json");
    }
}
