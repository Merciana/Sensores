package com.example.sensorproximacao

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.sensorproximacao.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var sensorManager: SensorManager
    private lateinit var Proximidade1: Sensor
    private lateinit var Luminosidade1: Sensor
    private lateinit var Acelerometro1: Sensor
    private lateinit var Giroscopio1: Sensor

    private var Proximidade2: SensoresDta? = null
    private var Luminosidade2: SensoresDta? = null
    private var Acelerometro2: SensoresDta? = null
    private var Giroscopio2: SensoresDta? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        CondicaoSensor()
    }

    private fun CondicaoSensor() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            Acelerometro1 = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            Luminosidade1 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            Proximidade1 = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            Giroscopio1 = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        }
        binding.Iniciar.setOnClickListener{
            DelayListener()
            binding.Iniciar.isEnabled = false
            binding.Parar.isEnabled = true
        }
        binding.Parar.setOnClickListener{
            unregisterListener()
            binding.Iniciar.isEnabled = true
            binding.Parar.isEnabled = false
        }
    }


    private fun DelayListener(){
        if(sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null){
            sensorManager.registerListener(this, Acelerometro1, SensorManager.SENSOR_DELAY_FASTEST)
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
            sensorManager.registerListener(this, Luminosidade1, SensorManager.SENSOR_DELAY_FASTEST)
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
            sensorManager.registerListener(this, Proximidade1, SensorManager.SENSOR_DELAY_FASTEST)
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            sensorManager.registerListener(this, Giroscopio1, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }
    private fun unregisterListener(){
        sensorManager.unregisterListener(this, Proximidade1)
        sensorManager.unregisterListener(this, Luminosidade1)
        sensorManager.unregisterListener(this, Acelerometro1)
        sensorManager.unregisterListener(this, Giroscopio1)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event !!.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION){
            getAcelerometro(event)
        }else if(event.sensor.type == Sensor.TYPE_LIGHT){
            getLuminosidade(event)
        }else if(event.sensor.type == Sensor.TYPE_PROXIMITY){
            getProximidade(event)
        }else if(event.sensor.type == Sensor.TYPE_GYROSCOPE){
            getGiroscopio(event)
        }

    }

    private fun getGiroscopio(event: SensorEvent ?) {
        if(Giroscopio2 == null){
            Giroscopio2 = SensoresDta(event !!.values[0], event.values[1], event.values[2], event.timestamp)
        }else{
            Giroscopio2 !!.x = event !!.values[0]
            Giroscopio2 !!.y = event.values[1]
            Giroscopio2 !!.z = event.values[2]
        }
        binding.xGiroscopioR.text = "X: ${"%.2f".format(Giroscopio2 !!.x)} /s"
        binding.yGiroscopioR.text = "Y: ${"%.2f".format(Giroscopio2 !!.y)} /s"
        binding.zGiroscopioR.text = "Z: ${"%.2f".format(Giroscopio2 !!.z)} /s"
    }

    private fun getProximidade(event: SensorEvent ?) {
        if(Proximidade2 == null){
            Proximidade2 = SensoresDta(event!!.values[0], event.timestamp)
        }else{
            Proximidade2!!.x = event!!.values[0]
        }

        binding.ProximidadeR.text = "Distância: ${"%.2f".format(Proximidade2!!.x)} cm"
    }

    private fun getLuminosidade(event: SensorEvent ?) {
        if(Luminosidade2 == null){
            Luminosidade2 = SensoresDta(event !!.values[0], event.timestamp)
        }else{
            Luminosidade2 !!.x = event !!.values[0]
        }

        binding.LuminosidadeR.text = "Intensidade: ${"%.2f".format(Luminosidade2!!.x)} lux"
    }

    private fun getAcelerometro(event: SensorEvent ?) {
        if(Acelerometro2 == null){
            Acelerometro2 = SensoresDta(event !!.values[0], event.values[1], event.values[2], event.timestamp)
        }else{
            Acelerometro2 !!.x = event !!.values[0]
            Acelerometro2 !!.y = event.values[1]
            Acelerometro2 !!.z = event.values[2]
        }
        binding.xAcelerometroR.text = "X: ${"%.2f".format(Acelerometro2 !!.x)} m/s^2"
        binding.yAcelerometroR.text = "Y: ${"%.2f".format(Acelerometro2 !!.y)} m/s^2"
        binding.zAcelerometroR.text = "Z: ${"%.2f".format(Acelerometro2 !!.z)} m/s^2"
    }

}