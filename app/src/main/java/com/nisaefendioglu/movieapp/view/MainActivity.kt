package com.nisaefendioglu.movieapp.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nisaefendioglu.movieapp.R
import com.nisaefendioglu.movieapp.adapters.MovieAdapter
import com.nisaefendioglu.movieapp.api.helper.ApiClient
import com.nisaefendioglu.movieapp.model.DataModel
import com.nisaefendioglu.movieapp.api.ApiService
import com.nisaefendioglu.movieapp.databinding.ActivityMainBinding
import com.nisaefendioglu.movieapp.repository.MovieRepository
import com.nisaefendioglu.movieapp.viewModel.MainViewModel
import com.nisaefendioglu.movieapp.viewModel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private var page = 1
    private var searchText = "All"
    private var contentType = ""


    private val movieAdapter by lazy {
        val itemObject = mutableListOf<DataModel>()
        MovieAdapter(itemObject)
    }

    private val omDBLayoutManager by lazy {
        LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val apiService = ApiClient.getInstance().create(ApiService::class.java)
        val repository = MovieRepository(apiService)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(repository, searchText, page, contentType)
        )[MainViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.showsLiveData.observe(this) {
            viewModel.showLoader.value = false
            movieAdapter.updateData(it, page == 1)
        }

        binding.homeGridRecycler.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL,
                false
            )

            setHasFixedSize(true)
        }

        binding.homeGridRecycler2.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL,
                false
            )

            setHasFixedSize(true)
        }

        movieAdapter.onItemClick = {
            val fragment = MovieDetails()
            val bundle = Bundle()
            bundle.putParcelable(MovieDetails.DATA_KEY, it)
            fragment.arguments = bundle
            fragment.show(supportFragmentManager, "movieDetails")
        }

        viewModel.getData(searchText, page, contentType)
        binding.also {
            it.search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                    if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                        if (p0 != null) {
                            searchText = p0.text.toString()
                            contentType = ""
                            page = 1
                            viewModel.getData(searchText, page, contentType)
                            return true
                        }
                    }
                    return false
                }
            })
        }

        binding.homeGridRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = omDBLayoutManager.childCount
                val totalItemCount = omDBLayoutManager.itemCount
                val firstVisibleItemPosition: Int = omDBLayoutManager.findFirstVisibleItemPosition()

                if (!isLoading() && !isLastPage()) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        loadMoreItems()
                    }
                }
            }
        });

    }

    private fun loadMoreItems() {
        page += 1
        viewModel.getData(searchText, page, contentType)
    }

    private fun isLastPage(): Boolean {
        return false
    }

    private fun isLoading(): Boolean {
        return false
    }
}
