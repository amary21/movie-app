package com.amarydev.movieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.amarydev.movieapp.databinding.FragmentHomeBinding
import com.amarydev.movieapp.utils.Adapter
import com.amarydev.movieapp.utils.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeAdapter = Adapter()
        homeAdapter.onItemClick = {
            val detailActivity = HomeFragmentDirections.actionNavigationHomeToDetailActivity(it)
            view.findNavController().navigate(detailActivity)
        }

        homeViewModel.movies.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.errorNotFound.root.visibility = View.GONE
                    binding.rvHome.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.errorNotFound.root.visibility = View.GONE
                    binding.rvHome.visibility = View.VISIBLE

                    it.data?.let { movies -> homeAdapter.setData(movies) }
                }
                is Resource.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.errorNotFound.root.visibility = View.VISIBLE
                    binding.rvHome.visibility = View.GONE
                }
            }
        })

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