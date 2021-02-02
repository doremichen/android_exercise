package com.adam.demo.camera;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class CameraMain extends Activity implements SurfaceHolder.Callback, OnClickListener,
                                                    Camera.PictureCallback{

    SurfaceView cameraView;
    SurfaceHolder surfaecHolder;
    
    Camera camera;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_main);
        
        cameraView = (SurfaceView)this.findViewById(R.id.cameraView);
        surfaecHolder = cameraView.getHolder();
        surfaecHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaecHolder.addCallback(this);
        
        cameraView.setFocusable(true);
        cameraView.setFocusableInTouchMode(true);
        cameraView.setClickable(true);
        cameraView.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera_main, menu);
        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        // TODO Auto-generated method stub
        
        
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        camera = Camera.open();
        
        setCameraDisplayOrientation(this, 0, camera);
        
        Camera.Parameters parameters = camera.getParameters();
        
        if (this.getResources().getConfiguration().orientation !=
                Configuration.ORIENTATION_LANDSCAPE) {
            parameters.set("orientation", "portrait");
            
        } else {
            
            parameters.set("orientation", "landscape");
        }
        
        parameters.setPreviewSize(640, 480);
        
        camera.setParameters(parameters);
        try {
            camera.setPreviewDisplay(holder);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        camera.stopPreview();
        camera.release();
    }
    
    
    public static int getDisplayRotation(Activity activity) {

        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        switch (rotation) {
            case Surface.ROTATION_0: return 0;
            case Surface.ROTATION_90: return 90;
            case Surface.ROTATION_180: return 180;
            case Surface.ROTATION_270: return 270;
        }
        return 0;
    }
    
    
    public static void setCameraDisplayOrientation(Activity activity,
            int cameraId, Camera camera) {

        // See android.hardware.Camera.setCameraDisplayOrientation for
        // documentation.
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int degrees = getDisplayRotation(activity);
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        // TODO Auto-generated method stub
        Uri imageFileUri =
        getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, new ContentValues());
        try {
            OutputStream imageFileOS =
            getContentResolver().openOutputStream(imageFileUri);
            imageFileOS.write(data);
            imageFileOS.flush();
            imageFileOS.close();
        } catch (FileNotFoundException e) {
            Toast t = Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT);
            t.show();
        } catch (IOException e) {
            Toast t = Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT);
            t.show();
        }
        camera.startPreview();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        camera.takePicture(null, null, this);
    }


}
