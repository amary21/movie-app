package com.amarydev.movieapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.amarydev.movieapp.R
import com.amarydev.movieapp.data.model.Movie
import com.amarydev.movieapp.databinding.ActivityDetailBinding
import com.amarydev.movieapp.di.ViewModelFactory
import com.amarydev.movieapp.ui.home.HomeViewModel
import com.amarydev.movieapp.utils.Constant
import com.amarydev.movieapp.utils.Constant.DETAIL_KEY
import com.amarydev.movieapp.utils.Resource
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
                    }
                    is Resource.Success -> {
                        val data = result.data
                        Log.e("DetailActivity", data.toString() )
                        with(binding){
                            Glide.with(this@DetailActivity)
                                .load(Constant.COVER_IMAGE + data?.backdropPath)
                                .into(imgPoster)


                        }
                    }
                    is Resource.Error -> {

                    }
                }
            })
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