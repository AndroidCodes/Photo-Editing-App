package com.example.androidcodes.photoeditingapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.androidcodes.photoeditingapp.Frames.Common.Common;


/**
 * Created by peacock on 29/4/16.
 */
public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Common.changeActivity(Splash.this, MainActivity.class, false);

                /*finish();

                startActivity(new Intent(Splash.this, MainActivity.class));

                overridePendingTransition(R.anim.right_in, R.anim.left_out);*/

            }
        }, 3000);
    }

    /*@Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = checkSelfPermission(Manifest.permission.
                    WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.
                    READ_EXTERNAL_STORAGE);
            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            }

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.CAMERA);

            }
            if (!permissions.isEmpty()) {

                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {

            case 111: {

                for (int i = 0; i < permissions.length; i++) {

                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        System.out.println("Permissions --> " + "Permission Granted: " +
                                permissions[i]);

                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                        System.out.println("Permissions --> " + "Permission Denied: " +
                                permissions[i]);

                    }
                }
            }

            break;

            default: {

                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            }
        }
    }*/

    @Override
    public void onBackPressed() {
    }
}
