package swa.pin.MyOwnBot

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import swa.pin.MyOwnBot.ui.theme.MyOwnBotTheme
import swa.pin.MyOwnBot.ui.theme.app_color
import swa.pin.MyOwnBot.ui.theme.modelColor
import swa.pin.MyOwnBot.ui.theme.my_app_color
import swa.pin.MyOwnBot.ui.theme.userColor

@Composable
fun ChatPage(modifier: Modifier = Modifier, viewModel: ChatViewModel) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            MessageList(
                messageList = viewModel.messageList
            )
        }
        MessageInput(onMessageSend = {
            viewModel.sendMessage(it)
        })
    }
}


@Composable
fun MessageInput(onMessageSend:(String)->Unit) {
    var input by remember {
        mutableStateOf(
            ""
        )
    }
    Row (
        modifier= Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,

    ){

        OutlinedTextField(
            modifier= Modifier
                .fillMaxWidth()
                .weight(1f),
            value = input,
            placeholder = {Text("Ask me Anything?")},
            onValueChange = {input=it}
        )
        IconButton({
            if(input.isNotEmpty()){
                onMessageSend(input)
            }
            input=""

        }) {
            Icon(Icons.Default.Send, contentDescription = null)
        }
    }
    
}

@Preview(showSystemUi = true,)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        navigationIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = Color.White,modifier= Modifier.size(32.dp)) },
        title = { Text("DevBot",modifier= Modifier.padding(start = 16.dp), style =
            TextStyle(
                color = Color.White,
                fontFamily = FontFamily.Serif,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,

            )) },
        colors = TopAppBarDefaults.topAppBarColors(app_color)

    )

}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel=messageModel.role=="model"
    Row (verticalAlignment = Alignment.CenterVertically){
        Box(modifier = Modifier.fillMaxWidth()){
            Box(modifier = Modifier.align(if(isModel) Alignment.BottomStart else Alignment.BottomEnd).padding(
                top = 8.dp,
                bottom = 8.dp,
                start = if(isModel){
                    8.dp
                }else 70.dp,
                end = if(isModel) 70.dp else 8.dp
            ).clip(RoundedCornerShape(25f))
                .background(if(isModel) modelColor else userColor).padding(8.dp)){
                SelectionContainer {
                    Text(messageModel.Message.toString(), fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }

}


@Composable
fun MessageList(messageList: List<MessageModel>) {
    if (messageList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(R.drawable.baseline_message_24),
                    contentDescription = null,
                    tint = my_app_color
                )
                Text("Ask Me Anything?", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            reverseLayout = true
        ) {
            items(messageList.reversed()) {
                MessageRow(it)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChatPagePreview() {
    MyOwnBotTheme {
        ChatPage(viewModel= viewModel())
    }
}