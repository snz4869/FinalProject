package id.adiyusuf.finalproject;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpHandler {
    // Constructor 1: send POST request
    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
        // membuat URL
        URL url;
        StringBuilder sb = new StringBuilder();  // wadah untuk menampung semua data
        try {
            url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //mengirim request dari client ke server
            OutputStream os = connection.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8")
            );
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            // cek Http status code untuk memastikan request diterima oleh server
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // response dikirim dari server ke client
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
                sb = new StringBuilder();
                String response;
                while ((response = reader.readLine()) != null) {
                    sb.append(response);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace(); // error message
        }
        return sb.toString();
    }

    private String getPostDataString(HashMap<String, String> params) //HashMap --> json
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    //Constructor 2 : send GET request/response --> GET All dan GET detail
    // GET_ALL
    public String sendGetResponse(String responseUrl){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(responseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String response;
            while ((response = reader.readLine()) != null){
                sb.append(response + "\n");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return sb.toString();
    }

    // GET_DETAIL
    public String sendGetResponse(String responseUrl, String id){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(responseUrl + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String response;
            while ((response = reader.readLine()) != null){
                sb.append(response + "\n");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return sb.toString();
    }

    public String sendGetRespDate(String responseUrl, String mulai, String akhir) {
        StringBuilder sb = new StringBuilder();
        try {
            Uri.Builder builder = new Uri.Builder();
            builder.appendQueryParameter("mulai", mulai).
                    appendQueryParameter("akhir", akhir);
            URL url = new URL(responseUrl + builder);
            Log.d("url:", String.valueOf(builder));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String response;
            while ((response = reader.readLine()) != null) {
                sb.append(response + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }
}
