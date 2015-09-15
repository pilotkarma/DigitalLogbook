package com.pilotkarma.pilot.digitallogbook.activity;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pilotkarma.pilot.digitallogbook.R;
import com.pilotkarma.pilot.digitallogbook.view.CameraPreview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vsivajothy on 9/14/15.
 */
public class CameraActivity extends Activity {
    private Context context;
    private boolean cameraFront =false;
    private Camera camera;
    private CameraPreview preview;
    private Camera.PictureCallback mPicture;
    private Button capture, switchCamera;
    private LinearLayout cameraPreview;

    /**
     *
     * @return
     */
    private int findFrontFacingCamera(){
        int cameraId = -1;
        int numberOfCamera = Camera.getNumberOfCameras();
        for (int i=0; i<numberOfCamera; i++){
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i,info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId =i;
                cameraFront = true;
                break;
            }
        }
        return cameraId;

    }

    /**
     *
     * @return
     */
    private int findBackFacingCamera(){
        int cameraId = -1;

        int numberOfCamera = Camera.getNumberOfCameras();
        for (int i=0;i <numberOfCamera;i++){
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i,info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
                cameraId = i;
                cameraFront = false;
                break;
            }
        }
        return cameraId;
    }

    /**
     *
     */
    private void releaseCamera() {

        if (camera!=null) {
            camera.release();
            camera =null;
        }
    }

    /**
     *
     * @param context
     * @return
     */
    private boolean hasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     */
    private void chooseCamera() {
        //if the camera preview is the front
        if (cameraFront) {
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                camera = Camera.open(cameraId);
                mPicture = getPictureCallback();
                preview.refreshCamera(camera);
            }
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview

                camera = Camera.open(cameraId);
                mPicture = getPictureCallback();
                preview.refreshCamera(camera);
            }
        }
    }

    /**
     *
     * @return
     */
    private static File getOutMediaFile(){
        File mediaStorage = new File("/sdcard/", "DigitalLogCamera");
        if (!mediaStorage.exists()) {
            if (!mediaStorage.mkdir()) {
                return null;
            }
        }
        String timeStamp =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorage.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }

    /**
     *
     * @return
     */
    private Camera.PictureCallback getPictureCallback(){
        Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                File file = getOutMediaFile();
                if (file!=null) {
                    return;
                }
                try {
                    FileOutputStream fileOutputStream =  new FileOutputStream(file);
                    fileOutputStream.write(data);
                    fileOutputStream.close();
                    Toast.makeText(context, "Picture saved: " + file.getName(), Toast.LENGTH_LONG).show();

                } catch (FileNotFoundException ex) {

                } catch (Exception ex) {
                }
                preview.refreshCamera(camera);
            }
        };
        return mPicture;
    }

    /**
     *
     */
    DialogInterface.OnClickListener captureListner = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            camera.takePicture(null,null,mPicture);
        }
    };

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_module);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        context =this;
        //initialize();
    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (!hasCamera(context)) {
            Toast.makeText(context,"Sorry no camera right now",Toast.LENGTH_LONG).show();
            finish();
        }
        if (camera==null) {
            if (findFrontFacingCamera() < 0 ) {
                releaseCamera();
                chooseCamera();
            } else {
                Toast.makeText(context, "Sorry Buddy you have only one Camera",Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }
}
