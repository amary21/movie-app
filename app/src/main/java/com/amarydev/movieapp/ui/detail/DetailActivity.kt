package com.amarydev.movieapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.amarydev.movieapp.R
import com.amarydev.movieapp.domain.model.Genre
import com.amarydev.movieapp.domain.model.Movie
import com.amarydev.movieapp.domain.model.Production
import com.amarydev.movieapp.domain.model.Tv
import com.amarydev.movieapp.databinding.ActivityDetailBinding
import com.amarydev.movieapp.core.utils.Constant
import com.amarydev.movieapp.core.utils.Resource
import com.amarydev.movieapp.ui.favorite.FavoriteActivity
import com.bumptech.glide.Glide
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    companion object{
        var ACTIVITY_FROM = ""
    }

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding
    private var id: Int? = null
    private var type: String = ""
    private var title: String = ""
    private var movie: Movie? = null
    private var tv: Tv? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ACTIVITY_FROM == "main"){
            id = DetailActivityArgs.fromBundle(intent.extras as Bundle).id
            type = DetailActivityArgs.fromBundle(intent.extras as Bundle).type
            title = DetailActivityArgs.fromBundle(intent.extras as Bundle).title
            movie = DetailActivityArgs.fromBundle(intent.extras as Bundle).movie
            tv = DetailActivityArgs.fromBundle(intent.extras as Bundle).tv
        } else {
            id = intent.getIntExtra("id", 0)
            type = intent.getStringExtra("type").toString()
            title = intent.getStringExtra("title").toString()
            movie = intent.getParcelableExtra("movie")
            tv = intent.getParcelableExtra("tv")
        }
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onViewActionBar()
        onViewCreate()
        onViewFavorite()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (ACTIVITY_FROM == "favorite"){
            startActivity(Intent(this, FavoriteActivity::class.java))
            finish()
        }
    }

    private fun onViewActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = title
    }

    private fun onViewCreate(){
        id?.let {
            detailViewModel.detail(type, it).observe(this) { result ->
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
                        with(binding) {
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
            }
        }
    }

    private fun onViewFavorite() {
        id?.let { id ->
            detailViewModel.isFavorite(id, type).observe(this) {
                var isFavorite = it == 1
                setStatusFavorite(isFavorite)

                binding.fabFavorite.setOnClickListener {
                    isFavorite = !isFavorite
                    detailViewModel.setFavorite(movie, tv, isFavorite)
                    setStatusFavorite(isFavorite)

                    val message =
                        if (isFavorite) "Success to add favorite" else "Success to remove favorite"
                    FancyToast.makeText(
                        this,
                        message,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                }
            }
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