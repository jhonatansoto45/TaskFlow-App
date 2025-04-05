package com.example.taskflowapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController // libreria que permite pasar parametro de navegacion
import com.example.taskflowapp.R
import com.example.taskflowapp.ui.theme.TaskFlowAppTheme
import kotlinx.coroutines.launch

//Fragmento de pantalla de tareas
@Composable
fun TasksScreen(navController: NavController) {
    TaskFlowAppTheme {
        ToolbarScreen()
    }
}

/**
 * Crea el toolbar de la screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarScreen(){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(280.dp)
                    .background(Color.White)
            ) {
                MenuContent()
            }
        }
    ) {
        Scaffold (
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.listado_de_tareas)) },
                    navigationIcon = {
                        IconButton (onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                )
            }
        ) { padding ->
            ViewContent(Modifier.padding(padding))
        }
    }
}

/**
 * Menu desplegable con sus items para navegar (Aún en desarrollo)
 */
@Composable
fun MenuContent() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val drawerWidth = screenWidth * 0.5f // 50% del ancho de la pantalla

    Column (modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .width(drawerWidth)
        .padding(16.dp)) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        Divider()
        Text("Opción 1", modifier = Modifier.padding(16.dp))
        Text("Opción 2", modifier = Modifier.padding(16.dp))
    }
}

/**
 * Contenido del screen
 */
@Composable
fun ViewContent(modifier: Modifier = Modifier) {
    TaskList()
}

@SuppressLint("RememberReturnType")
@Composable
fun TaskList() {
    var texto by remember { mutableStateOf("") }
    val tareas = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text(text = stringResource(R.string.nueva_tarea)) },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (texto.isNotBlank()) {
                    tareas.add(texto)
                    texto = ""
                }
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),// Centra el botón
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green
            )
        ) {
            Text(text = stringResource(R.string.name_add))
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espacio antes del listado

        tareas.forEach {
            Text("- $it", modifier = Modifier.padding(top = 4.dp, end = 10.dp))
        }
    }
}



@Preview(showBackground = false)
@Composable()
fun TaskScreenPreview(){
    TaskFlowAppTheme {
        ToolbarScreen()
    }
}