package com.coldlight.restapicrudapp.util;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class RequestParserUtils {
    public static JSONObject parseHttpRequest(HttpServletRequest req) throws IOException {
        StringBuilder jb = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
            removeBlankSpace(jb);
        } catch (Exception e) {
            System.out.println("Error");
        }
        return (JSONObject) JSONValue.parse(jb.toString());
    }

    private static void removeBlankSpace(StringBuilder sb) {
        int j = 0;
        for (int i = 0; i < sb.length(); i++) {
            if (!Character.isWhitespace(sb.charAt(i))) {
                sb.setCharAt(j++, sb.charAt(i));
            }
        }
        sb.delete(j, sb.length());
    }
}
