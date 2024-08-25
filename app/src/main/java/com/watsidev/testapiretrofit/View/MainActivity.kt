package com.watsidev.testapiretrofit.View

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.watsidev.testapiretrofit.ui.theme.TestAPIRetrofitTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.squareup.picasso.Picasso
import com.watsidev.testapiretrofit.R
import com.watsidev.testapiretrofit.ViewModel.PokemonViewModel
import com.watsidev.testapiretrofit.pokemoncard.PokemonCard
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAPIRetrofitTheme {
                Surface {
                    PokemonListScreen()
                }
            }
        }
    }
}

@Composable
fun PokemonListScreen(viewModel: PokemonViewModel = viewModel()) {
    val pokemones = viewModel.pokemonsDetail.value

    Column {
        Header()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(pokemones) { pokemon ->
                PokemonItem(
                    number = "#${pokemon.id.toString().padStart(4, '0')}",
                    name = pokemon.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    types = pokemon.types.map { it.type.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    } },
                    imageRes = pokemon.sprites.other.home.front_default
                )
            }
        }
    }
}


@Composable
fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray)
            .height(75.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "PokeDex",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}


@Composable
fun PokemonItem(number: String, name: String, types: List<String>, imageRes: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .padding(vertical = 4.dp),
//        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.LightGray)
                .padding(8.dp)
        ) {
            LoadImageWithPicasso(url = imageRes)
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = number,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
            Column (
                modifier = Modifier
                    .padding(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                types.forEach { type ->
                    TypeChip(type = type)
                }
            }
        }
    }
}


@Composable
fun TypeChip(type: String) {
    val backgroundColor = when (type) {
        "Grass" -> Color(0xFF3DA224)
        "Poison" -> Color(0xFF923FCC)
        "Fire" -> Color(0xFFE72324)
        "Flying" -> Color(0xFF82BAF0)
        "Water" -> Color(0xFF2481F0)
        "Bug" -> Color(0xFF92A212)
        "Steel" -> Color(0xFF60A2B9)
        "Dragon" -> Color(0xFF4F60E2)
        "Electric" -> Color(0xFFFAC100)
        "Ghost" -> Color(0xFF713F71)
        "Ice" -> Color(0xFF3DD9FF)
        "Fighting" -> Color(0xFFFF8100)
        "Normal" -> Color(0xFFA0A2A0)
        "Psychic" -> Color(0xFFEF3F7A)
        "Rock" -> Color(0xFFB0AB82)
        "Dark" -> Color(0xFF4F3F3D)
        "Fairy" -> Color(0xFFEF71F0)
        "Ground" -> Color(0xFF92501B)
        else -> Color.Gray
    }
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .background(backgroundColor, shape = RoundedCornerShape(25))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = type,
            fontSize = 12.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(50.dp)
        )
    }
    Spacer(modifier = Modifier.padding(8.dp))
}


@Composable
fun LoadImageWithPicasso(url: String) {
    Box(
        modifier = Modifier
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(factory = { context ->
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP // Equivalente a ContentScale.Crop
                Picasso.get()
                    .load(url)
                    .into(this)
            }
        },
            modifier = Modifier
                .width(95.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewList() {
    PokemonListScreen()
}