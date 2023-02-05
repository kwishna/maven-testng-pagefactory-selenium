package org.example.utils;

import com.google.common.io.Files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utilities {
    private String filePath;
    private FileWriter fwr = null;

    public static void saveScreenshot(File screenshot) throws IOException {
        File sink = new File(System.getProperty("user.dir") + "/Resources/screenshot/" + String.join("_", LocalDateTime.now().toString().split("[^A-Za-z0-9]")) + ".PNG");
        if (!sink.exists()) {
            sink.createNewFile();
        }
        Files.copy(screenshot, sink);
    }

    public static String timeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_hh_mm_ss_SSS"));
    }

    public void saveIntoFile(String fileName, String data) throws IOException {

        filePath = System.getProperty("user.dir") + "/outputs/";
        if (!fileName.endsWith(".txt")) {
            fileName = fileName + ".txt";
        }
        fwr = new FileWriter(filePath + fileName, true);
        fwr.write(data + "\n");
        fwr.flush();
        fwr.close();
    }
}
