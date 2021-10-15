package com.amarydev.movieapp.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.amarydev.movieapp.databinding.FragmentFavoriteBinding
import com.amarydev.movieapp.utils.Adapter
import com.amarydev.movieapp.utils.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteAdapter = Adapter()
        favoriteAdapter.onItemClick = {
            val detailActivity = FavoriteFragmentDirections.actionNavigationFavoriteToDetailActivity(it)
            view.findNavController().navigate(detailActivity)
        }

        viewModel.movies.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.errorNotFound.root.visibility = View.GONE
                    binding.rvFavorite.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.errorNotFound.root.visibility = View.GONE
                    binding.rvFavorite.visibility = View.VISIBLE

                    it.data?.let { movies -> favoriteAdapter.setData(movies) }
                }
                is Resource.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.errorNotFound.root.visibility = View.VISIBLE
                    binding.rvFavorite.visibility = View.GONE
                }
            }
        })

        with(binding.rvFavorite) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}