package com.example.auth.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.auth.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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

        // Пока просто инициализируем элементы, логику добавим позже
        initViews()
    }

    private fun initViews() {
        // Здесь будет инициализация обработчиков событий
        // Пока просто заглушки
        binding.loginButton.setOnClickListener {
            // Обработка входа
        }

        binding.forgotPasswordTextView.setOnClickListener {
            // Переход к восстановлению пароля
        }

        binding.registerTextView.setOnClickListener {
            // Переход к регистрации
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}