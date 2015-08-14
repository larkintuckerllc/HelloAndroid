package com.larkintuckerllc.helloandroid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import com.crashlytics.android.Crashlytics;
import com.larkintuckerllc.mylibrary.MyTest;

import org.joda.time.DateTime;

import java.util.Date;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements MainFragment.OnThingSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
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

        // DEMO CODE USING JODA-TIME PACKAGE FROM JCENTER
        Date juDate = new Date();
        DateTime dt = new DateTime(juDate);

        // DEMO CODE USING LIBRARY
        String test = MyTest.echo("wow");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
             case R.id.action_hello:
                 Snackbar
                         .make(findViewById(R.id.snackbarPosition), R.string.snackbar_text, Snackbar.LENGTH_LONG)
                         .show();
                 return true;

             // IN notelephony FLAVOR CODE IS UNREACHABLE NO NEED TO CHECK FOR TELEPHONY
             case R.id.action_call:
                 Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
                 phoneCallIntent.setData(Uri.parse("tel:3524748328"));
                 startActivity(phoneCallIntent);
                 return true;

             default:
                 return super.onOptionsItemSelected(item);
         }
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
