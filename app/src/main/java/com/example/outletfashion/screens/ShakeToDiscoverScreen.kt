
package com.example.outletfashion.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.SystemClock
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.outletfashion.data.model.Product
import kotlin.math.sqrt

@Composable
fun ShakeToDiscoverScreen(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    val sensorManager = remember(context) {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val accelerometer = remember(sensorManager) {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    var discoveredProduct by remember {
        mutableStateOf<Product?>(null)
    }

    val currentProducts by rememberUpdatedState(products)

    fun discoverProduct() {
        val available = currentProducts
        if (available.isEmpty()) return

        val alternatives = available.filter {
            it.id != discoveredProduct?.id
        }

        discoveredProduct = (
                if (alternatives.isNotEmpty()) alternatives
                else available
                ).random()
    }

    val onShakeDetected by rememberUpdatedState {
        discoverProduct()
    }

    DisposableEffect(sensorManager, accelerometer) {
        val gravity = FloatArray(3)
        var initialized = false

        var impulseCount = 0
        var firstImpulseTime = 0L
        var lastImpulseTime = 0L
        var lastDiscoveryTime = 0L

        val listener = object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) {
                    return
                }

                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                if (!initialized) {
                    gravity[0] = x
                    gravity[1] = y
                    gravity[2] = z
                    initialized = true
                    return
                }

                val alpha = 0.90f

                gravity[0] = alpha * gravity[0] + (1f - alpha) * x
                gravity[1] = alpha * gravity[1] + (1f - alpha) * y
                gravity[2] = alpha * gravity[2] + (1f - alpha) * z

                val linearX = x - gravity[0]
                val linearY = y - gravity[1]
                val linearZ = z - gravity[2]

                val strength = sqrt(
                    linearX * linearX +
                            linearY * linearY +
                            linearZ * linearZ
                )

                val now = SystemClock.elapsedRealtime()

                if (now - lastDiscoveryTime < 1800L) {
                    return
                }

                if (impulseCount > 0 &&
                    now - firstImpulseTime > 1500L
                ) {
                    impulseCount = 0
                }

                if (
                    strength > 5.0f &&
                    now - lastImpulseTime > 180L
                ) {
                    if (impulseCount == 0) {
                        firstImpulseTime = now
                    }

                    impulseCount++
                    lastImpulseTime = now

                    if (impulseCount >= 3) {
                        impulseCount = 0
                        lastDiscoveryTime = now
                        onShakeDetected()
                    }
                }
            }

            override fun onAccuracyChanged(
                sensor: Sensor?,
                accuracy: Int
            ) = Unit
        }

        if (accelerometer != null) {
            sensorManager.registerListener(
                listener,
                accelerometer,
                SensorManager.SENSOR_DELAY_GAME
            )
        }

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Text("← BACK")
            }

            Text(
                text = "GALAXY CLUB",
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(Modifier.height(48.dp))

        Icon(
            imageVector = Icons.Outlined.PhoneAndroid,
            contentDescription = null,
            modifier = Modifier.size(52.dp)
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "SHAKE TO DISCOVER",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Shake your phone to unlock your next fashion discovery.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(36.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(Modifier.height(16.dp))

                if (discoveredProduct == null) {
                    Text(
                        text = "YOUR NEXT DISCOVERY",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "A surprise from the Outlet Galaxy collection is waiting for you.",
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "YOUR GALAXY PICK",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = discoveredProduct!!.name,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {
                            discoveredProduct?.let(onProductClick)
                        }
                    ) {
                        Text("VIEW PRODUCT")
                    }
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        Button(
            onClick = { discoverProduct() },
            modifier = Modifier.fillMaxWidth(),
            enabled = products.isNotEmpty()
        ) {
            Text("SURPRISE ME")
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = if (accelerometer == null) {
                "Shake detection is unavailable. Use the button above."
            } else {
                "Shake your phone or tap the button to discover something new."
            },
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))
    }
}
