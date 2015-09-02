package com.larkintuckerllc.helloandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.larkintuckerllc.helloandroid.R;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<String> mItems = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mItems.add("one");
        mItems.add("two");
        mItems.add("three");
        ArrayAdapter<String> adapter = new MyArrayAdapter(this, mItems);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    public class MyArrayAdapter extends ArrayAdapter<String> {

        public MyArrayAdapter(Context context, ArrayList<String> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }
            TextView text = (TextView) convertView.findViewById(R.id.text);
            text.setText(item) ;
            return convertView;
        }
    }

}
