package ru.test.app.cameraprocng;

import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * Created by vyacheslav.rogov on 19.02.2017.
 */

public class SurfaceWorker implements SurfaceHolder.Callback, Camera.PreviewCallback {
    public static final String TAG = SurfaceWorker.class.getName();

    Camera camera;
    private static int numberCams;
    private static int currentCam;

    SurfaceView preview;
    SurfaceHolder holder;

    public SurfaceWorker() {
        numberCams = Camera.getNumberOfCameras();
        currentCam = 0;
    }

    public void init(SurfaceView view) {
        preview = view;
        holder = preview.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void chooseNextCam() {
        stopCam();
        currentCam = (currentCam + 1) % numberCams;
        startCam();
    }

    public void stopCam() {
        Log.d(TAG, "stopCam: i=" + currentCam);
        if (null != camera) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public void startCam() {
        Log.d(TAG, "startCam: i=" + currentCam);
        camera = Camera.open(currentCam);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
//        Log.d(TAG, "onPreviewFrame");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated:" + holder.getSurface().toString());

        try {
            camera.setPreviewDisplay(holder);
            camera.setPreviewCallback(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        prepareCamView();
        camera.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
    }

    protected void prepareCamView() {
        Camera.Parameters camParams = camera.getParameters();
        Log.d(TAG, "camera: view-angles=" + camParams.getHorizontalViewAngle() + "x"
                + camParams.getVerticalViewAngle());

        Camera.Size previewSize = camParams.getPreviewSize();
        float aspect = (float) previewSize.width / previewSize.height;
        Log.d(TAG, "prepareCamView: size=" + previewSize.width + "x" + previewSize.height
                + " aspect=" + aspect);

        int prevWidth = preview.getWidth();
        int prevHeight = preview.getHeight();
        Log.d(TAG, "holder: size=" + prevWidth + "x" + prevHeight);

        ViewGroup.LayoutParams lp = preview.getLayoutParams();

        if (Configuration.ORIENTATION_LANDSCAPE == preview.getResources().getConfiguration().orientation) {
            camera.setDisplayOrientation(0);
            lp.width = prevWidth;
            lp.height = (int) (prevWidth / aspect);
        } else {
            camera.setDisplayOrientation(90);
            lp.height = prevHeight;
            lp.width = (int) (prevHeight / aspect);
        }

        preview.setLayoutParams(lp);
        Log.d(TAG, "holder: new-size=" + lp.width + "x" + lp.height);
    }
}
