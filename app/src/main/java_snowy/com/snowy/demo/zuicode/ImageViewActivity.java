package com.snowy.demo.zuicode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.ZoomControls;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

/**
 * Created by ts on 16-7-28.
 */
public class ImageViewActivity extends BaseActivity implements View.OnClickListener {
    Button btn_alpha_plus;
    Button btn_alpha_minus;
    Button btn_image_next;
    ImageView img_large;
    ImageView img_small;
    ZoomControls zoomControls;
    QuickContactBadge contactBadge;
    private int currentImage = 1;
    private int alpha = 255;
    private float zoomInScale = 1.2f;
    private float zoomOutScale = 0.8f;

    private int[] images = new int[]{
            R.drawable.caoyuan,
            R.drawable.green,
            R.drawable.road,
            R.drawable.bluesky,
            R.drawable.tiger
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_uicode_imageview);

        btn_alpha_minus = (Button) findViewById(R.id.btn_alpha_minus);
        btn_alpha_plus = (Button) findViewById(R.id.btn_alpha_plus);
        btn_image_next = (Button) findViewById(R.id.btn_image_next);

        img_large = (ImageView) findViewById(R.id.img_large);
        img_small = (ImageView) findViewById(R.id.img_small);

        zoomControls = (ZoomControls) findViewById(R.id.zoomcontrols);
        contactBadge = (QuickContactBadge) findViewById(R.id.contactbage);

        contactBadge.assignContactFromPhone("18518411302", false);

        img_large.setImageResource(images[currentImage]);

        btn_image_next.setOnClickListener(this);
        btn_alpha_minus.setOnClickListener(this);
        btn_alpha_plus.setOnClickListener(this);

        img_large.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img_large.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                double scale = bitmap.getWidth() / 320.0;

                int x = (int) (event.getX() * scale);
                int y = (int) (event.getY() * scale);

                if(x + 120 > bitmap.getWidth()){
                    x = bitmap.getWidth() - 120;
                }
                if(y + 120 > bitmap.getHeight()){
                    y = bitmap.getHeight() - 120;
                }

                img_small.setImageBitmap(Bitmap.createBitmap(bitmap, x, y, 120, 120));

                return false;
            }
        });

        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ImageViewActivity", "图片放大了" + zoomInScale);
                changeImageSize(zoomInScale);
            }
        });
        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ImageViewActivity", "图片缩小了" + zoomOutScale);
                changeImageSize(zoomOutScale);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_alpha_plus:
                alpha += 20;
                if(alpha >= 255){
                    alpha = 255;
                }
                img_large.setAlpha(alpha);
                break;
            case R.id.btn_alpha_minus:
                alpha -= 20;
                if(alpha <= 20){
                    alpha = 20;
                }
                img_large.setAlpha(alpha);
                break;
            case R.id.btn_image_next:
                img_large.setImageBitmap(
                        BitmapFactory.decodeResource(
                                ImageViewActivity.this.getResources(), images[++currentImage % images.length]));
                break;
            default:
                break;
        }
    }

    public void changeImageSize(float scale){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img_large.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();
        //产生缩放后的Bitmap
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, true);
        img_large.setScaleType(ImageView.ScaleType.MATRIX);
        img_large.setImageBitmap(bitmap);
    }
}
