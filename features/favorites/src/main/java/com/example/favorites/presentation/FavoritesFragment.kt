package com.example.favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favorites.databinding.FragmentFavoritesBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModel()
    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = FavoritesAdapter(
            onItemClick = { course ->
                navigateToCourseDetails(course.id)
            },
            onFavoriteClick = { course, isFavorite ->
                viewModel.toggleFavorite(course.id, isFavorite)
            }
        )

        binding.favoritesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoritesFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.favorites.collect { courses ->
                adapter.submitList(courses)
                updateUIState(courses.isEmpty())
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                showLoading(isLoading)
                if (isLoading) {
                    showContent(false)
                    showEmptyState(false)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let { showError(it) }
            }
        }
    }

    private fun updateUIState(isEmpty: Boolean) {
        if (isEmpty) {
            showEmptyState(true)
            showContent(false)
        } else {
            showEmptyState(false)
            showContent(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showContent(show: Boolean) {
        binding.favoritesRecyclerView.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showEmptyState(show: Boolean) {
        binding.emptyState.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        // Реализация показа ошибки, например, Snackbar
        // Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToCourseDetails(courseId: String) {
        // Навигация к экрану деталей курса
        // findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToCourseDetailsFragment(courseId))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}