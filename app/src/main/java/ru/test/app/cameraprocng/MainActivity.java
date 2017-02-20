package ru.test.app.cameraprocng;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import javax.inject.Inject;

import ru.test.app.cameraprocng.di.CameraComponent;
import ru.test.app.cameraprocng.di.DaggerCameraComponent;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();

    private CameraComponent camComp;

    @Inject
    public SurfaceWorker worker;

    public void onClick(View v) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        camComp = DaggerCameraComponent.builder().build();

        worker.init((SurfaceView) findViewById(R.id.cam_surface));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");

        worker.startCam();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");

        worker.stopCam();
    }

}
