package com.francine.assignment;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;

public class MainActivity extends Activity {

    private TextView textLabel;
    private TextView textKey;
    private Button btnGetKey;
    private Button btnDelete;

    private static final int REQUEST_CODE = 1;
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private static final int MINUTE = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLabel = findViewById(R.id.text_label);
        textKey = findViewById(R.id.text_key);
        btnGetKey = findViewById(R.id.btn_get_key);
        btnDelete = findViewById(R.id.btn_delete);

        btnGetKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanCodeActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences prefs = new Preferences();
                prefs.setPreferencesKey(null, MainActivity.this);
                prefs.setPreferencesLabel(null, MainActivity.this);
                updateUi();
            }
        });

        updateUi();
        scheduleAlarm();
        requestCameraPermission();

        IntentFilter filter = new IntentFilter("com.francine.assignment.MainActivity");
        registerReceiver(new BroadcastReceiver(){
            public void onReceive(Context context, Intent intent) {
                updateUi();
            }
        }, filter);
    }

    private void updateUi() {
        Preferences prefs = new Preferences();
        String label = prefs.getPreferencesLabel(this);
        String key = prefs.getPreferencesKey(this);

        if ((label != null) && (key != null)) {
            byte[] hash = generateOtp(key);
            int offset = 10;
            long otp = 0;
            if (hash != null) {
                int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16)
                        | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);
                otp = binary % 1000000;
            }
            DecimalFormat df = new DecimalFormat("000000");

            textLabel.setText(label);
            textKey.setText(df.format(otp));
            btnGetKey.setVisibility(View.GONE);
        } else {
            textLabel.setText("");
            textKey.setText("");
            btnGetKey.setVisibility(View.VISIBLE);
        }
    }

    private void scheduleAlarm() {
        AlarmManager manager =  (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int seconds2Minute = calendar.get(Calendar.SECOND);

        if (seconds2Minute < (MINUTE/2))
        {
            seconds2Minute = (MINUTE/2) - seconds2Minute;
        } else {
            seconds2Minute = MINUTE - seconds2Minute;
        }

        long inicio = calendar.getTimeInMillis() + seconds2Minute * 1000;
        manager.set(AlarmManager.RTC_WAKEUP, inicio, alarmIntent);
    }

    private void requestCameraPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Permission denied");
                        builder.setMessage("To scan QR code, allow access to camera in android settings.");
                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        builder.show();
                    } else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Limited functionality");
                        builder.setMessage("If camera access is not granted, this application will not be able to scan QR code.");
                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE == requestCode) {
            updateUi();
        }
    }

    public native byte[] generateOtp(String key);

    static {
        System.loadLibrary("otpjni");
    }


}
