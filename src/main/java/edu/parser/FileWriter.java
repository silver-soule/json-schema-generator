package edu.parser;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileWriter {
    
    public void writeToFile(String data, String filePath) {
        try {
            File file = new File(filePath);
            FileUtils.writeStringToFile(file, data, UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
