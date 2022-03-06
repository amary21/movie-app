package com.amarydev.movieapp.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.amarydev.movieapp.databinding.FragmentMovieBinding
import com.amarydev.movieapp.utils.Adapter
import com.amarydev.movieapp.utils.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModel()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeAdapter = Adapter()
        homeAdapter.onItemClickMovie = {
            val detailActivity = MovieFragmentDirections.actionNavigationHomeToDetailActivity(it.id, "movie", it.title, it, null)
            view.findNavController().navigate(detailActivity)
        }

        movieViewModel.movies.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.errorNotFound.root.visibility = View.GONE
                    binding.rvHome.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.errorNotFound.root.visibility = View.GONE
                    binding.rvHome.visibility = View.VISIBLE

                    it.data?.let { movies -> homeAdapter.setDataMovie(movies) }
                }
                is Resource.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.errorNotFound.root.visibility = View.VISIBLE
                    binding.rvHome.visibility = View.GONE
                }
            }
        }

        with(binding.rvHome) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}