package com.example.mrehman.vieweditandroid;


import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;


public class CameraFragment extends Fragment {

//    // Log tag.
//    private static final String TAG = CameraFragment.class.getName();
//
//    // Asset video file name.
//    private static final String FILE_NAME = "0videocapture_example.mp4";
//
//    // MediaPlayer instance to control playback of video file.
//    private MediaPlayer mMediaPlayer;


    private RelativeLayout mMainView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMainView = (RelativeLayout) getView().findViewById(R.id.mainrelview);
        mMainView.setOnTouchListener(new OnSwipeTouchListener(getActivity()){

            public void onSwipeTop() {
                Toast.makeText(getActivity(), "BACK", Toast.LENGTH_SHORT).show();
//                getActivity().onBackPressed();
                ((MainActivity)getActivity()).removeFragment();
            }

        });
//        initView();

    }


    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);

    }

    // [VIDEO PLAYER]
//    private void initView() {
//
//        TextureView textureView = (TextureView) getView().findViewById(R.id.textureView);
//        textureView.setSurfaceTextureListener(this);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mMediaPlayer != null) {
//            mMediaPlayer.stop();
//            mMediaPlayer.release();
//            mMediaPlayer = null;
//        }
//    }
//
//    @Override
//    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
//        Surface surface = new Surface(surfaceTexture);
//
//        try {
//            AssetFileDescriptor afd = getActivity().getAssets().openFd(FILE_NAME);
//            mMediaPlayer = new MediaPlayer();
//            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//            mMediaPlayer.setSurface(surface);
//            mMediaPlayer.setLooping(true);
//            mMediaPlayer.prepareAsync();
//
//            // Play video when the media source is ready for playback.
//            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mediaPlayer) {
//                    mediaPlayer.start();
//                }
//            });
//
//        } catch (IllegalArgumentException e) {
//            Log.d(TAG, e.getMessage());
//        } catch (SecurityException e) {
//            Log.d(TAG, e.getMessage());
//        } catch (IllegalStateException e) {
//            Log.d(TAG, e.getMessage());
//        } catch (IOException e) {
//            Log.d(TAG, e.getMessage());
//        }
//    }
//
//    @Override
//    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
//    }
//
//    @Override
//    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
//        return true;
//    }
//
//    @Override
//    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
//    }


}
