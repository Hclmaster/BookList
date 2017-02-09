package com.example.hclmaster.udacity_booklist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hclmaster on 2017/2/8.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tvTitle = (TextView)convertView.findViewById(R.id.book_title);
        TextView tvAuthor = (TextView)convertView.findViewById(R.id.book_author);
        TextView tvPublishedDate = (TextView)convertView.findViewById(R.id.book_publishedDate);

        String title = book.getTitle();
        String authors = book.getAuthors();
        String publishedDate = book.getPublishedDate();

        tvTitle.setText(title);
        tvAuthor.setText(authors);
        tvPublishedDate.setText(publishedDate);

        return convertView;
    }
}
