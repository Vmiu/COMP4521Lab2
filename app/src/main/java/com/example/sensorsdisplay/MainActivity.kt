package com.example.sensorsdisplay

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.getSystemService
import com.example.sensorsdisplay.ui.theme.SensorsDisplayTheme

class MainActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SensorsDisplayTheme {
                sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
                val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
                SensorsDisplay(deviceSensors)
            }
        }
    }
}

@Composable
fun SensorsDisplay(sensorsList: List<Sensor>,modifier: Modifier = Modifier) {
    LazyColumn (modifier = modifier){
        items(sensorsList){ sensor ->
            Text(
                text = "\n"+sensor.toString()
            )
        }
    }
}