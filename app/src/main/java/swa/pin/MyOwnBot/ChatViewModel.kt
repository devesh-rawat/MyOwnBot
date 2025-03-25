package swa.pin.MyOwnBot

import android.util.Log
import androidx.compose.runtime.mutableStateListOf

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {

    val messageList  by lazy {
        mutableStateListOf<MessageModel>()
    }
    val generativeModel= GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = constants.apikey

    )

    fun sendMessage(question:String){
        viewModelScope.launch {
            try {
                val chat=generativeModel.startChat(
                    history = messageList.map {
                        content (it.role){
                            text(it.Message)
                        }
                    }.toList()
                )
                messageList.add(MessageModel(question.toString().trim(),"user"))
                messageList.add(MessageModel("Typing","model"))

                val response=chat.sendMessage(question)
                messageList.removeAt(messageList.lastIndex)
                messageList.add(MessageModel(response.text.toString().trim(),"model"))

            }catch (e: Exception){
                messageList.removeAt(messageList.lastIndex)
                messageList.add(MessageModel("Error : "+e.message.toString(), role = "model"))
            }

        }
    }
}