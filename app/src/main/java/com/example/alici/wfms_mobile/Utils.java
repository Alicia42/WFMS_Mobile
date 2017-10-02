package com.example.alici.wfms_mobile;

/**
 * Created by libbyjennings on 2/10/17.
 */

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Map;
import android.net.Uri;
import java.util.HashMap;
import java.util.Iterator;
import java.net.URLConnection;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Utils {

    public static String buildPostParameters(Object content) {
        String output = null;
        if ((content instanceof String) ||
                (content instanceof JSONObject) ||
                (content instanceof JSONArray)) {
            output = content.toString();
        } else if (content instanceof Map) {
            Uri.Builder builder = new Uri.Builder();
            HashMap hashMap = (HashMap) content;
            if (hashMap != null) {
                Iterator entries = hashMap.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry entry = (Map.Entry) entries.next();
                    builder.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                    entries.remove(); // avoids a ConcurrentModificationException
                }
                output = builder.build().getEncodedQuery();
            }
        }

        return output;
    }

    public static URLConnection makeRequest(String method, String apiAddress, String accessToken, String mimeType, String requestBody) throws IOException {

            URL url = new URL(apiAddress);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(!method.equals("GET"));

                    urlConnection.setRequestMethod(method);

                    urlConnection.setRequestProperty("Authorization", "Bearer " + accessToken);

                    urlConnection.setRequestProperty("Content-Type", mimeType);
                    OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
                    writer.write(requestBody);
                    writer.flush();
                    writer.close();
                    outputStream.close();

                    urlConnection.connect();

        return urlConnection;
    }
}
