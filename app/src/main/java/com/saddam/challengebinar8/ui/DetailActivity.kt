package com.saddam.challengebinar8.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.saddam.challengebinar8.model.Movie
import com.saddam.challengebinar8.ui.theme.ChallengeBinar8

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeBinar8 {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val movie = intent.getParcelableExtra<Movie>("DATAMOVIE")!!
                    DisplayDetail(movie = movie)
                }
            }
        }
    }
}

@Composable
fun DisplayDetail(movie: Movie) {
    val posterBaseUrl = "https://image.tmdb.org/t/p/w500/"
    Column(
        modifier = Modifier
            .background(Color(0xFF292929))
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = rememberImagePainter(data = posterBaseUrl + movie.backdropPath),
            contentDescription = "",
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .fillMaxWidth()
                .height(400.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    PaddingValues(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 5.dp
                    )
                )
            )
            Divider(
                thickness = 2.dp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text(
                text = "Release date: ${movie.releaseDate}",
                color = Color.White,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(
                    PaddingValues(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    )
                )
            )
            Text(
                text = "Score: ${movie.voteAverage}",
                color = Color.White,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(
                    PaddingValues(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    )
                )
            )
            Text(
                text = "Overview: \n${movie.overview}",
                color = Color.White,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(
                    PaddingValues(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    )
                ),
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview4() {
    ChallengeBinar8 {

    }
}