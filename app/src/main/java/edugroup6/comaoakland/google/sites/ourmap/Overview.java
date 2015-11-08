package edugroup6.comaoakland.google.sites.ourmap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

/**
 * Created by Steven on 10/13/2015.
 */
public class Overview extends View implements SensorEventListener {
    public static final String DEBUG_TAG = "Overlay View";
    String Placesdata = "This is:";
    String accelData = "Accelerometer Data:";
    String gyroData = "Gyroscope Data:";
    String magData = "Magnetic Field Data:";

    public Overview(Context context){
        super(context);
        SensorManager sensors = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelSensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor compassSensor = sensors.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor gyroSensor = sensors.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        boolean isAccelAvailable = sensors.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        boolean isCompassAvailable = sensors.registerListener(this, compassSensor, SensorManager.SENSOR_DELAY_NORMAL);
        boolean isGyroAvailable = sensors.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void onSensorChanged(SensorEvent event){
            StringBuilder message = new StringBuilder(event.sensor.getName()).append(" ");
            for(float value: event.values){
                 message.append("[").append(value).append("]");
            }
        switch(event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                 accelData=message.toString();
             break;
            case Sensor.TYPE_GYROSCOPE:
                 gyroData=message.toString();
             break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                  magData=message.toString();
             break;
        }
        this.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Paint contentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        contentPaint.setTextSize(30);
        contentPaint.setTextAlign(Paint.Align.CENTER);
        contentPaint.setColor(Color.rgb(255,215,0));

        canvas.drawText("+",canvas.getWidth()/2,canvas.getHeight()/2,contentPaint);
        canvas.drawText(accelData,canvas.getWidth()/2,canvas.getHeight()/3,contentPaint);
        canvas.drawText(magData,canvas.getWidth()/2,canvas.getHeight()/4,contentPaint);
        canvas.drawText(gyroData,canvas.getWidth()/2,canvas.getHeight()/5,contentPaint);
    }


}
