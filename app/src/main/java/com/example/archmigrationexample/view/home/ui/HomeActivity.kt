package com.example.archmigrationexample.view.home.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.Window
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.archmigrationexample.R
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.util.Constants.Companion.limit
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity(), HomeContract.View {

    private val presenter by inject(HomePresenter::class.java)
    private val pAdapter = PokemonAdapter(emptyList())

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_home)
        presenter.view = this
        pAdapter.offset = presenter.offset
        pokemonRecycler.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 3)
            adapter = pAdapter
        }
        presenter.getPokemonList(0)
        swipeRefresh.apply {
            setColorSchemeColors(resources.getColor(R.color.colorAccent))
            setOnRefreshListener {
                presenter.getPokemonList(presenter.offset)
            }
        }
        arrowRight.apply {
            setOnClickListener {
                presenter.getPokemonList(limit)
            }
        }

        arrowLeft.apply {
            setOnClickListener {
                presenter.getPokemonList(-limit)
            }
        }
    }

    override fun showLoading() {
        arrowLeft.visibility = View.GONE
        arrowRight.visibility = View.GONE
        swipeRefresh.isRefreshing = true
        recyclerContainer.visibility = View.GONE
        errorText.visibility = View.GONE
        arrowRight.visibility = View.GONE
        arrowLeft.visibility = View.GONE
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    @SuppressLint("SetTextI18n")
    override fun showPokemonList(list: PokemonListEntity) {
        pAdapter.offset = presenter.offset
        pAdapter.pokemonList = list.results
        pAdapter.notifyDataSetChanged()
        errorText.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        recyclerContainer.visibility = View.VISIBLE
        pagCounter.text = "${(presenter.offset + limit) / limit} / ${list.count / limit}"
        paginationVisibility(list.count)
    }

    @SuppressLint("SetTextI18n")
    override fun showEmptyView(error: Throwable) {
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
        if (presenter.offset > 0) {
            arrowLeft.visibility = View.VISIBLE
        } else {
            arrowLeft.visibility = View.GONE
        }
        if (count >= presenter.offset) {
            arrowRight.visibility = View.VISIBLE
        } else {
            arrowRight.visibility = View.GONE
        }
    }
}