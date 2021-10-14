package com.amarydev.movieapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.amarydev.movieapp.databinding.FragmentHomeBinding
import com.amarydev.movieapp.ui.detail.DetailActivity
import com.amarydev.movieapp.utils.Adapter
import com.amarydev.movieapp.utils.Constant.DETAIL_KEY
import com.amarydev.movieapp.utils.Resource
import com.amarydev.movieapp.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val factory = ViewModelFactory.getInstance(requireActivity())
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeAdapter = Adapter()
        homeAdapter.onItemClick = {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DETAIL_KEY, it)
            startActivity(intent)
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