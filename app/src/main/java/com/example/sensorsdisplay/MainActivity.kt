package com.example.sensorsdisplay

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.HorizontalDivider

import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensors = listOf<Sensor>()
    private val sensorValues = mutableStateListOf<Pair<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)

        sensors.forEach { sensor ->
            sensorValues.add(Pair(sensor.name, ""))
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        setContent {
            SensorDisplay(sensorValues)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val sensor = it.sensor
            val value = it.values.joinToString(", ") { v -> "%.2f".format(v) }
            val index = sensors.indexOfFirst { it.name == sensor.name }
            if (index != -1) {
                sensorValues[index] = Pair(sensor.name, value)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onDestroy() {
        super.onDestroy()
        sensors.forEach { sensorManager.unregisterListener(this, it) }
    }
}

@Composable
fun SensorDisplay(sensorData: List<Pair<String, String>>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(sensorData) { (name, value) ->
            Text(text = "Sensor: $name")
            Text(text = "Values: $value")
            HorizontalDivider()
        }
    }
}