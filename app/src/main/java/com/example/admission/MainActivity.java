package com.example.admission;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

    String[] arraySpinner = new String[]{
            "Select Province",
            "Punjab", "Sindh", "KPK", "Gilgit", "Balochistan"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("PREFRENCE", MODE_PRIVATE);
        String FirstTime = preferences.getString("FirstTimeInstall", "");
        if (FirstTime.equals("Yes")) {
            Intent intent = new Intent(MainActivity.this, ShowData.class);
            startActivity(intent);
        } else {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();

        }
        Spinner s = findViewById(R.id.Spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);
        Button Go = findViewById(R.id.Go);

    }

    public void Go(View view) {

        EditText Name = findViewById(R.id.Name);
        String UserName = Name.getText().toString();
        Intent Dept = new Intent(this, ShowData.class);

        SharedPreferences sharedPreferences = getSharedPreferences("namePref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", UserName);
        editor.commit();

        startActivity(Dept);

    }
}