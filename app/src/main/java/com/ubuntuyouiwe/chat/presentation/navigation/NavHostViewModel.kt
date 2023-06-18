package com.ubuntuyouiwe.chat.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubuntuyouiwe.chat.domain.use_case.auth.AuthStateUseCase
import com.ubuntuyouiwe.chat.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NavHostViewModel @Inject constructor(
    private val authStateUseCase: AuthStateUseCase
) : ViewModel() {




}