package com.example.wishlistapp

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FixedThreshold
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.layout.LocalPinnableContainer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlistapp.data.DummyWish
import com.example.wishlistapp.data.Wish
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StartView(
    navController: NavController,
    viewModel: WishViewModel,
){
    val context = LocalContext.current
    val searchQuery = remember{ mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()

    Scaffold(
        topBar = { AppBarView(title = "WishList App", { Toast.makeText(context,"Back Clicked",Toast.LENGTH_SHORT).show() }) },
        floatingActionButton = {
            //Add Button
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddScreen.route + "/0L")
                    Toast.makeText(context,"FAB Clicked",Toast.LENGTH_SHORT).show() },
                contentColor = Color.White,
                modifier = Modifier.padding(8.dp),
                backgroundColor = colorResource(id = R.color.fab_color)
                ){
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }

            //Delete Button
            FloatingActionButton(
                onClick = { Toast.makeText(context,"All Wishes Cleared",Toast.LENGTH_SHORT).show()
                          viewModel.deleteAllWishes()},
                contentColor = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .absoluteOffset(y = -70.dp),
                backgroundColor = colorResource(id = R.color.fab_color)
            ){
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    ) {

        Column(modifier = Modifier.fillMaxSize())
        {

            TextField(value = searchQuery.value,
                onValueChange = {
                    searchQuery.value = it
                    viewModel.searchWishes(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Search")})

            Spacer(modifier = Modifier.height(5.dp))

            val wishList : List<Wish> =
                if(searchQuery.value.isEmpty()){
                    viewModel.getAllWishes.collectAsState(initial = listOf()).value
                }else{
                    searchResults
                }

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(it)) {
                items(wishList, key = {wish -> wish.id}){
                        wish ->
                    ///////////////////////////////////////////////////////////////
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if(it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd){
                                viewModel.deleteWish(wish)
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val color by animateColorAsState(
                                if(dismissState.dismissDirection == DismissDirection.EndToStart){
                                    Color.Red
                                }else{
                                    Color.Transparent
                                }
                            )
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ){
                                Icon(Icons.Default.Delete, contentDescription = "Delete Icon", tint = Color.White)
                            }
                        },
                        directions = setOf(DismissDirection.EndToStart),
                        dismissContent = {
                            WishItemLayout(wish = wish,
                                onClick = { navController.navigate(Screen.AddScreen.route + "/${wish.id}") }
                            )
                        },
                        dismissThresholds = {FractionalThreshold(0.4f)})
                    ///////////////////////////////////////////////////////////////

                }
            }
        }
        //end of Scaffold

        }





}

@Composable
fun WishItemLayout(wish: Wish, onClick: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 9.dp, end = 9.dp)
            .clickable { onClick() },
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(horizontal = 3.dp))
            Text(text = wish.description, fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 3.dp))
        }
    }
}





