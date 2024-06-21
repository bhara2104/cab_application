package com.application.cab_application.Util;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadJson {
    public static String convertJsonToString(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine())!=null){
            stringBuilder.append(line);
        }
        reader.close();
        return stringBuilder.toString();
    }
}
