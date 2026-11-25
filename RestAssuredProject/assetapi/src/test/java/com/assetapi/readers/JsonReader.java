package com.assetapi.readers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JsonReader {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static Map<String, Object> readJsonFile(String filepath){
        try (InputStream input = JsonReader.class.getClassLoader()
                .getResourceAsStream(filepath)){
            if (input == null){
                throw new IllegalArgumentException(
                        "JSON file not found: " + filepath
                );
            }

            return mapper.readValue(
                    input,
                    new TypeReference<Map<String, Object>>(){}
            );
        }

        catch (IOException e){
            throw new IllegalArgumentException(
                    "Unable to read JSON file:" + filepath, e
            );
        }
    }
}
