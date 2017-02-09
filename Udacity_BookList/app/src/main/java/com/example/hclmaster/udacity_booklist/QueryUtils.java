package com.example.hclmaster.udacity_booklist;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by hclmaster on 2017/2/8.
 */

public final class QueryUtils {
    public QueryUtils() {
    }

    public static ArrayList<Book> fetchBookData(String requestUrl){
        URL url = createURL(requestUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e("QueryUtils", "Error with closing InputStream", e);
        }

        ArrayList<Book> books = extraBooks(jsonResponse);
        return books;
    }

    private static URL createURL(String requestUrl) {
        URL url = null;
        try{
            url = new URL(requestUrl);
        }catch (Exception e){
            Log.e("QueryUtils" ,"Error with createURL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            // urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // if the request is successful, then parse the json response.
            if(urlConnection.getResponseCode() == HTTP_OK){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("QueryUtils", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("QueryUtils", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<Book> extraBooks(String booksJSON) {
        if(TextUtils.isEmpty(booksJSON)){
            return null;
        }
        ArrayList<Book> books= new ArrayList<>();

        try {
            JSONObject root = new JSONObject(booksJSON);
            JSONArray itemsArray = root.getJSONArray("items");
            for(int i=0; i<itemsArray.length(); i++){
                JSONObject items = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = items.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authorsArray= volumeInfo.getJSONArray("authors");
                StringBuilder authors = new StringBuilder();
                for(int j=0; j<authorsArray.length(); j++){
                    String tmp = authorsArray.getString(j);
                    authors.append(tmp);
                    if(j != authorsArray.length()-1){
                        authors.append(", ");
                    }
                }
                String publishedDate = volumeInfo.getString("publishedDate");
                String description = volumeInfo.getString("description");

                books.add(new Book(title, authors.toString(), publishedDate, description));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the Google Books JSON results", e);
        }

        return books;
    }

}
