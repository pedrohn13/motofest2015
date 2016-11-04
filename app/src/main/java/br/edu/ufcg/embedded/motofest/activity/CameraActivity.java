package br.edu.ufcg.embedded.motofest.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.utils.CameraFile;


public class CameraActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "Camera Activity";
    private static final int NOVENTA_GRAUS_HORARIO = 90;
    private static final int NOVENTA_GRAUS_ANTI_HORARIO = -90;
    private static final double TRINTA_PORCENTO = 0.3;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int POSICAO_MOLDURA = 4;
    private static final int INIT_SELECTION = 7;

    private int mCurrentCamera;
    private boolean flashmode = false;


    private Camera mCamera;
    private ImageButton flashButton;
    private CameraPreview mPreview;
    private FrameLayout mPreviewContainer;
    private ImageView ivMoldura;
    private static final int[] MOLDURAS = {R.drawable.app_motofest_xxxhdpi_molduras_0004_moldura_04_c,
            R.drawable.app_motofest_xxxhdpi_molduras_0003_moldura_03_c,
            R.drawable.app_motofest_xxxhdpi_molduras_0002_moldura_02_c,
            R.drawable.app_motofest_xxxhdpi_molduras_0001_moldura_01_c,
            R.drawable.app_motofest_xxxhdpi_molduras_0000_moldura_00_c};
    private static final int[] MOLDURAS_PEQUENAS = {R.drawable.app_motofest_xxxhdpi_molduras_0004_moldura_04_small,
            R.drawable.app_motofest_xxxhdpi_molduras_0003_moldura_03_small,
            R.drawable.app_motofest_xxxhdpi_molduras_0002_moldura_02_small,
            R.drawable.app_motofest_xxxhdpi_molduras_0001_moldura_01_small,
            R.drawable.app_motofest_xxxhdpi_molduras_0000_moldura_00_small};
    private int posicaoAtualDaMoldura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.camera_activity);

        posicaoAtualDaMoldura = POSICAO_MOLDURA;

        findViewById(R.id.button_capture).setOnClickListener(this);
        findViewById(R.id.button_switch_camera).setOnClickListener(this);
        findViewById(R.id.button_import_image).setOnClickListener(this);
        flashButton = (ImageButton) findViewById(R.id.button_flash);
        flashButton.setOnClickListener(this);
        if (!getBaseContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            flashButton.setVisibility(View.VISIBLE);
        }
        findViewById(R.id.button_close).setOnClickListener(this);
        mPreviewContainer = (FrameLayout) findViewById(R.id.camera_preview);
        ivMoldura = (ImageView) findViewById(R.id.moldura);

        ivMoldura.setBackgroundResource(MOLDURAS[posicaoAtualDaMoldura]);

        ListView listview = (ListView) findViewById(R.id.lv_molduras);
        listview.setAdapter(mAdapter);
        listview.setSelection(INIT_SELECTION);


        if (!checkCameraHardware(this)) {
            showToast(getString(R.string.no_camera));
            return;
        }

        mCurrentCamera = Camera.CameraInfo.CAMERA_FACING_BACK;

        mCamera = getCameraInstance(mCurrentCamera);

        if (mCamera == null) {
            return;
        }


    }

    private void switchCamera() {

        if (mCamera != null) {
            mCamera.stopPreview(); // stop preview
            mCamera.release(); // release previous camera
        }

        if (mCurrentCamera == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mCurrentCamera = Camera.CameraInfo.CAMERA_FACING_FRONT;
            ((ImageButton) findViewById(R.id.button_switch_camera)).setImageResource(R.drawable.ic_capture_cam1);
        } else {
            mCurrentCamera = Camera.CameraInfo.CAMERA_FACING_BACK;
            ((ImageButton) findViewById(R.id.button_switch_camera)).setImageResource(R.drawable.ic_capture_cam2);
        }

        // Create an instance of Camera
        mCamera = getCameraInstance(mCurrentCamera);

        if (mCamera == null) {
            return;
        }

        mPreview = new CameraPreview(this, mCamera);
        mPreviewContainer.removeAllViews();
        mPreviewContainer.addView(mPreview);
    }


    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    /**
     * Check if device has camera
     */
    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private Camera getCameraInstance(int type) {
        Camera camera = null;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = getNewCameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == type) {
                try {
                    camera = Camera.open(i); // attempt to get a Camera instance
                } catch (Exception e) {
                    // Camera is not available
                    showToast(getString(R.string.camera_not_found));
                }
                break;
            }
        }
        return camera;
    }

    private Camera.CameraInfo getNewCameraInfo() {
        return new Camera.CameraInfo();
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {


            File pictureFile = CameraFile.getOutputMediaFile();
            if (pictureFile == null) {
                Log.d(TAG,
                        "Error creating media file, check storage permissions.");
                showToast(getString(R.string.error_create_media));
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                Matrix matrix = new Matrix();
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(data, 0, data.length);

                Bitmap bitmap = Bitmap.createBitmap(mPreview.getHeight(), mPreview.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);

                int alturaInicialBitmap1 = (bitmap1.getWidth() - bitmap1.getHeight()) / 2;

                if (mCurrentCamera == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    alturaInicialBitmap1 = alturaInicialBitmap1 - (int) (alturaInicialBitmap1 * TRINTA_PORCENTO);
                } else {
                    alturaInicialBitmap1 = alturaInicialBitmap1 + (int) (alturaInicialBitmap1 * TRINTA_PORCENTO);
                    matrix.preScale(-1.0f, 1.0f);
                }
                matrix.postRotate(NOVENTA_GRAUS_HORARIO);


                bitmap1 = Bitmap.createBitmap(bitmap1, alturaInicialBitmap1, 0, bitmap1.getHeight(), bitmap1.getHeight(), matrix, true);

                Drawable drawable1 = new BitmapDrawable(bitmap1);
                drawable1.setBounds(0, 0, mPreview.getHeight(), mPreview.getHeight());
                drawable1.draw(canvas);

                ByteArrayOutputStream blob = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, blob);
                data = blob.toByteArray();

                fos.write(data);
                fos.close();

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(pictureFile);
                mediaScanIntent.setData(contentUri);
                CameraActivity.this.sendBroadcast(mediaScanIntent);


            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }

            Intent myIntent = new Intent(CameraActivity.this, ImageActivity.class);
            myIntent.putExtra("PICTURE_FILE", pictureFile.toString());
            myIntent.putExtra("MOLDURA_POSICAO", (mAdapter.getCount() - 1) - posicaoAtualDaMoldura);
            CameraActivity.this.startActivity(myIntent);

            onPause();
            onResume();
        }


    };

    private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
            TextView toast = (TextView) findViewById(R.id.toastText);
            toast.setText(getText(R.string.informacao));
            toast.setVisibility(View.VISIBLE);

            // do stuff like playing shutter sound here
        }
    };

    private Camera.ShutterCallback getmShutterCallback() {
        return mShutterCallback;
    }

    private void captureImage() {
        mCamera.takePicture(getmShutterCallback(), null, mPicture);

    }

    @Override
    protected void onPause() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mPreview.getHolder().removeCallback(mPreview);
            mCamera.release();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        TextView progressBar = (TextView) findViewById(R.id.toastText);
        progressBar.setVisibility(View.INVISIBLE);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera != null) {
            mCamera.release(); // release previous camera
        }

        mCamera = getCameraInstance(mCurrentCamera);

        if (mCamera == null) {
            return;
        }

        mPreview = new CameraPreview(this, mCamera);
        mPreviewContainer.removeAllViews();
        mPreviewContainer.addView(mPreview);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.button_switch_camera) {
            switchCamera();
        } else if (viewId == R.id.button_capture) {
            captureImage();
        } else if (viewId == R.id.button_import_image) {
            importImage();
        } else if (viewId == R.id.button_flash) {
            flashOnButton();
        } else if (viewId == R.id.button_close) {
            onBackPressed();
        }
    }

    private void flashOnButton() {
        if (mCamera != null) {
            try {
                Camera.Parameters param = mCamera.getParameters();
                if (!flashmode) {
                    param.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                    flashButton.setImageResource(R.drawable.ic_flash_on);
                } else {
                    param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    flashButton.setImageResource(R.drawable.ic_flash_off);
                }
                mCamera.setParameters(param);
                flashmode = !flashmode;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void importImage() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mCamera != null) {
            mCamera.release(); // release the camera for other applications
            mCamera = null;
        }
    }

    private BaseAdapter mAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return MOLDURAS.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image, parent, false);
            ImageView image = (ImageView) retval.findViewById(R.id.image);
            image.setImageResource(MOLDURAS_PEQUENAS[position]);
            image.setRotation(NOVENTA_GRAUS_ANTI_HORARIO);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    posicaoAtualDaMoldura = position;
                    ivMoldura.setBackgroundResource(MOLDURAS[position]);
                }
            });
            return retval;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Intent myIntent = new Intent(CameraActivity.this, ImageActivity.class);
            myIntent.putExtra("PICTURE_FILE", picturePath);
            myIntent.putExtra("IMPORTED", true);
            myIntent.putExtra("MOLDURA_POSICAO", (mAdapter.getCount() - 1) - posicaoAtualDaMoldura);
            CameraActivity.this.startActivity(myIntent);


        }
    }

    private void showFlashButton(Camera.Parameters params) {
        boolean showFlash = (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH) && params.getFlashMode() != null)
                && params.getSupportedFlashModes() != null
                && params.getSupportedFocusModes().size() > 1;

        flashButton.setVisibility(showFlash ? View.VISIBLE : View.INVISIBLE);

    }

    private class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

        private static final String TAG = "Camera Preview";

        private final SurfaceHolder mHolder;
        private final Camera mCamera;

        @SuppressWarnings("deprecation")
        public CameraPreview(Context context, Camera camera) {
            super(context);
            mCamera = camera;

            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

        }

        public void surfaceCreated(SurfaceHolder holder) {
            try {
                if (mCamera != null) {
                    mCamera.setPreviewDisplay(holder);
                    Camera.Parameters params = mCamera.getParameters();
                    showFlashButton(params);
                    List<String> focusModes = params.getSupportedFlashModes();
                    if (focusModes != null && focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                        params.setFlashMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                    }
                    if (mCurrentCamera == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                        try {
                            List<Camera.Size> sizes = params.getSupportedPreviewSizes();
                            Camera.Size optimalSize = getOptimalPreviewSize(sizes,
                                    getResources().getDisplayMetrics().widthPixels,
                                    getResources().getDisplayMetrics().heightPixels);
                            params.setPreviewSize(optimalSize.width, optimalSize.height);
                            mCamera.setParameters(params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mCamera.startPreview();
                }
            } catch (IOException exception) {
                Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
            }
        }

        private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int width, int height) {
            final double aspectTolerance = 0.75;
            double targetRatio = (double) width / height;

            if (sizes == null) {
                return null;
            }

            Camera.Size size1 = null;

            double minDiff = Double.MAX_VALUE;

            int targetHeight = height;

            // Find size
            for (Camera.Size size : sizes) {
                double ratio = (double) size.width / size.height;
                if (Math.abs(ratio - targetRatio) > aspectTolerance) {
                    continue;
                }
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    size1 = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }

            if (size1 == null) {
                minDiff = Double.MAX_VALUE;
                for (Camera.Size size : sizes) {
                    if (Math.abs(size.height - targetHeight) < minDiff) {
                        size1 = size;
                        minDiff = Math.abs(size.height - targetHeight);
                    }
                }
            }
            return size1;
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }


    }

}