package com.amarydev.movieapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.amarydev.movieapp.R
import com.amarydev.movieapp.data.model.Genre
import com.amarydev.movieapp.data.model.Movie
import com.amarydev.movieapp.data.model.Production
import com.amarydev.movieapp.databinding.ActivityDetailBinding
import com.amarydev.movieapp.utils.Constant
import com.amarydev.movieapp.utils.Constant.DETAIL_KEY
import com.amarydev.movieapp.utils.Resource
import com.amarydev.movieapp.utils.ViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = intent.getParcelableExtra(DETAIL_KEY)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        detailViewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        onViewActionBar()
        onViewCreate()
        onViewFavorite()
    }

    private fun onViewActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = movie?.title
    }

    private fun onViewCreate(){
        movie?.id?.let {
            detailViewModel.movie(it).observe(this, { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.pbLoading.visibility = View.VISIBLE
                        binding.errorNotFound.root.visibility = View.GONE
                        binding.containerDetail.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.errorNotFound.root.visibility = View.GONE
                        binding.containerDetail.visibility = View.VISIBLE

                        val data = result.data
                        with(binding){
                            Glide.with(this@DetailActivity)
                                .load(Constant.COVER_IMAGE + data?.backdropPath)
                                .into(imgPoster)

                            setGenresMovie(data?.genres, binding.tvGenre)
                            setProductionsMovie(data?.productionCompanies, binding.tvProduction)

                            binding.tvReleaseDate.text = data?.releaseDate
                            binding.tvRating.text = data?.voteAverage.toString()
                            binding.tvHomepage.text = data?.homepage
                            binding.tvOverview.text = data?.overview
                        }
                    }
                    is Resource.Error -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.errorNotFound.root.visibility = View.VISIBLE
                        binding.containerDetail.visibility = View.GONE
                    }
                }
            })
        }
    }

    private fun onViewFavorite() {
        movie?.id?.let { id ->
            detailViewModel.isFavorite(id).observe(this, {
                Log.e("onViewFavorite: ", it.toString())
                var isFavorite = it == 1
                setStatusFavorite(isFavorite)

                binding.fabFavorite.setOnClickListener {
                    isFavorite = !isFavorite
                    detailViewModel.setFavorite(movie!!, isFavorite)
                    setStatusFavorite(isFavorite)
                }
            })
        }
    }

    private fun setProductionsMovie(
        productionCompanies: List<Production>?,
        tvProduction: TextView
    ) {
        val productionMovie = productionCompanies?.size?.let { arrayOfNulls<String>(it) }
        for (i in productionCompanies?.indices!!){
            productionMovie?.set(i, productionCompanies[i].name)
            productionMovie?.let { printArray(it, tvProduction) }
        }
    }

    private fun setGenresMovie(genres: List<Genre>?, tvGenre: TextView) {
        val genreMovie = genres?.size?.let { arrayOfNulls<String>(it) }
        for (i in genres?.indices!!){
            genreMovie?.set(i, genres[i].name)
            genreMovie?.let { printArray(it, tvGenre) }
        }
    }

    private fun printArray(anArray: Array<String?>, textView: TextView) {
        val sb = StringBuilder()
        for (i in anArray.indices) {
            if (i > 0) {
                sb.append(", ")
            }
            sb.append(anArray[i])
        }
        textView.text = sb.toString()
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_24
                )
            )
        } else {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_border_24
                )
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}