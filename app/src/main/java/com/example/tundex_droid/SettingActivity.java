package com.example.tundex_droid;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Configuration;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity implements OnClickListener {
    Context context = this;
    Button btn_back;
    Button btn_about;
    final int DIALOG_CHANGE=1;
    final int DIALOG_ABOUT=2;
    final int DIALOG_GPS=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        btn_about = (Button) findViewById(R.id.btn_about);
        btn_about.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_change_language:
                showDialog(DIALOG_CHANGE);
                break;
            case R.id.btn_about:
                showDialog(DIALOG_ABOUT);
                break;
        }
    }
    public void onclick(View v) {
        // вызываем диалог
        showDialog(DIALOG_CHANGE);
    }
    public void onclick2(View v) {
        // вызываем диалог
        showDialog(DIALOG_ABOUT);
    }

    public void onClickGps(View v) {
        // вызываем диалог
        showDialog(DIALOG_GPS);
    }
   protected Dialog onCreateDialog(int id) {
        if(id==DIALOG_CHANGE){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.Pick_language)
                .setItems(R.array.Language, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    Configuration config = new Configuration();
                        switch(which){
                            case 0:
                                String lang = "ru";
                                Locale locale = new Locale(lang);
                                Locale.setDefault(locale);
                                config.locale = locale;
                                getBaseContext().getResources().updateConfiguration(config,
                                        getBaseContext().getResources().getDisplayMetrics());
                                    break;
                            case 1:
                                config.locale = Locale.ENGLISH;
                                break;
                        }

                    getResources().updateConfiguration(config, null);
                    Intent mStartActivity = new Intent(context, MainActivity.class);
                    int mPendingIntentId = 123456;
                    PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                    finish();
                    }
                });

                return builder.create();

            }
            if(id==DIALOG_ABOUT){
               AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.About_us)
                   .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {

                       }
                    });
                // Create the AlertDialog object and return it
                return builder.create();
            }
            if(id==DIALOG_GPS){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //hardcode debug
                String curCoords= "0.00056;0.099";
                builder.setTitle(R.string.your_coords)
                    .setMessage(curCoords)
                    .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}
                    });
                return builder.create();
            }
       return super.onCreateDialog(id);
    }
}

