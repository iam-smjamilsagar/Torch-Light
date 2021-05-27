package com.smjsp.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    ImageButton torchbtn;
    boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        torchbtn = findViewById(R.id.torchbtn);

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                runFlashlight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Camera permission required.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();

    }

    private void runFlashlight() {
        torchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!state) {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        String camereId = cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(camereId, true);
                        state = true;
                        torchbtn.setImageResource(R.drawable.torch_on);
                    }
                    catch (CameraAccessException e) {
                    }
                }
                else
                    {
                        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                        try {
                            String camereId = cameraManager.getCameraIdList()[0];
                            cameraManager.setTorchMode(camereId, false);
                            state = false;
                            torchbtn.setImageResource(R.drawable.torch_off);
                        }
                        catch (CameraAccessException e) {
                        }

                    }
            }
        });
    }
}