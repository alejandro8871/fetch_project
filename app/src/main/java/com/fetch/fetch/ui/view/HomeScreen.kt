package com.fetch.fetch.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fetch.fetch.data.model.Hiring
import com.fetch.fetch.data.remote.HiringUiState

@Composable
fun HomeScreen(paddingValues: PaddingValues, viewModel: HomeViewModel = hiltViewModel()) {
    val fetchUiState by viewModel.fetchHiring.collectAsStateWithLifecycle()

    // Preserve scroll position
    val listState = rememberLazyListState()
    when (fetchUiState) {
        is HiringUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is HiringUiState.Success -> {

            val sortedItems = remember((fetchUiState as HiringUiState.Success).items) {
                (fetchUiState as HiringUiState.Success).items.groupBy { it.listId }
            }
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                sortedItems.forEach { (listId, items) ->
                    item {
                        Text(
                            text = "List ID: $listId",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    items(items.size, key = { items[it].id }) {
                        HiringItem(items[it])
                    }
                }
            }
        }

        is HiringUiState.Error -> {
            val errorMessage = (fetchUiState as HiringUiState.Error).message
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: $errorMessage", color = Color.Red)
            }
        }
    }

}

@Composable
fun HiringItem(item: Hiring) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp)), elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.name, fontSize = 14.sp, modifier = Modifier.weight(1f)
            )
        }
    }
}