package com.snowy.demo.zcamera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zx on 17-3-22.
 */

public class CameraDemoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_take_photo;
    private VideoView vv_take_video;
    private String mCurrentPhotoPath;

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_VIDEO_CAPTURE = 2;

    private Camera mCamera;
    private Preview mPreview;
    private List<Camera.Size> mSupportedPreviewSizes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_camera_demo);
        findViewById(R.id.btn_take_photo).setOnClickListener(this);
        findViewById(R.id.btn_take_video).setOnClickListener(this);
        findViewById(R.id.btn_control_camera).setOnClickListener(this);

        iv_take_photo = findViewById(R.id.iv_take_photo);
        vv_take_video = findViewById(R.id.vv_take_video);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_photo:
                //先判断有没有相机
                boolean hasCamera = getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
                if (hasCamera) {
                    /* //返回的是缩略图
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    *//*
                    在调用startActivityForResult()方法之前，先调用resolveActivity()，
                    这个方法会返回能处理该Intent的第一个Activity（译注：即检查有没有能处理这个Intent的Activity）。
                    执行这个检查非常重要，因为如果在调用startActivityForResult()时，没有应用能处理你的Intent，
                    应用将会崩溃。所以只要返回结果不为null，使用该Intent就是安全的。
                     *//*
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        getActivity().startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }*/

                    //保存全尺寸照片
                    dispatchTakePictureIntent();

                }
                break;
            case R.id.btn_take_video:
                if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        getActivity().startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                    }
                }
                break;
            case R.id.btn_control_camera:
                Toast.makeText(getActivity(), "没整明白...", Toast.LENGTH_SHORT).show();
                /*mPreview = new Preview(getActivity());
                int cameraNum = Camera.getNumberOfCameras();
                if (safeOpenCamera(0)) {
                    mPreview.setCamera(mCamera);
                }*/
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK && data!=null) {
            Bundle extra = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extra.get("data");
            iv_take_photo.setImageBitmap(imageBitmap);
        }*/
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            vv_take_video.setVisibility(View.INVISIBLE);
            iv_take_photo.setVisibility(View.VISIBLE);
            galleryAddPic();
            setPic();
        }else if(requestCode == REQUEST_VIDEO_CAPTURE && resultCode == getActivity().RESULT_OK){
            Uri videoUri = data.getData();
            vv_take_video.setVideoURI(videoUri);
            iv_take_photo.setVisibility(View.INVISIBLE);
            vv_take_video.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "不知道为什么不能播放录像", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 存储照片的文件
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imgeFimeNmae = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imgeFimeNmae, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * 保存全尺寸照片
     */
    private void dispatchTakePictureIntent(){
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                getActivity().startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    /**
     * 将照片保存到相册
     */
    private void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    /**
     * 显示全尺寸照片
     */
    private void setPic(){
       // Get the dimensions of the View
        int targetW = iv_take_photo.getWidth();
        int targetH = iv_take_photo.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, options);
        int photoW = options.outWidth;
        int photeH = options.outHeight;

        // Determine how much to scale down the image
        int scale = Math.min(photoW/targetW, photeH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;
        options.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, options);
        iv_take_photo.setImageBitmap(bitmap);
    }

    /**
     * 打开相机对象
     */
    private boolean safeOpenCamera(int id){
        boolean qOpen = false;
        try {
            releaseCameraAndPreview();
            mCamera = Camera.open(id);
            qOpen = (mCamera != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qOpen;
    }
    private void releaseCameraAndPreview() {
        mPreview.setCamera(null);
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    class Preview extends ViewGroup implements SurfaceHolder.Callback {
        SurfaceView mSurfaceView;
        SurfaceHolder mHolder;

        Preview(Context context) {
            super(context);

            mSurfaceView = new SurfaceView(context);
            addView(mSurfaceView);

            // Install a SurfaceHolder.Callback so we get notified when the
            // underlying surface is created and destroyed.
            mHolder = mSurfaceView.getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        public void setCamera(Camera camera){
            if (mCamera == camera)
                return;

            //停止预览并释放相机
            stopPreviewAndFreeCamera();

            mCamera = camera;
            if (mCamera != null) {
                List<Camera.Size> localSizes = mCamera.getParameters().getSupportedPreviewSizes();
                mSupportedPreviewSizes = localSizes;
                requestLayout();

                try {
                    mCamera.setPreviewDisplay(mHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Important: Call startPreview() to start updating the preview
                // surface. Preview must be started before you can take a picture.
                mCamera.startPreview();
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // Now that the size is known, set up the camera parameters and begin
            // the preview.
            Camera.Parameters parameters = mCamera.getParameters();
//            parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            requestLayout();
            mCamera.setParameters(parameters);

            // Important: Call startPreview() to start updating the preview surface.
            // Preview must be started before you can take a picture.
            mCamera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // Surface will be destroyed when we return, so stop the preview.
            if (mCamera != null) {
                // Call stopPreview() to stop updating the preview surface.
                mCamera.stopPreview();
            }
        }

        /**
         * When this function returns, mCamera will be null.
         */
        private void stopPreviewAndFreeCamera() {

            if (mCamera != null) {
                // Call stopPreview() to stop updating the preview surface.
                mCamera.stopPreview();

                // Important: Call release() to release the camera for use by other
                // applications. Applications should release the camera immediately
                // during onPause() and re-open() it during onResume()).
                mCamera.release();

                mCamera = null;
            }
        }
    }}
