package com.example.appmeucurso;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnNotify;
    private EditText editMinute;
    private TimePicker timePicker;
    private int hour;
    private int minute;
    private int interval;

    private boolean activated = false;

    private SharedPreferences preferences;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instanciando os ID

        btnNotify = findViewById(R.id.btn_notify);
        editMinute = findViewById(R.id.edit_txt_number);
        timePicker = findViewById(R.id.time_picker);

        //mudando o formato do relogio;

        timePicker.setIs24HourView(true);
        preferences = getSharedPreferences("db", Context.MODE_PRIVATE);
        activated = preferences.getBoolean("activated" , false);
        if (activated){
            btnNotify.setText(R.string.pause);
            btnNotify.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.black));

            int interval = preferences.getInt("interval", 0);
            int hour = preferences.getInt("hour" , timePicker.getCurrentHour());
            int minute = preferences.getInt("minute" , timePicker.getCurrentMinute());

            editMinute.setText(String.valueOf(interval));
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void notifyClick(View view) {
        String sInterval = editMinute.getText().toString();

        if (sInterval.isEmpty()) {
            Toast.makeText(this, "Digite o intervalo", Toast.LENGTH_SHORT).show();
        }


        hour = timePicker.getCurrentHour();
        minute = timePicker.getCurrentMinute();
        interval = Integer.parseInt(sInterval);
        if (!activated) {
            btnNotify.setText(R.string.pause);
            btnNotify.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.black));
            activated = true;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("activated", true);
            editor.putInt("interval", interval);
            editor.putInt("hour", hour);
            editor.putInt("minute", minute);
            editor.apply();

        } else {
            btnNotify.setText(R.string.notify);
            btnNotify.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
            activated = false;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("activated", false);
            editor.remove("interval");
            editor.remove("hour");
            editor.remove("minute");
            editor.apply();


        }
    }
}