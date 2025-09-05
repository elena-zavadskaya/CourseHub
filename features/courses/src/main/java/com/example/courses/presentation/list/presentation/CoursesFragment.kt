package com.example.courses.presentation.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courses.databinding.FragmentCoursesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoursesFragment : Fragment() {

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CoursesViewModel by viewModel()
    private lateinit var adapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupListeners()

        viewModel.loadCourses()
    }

    private fun setupRecyclerView() {
        adapter = CoursesAdapter(
            onItemClick = { course ->
                // Навигация к деталям курса
                navigateToCourseDetails(course.id)
            },
            onFavoriteClick = { course ->
                // Переключение избранного
                viewModel.toggleFavorite(course.id)
            }
        )

        binding.coursesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CoursesFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            adapter.submitList(courses)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let { showError(it) }
        }
    }

    private fun setupListeners() {
        // Обработка поиска
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEditText.text.toString()
                viewModel.searchCourses(query)
                true
            } else {
                false
            }
        }

        // Обработка кнопки фильтра
        binding.filterButton.setOnClickListener {
            viewModel.openFilters()
        }

        // Обработка кнопки сортировки
        binding.sortButton.setOnClickListener {
            viewModel.openSortOptions()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.coursesRecyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun showError(message: String) {
        // Реализация отображения ошибки
        // Можно использовать Snackbar или Toast
    }

    private fun navigateToCourseDetails(courseId: String) {
        // Навигация к экрану деталей курса
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}