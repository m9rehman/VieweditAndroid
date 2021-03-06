package com.example.mrehman.vieweditandroid;

import android.*;
import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.PixelFormat;
import android.media.MediaRecorder;
import android.media.session.MediaController;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.widget.VideoView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;


public class MainActivity extends AppCompatActivity {




    private LinearLayout mlinlayout;
    private final static int CAMERA_RQ = 6969;



    private final static String TAG = "Camera2testJ";
    private Size mPreviewSize;

    private TextureView mTextureView;
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mPreviewBuilder;
    private CameraCaptureSession mPreviewSession;

//    MediaRecorder mMediaRecorderLow = new MediaRecorder();
//    MediaRecorder mMediaRecorderHigh = new MediaRecorder();
//    CaptureRequest mCaptureRequest;
//    CameraCaptureSession mSession;
//    boolean recording = false;

    private ImageButton mBtnShot;
    private ImageButton mLibBtn;
    private ImageButton mNotifBtn;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    public void removeFragment(){
        getSupportFragmentManager().beginTransaction().remove(
                getSupportFragmentManager().findFragmentById(R.id.fragment_container)).commit();

    }

//    private static final int VIDEO_CAPTURE = 101;
//    Uri videoUri;
//    public void startRecordingVideo() {
//        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
//            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//            File mediaFile = new File(
//                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvideo.mp4");
//            videoUri = Uri.fromFile(mediaFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
//            startActivityForResult(intent, VIDEO_CAPTURE);
//        } else {
//            Toast.makeText(this, "No camera on device", Toast.LENGTH_LONG).show();
//        }
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        if (requestCode == VIDEO_CAPTURE) {
////            if (resultCode == RESULT_OK) {
////                Toast.makeText(this, "Video has been saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
////                playbackRecordedVideo();
////            } else if (resultCode == RESULT_CANCELED) {
////                Toast.makeText(this, "Video recording cancelled.",  Toast.LENGTH_LONG).show();
////            } else {
////                Toast.makeText(this, "Failed to record video",  Toast.LENGTH_LONG).show();
////            }
////        }
//        Toast.makeText(this,"ACTIVITYRESULT",Toast.LENGTH_SHORT).show();
//    }
//
//    public void playbackRecordedVideo() {
////        VideoView mVideoView = (VideoView) findViewById(R.id.video_view);
////        mVideoView.setVideoURI(videoUri);
////        mVideoView.setMediaController(new MediaController(this));
////        mVideoView.requestFocus();
////        mVideoView.start();
//
//        Toast.makeText(this,"PLAYBACK",Toast.LENGTH_SHORT).show();
//    }

    // [BEGIN: VIDEO RECORDING]



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final FragmentManager fm = getSupportFragmentManager();
        final Fragment fragment = fm.findFragmentById(R.id.fragment_container);



        //Linear Layout Swiping Functionality
        mlinlayout = (LinearLayout) findViewById(R.id.linlaymain);
        mlinlayout.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {

            public void onSwipeRight() {
//                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                if (fragment == null) {
                    Fragment fragmentRight = new LibraryFragment();
                    fm.beginTransaction()
                            .add(R.id.fragment_container, fragmentRight)
                            .commit();
                }
            }
            public void onSwipeLeft() {

//                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                if (fragment == null) {
                    Fragment fragmentLeft = new NotificationsFragment();
                    fm.beginTransaction()
                            .add(R.id.fragment_container, fragmentLeft)
                            .commit();
                }
            }
            public void onSwipeBottom() {
//                Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                if (fragment == null) {
                    Fragment fragmentTop = new CameraFragment();
                    fm.beginTransaction()
                            .replace(R.id.fragment_container, fragmentTop)
                            .commit();
                }
            }

        });

        mTextureView = (TextureView)findViewById(R.id.texture);
        mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);

        mBtnShot = (ImageButton)findViewById(R.id.btn_takepicture);
        mLibBtn = (ImageButton) findViewById(R.id.btn_lib);
        mNotifBtn = (ImageButton) findViewById(R.id.btn_notif);

        mLibBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment == null) {
                    Fragment fragmentRight = new LibraryFragment();
                    fm.beginTransaction()
                            .add(R.id.fragment_container, fragmentRight)
                            .setCustomAnimations(R.anim.slide_right, R.anim.slide_left_exit).commit();

                }

            }
        });

        mNotifBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fragment == null) {
                    Fragment fragmentRight = new NotificationsFragment();
                    fm.beginTransaction()
                            .add(R.id.fragment_container, fragmentRight)
                            .commit();
                }

            }
        });



        mBtnShot.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.e(TAG, "mBtnShot clicked");
//                takePicture();
//                startRecordingVideo();
                Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                String path = Environment.getExternalStorageDirectory().getPath()+"videocapture_example.mp4";
                videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, path);
                Log.d(TAG,path);
                startActivityForResult(videoIntent,0);
//                Toast.makeText(MainActivity.this,path,Toast.LENGTH_LONG).show();
                String token = FirebaseInstanceId.getInstance().getToken();

                Log.d("POO",token);
//                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
            }

        });

    }



    protected void takePicture() {
        Log.e(TAG, "takePicture");
        if(null == mCameraDevice) {
            Log.e(TAG, "mCameraDevice is null, return");
            return;
        }

        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(mCameraDevice.getId());

            Size[] jpegSizes = null;
            if (characteristics != null) {
                jpegSizes = characteristics
                        .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                        .getOutputSizes(ImageFormat.JPEG);
            }
            int width = 640;
            int height = 480;
            if (jpegSizes != null && 0 < jpegSizes.length) {
                width = jpegSizes[0].getWidth();
                height = jpegSizes[0].getHeight();
            }

            ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
            List<Surface> outputSurfaces = new ArrayList<Surface>(2);
            outputSurfaces.add(reader.getSurface());
            outputSurfaces.add(new Surface(mTextureView.getSurfaceTexture()));

            final CaptureRequest.Builder captureBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

            // Orientation
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));

            final File file = new File(Environment.getExternalStorageDirectory()+"/DCIM", "pic.jpg");

            ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {

                @Override
                public void onImageAvailable(ImageReader reader) {

                    Image image = null;
                    try {
                        image = reader.acquireLatestImage();
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        byte[] bytes = new byte[buffer.capacity()];
                        buffer.get(bytes);
                        save(bytes);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (image != null) {
                            image.close();
                        }
                    }
                }

                private void save(byte[] bytes) throws IOException {
                    OutputStream output = null;
                    try {
                        output = new FileOutputStream(file);
                        output.write(bytes);
                    } finally {
                        if (null != output) {
                            output.close();
                        }
                    }
                }

            };

            HandlerThread thread = new HandlerThread("CameraPicture");
            thread.start();
            final Handler backgroudHandler = new Handler(thread.getLooper());
            reader.setOnImageAvailableListener(readerListener, backgroudHandler);

            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {

                @Override
                public void onCaptureCompleted(CameraCaptureSession session,
                                               CaptureRequest request, TotalCaptureResult result) {

                    super.onCaptureCompleted(session, request, result);
//                    Toast.makeText(MainActivity.this, "Saved:"+file, Toast.LENGTH_SHORT).show();
                    startPreview();
                }

            };

            mCameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {

                @Override
                public void onConfigured(CameraCaptureSession session) {

                    try {
                        session.capture(captureBuilder.build(), captureListener, backgroudHandler);
                    } catch (CameraAccessException e) {

                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {

                }
            }, backgroudHandler);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    private void openCamera() {

        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        Log.e(TAG, "openCamera E");
        try {
            String cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            mPreviewSize = map.getOutputSizes(SurfaceTexture.class)[0];

            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

                manager.openCamera(cameraId, mStateCallback, null);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},333
                        );
            }


        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "openCamera X");
    }

    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener(){

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            Log.e(TAG, "onSurfaceTextureAvailable, width="+width+",height="+height);
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface,
                                                int width, int height) {
            Log.e(TAG, "onSurfaceTextureSizeChanged");
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            //Log.e(TAG, "onSurfaceTextureUpdated");
        }

    };

    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(CameraDevice camera) {

            Log.e(TAG, "onOpened");
            mCameraDevice = camera;
            startPreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {

            Log.e(TAG, "onDisconnected");
        }

        @Override
        public void onError(CameraDevice camera, int error) {

            Log.e(TAG, "onError");
        }

    };

    @Override
    protected void onPause() {

        Log.e(TAG, "onPause");
        super.onPause();
        if (null != mCameraDevice) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }

    protected void startPreview() {

        if(null == mCameraDevice || !mTextureView.isAvailable() || null == mPreviewSize) {
            Log.e(TAG, "startPreview fail, return");
            return;
        }

        SurfaceTexture texture = mTextureView.getSurfaceTexture();
        if(null == texture) {
            Log.e(TAG,"texture is null, return");
            return;
        }

        texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
        Surface surface = new Surface(texture);

        try {
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        } catch (CameraAccessException e) {

            e.printStackTrace();
        }
        mPreviewBuilder.addTarget(surface);

        try {
            mCameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {

                @Override
                public void onConfigured(CameraCaptureSession session) {

                    mPreviewSession = session;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {

//                    Toast.makeText(MainActivity.this, "onConfigureFailed", Toast.LENGTH_LONG).show();
                }
            }, null);
        } catch (CameraAccessException e) {

            e.printStackTrace();
        }
    }

    protected void updatePreview() {

        if(null == mCameraDevice) {
            Log.e(TAG, "updatePreview error, return");
        }

        mPreviewBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        HandlerThread thread = new HandlerThread("CameraPreview");
        thread.start();
        Handler backgroundHandler = new Handler(thread.getLooper());

        try {
            mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null, backgroundHandler);
        } catch (CameraAccessException e) {

            e.printStackTrace();
        }
    }


}

