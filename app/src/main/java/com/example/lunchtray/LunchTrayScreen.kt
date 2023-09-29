package com.example.lunchtray

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayApp() {
    Scaffold (
        topBar = { 
            LunchTrayAppBar()
        }
    ){ paddingValues ->  
        Column (
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            Button(onClick = { /*TODO*/ }, modifier =Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Text(text = stringResource(id = R.string.start_order))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayAppBar() {
    TopAppBar(title = { 
        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
            Text(text = stringResource(id = R.string.app_name))
        }
    })
}