package com.example.hclmaster.udacity_booklist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText searchKeyWord;
    private Button searchButton;

    public static String GOOGLE_BOOK_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>>{

        @Override
        protected List<Book> doInBackground(String... urls) {
            if(urls.length < 1 || urls[0] == null){
                return null;
            }

            ArrayList<Book> books = QueryUtils.fetchBookData(urls[0]);
            return books;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            BookAdapter bookAdapter = new BookAdapter(MainActivity.this, books);
            listView.setAdapter(bookAdapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list);
        searchKeyWord = (EditText)findViewById(R.id.input_keyword);
        searchButton = (Button)findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryParam = searchKeyWord.getText().toString();
                GOOGLE_BOOK_URL += queryParam;
                GOOGLE_BOOK_URL += "&key=AIzaSyDNMJ6ukCYQxB6BxJOEdVIrq91KbrQQVAE";

                Log.e("------->", GOOGLE_BOOK_URL);

                BookAsyncTask bookAsyncTask = new BookAsyncTask();
                bookAsyncTask.execute(GOOGLE_BOOK_URL);
            }
        });
    }

}
