package com.example.rasachatbotapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.rasachatbotapp.network.Message
import com.example.rasachatbotapp.ui.theme.RasaChatbotAppTheme
import java.text.SimpleDateFormat
import java.util.*

val message = mutableStateOf("")

private val BotChatBubbleShape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 8.dp)
private val AuthorChatBubbleShape = RoundedCornerShape(8.dp, 0.dp, 8.dp, 8.dp)

@Composable
fun TopBarSection(
    username: String,
    profile: Painter,
    isOnline: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        backgroundColor = Color(0xFFFAFAFA),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = profile,
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = username, fontWeight = FontWeight.SemiBold)
                Text(
                    text = if (isOnline) "Online" else "Offline",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun ChatSection(
    modifier: Modifier = Modifier,
    viewModel: MainActivityViewModel,
    chats: List<Message>? = null
) {
    val simpleDateFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        reverseLayout = true
    ) {
        if (chats == null){
            items(viewModel.messages) { chat ->
                MessageItem(
                    messageText = chat.text,
                    time = simpleDateFormat.format(chat.time),
                    isOut = chat.isOut,
                    viewModel = viewModel
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        else if (chats != null){
            items(chats) { chat ->
                MessageItem(
                    messageText = chat.text,
                    time = simpleDateFormat.format(chat.time),
                    isOut = chat.isOut,
                    viewModel = viewModel
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }


    }
}

@Composable
fun MessageItem(
    messageText: String?,
    time: String,
    viewModel: MainActivityViewModel,
    isOut: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isOut) Alignment.End else Alignment.Start
    ) {
        if (messageText != null) {
            if (messageText != "") {
                Box(
                    modifier = Modifier
                        .background(
                            if (isOut) MaterialTheme.colors.primary else Color(0xFF616161),
                            shape = if (isOut) AuthorChatBubbleShape else BotChatBubbleShape
                        )
                        .padding(
                            top = 8.dp,
                            bottom = 8.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                ) {

                    Text(
                        text = messageText,
                        color = Color.White
                    )
                }

            }
        }

        Text(
            text = time,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp)
        )


    }
}


