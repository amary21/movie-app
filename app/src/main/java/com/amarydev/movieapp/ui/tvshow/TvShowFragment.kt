package com.amarydev.movieapp.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.amarydev.domain.utils.Resource
import com.amarydev.movieapp.databinding.FragmentTvshowBinding
import com.amarydev.movieapp.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

    private val viewModel: TvShowViewModel by viewModel()
    private var _binding: FragmentTvshowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvshowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteAdapter = com.amarydev.presentation.Adapter()
        favoriteAdapter.onItemClickTv = {
            DetailActivity.ACTIVITY_FROM = "main"
            val detailActivity = TvShowFragmentDirections.actionNavigationFavoriteToDetailActivity(it.id, "tv", it.name, null, it)
            view.findNavController().navigate(detailActivity)
        }

        viewModel.tv.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.errorNotFound.root.visibility = View.GONE
                    binding.rvFavorite.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.errorNotFound.root.visibility = View.GONE
                    binding.rvFavorite.visibility = View.VISIBLE

                    it.data?.let { movies -> favoriteAdapter.setDataTv(movies) }
                }
                is Resource.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.errorNotFound.root.visibility = View.VISIBLE
                    binding.rvFavorite.visibility = View.GONE
                }
            }
        }

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