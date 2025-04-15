package com.example.coop3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coop3.ui.theme.Coop3Theme
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.ui.input.nestedscroll.nestedScroll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.ui.graphics.RectangleShape

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Coop3Theme {
                RefreshDemo()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshDemo() {
    val pullRefreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    var items by remember { mutableStateOf(generateItems()) }
    var isRefreshing by remember { mutableStateOf(false) }

    fun refreshData() {
        coroutineScope.launch {
            isRefreshing = true
            delay(1000)
            items = generateItems()
            isRefreshing = false
            pullRefreshState.endRefresh()
        }
    }

    if (pullRefreshState.isRefreshing) {
        refreshData()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullRefreshState.nestedScrollConnection)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (isRefreshing) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { item ->
                    ItemCard(item)
                }
            }
        }

        Button(
            onClick = { refreshData() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            enabled = !isRefreshing
        ) {
            Text("Hit Me")
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullRefreshState,
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(item: Item) {
    var currentValue by remember(item.value) { mutableStateOf(item.value) }
    var isSwiping by remember { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState()

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (isSwiping) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    }
                    Text(
                        text = "Swipe to refresh",
                        color = Color.White
                    )
                }
            }
        },
        content = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                shape = RectangleShape // This removes rounded corners
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Value: $currentValue",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    )

    LaunchedEffect(dismissState.currentValue) {
        when (dismissState.currentValue) {
            SwipeToDismissBoxValue.EndToStart -> {
                isSwiping = true
                delay(300)
                currentValue = (0..100).random()

                delay(150)
                dismissState.reset()
                isSwiping = false
            }
            else -> {  }
        }
    }
}

data class Item(val id: Int, val title: String, val value: Int)

fun generateItems(): List<Item> {
    return List(10) { index ->
        Item(
            id = index,
            title = "Item ${index + 1}",
            value = (0..100).random()
        )
    }
}
