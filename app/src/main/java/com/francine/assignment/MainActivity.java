package com.francine.assignment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView textKey;
    private Button btnGetKey;
    private Button btnDelete;

    private static final int RC_HANDLE_CAMERA_PERM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int rc = ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA);
        if (rc != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        }

        textKey = (TextView) findViewById(R.id.text_key);
        btnGetKey = (Button) findViewById(R.id.btn_get_key);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        Preferences prefs = new Preferences();
        String key = prefs.getPreferences(this);
        if (key != null) {
            textKey.setText(key);
            btnGetKey.setVisibility(View.GONE);
        } else {
            textKey.setText("");
            btnGetKey.setVisibility(View.VISIBLE);
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
                Preferences prefs = new Preferences();
                prefs.setPreferences(null, MainActivity.this);
                onResume();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        int rc = ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA);
        if (rc != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        }

        Preferences prefs = new Preferences();
        String key = prefs.getPreferences(this);
        if (key != null) {
            textKey.setText(key);
            btnGetKey.setVisibility(View.GONE);
        } else {
            textKey.setText("");
            btnGetKey.setVisibility(View.VISIBLE);
        }

    }

    // Handles the requesting of the camera permission.
    private void requestCameraPermission() {
        final String[] permissions = new String[]{android.Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
        }
    }

    public native byte[] generateOtp(String key);

    static {
        System.loadLibrary("otpjni");
    }
}
