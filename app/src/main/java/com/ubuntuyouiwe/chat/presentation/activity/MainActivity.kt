package com.ubuntuyouiwe.chat.presentation.activity

import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chuckerteam.chucker.api.Chucker
import com.squareup.seismic.ShakeDetector
import com.ubuntuyouiwe.chat.BuildConfig
import com.ubuntuyouiwe.chat.presentation.navigation.NavHostScreen
import com.ubuntuyouiwe.chat.presentation.navigation.Screen
import com.ubuntuyouiwe.chat.ui.theme.ChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), ShakeDetector.Listener {
    private companion object {
        const val BUILD_TYPE_RELEASE = "release"
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            ChatTheme {
                val viewModel: MainActivityViewModel = hiltViewModel()
                val state by viewModel.stateAuth.collectAsStateWithLifecycle()
                val startDestination = if (state.isLoading) Screen.SPLASH
                else if (state.success != null) Screen.MAIN
                else Screen.LOGIN
                installSplashScreen().apply {
                    this.setKeepOnScreenCondition {
                        state.isLoading
                    }
                }

                NavHostScreen(startDestination = startDestination)
            }
            if (!BUILD_TYPE_RELEASE.equals(BuildConfig.BUILD_TYPE, true)) {
                val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                val shakeDetector = ShakeDetector(this)
                shakeDetector.start(sensorManager, SensorManager.SENSOR_DELAY_GAME)
            }

        }
    }
    override fun hearShake() {
        startActivity(Chucker.getLaunchIntent(this))
    }

}
