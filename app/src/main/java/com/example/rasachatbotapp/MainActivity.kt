package com.example.rasachatbotapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.example.rasachatbotapp.network.Message
import com.example.rasachatbotapp.ui.theme.RasaChatbotAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            RasaChatbotAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainActivityViewModel = MainActivityViewModel()) {
    val context = LocalContext.current
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    viewModel._connectivityState.value = activeNetwork != null && activeNetwork.isConnected
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopBarSection(
            username = "Bot",
            profile = painterResource(id=R.drawable.gojo),
            isOnline = viewModel._connectivityState.value
        )
        ChatSection(Modifier.weight(1f), viewModel)
        MessageSection(viewModel)
    }

}

@Composable
fun MessageSection(
    viewModel: MainActivityViewModel,
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Color.White,
        elevation = 10.dp
    ) {
        OutlinedTextField(
            placeholder = {
                Text("Message..")
            },
            value = message.value,
            onValueChange = {
                message.value = it
            },
            shape = RoundedCornerShape(25.dp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_send),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.clickable {
                        if (viewModel._connectivityState.value){
                            viewModel.sendMessagetoRasa(
                                Message(
                                    text = message.value,
                                    recipient_id = viewModel.username,
                                    time = Calendar.getInstance().time,
                                    isOut = true
                                )
                            )
                        }else{
                            Toast.makeText(context, "Please connect to internet", Toast.LENGTH_SHORT).show()
                        }

                        message.value = ""
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RasaChatbotAppTheme {
        MainScreen()
    }
}