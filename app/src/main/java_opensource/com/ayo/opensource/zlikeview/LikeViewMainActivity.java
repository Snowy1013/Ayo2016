package com.ayo.opensource.zlikeview;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;


public class LikeViewMainActivity extends BaseActivity implements OnLikeListener {

    LikeButton starButton;
    LikeButton likeButton;
    LikeButton thumbButton;
    LikeButton smileButton;

    private <T> T fid(int id){
        return (T)findViewById(id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.likeview_content_main);

        starButton = fid(R.id.star_button);
        likeButton = fid(R.id.heart_button);
        thumbButton = fid(R.id.thumb_button);
        smileButton = fid(R.id.smile_button);


        starButton.setOnLikeListener(this);
        likeButton.setOnLikeListener(this);
        smileButton.setOnLikeListener(this);
        thumbButton.setOnLikeListener(this);

        thumbButton.setLiked(true);

        usingCustomIcons();

    }

    public void usingCustomIcons() {

        //shown when the button is in its default state or when unLiked.
        smileButton.setUnlikeDrawable(new BitmapDrawable(getResources(), new IconicsDrawable(getActivity(), CommunityMaterial.Icon.cmd_emoticon).colorRes(android.R.color.darker_gray).sizeDp(25).toBitmap()));

        //shown when the button is liked!
        smileButton.setLikeDrawable(new BitmapDrawable(getResources(), new IconicsDrawable(getActivity(), CommunityMaterial.Icon.cmd_emoticon).colorRes(android.R.color.holo_purple).sizeDp(25).toBitmap()));

    }



    @Override
    public void liked(LikeButton likeButton) {
        Toast.makeText(getActivity(), "Liked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        Toast.makeText(getActivity(), "Disliked!", Toast.LENGTH_SHORT).show();
    }
}
