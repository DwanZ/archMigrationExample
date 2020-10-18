package com.example.archmigrationexample.view.home.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.archmigrationexample.R
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.util.Constants.Companion.limit
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    private val viewModel by inject(HomeViewModel::class.java)
    private val pAdapter = PokemonAdapter(emptyList())

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_home)
        pokemonRecycler.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 3)
            adapter = pAdapter
        }
        swipeRefresh.apply {
            setColorSchemeColors(resources.getColor(R.color.colorAccent))
            setOnRefreshListener {
                showLoading()
                viewModel.getPokemonList(0)
            }
        }
        arrowRight.apply {
            setOnClickListener {
                showLoading()
                viewModel.getPokemonList(limit)
            }
        }

        arrowLeft.apply {
            setOnClickListener {
                showLoading()
                viewModel.getPokemonList(-limit)
            }
        }
        showLoading()
        viewModel.pokemonList.observe(this, Observer {
            hideLoading()
            showPokemonList(it)
        })
        viewModel.errorList.observe(this, Observer {
            hideLoading()
            showEmptyView(it)
        })
    }

    private fun showLoading() {
        arrowLeft.visibility = View.GONE
        arrowRight.visibility = View.GONE
        swipeRefresh.isRefreshing = true
        recyclerContainer.visibility = View.GONE
        errorText.visibility = View.GONE
        arrowRight.visibility = View.GONE
        arrowLeft.visibility = View.GONE
    }

    private fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    @SuppressLint("SetTextI18n")
    fun showPokemonList(list: PokemonListEntity) {
        pAdapter.offset = viewModel.offset
        pAdapter.pokemonList = list.results
        pAdapter.notifyDataSetChanged()
        errorText.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        recyclerContainer.visibility = View.VISIBLE
        pagCounter.text = "${(viewModel.offset + limit) / limit} / ${list.count / limit}"
        paginationVisibility(list.count)
    }

    @SuppressLint("SetTextI18n")
    fun showEmptyView(error: Throwable) {
        pAdapter.pokemonList = emptyList()
        pAdapter.notifyDataSetChanged()
        swipeRefresh.isRefreshing = false
        recyclerContainer.visibility = View.GONE
        errorText.apply {
            text = "Error al recuperar los datos causado por ${error.javaClass.canonicalName}"
            visibility = View.VISIBLE
        }
    }

    private fun paginationVisibility(count: Int) {
        if (viewModel.offset > 0) {
            arrowLeft.visibility = View.VISIBLE
        } else {
            arrowLeft.visibility = View.GONE
        }
        if (count >= viewModel.offset) {
            arrowRight.visibility = View.VISIBLE
        } else {
            arrowRight.visibility = View.GONE
        }
    }
}