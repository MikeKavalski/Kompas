 package com.example.kompas

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

 @Suppress("DEPRECATION")
 class MainActivity : AppCompatActivity(), SensorEventListener {
    var manager:SensorManager? = null           //keeps sensor manager as null for the begining
    var current_degree:Int = 0                  //keeps the degree value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager    //makes manager not null as was previously stated but real manager that manages the sensors

    }

    override fun onResume() {                  //to register of the listener
        super.onResume()
        manager?.registerListener(this, manager?.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME)      //registration of the listener
    }

    override fun onPause() {                //to switch off the registration of the listener to disable listener to avoid battery consume during the call for example
        super.onPause()
        manager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {           //to get data from the sensor
        val degree: Int = event?.values?.get(0)?.toInt()!!
        var degreeText =
            this.findViewById<TextView>(R.id.textDegree)  //without this issue doesn't work
        degreeText.text = degree.toString()   //set data from sensor to the TextView

        val rotationAnimation = RotateAnimation(
            current_degree.toFloat(), (-degree).toFloat(),
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )   //animate the images to move relatively each other. Relatively the middle of the image (0.5F for x and y)
        rotationAnimation.duration = 210 // duration of animation 210 ms
        rotationAnimation.fillAfter = true
        current_degree = -degree    //to rotate - start position and end position every time??

        val rotimage = this.findViewById <ImageView>(R.id.imDinamic)  //without this issue doesn't work
        rotimage.startAnimation(rotationAnimation)  //adjust the animation to the image

    }
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}