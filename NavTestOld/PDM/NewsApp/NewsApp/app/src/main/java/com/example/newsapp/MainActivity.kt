package com.example.newsapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.newsapp.presentation.news_list.ArticleListViewModel
import com.example.newsapp.ui.theme.NewsAppTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.news_list.ArticleDetailsViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedArticleUrl by remember { mutableStateOf<String?>(null) }

    if (selectedArticleUrl == null){
        val articlesListViewModel: ArticleListViewModel = viewModel()
        ArticleListScreen(modifier, articlesListViewModel){ articleUrl ->
            selectedArticleUrl = articleUrl
        }
    }else{
        val articleDetailViewModel: ArticleDetailsViewModel = viewModel()
        ArticleDetailScreen(modifier,articleDetailViewModel, selectedArticleUrl!!){
            selectedArticleUrl = null
        }
    }
}

//@Composable
//fun ScreenConstaint(){
//    ConstraintLayout(){
//
//    }
//}



@Composable
fun ArticleDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ArticleDetailsViewModel,
    articleUrl: String,
    onBackPressed: () -> Unit
) {
    // Chama fetchCoinDetail uma vez quando a CoinDetailScreen é composta
    LaunchedEffect(articleUrl) {
        viewModel.fetchArticleDetails(articleUrl)  // Chama o método para buscar os detalhes da moeda
    }

    // Observa o estado do CoinDetail
    val articleDetail = viewModel.articleDetails.collectAsState().value

    // Verifica se os dados estão carregados
    if (articleDetail == null) {
        Column (modifier = modifier
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ){

            CircularProgressIndicator(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
            )
            Button(
                onClick = onBackPressed,
                //modifier = Modifier.align()
            ) {
                Text("Back")
            }
        }

    } else {
        // Exibe os detalhes da moeda
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = articleDetail.snippet,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Description:",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Text(
                text = articleDetail.leadParagraph,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Botão de voltar
            Button(
                onClick = onBackPressed,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Back")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(
    modifier: Modifier = Modifier,
    viewModel: ArticleListViewModel = viewModel(),
    onArticleSelected: (String) -> Unit
) {

    // Chama fetchCoins uma vez quando a CoinListScreen é composta
    LaunchedEffect(Unit) {
        viewModel.fetchArticles()
    }

    // Observa a lista de moedas do ViewModel
    val articlesList = viewModel.articles.collectAsState()

    Column (modifier = modifier){
        TopAppBar(
            modifier = Modifier
                .weight(0.05f)
                .fillMaxSize()
            ,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Box(modifier = Modifier
                    .fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ){
                    Text("Centered Top Bar", modifier = Modifier.align(Alignment.TopCenter))
                }
                //Text("Centered Top Bar", modifier = Modifier.align())
            }
        )

        LazyColumn(
            modifier = Modifier
                .weight(0.95f)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(articlesList.value) { article ->
                ArticleItem(article = article, onArticleSelected = onArticleSelected)
            }
        }
    }
}


@Composable
fun ArticleItem(article: Article, onArticleSelected: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onArticleSelected(article.url) }
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Cyan
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = article.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "section: ${article.section}",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsAppTheme {
        Greeting("Android")
    }
}