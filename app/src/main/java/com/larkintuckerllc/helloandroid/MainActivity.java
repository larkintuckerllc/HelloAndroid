package com.larkintuckerllc.helloandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.larkintuckerllc.mylibrary.MyTest;

import org.joda.time.DateTime;

import java.util.Date;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements MainFragment.OnThingSelectedListener {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

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

        // GCM
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                } else {
                }
            }
        };
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
        // GCM END
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
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

             case R.id.action_foo:
                 MyIntentService.startActionFoo(this, "yes", "wow");
                 return true;

             case R.id.action_baz:
                 MyIntentService.startActionBaz(this, "no", "silly");
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

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

}
