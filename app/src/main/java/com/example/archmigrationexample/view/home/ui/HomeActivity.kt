package com.example.archmigrationexample.view.home.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.archmigrationexample.R
import com.example.archmigrationexample.data.entity.PokemonListEntity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity(), HomeContract.View {

    private val presenter by inject(HomePresenter::class.java)
    private val pAdapter = PokemonAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        presenter.view = this
        pAdapter.offset = presenter.offset
        pokemonRecycler.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 3)
            adapter = pAdapter
        }
        presenter.getPokemonList(0)
        swipeRefresh.setOnRefreshListener {
            presenter.getPokemonList(presenter.offset)
        }
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
        recyclerContainer.visibility = View.GONE
        errorText.visibility = View.GONE
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showPokemonList(list: PokemonListEntity) {
        pAdapter.offset = presenter.offset
        pAdapter.pokemonList = list.results
        pAdapter.notifyDataSetChanged()
        errorText.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        recyclerContainer.visibility = View.VISIBLE
    }

    override fun showEmptyView(error: Throwable) {
        pAdapter.pokemonList = emptyList()
        pAdapter.notifyDataSetChanged()
        swipeRefresh.isRefreshing = false
        recyclerContainer.visibility = View.GONE
        errorText.apply {
            text = "Error al recuperar los datos causado por ${error.javaClass}"
            visibility = View.GONE
        }
    }

    override fun moveLeft() {
        presenter.getPokemonList(20)
    }

    override fun moveRight() {
        presenter.getPokemonList(20)
    }
}