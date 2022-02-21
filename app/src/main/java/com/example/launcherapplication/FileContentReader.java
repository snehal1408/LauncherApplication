package com.example.launcherapplication;

import android.content.Context;
import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class FileContentReader {
    private Context appContext;

    public FileContentReader(Context context) {
        this.appContext = context;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public String getContentFromUrl(String url) {
        StringBuilder content = new StringBuilder();
        try {
            URL u = new URL(url);
            HttpURLConnection uc = (HttpURLConnection) u.openConnection();
            if (uc.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream is = uc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String line;
                while ((line = br.readLine()) != null) {

                    content.append(line).append("\n");

                }

            } else {

                throw new IOException(uc.getResponseMessage());
            }
        } catch (StackOverflowError | Exception s) {
            s.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}

