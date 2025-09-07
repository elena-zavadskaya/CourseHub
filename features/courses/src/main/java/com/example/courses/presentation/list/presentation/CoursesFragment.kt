// courses/src/main/java/com/example/courses/presentation/list/presentation/CoursesFragment.kt
package com.example.courses.presentation.list.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courses.databinding.FragmentCoursesBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
    }

    private fun setupRecyclerView() {
        adapter = CoursesAdapter(
            onItemClick = { course ->
                navigateToCourseDetails(course.id)
            },
            onFavoriteClick = { course, isFavorite ->
                viewModel.toggleFavorite(course.id, isFavorite)
            }
        )

        binding.coursesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CoursesFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.courses.collect { courses ->
                adapter.submitList(courses)
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                showLoading(isLoading)
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let { showError(it) }
            }
        }
    }

    private fun setupListeners() {
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEditText.text.toString().trim()
                if (query.isNotEmpty()) {
                    viewModel.searchCourses(query)
                    hideKeyboard()
                }
                true
            } else {
                false
            }
        }

        binding.filterButton.setOnClickListener {
            viewModel.openFilters()
        }

        // Обработка нажатия на кнопку сортировки
        binding.sortButton.setOnClickListener {
            viewModel.openSortOptions()
        }

        // Обработка нажатия на текст сортировки
        binding.sortText.setOnClickListener {
            viewModel.openSortOptions()
        }

        binding.searchEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && binding.searchEditText.text.isNullOrEmpty()) {
                viewModel.loadCourses()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.coursesRecyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
    }

    private fun navigateToCourseDetails(courseId: String) {
        // Навигация к экрану деталей курса
        // findNavController().navigate(CoursesFragmentDirections.actionCoursesFragmentToCourseDetailsFragment(courseId))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}