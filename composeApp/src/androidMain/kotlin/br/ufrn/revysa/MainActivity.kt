package br.ufrn.revysa

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    private var requestId by mutableIntStateOf(0)
    private var incomingUri by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestId = savedInstanceState?.getInt("deepLinkRequestId") ?: 0
        incomingUri = intent.dataString
        setContent { App(deepLink = incomingUri, deepLinkRequestId = requestId) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("deepLinkRequestId", requestId)
        super.onSaveInstanceState(outState)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        incomingUri = intent.dataString
        requestId++
    }
}
