package com.example.magentdev;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class PrivateData {
    private String quickpin;
    private String sessionToken;
    private String deviceToken;
    private String refreshToken;
    private String fingerprint;

    public PrivateData(Context context) throws FileNotFoundException {

        String[] files = context.fileList();
        String filename = "";
        for(int i =0; i < files.length; i++){
            if(i == 0) filename = "DTK";
            else if(i == 1) filename = "RTK";
            else if(i == 2) filename = "PIN";
            else if(i == 3) filename = "STK";
            else filename = "FP";

            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
            } finally {
                if(i == 0) deviceToken = stringBuilder.toString().replace("\n","");
                else if(i == 1) refreshToken = stringBuilder.toString().replace("\n","");
                else if(i == 2) quickpin = stringBuilder.toString().replace("\n","");
                else if(i == 3) sessionToken = stringBuilder.toString().replace("\n","");
                else fingerprint = stringBuilder.toString().replace("\n","");
            }
        }
    }

    public String getQuickpin() {
        return quickpin;
    }

    public void setQuickpin(String quickpin) {
        this.quickpin = quickpin;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }


}
