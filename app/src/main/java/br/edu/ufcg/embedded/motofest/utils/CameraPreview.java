package br.edu.ufcg.embedded.motofest.utils;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;


public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "Camera Preview";
    private static final double ASPECT_TOLERANCE = 0.1;

    private final SurfaceHolder mHolder;
    private final Camera mCamera;
    private static  Size mPreviewSize;
    private final List<Size> mSupportedPreviewSizes;

    @SuppressWarnings("deprecation")
    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mSupportedPreviewSizes = mCamera.getParameters()
                .getSupportedPreviewSizes();

        setIfAutoFocusSupported();
    }

    public static Size getmPreviewSize() {
        return mPreviewSize;
    }

    public void initPreview() {
        if (mCamera == null) {
            return;
        }

        Parameters parameters = mCamera.getParameters();
        parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);

        mCamera.setParameters(parameters);
        mCamera.startPreview();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (mCamera != null) {
                mCamera.setPreviewDisplay(holder);
            }
        } catch (IOException exception) {
            Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private Size getOptimalPreviewSize(List<Size> sizes, int width, int height) {
        double targetRatio = (double) width / height;
        if (sizes == null) {
            return null;
        }
        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = height;

        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
                continue;
            }
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mHolder.getSurface() == null) {
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
                        Log.d(TAG,
                                "Error stop preview");
        }

        if (mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
        }

        // start preview with new settings
        try {

            mCamera.setPreviewDisplay(mHolder);

            Parameters params = mCamera.getParameters();
            params.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
//            params.setPictureSize(mPreviewSize.width, mPreviewSize.height);
            mCamera.setParameters(params);
            mCamera.startPreview();

            Log.d(TAG, "Preview Size: " + mPreviewSize.width + "X"
                    + mPreviewSize.height);

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }

    }


    private void setIfAutoFocusSupported() {
        // get Camera parameters
        Parameters params = mCamera.getParameters();

        List<String> focusModes = params.getSupportedFocusModes();
        if (focusModes.contains(Parameters.FOCUS_MODE_AUTO)) {
            params.setFocusMode(Parameters.FOCUS_MODE_AUTO);
            mCamera.setParameters(params);
        }
    }

}