package com.esbnetworks.automation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class JsonDataReader {

    private static JsonNode rootNode;

    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            rootNode = mapper.readTree(
                    new File("src/test/resources/testdata/renewable_form_data.json"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON test data", e);
        }
    }

    public static JsonNode getNode(String key) {
        return rootNode.get(key);
    }
}
