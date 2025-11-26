package com.jeopardy;

public class FileTypeSelector {

    public Loader selectLoader(String filePath) {

        if (filePath == null || filePath.isBlank()) {
            return null;
        }

        filePath = filePath.toLowerCase().trim();

        if (filePath.endsWith(".csv")) {
            return new CSVLoader();
        }
        else if (filePath.endsWith(".json")) {
            return new JSONLoader();
        }
        else if (filePath.endsWith(".xml")) {
            return new XMLLoader();
        }

        return null; // unsupported
    }
}

