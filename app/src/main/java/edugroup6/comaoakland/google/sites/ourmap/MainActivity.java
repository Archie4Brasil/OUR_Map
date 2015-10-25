package edugroup6.comaoakland.google.sites.ourmap;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import java.io.IOException;
import java.util.List;

@SuppressWarnings( "deprecation" )
public class MainActivity extends ActionBarActivity implements GoogleApiClient.OnConnectionFailedListener {
private GoogleApiClient mGoogleApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout arViewPane = (FrameLayout) findViewById(R.id.ar_view_pane);

        ArDisplayView arDisplay = new ArDisplayView(this, this);
        arViewPane.addView(arDisplay);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addOnConnectionFailedListener(this)
                .build();

    }

    @Override
    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ArDisplayView extends SurfaceView implements SurfaceHolder.Callback
    {
        public static final String DEBUG_TAG = "ArDisplayView Log";
        Camera mCamera;
        SurfaceHolder mHolder;
        Activity mActivity;

        public ArDisplayView(Context context, Activity activity)
        {
            super(context);

            mActivity = activity;
            mHolder = getHolder();
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            mHolder.addCallback(this);

        }

        public void surfaceCreated(SurfaceHolder holder) {
            mCamera = Camera.open();

            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);
            int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0: degrees = 0; break;
                case Surface.ROTATION_90: degrees = 90; break;
                case Surface.ROTATION_180: degrees = 180; break;
                case Surface.ROTATION_270: degrees = 270; break;
            }
            mCamera.setDisplayOrientation((info.orientation - degrees + 360) % 360);

            try {
                mCamera.setPreviewDisplay(mHolder);
            } catch (IOException e) {
                Log.e(DEBUG_TAG, "surfaceCreated exception: ", e);
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
        {
            Camera.Parameters params = mCamera.getParameters();
            List<Camera.Size> prevSizes = params.getSupportedPreviewSizes();
            for (Camera.Size s : prevSizes)
            {
                if((s.height <= height) && (s.width <= width))
                {
                    params.setPreviewSize(s.width, s.height);
                    break;
                }
            }

            mCamera.setParameters(params);
            mCamera.startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            mCamera.stopPreview();
            mCamera.release();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {


    }



}
