package com.ichin23.todolist.ui.add_edit_todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ichin23.todolist.util.UiEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodoScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackBar -> {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action
                        )
                    }
                }

                else -> Unit
            }

        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                                 IconButton(onClick = {
                                     viewModel.onEvent(AddEditTodoEvent.OnPop)
                                 }){
                                     Icon(Icons.Default.KeyboardArrowLeft, "Back")
                                 }
                },
                title = { Text(if(viewModel.todo !=null)"Editar Tarefa" else "Adicionar Tarefa") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { newTitle ->
                    viewModel.onEvent(
                        AddEditTodoEvent.OnTitleChange(newTitle)
                    )
                },
                label = {
                    Text("Title")
                },
                shape= RoundedCornerShape(12.dp),

                modifier = Modifier
                    .fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.description,
                onValueChange = { newDesc ->
                    viewModel.onEvent(
                        AddEditTodoEvent.OnDescriptionChange(newDesc)
                    )
                },
                label = {
                    Text("Description")
                },
                shape= RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = false,
                maxLines = 5,

                )
        }
    }
}