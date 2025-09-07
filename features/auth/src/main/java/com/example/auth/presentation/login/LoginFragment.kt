package com.example.auth.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.auth.databinding.FragmentLoginBinding
import com.example.core.navigation.Navigator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModel()
    private val navigator: Navigator by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setupSocialButtons()
        setupObservers()
    }

    private fun initViews() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text?.toString()?.trim() ?: ""
            val password = binding.passwordEditText.text?.toString() ?: ""

            if (validateInput(email, password)) {
                viewModel.login(email, password)
            }
        }

        binding.forgotPasswordTextView.setOnClickListener {
            showMessage("Функция восстановления пароля временно недоступна")
        }

        binding.registerTextView.setOnClickListener {
            showMessage("Функция регистрации временно недоступна")
        }

        // Автозаполнение тестовых данных для удобства тестирования
        binding.emailEditText.setText("test@example.com")
        binding.passwordEditText.setText("password123")
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            showError("Введите email")
            return false
        }

        if (password.isEmpty()) {
            showError("Введите пароль")
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Введите корректный email")
            return false
        }

        return true
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is LoginUiState.Idle -> {
                        setLoading(false)
                    }
                    is LoginUiState.Loading -> {
                        setLoading(true)
                    }
                    is LoginUiState.Success -> {
                        setLoading(false)
                        navigateToCourses()
                    }
                    is LoginUiState.Error -> {
                        setLoading(false)
                        showError(state.message)
                    }
                }
            }
        }
    }

    private fun navigateToCourses() {
        navigator.navigateToCourses()
    }

    private fun setLoading(isLoading: Boolean) {
        binding.loginButton.isEnabled = !isLoading
        binding.loginButton.text = if (isLoading) "Загрузка..." else "Вход"
        binding.emailEditText.isEnabled = !isLoading
        binding.passwordEditText.isEnabled = !isLoading
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setupSocialButtons() {
        binding.vkButton.setOnClickListener {
            openUrlInBrowser("https://vk.com")
        }

        binding.odnoklassnikiButton.setOnClickListener {
            openUrlInBrowser("https://ok.ru")
        }
    }

    private fun openUrlInBrowser(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            showError("Не удалось открыть браузер")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}