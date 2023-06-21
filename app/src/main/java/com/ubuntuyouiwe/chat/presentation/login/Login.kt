package com.ubuntuyouiwe.chat.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.material.icons.twotone.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ubuntuyouiwe.chat.R
import com.ubuntuyouiwe.chat.presentation.components.SpecialSnackBar
import com.ubuntuyouiwe.chat.presentation.components.SpecialTextField
import com.ubuntuyouiwe.chat.presentation.components.SpecialTopBar
import com.ubuntuyouiwe.chat.presentation.login.components.login_snackbar.LoginSnackbar
import com.ubuntuyouiwe.chat.presentation.login.components.login_text_field.LoginLabel
import com.ubuntuyouiwe.chat.presentation.login.components.login_text_field.LoginLeadingIcon
import com.ubuntuyouiwe.chat.presentation.login.components.login_text_field.LoginTrailingIcon
import com.ubuntuyouiwe.chat.presentation.login.components.login_top_bar.LoginTopBarTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController) {

    val viewModel: LoginViewModel = hiltViewModel()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordShow by remember {
        mutableStateOf(true)
    }

    val visualTransformation = VisualTransformation { text ->
        val transformedText = "\u26D3".repeat(text.text.length)
        TransformedText(AnnotatedString(transformedText), OffsetMapping.Identity)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { LoginTopBarTitle("Login") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ))
        },
        snackbarHost = {
            LoginSnackbar(
                isLoading = state.isLoading,
                errorMessage = state.error,
                success = state.successMessage
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {


            TextField(
                value = email,
                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                isError = state.error.isNotBlank() && email.isBlank(),
                modifier = Modifier.fillMaxWidth(0.8f),
                label = { LoginLabel("E Mail") },
                leadingIcon = { LoginLeadingIcon(Icons.TwoTone.MailOutline) }
            )

            Spacer(modifier = Modifier.padding(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                isError = state.error.isNotBlank() && password.isBlank(),
                modifier = Modifier.fillMaxWidth(0.8f),
                label = { LoginLabel("Password") },
                leadingIcon = { LoginLeadingIcon( Icons.TwoTone.Lock) },
                trailingIcon = {
                    LoginTrailingIcon(passwordShow = passwordShow, passwordShowChange = { passwordShow = it})
                },
                visualTransformation = if (passwordShow) visualTransformation else VisualTransformation.None,

            )

            Spacer(modifier = Modifier.padding(16.dp))

            Row {
                Button(onClick = {
                    viewModel.onEvent(LoginEvent.LogIn(email, password))
                }) {
                    if (state.isLoading)
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 1.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    else
                        Text(text = "Log in")
                }

                Spacer(modifier = Modifier.padding(16.dp))

                Button(onClick = {
                    viewModel.onEvent(LoginEvent.SignUp(email, password))

                }) {
                    Text(text = "Sign up")

                }

            }
        }
    }


}

