package swa.pin.MyOwnBot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import swa.pin.MyOwnBot.ui.theme.MyOwnBotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ChatViewModel= viewModel()
            MyOwnBotTheme(darkTheme = false) {
                Scaffold(

                    topBar = {MyAppBar()},
                    modifier = Modifier.statusBarsPadding().fillMaxSize(),) { innerPadding ->
                    ChatPage(modifier = Modifier.padding(innerPadding),viewModel)
                }
            }
        }
    }
}

