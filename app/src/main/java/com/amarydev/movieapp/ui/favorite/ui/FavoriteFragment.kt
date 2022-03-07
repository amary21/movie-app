package com.amarydev.movieapp.ui.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.amarydev.domain.utils.Resource
import com.amarydev.movieapp.databinding.FragmentFavoriteBinding
import com.amarydev.movieapp.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private val pageViewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private var favoriteAdapter: com.amarydev.presentation.Adapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter = com.amarydev.presentation.Adapter()
        with(binding.rvFavorite) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }

        initObserver()
    }

    private fun initObserver() {
        val type = arguments?.getString(ARG_SECTION_TYPE, "")
        if (type == "movie"){
            pageViewModel.getAllFavoriteMovie.observe(viewLifecycleOwner){
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
                        it.data?.let { movies -> favoriteAdapter?.setDataMovie(movies) }
                        favoriteAdapter?.onItemClickMovie = { movie ->
                            DetailActivity.ACTIVITY_FROM = "favorite"
                            val intent = Intent(requireContext(), DetailActivity::class.java)
                            intent.putExtra("id", movie.id)
                            intent.putExtra("type", "movie")
                            intent.putExtra("title", movie.title)
                            intent.putExtra("movie", movie)
                            requireContext().startActivity(intent)
                            requireActivity().finish()
                        }
                    }
                    is Resource.Error -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.errorNotFound.root.visibility = View.VISIBLE
                        binding.rvFavorite.visibility = View.GONE
                    }
                }
            }
        } else {
            pageViewModel.getAllFavoriteTv.observe(viewLifecycleOwner){
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

                        it.data?.let { movies -> favoriteAdapter?.setDataTv(movies) }
                        favoriteAdapter?.onItemClickTv = { tv ->
                            DetailActivity.ACTIVITY_FROM = "favorite"
                            val intent = Intent(requireContext(), DetailActivity::class.java)
                            intent.putExtra("id", tv.id)
                            intent.putExtra("type", "tv")
                            intent.putExtra("title", tv.name)
                            intent.putExtra("tv", tv)
                            requireContext().startActivity(intent)
                            requireActivity().finish()
                        }
                    }
                    is Resource.Error -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.errorNotFound.root.visibility = View.VISIBLE
                        binding.rvFavorite.visibility = View.GONE
                    }
                }
            }
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_SECTION_TYPE = "section_type"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, type: String): FavoriteFragment {
            return FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putString(ARG_SECTION_TYPE, type)
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.rvFavorite.adapter = null
        favoriteAdapter = null
        _binding = null
        super.onDestroyView()

    }
}