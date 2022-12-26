package com.fappslab.resultbuilder.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fappslab.resultbuilder.databinding.ActivityMainBinding
import com.fappslab.resultbuilder.presentation.viewmodel.MainViewAction
import com.fappslab.resultbuilder.presentation.viewmodel.MainViewModel
import com.fappslab.resultbuilder.presentation.viewmodel.MainViewState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupObservables()
        setupListeners()
    }

    private fun setupObservables() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    loadingState(state.shouldShowLoading)
                    state.inputState()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.action.collect { action ->
                    when (action) {
                        is MainViewAction.ErrorMessage -> showError(action.message)
                    }
                }
            }
        }
    }

    private fun setupListeners() = binding.run {
        buttonSearch.setOnClickListener {
            viewModel.getAddress(inputCode.text.toString())
        }
    }

    private fun loadingState(isVisible: Boolean) {
        binding.loading.isVisible = isVisible
    }

    private fun MainViewState.inputState() = binding.run {
        inputStreet.setText(address?.street)
        inputCity.setText(address?.city)
        inputState.setText(address?.state)
        inputAreaCode.setText(address?.areaCode)
    }

    private fun showError(message: String?) {
        message?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }
    }
}
