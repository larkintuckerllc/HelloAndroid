package com.larkintuckerllc.helloandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MainFragment.OnThingSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MainFragment mainFragment = (MainFragment)
                getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        ThingFragment thingFragment = (ThingFragment)
                getSupportFragmentManager().findFragmentById(R.id.thing_fragment);
        if (thingFragment != null) {
            thingFragment.selectThing(mainFragment.getFirstThing());
        }
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

    public void onThingSelected(String id) {
        ThingFragment thingFragment = (ThingFragment)
                getSupportFragmentManager().findFragmentById(R.id.thing_fragment);
        if (thingFragment != null) {
            thingFragment.selectThing(id);
        } else {
            Intent intent = new Intent(this, ThingActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
    }

}
