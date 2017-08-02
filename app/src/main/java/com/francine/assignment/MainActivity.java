package com.francine.assignment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private TextView textKey;
    private Button btnGetKey;
    private Button btnDelete;
    private Timer timer;
    private TimerTask task;
    private DecimalFormat df = new DecimalFormat("000000");

    private String key;
    private byte hash[];
    private long otp;
    private int cont = 0;

    private static final int RC_HANDLE_CAMERA_PERM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textKey = (TextView) findViewById(R.id.text_key);
        btnGetKey = (Button) findViewById(R.id.btn_get_key);
        btnDelete = (Button) findViewById(R.id.btn_delete);

        btnGetKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rc = ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA);
                if (rc != PackageManager.PERMISSION_GRANTED) {
                    requestCameraPermission();
                } else {
                    Intent intent = new Intent(MainActivity.this, ScanCodeActivity.class);
                    startActivity(intent);
                }
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

        setTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        int rc = ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA);
        if (rc != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        }

        Preferences prefs = new Preferences();
        key = prefs.getPreferences(this);
        displayOtp();
        setTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            cont = 0;
            task.cancel();
            timer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handles the requesting of the camera permission.
    private void requestCameraPermission() {
        final String[] permissions = new String[]{android.Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
    }

    private void generate() {
        byte aux[] = new byte[4];
        aux[0] = hash[10];
        aux[1] = hash[11];
        aux[2] = hash[12];
        aux[3] = hash[13];

        StringBuilder sb = new StringBuilder(aux.length * 2);
        for(byte b: aux)
            sb.append(String.format("%02x", b));

        otp = Long.parseLong(sb.toString(), 16) % 1000000;
    };

    private void displayOtp() {
        if (key != null) {
            hash = generateOtp(key);
            generate();
            textKey.setText(df.format(otp));
            btnGetKey.setVisibility(View.GONE);
        } else {
            textKey.setText("");
            btnGetKey.setVisibility(View.VISIBLE);
        }
    };

    private void setTimer() {
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (cont <= 0) {
                                displayOtp();
                                cont = 30;
                            } else {
                                cont--;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000); ;
    };

    public native byte[] generateOtp(String key);

    static {
        System.loadLibrary("otpjni");
    }
}
