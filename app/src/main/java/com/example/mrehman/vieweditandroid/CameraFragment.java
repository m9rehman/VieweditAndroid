package com.example.mrehman.vieweditandroid;


import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;


public class CameraFragment extends Fragment implements TextureView.SurfaceTextureListener {

    private Cursor videoCursor;
    private MediaPlayer mediaPlayer;
    private Surface surface;
    private ImageButton mShareBtn;

    // Log tag.
    private static final String TAG = CameraFragment.class.getName();

    // Asset video file name.
    private static  String FILE_NAME = "";

    // MediaPlayer instance to control playback of video file.
    private MediaPlayer mMediaPlayer;


    private RelativeLayout mMainView;
    private TextureView mTextureView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMainView = (RelativeLayout) getView().findViewById(R.id.mainrelview);
        mMainView.setOnTouchListener(new OnSwipeTouchListener(getActivity()){

            public void onSwipeTop() {
//                Toast.makeText(getActivity(), "BACK", Toast.LENGTH_SHORT).show();
//                getActivity().onBackPressed();
                ((MainActivity)getActivity()).removeFragment();
            }

        });

        mShareBtn = (ImageButton) getView().findViewById(R.id.shareBtn);
        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareVideo();
            }
        });
        initView();
        String videoPath = returnPathVideo();

//        mTextureView = (TextureView) getView().findViewById(R.id.textureView);
//        mTextureView.setSurfaceTextureListener(this);


    }

    public void shareVideo(){
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("video/mp4");

        String videoPath = FILE_NAME;

        File videoFileToShare = new File(videoPath);

        Uri uri = Uri.fromFile(videoFileToShare);
        share.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(share, "New Viewedit Video :)"));

    }


    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
//        Toast.makeText(getActivity(),returnPathVideo(),Toast.LENGTH_SHORT).show();



    }

    // [VIDEO PLAYER]
    private void initView() {

        TextureView textureView = (TextureView) getView().findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public String returnPathVideo(){
        String path="OK";
        String[] projection = new String[]{
                MediaStore.Video.VideoColumns._ID,
                MediaStore.Video.VideoColumns.DATA,
                MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Video.VideoColumns.DATE_TAKEN

        };
        try {
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permissionCheck== PackageManager.PERMISSION_GRANTED) {
                ;
                } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},333
                );
            }
             videoCursor = getContext().getContentResolver()
                    .query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null,
                            null, MediaStore.Video.VideoColumns.DATE_TAKEN + " DESC");

            if(videoCursor!=null && videoCursor.moveToFirst()) {
                path = videoCursor.getString(videoCursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA));
            }
        } catch(Exception e){
            Log.e("TAG",e.toString());
        }

//        Toast.makeText(getActivity(),path,Toast.LENGTH_SHORT).show();
        FILE_NAME = path;
        Log.d("HO",FILE_NAME);
        return path;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        surface = new Surface(surfaceTexture);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setSurface(surface);

        try {

            mediaPlayer.reset();
            mediaPlayer.setDataSource(FILE_NAME);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (Exception e){
            e.printStackTrace();
        }





    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }


}
