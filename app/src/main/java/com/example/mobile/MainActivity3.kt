@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.mobile
import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.mobile.model.Category
import com.example.mobile.model.ReminderViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


    @Composable
    fun Home(
        viewModel: ReminderViewModel = viewModel()
    ) {
        val viewState by viewModel.state.collectAsState()
        val selectedCategory = viewState.selectedCategory
        if (viewState.categories.isNotEmpty() && selectedCategory != null) {
            Surface(modifier = Modifier.fillMaxSize()) {
                HomeContent(
                    selectedCategory = selectedCategory,
                    categories = viewState.categories,
                    onCategorySelected = viewModel::onCategorySelected
                )
            }
        }
    }
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun HomeContent(
        selectedCategory: Category,
        categories: List<Category>,
        onCategorySelected: (Category) -> Unit,
    ) {
        Scaffold(
            modifier = Modifier.padding(bottom = 24.dp),
            floatingActionButton = {
                val context = LocalContext.current
                FloatingActionButton(
                    onClick = {context.startActivity(Intent(context,MainActivity4::class.java))},
                    contentColor = Color.Black,
                    modifier = Modifier.padding(all = 20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxWidth()
            ) {
                val appBarColor = MaterialTheme.colors.secondary.copy(red = 0.99f)

                HomeAppBar(
                    backgroundColor = appBarColor,
                )

                CategoryTabs(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = onCategorySelected,
                )

                CategoryReminder(
                    modifier = Modifier.fillMaxSize(),
                    categoryId = selectedCategory.id
                )

            }
        }
    }

    @Composable
    private fun HomeAppBar(
        backgroundColor: Color
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Home",
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .heightIn(max = 24.dp)
                )
            },
            backgroundColor = backgroundColor,
            actions = {
                val context = LocalContext.current
                IconButton( onClick = {
                    context.startActivity(Intent(context,MainActivity::class.java))
                } ) {
                    Icon(imageVector = Icons.Filled.Lock, contentDescription = stringResource(R.string.lock))
                }
                IconButton( onClick = {
                    context.startActivity(Intent(context,MainActivity2::class.java))
                } ) {
                    Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = stringResource(R.string.account))
                }
            }
        )
    }
    @Composable
    private fun CategoryTabs(
        categories: List<Category>,
        selectedCategory: Category,
        onCategorySelected: (Category) -> Unit
    ) {
        val selectedIndex = categories.indexOfFirst { it == selectedCategory }
        ScrollableTabRow(
            selectedTabIndex = selectedIndex,
            edgePadding = 24.dp,
            indicator = emptyTabIndicator,
            modifier = Modifier.fillMaxWidth(),
        ) {
            categories.forEachIndexed { index, category ->
                Tab(
                    selected = index == selectedIndex,
                    onClick = { onCategorySelected(category) }
                ) {
                    ChoiceChipContent(
                        text = category.name,
                        selected = index == selectedIndex,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun ChoiceChipContent(
        text: String,
        selected: Boolean,
        modifier: Modifier = Modifier
    ) {
        Surface(
            color = when {
                selected -> MaterialTheme.colors.secondary.copy(red = 0.99f)
                else -> MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
            },
            contentColor = when {
                selected -> Color.Black
                else -> Color.Black
            },
            shape = MaterialTheme.shapes.small,
            modifier = modifier
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }

    private val emptyTabIndicator: @Composable (List<TabPosition>) -> Unit = {}