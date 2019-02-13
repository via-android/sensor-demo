package com.morepranit.sensordemo;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor proximitySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String str = "";
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor :
                sensors) {
            str += sensor.getName() + "---" + sensor.getVendor() + "---" + sensor.getVersion() + "\n";
        }

        TextView textView = findViewById(R.id.tv_sensors);
        textView.setText(str);

        // proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (proximitySensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Random r = new Random(255);
        int red = (int) (sensorEvent.values[0] / 1000 * r.nextInt());
        int green = (int) (sensorEvent.values[0] / 1000 * r.nextInt());
        int blue = (int) (sensorEvent.values[0] / 1000 * r.nextInt());
        getWindow().getDecorView().setBackgroundColor(Color.rgb(red, green, blue));
        /*Log.e("onSensorChanged: ", sensorEvent.values[0] + " __ " + proximitySensor.getMaximumRange());
        if (sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        } else {
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        }*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
