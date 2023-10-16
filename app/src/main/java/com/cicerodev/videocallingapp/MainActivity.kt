package com.cicerodev.videocallingapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import io.getstream.video.android.core.GEO
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.model.User
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiRW1wZXJvcl9QYWxwYXRpbmUiLCJpc3MiOiJwcm9udG8iLCJzdWIiOiJ1c2VyL0VtcGVyb3JfUGFscGF0aW5lIiwiaWF0IjoxNjk3NDU4OTA1LCJleHAiOjE2OTgwNjM3MTB9.qEHY_n3c5m2ZNwKua0k5Ac99zxm1cZfo5xc95rK_TBM"
        val userId = "Emperor_Palpatine"
        val callId = "IFUeFFstjs8q"

        // step1 - create a user.
        val user = User(
            id = userId, // any string
            name = "Cícero" // name and image are used in the UI
        )

        // step2 - initialize StreamVideo. For a production app we recommend adding the client to your Application class or di module.
        val client = StreamVideoBuilder(
            context = applicationContext,
            apiKey = "mmhfdzb5evj2", // demo API key
            geo = GEO.GlobalEdgeNetwork,
            user = user,
            token = userToken,
        ).build()

        // step3 - join a call, which type is `default` and id is `123`.
        val call = client.call("default", callId)
        lifecycleScope.launch {
            val result = call.join(create = true)
            result.onError {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
            }
        }

        setContent {
            // step4 - apply VideoTheme
            VideoTheme {
                // step5 - define required properties.
                CallContent(
                    modifier = Modifier.fillMaxSize(),
                    call = call,
                    onBackPressed = {onBackPressed()}
                )
            }
        }
    }
}