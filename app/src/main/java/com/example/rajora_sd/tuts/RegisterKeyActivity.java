package com.example.rajora_sd.tuts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterKeyActivity extends AppCompatActivity {

    Button registerKey;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_key);
        editText = (EditText) findViewById(R.id.editText);
        registerKey = (Button) findViewById(R.id.registerKey);
        registerKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("DATA", MODE_APPEND);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong("KEY",Long.parseLong(editText.getText().toString()));
                editor.apply();
                editor.commit();
                startActivity(new Intent(RegisterKeyActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                RegisterKeyActivity.this.finish();
            }
        });
    }
}
