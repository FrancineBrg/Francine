package com.francine.assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView textKey;
    private Button btnGetKey;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textKey = (TextView) findViewById(R.id.text_key);
        btnGetKey = (Button) findViewById(R.id.btn_get_key);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        Preferences prefs = new Preferences();
        int key = prefs.getPreferences(this);
        if (key > 0) {
            textKey.setText(String.valueOf(key));
        } else {
            textKey.setText("");
        }

        btnGetKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanCodeActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
