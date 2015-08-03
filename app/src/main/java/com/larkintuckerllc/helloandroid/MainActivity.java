package com.larkintuckerllc.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> things;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        things = new ArrayList<String>();
        things.add("one");
        things.add("two");
        things.add("three");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView thingRV = (RecyclerView) findViewById(R.id.things);
        thingRV.setHasFixedSize(true);
        thingRV.setLayoutManager(new LinearLayoutManager(this));
        thingRV.setAdapter(new ThingAdapter(things));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_hello) {
            Snackbar
                    .make(findViewById(R.id.snackbarPosition), R.string.snackbar_text, Snackbar.LENGTH_LONG)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ThingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        String id;
        TextView name;

        ThingViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,ThingActivity.class);
            intent.putExtra("id",  name.getText());
            startActivity(intent);
        }
    }

    public class ThingAdapter extends RecyclerView.Adapter<ThingViewHolder> {

        List<String> things;

        ThingAdapter(List<String> things) {
            this.things = things;
        }

        public ThingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.thing, viewGroup, false);
            return new ThingViewHolder(view);
        }

        public int getItemCount() {
            return things.size();
        }

        public void onBindViewHolder(ThingViewHolder viewHolder, int i) {
            viewHolder.id = things.get(i);
            viewHolder.name.setText(things.get(i));
        }

    }

}
