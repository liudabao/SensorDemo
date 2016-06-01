package com.example.sensordemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.GpsStatus.Listener;
import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	Button btn_1;
	Button btn_2;
	Button btn_3;
	Button btn_4;
    TextView tv;
    TextView tv2;
	ImageView picture;
	SensorManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView(){
		btn_1=(Button)findViewById(R.id.button1);
		btn_2=(Button)findViewById(R.id.button2);
		btn_3=(Button)findViewById(R.id.button3);
		picture=(ImageView)findViewById(R.id.imageView1);
		tv=(TextView)findViewById(R.id.textView1);
		tv2=(TextView)findViewById(R.id.textView2);
		manager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		btn_1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				lightSensor();
			}
		});
		
		btn_2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				acceSensor();
			}
		});
		
		btn_3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				degreeSensor();
			}
		});
	}
	
	private void lightSensor(){
		Sensor sensor=manager.getDefaultSensor(Sensor.TYPE_LIGHT);
		manager.registerListener(listener1, sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	private void acceSensor(){
		Sensor sensor=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		manager.registerListener(listener1, sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	private void degreeSensor(){
		Sensor sensor1=manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		Sensor sensor2=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		manager.registerListener(listener2, sensor1, SensorManager.SENSOR_DELAY_NORMAL);
		manager.registerListener(listener2, sensor2, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		if(listener1!=null){
			manager.unregisterListener(listener1);
		}
		if(listener2!=null){
			manager.unregisterListener(listener2);
		}
	}
	
	private SensorEventListener listener1=new SensorEventListener(){

		@Override
		public void onAccuracyChanged(Sensor sensor, int value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			if(event.sensor.getType()==Sensor.TYPE_LIGHT){
				tv.setText("light is :"+event.values[0]);
			}
			else if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
				if(Math.abs(event.values[0])>15&&Math.abs(event.values[1])>15&&Math.abs(event.values[1])>15){
					ObjectAnimator.ofFloat(picture, "alpha", 1, 0)
					.setDuration(3000)
					.start();
				}
			}
			
		}
		
	};
	private SensorEventListener listener2=new SensorEventListener(){
		float acc[]=new float[3];
		float man[]=new float[3];
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			
			if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
				man=event.values.clone();
			}
			else if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
				acc=event.values.clone();
			}
			float[] R=new float[9];
			float[] values=new float[3];
			SensorManager.getRotationMatrix(R, null, acc, man);
			SensorManager.getOrientation(R, values);
			tv2.setText("degree is "+Math.toDegrees(values[0]));
			
		}
		
	};


}
