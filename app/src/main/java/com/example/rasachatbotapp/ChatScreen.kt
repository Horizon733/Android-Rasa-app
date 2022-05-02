package com.example.rasachatbotapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.rasachatbotapp.destinations.OpenUrlDestination
import com.example.rasachatbotapp.network.Carousel
import com.example.rasachatbotapp.network.Message
import com.example.rasachatbotapp.network.RasaButton
import com.example.rasachatbotapp.network.UrlButton
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.text.SimpleDateFormat
import java.util.*

val message = mutableStateOf("")

val openUrl = mutableStateOf(false)

private val BotChatBubbleShape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 8.dp)
private val AuthorChatBubbleShape = RoundedCornerShape(8.dp, 0.dp, 8.dp, 8.dp)

@Composable
fun TopBarSection(
    username: String,
    profile: Painter?=null,
    isOnline: Boolean?=null,
    navigator: DestinationsNavigator
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
            if(profile != null) {
                Image(
                    painter = profile,
                    contentDescription = null,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                )
            }else{
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier.clickable {
                        navigator.popBackStack()
                        openUrl.value = false
                    }
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = username, fontWeight = FontWeight.SemiBold)
                if (isOnline != null) {
                    Text(
                        text = if (isOnline) "Online" else "Offline",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ChatSection(
    modifier: Modifier = Modifier,
    viewModel: MainActivityViewModel?=null,
    chats: List<Message>? = null,
    navigator: DestinationsNavigator
) {
    val simpleDateFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 6.dp),
        reverseLayout = true
    ) {
        if (chats == null){
            items(viewModel!!.messages) { chat ->
                MessageItem(
                    messageText = chat.text,
                    time = simpleDateFormat.format(chat.time),
                    isOut = chat.isOut,
                    image = chat.image,
                    buttons = chat.buttons,
                    carousels = chat.carousels,
                    viewModel = viewModel,
                    navigator = navigator
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
                    image = chat.image,
                    buttons = chat.buttons,
                    carousels = chat.carousels,
                    navigator = navigator
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }


    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MessageItem(
    messageText: String?,
    time: String,
    image: String?,
    buttons: List<RasaButton>?,
    carousels: List<Carousel>?,
    viewModel: MainActivityViewModel?=null,
    isOut: Boolean,
    navigator: DestinationsNavigator
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
        if (image != null) {
            if (image.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    color = if (isOut) MaterialTheme.colors.primary else Color(0xFF616161),
                    shape = if (isOut) AuthorChatBubbleShape else BotChatBubbleShape
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = image
                        ),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(160.dp),
                        contentDescription = "attached image"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        if(buttons != null){
            ShowButtons(buttons, viewModel!!)
        }
        Spacer(modifier = Modifier.height(4.dp))
        if(carousels != null){
            ShowCarousels(
                carousels,
                viewModel,
                navigator
            )
        }

        Text(
            text = time,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 8.dp)
        )


    }
}

@Composable
fun ShowButtons(
    buttons: List<RasaButton>,
    viewModel: MainActivityViewModel?
){
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(buttons){ button ->
            Button(
                onClick = {
                    viewModel!!.sendMessagetoRasa(
                        Message(
                            text = button.payload,
                            recipient_id = viewModel!!.username,
                            time = Calendar.getInstance().time,
                            isOut = true
                        )
                    )
                },
                modifier = Modifier.clip(RoundedCornerShape(20.dp))
            ){
                Text(
                    button.title
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}



@OptIn(ExperimentalCoilApi::class)
@Composable
fun ShowCarousels(
    carousels: List<Carousel>,
    viewModel: MainActivityViewModel?,
    navigator: DestinationsNavigator
){
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(carousels){ carousel ->
            CarouselItem(single_carousel = carousel, viewModel = viewModel, navigator = navigator)
            Spacer(modifier = Modifier.width(8.dp))
        }

    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CarouselItem(
    single_carousel: Carousel,
    viewModel: MainActivityViewModel?,
    navigator: DestinationsNavigator
){
    Column(
        modifier = Modifier.border(
            width = 1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Image(
            painter = rememberImagePainter(
                single_carousel.image
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = single_carousel.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = single_carousel.subtitle,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Divider(
            color = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        if(single_carousel.button != null) {
            TextButton(
                onClick = {
                    viewModel!!.sendMessagetoRasa(
                        Message(
                            text = single_carousel.button.payload,
                            recipient_id = viewModel.username,
                            time = Calendar.getInstance().time,
                            isOut = true
                        )
                    )
                },
                modifier = Modifier.width(200.dp)
            ){
                Text(
                    single_carousel.button.title
                )
            }

        }
        if(single_carousel.link != null){
            TextButton(
                onClick = { openUrl.value = true },
                modifier = Modifier.width(200.dp)
            ){
                Text(
                    single_carousel.link.title
                )
            }
            if (openUrl.value){
                val context = LocalContext.current
                navigator.navigate(direction = OpenUrlDestination(link = single_carousel.link.link))
            }
        }
    }
}
