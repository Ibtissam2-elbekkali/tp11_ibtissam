package com.example.lab11;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lab11.utils.SecurePrefs_ibtissam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private double lat_ibtissam;
    private double lon_ibtissam;
    private boolean hasLocation_ibtissam = false;
    private RequestQueue queue_ibtissam;
    private TextView tvInfo_ibtissam;
    private LocationManager locManager_ibtissam;
    
    private final String url_ibtissam = "http://10.0.2.2/localisation/createPosition.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo_ibtissam = findViewById(R.id.ibtissam_tv_info);
        Button btnSend_ibtissam = findViewById(R.id.ibtissam_btn_send);
        Button btnClear_ibtissam = findViewById(R.id.ibtissam_btn_clear);
        
        queue_ibtissam = Volley.newRequestQueue(this);
        locManager_ibtissam = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        checkPermissions_ibtissam();

        btnSend_ibtissam.setOnClickListener(v -> sendPosition_ibtissam());
        btnClear_ibtissam.setOnClickListener(v -> {
            tvInfo_ibtissam.setText(R.string.ibtissam_no_location);
            Toast.makeText(this, "Reset effectué", Toast.LENGTH_SHORT).show();
        });
        
        try {
            SecurePrefs_ibtissam.saveToken_ibtissam(this, "IBTISSAM_SECURE_TOKEN_XYZ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPermissions_ibtissam() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE
            }, 101);
        } else {
            startLocationUpdates_ibtissam();
        }
    }

    private void startLocationUpdates_ibtissam() {
        try {
            locManager_ibtissam.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    lat_ibtissam = location.getLatitude();
                    lon_ibtissam = location.getLongitude();
                    hasLocation_ibtissam = true;
                    String info_ibtissam = "Latitude : " + lat_ibtissam + "\nLongitude : " + lon_ibtissam + "\nAltitude : " + location.getAltitude() + " m\nPrécision : " + location.getAccuracy() + " m";
                    tvInfo_ibtissam.setText(info_ibtissam);
                }

                @Override
                public void onProviderEnabled(@NonNull String provider) {
                    Toast.makeText(MainActivity.this, getString(R.string.ibtissam_provider_enabled, provider), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    Toast.makeText(MainActivity.this, getString(R.string.ibtissam_provider_disabled, provider), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void sendPosition_ibtissam() {
        if (!hasLocation_ibtissam) {
            Toast.makeText(this, "Aucune position GPS disponible, patientez...", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest request_ibtissam = new StringRequest(Request.Method.POST, url_ibtissam,
                response -> Toast.makeText(this, getString(R.string.ibtissam_success_send), Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this, getString(R.string.ibtissam_error_send), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params_ibtissam = new HashMap<>();
                SimpleDateFormat sdf_ibtissam = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                
                params_ibtissam.put("latitude", String.valueOf(lat_ibtissam));
                params_ibtissam.put("longitude", String.valueOf(lon_ibtissam));
                params_ibtissam.put("date_position", sdf_ibtissam.format(new Date()));
                
                String imei_ibtissam = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                params_ibtissam.put("imei", imei_ibtissam);
                
                return params_ibtissam;
            }
        };
        queue_ibtissam.add(request_ibtissam);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates_ibtissam();
        }
    }
}