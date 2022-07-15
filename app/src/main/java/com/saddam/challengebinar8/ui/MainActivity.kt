package com.saddam.challengebinar8.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.saddam.challengebinar8.R
import com.saddam.challengebinar8.datastore.UserLoginManager
import com.saddam.challengebinar8.model.Movie
import com.saddam.challengebinar8.ui.theme.ChallengeBinar8
import com.saddam.challengebinar8.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChallengeBinar8 {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF292929)),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModelMovie = viewModel(modelClass = MovieViewModel::class.java)
                    val dataMovie by viewModelMovie.dataMovieState.collectAsState()
                    val mContext = LocalContext.current
                    val userLoginManager = UserLoginManager(mContext)
                    var username by remember {
                        mutableStateOf("")
                    }
                    val isShowAlert = remember { mutableStateOf(false) }

                    userLoginManager.username.asLiveData().observe(this) {
                        username = it
                    }

                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painterResource(id = R.drawable.ic_person),
                                contentDescription = "",
                            )
                            Text(
                                text = "Hello, $username",
                                color = Color.White,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Text(
                                text = "LOGOUT",
                                color = Color.Red,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        isShowAlert.value = true
                                    }
                            )
                        }
                        LazyVerticalGrid(cells = GridCells.Adaptive(170.dp),
                            contentPadding = PaddingValues(
                                start = 8.dp,
                                top = 12.dp,
                                end = 8.dp,
                                bottom = 12.dp
                            ),
                            content = {
                                if (dataMovie.isEmpty()) {
                                    item {
                                    }
                                } else {
                                    items(dataMovie) {
                                        DisplayFilmList(movie = it)
                                    }
                                }
                            }
                        )
                    }
                    if (isShowAlert.value) {
                        AlertDialogView(state = isShowAlert)
                    }
                }
            }
        }
    }
}

@Composable
fun AlertDialogView(state: MutableState<Boolean>) {
    CommonDialog(title = "Logout", state = state) {
        Text(text = "Anda yakin ingin logout?")
    }
}

@Composable
fun CommonDialog(
    title: String?,
    state: MutableState<Boolean>,
    content: @Composable (() -> Unit)? = null
) {
    val mContext = LocalContext.current
    val userLoginManager = UserLoginManager(mContext)

    AlertDialog(
        onDismissRequest = { state.value = false },
        title = title?.let {
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = title)
                    Divider(modifier = Modifier.padding(bottom = 8.dp))
                }
            }
        },
        text = content,
        dismissButton = {
            Button(onClick = {
                state.value = false
            }) {
                Text(text = "Tidak")
            }
        },
        confirmButton = {
            Button(onClick = {
                GlobalScope.launch {
                    userLoginManager.clearDataLogin()
                }
                Toast.makeText(mContext, "Berhasil logout", Toast.LENGTH_SHORT).show()
                state.value = false
                mContext.startActivity(Intent(mContext, LoginActivity::class.java))
            }) {
                Text(text = "Ya")
            }
        }
    )
}

@Composable
fun DisplayFilmList(movie: Movie) {
    val posterBaseUrl = "https://image.tmdb.org/t/p/w500/"
    val mContext = LocalContext.current
    Column(modifier = Modifier
        .clip(RoundedCornerShape(25.dp))
        .padding(3.dp)
        .background(Color(0xFF292929))) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clickable {
                    val intent = Intent(mContext, DetailActivity::class.java)
                    intent.putExtra("DATAMOVIE", movie)
                    mContext.startActivity(intent)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.card_background),
                contentDescription = "jpg",
                contentScale = ContentScale.Crop,
                alpha = 0.6F
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Image(
                    painter = rememberImagePainter(data = posterBaseUrl + movie.posterPath),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = movie.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Score: ${movie.voteAverage}",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ChallengeBinar8 {
        DisplayFilmList(
            movie = Movie(
                "overview",
                "title",
                "posterpath",
                "backdroppath",
                "release date",
                1232.3,
                7.89,
                32,
                1089
            )
        )
    }
}