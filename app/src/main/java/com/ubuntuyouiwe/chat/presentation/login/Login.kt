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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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

    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            SpecialTopBar(title = "Login") {

            }
        },
        snackbarHost = {

            SpecialSnackBar(
                hostState = snackbarHostState,
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
            SpecialTextField(
                value = email, onValueChange = { email = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                isError = state.error.isNotBlank() && email.isBlank(),
                modifier = Modifier.fillMaxWidth(0.8f),
                label = {
                    Text(text = "E Mail")
                },
                prefix = {
                    Icon(
                        imageVector = Icons.TwoTone.MailOutline,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )


                }

            )

            Spacer(modifier = Modifier.padding(16.dp))

            SpecialTextField(
                value = password, onValueChange = { password = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                label = {
                    Text(text = "Password")
                },
                prefix = {
                    Icon(
                        imageVector = Icons.TwoTone.Lock,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )


                },
                suffix = {
                    if (passwordShow) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_key_off_24),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable {
                                passwordShow = false
                            }
                        )

                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_key_24),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable {
                                passwordShow = true
                            }
                        )

                    }
                },
                visualTransformation = if (passwordShow) visualTransformation else VisualTransformation.None,
                isError = state.error.isNotBlank() && password.isBlank(),
                modifier = Modifier.fillMaxWidth(0.8f),

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

