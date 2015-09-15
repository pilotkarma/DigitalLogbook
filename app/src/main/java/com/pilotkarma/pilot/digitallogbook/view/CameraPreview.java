package com.pilotkarma.pilot.digitallogbook.view;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by vsivajothy on 9/14/15.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private Camera camera;


    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void refreshCamera(Camera camera) {
        if (surfaceHolder.getSurface() == null) {
            return;
        }
        try {
            camera.startPreview();
        }catch (Exception ex){

        }
        setCamera(camera);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        }catch (Exception ex){
            Log.d(VIEW_LOG_TAG, "Error starting Camera prevview: " + ex.getMessage());
        }
    }


    private void setCamera(Camera camera) {
        this.camera=camera;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (camera == null) {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            }
        } catch (IOException ex) {
            Log.d(VIEW_LOG_TAG, "Error Setting Camera prevview: " + ex.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera(camera);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
