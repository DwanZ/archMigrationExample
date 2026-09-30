package com.example.archmigrationexample.view.home.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.addRepeatingJob
import androidx.recyclerview.widget.GridLayoutManager
import com.example.archmigrationexample.R
import com.example.archmigrationexample.data.entity.PokemonItemListEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.util.Constants
import com.example.archmigrationexample.util.Constants.Companion.limit
import com.example.archmigrationexample.view.detail.ui.DetailActivity
import com.example.archmigrationexample.view.home.HomeEvent
import com.example.archmigrationexample.view.home.HomeState
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity(), PokemonAdapter.Interaction {

    private val viewModel by inject(HomeViewModel::class.java)
    private val pAdapter = PokemonAdapter(emptyList(), this)

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
                viewModel.processUIEvent(HomeEvent.RefreshPage)
            }
        }
        arrowRight.apply {
            setOnClickListener {
                viewModel.processUIEvent(HomeEvent.NextPage(limit))
            }
        }
        arrowLeft.apply {
            setOnClickListener {
                viewModel.processUIEvent(HomeEvent.PreviousPage(-limit))
            }
        }

        addRepeatingJob(Lifecycle.State.STARTED) {
            viewModel.viewState.collect(::processState)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.processUIEvent(HomeEvent.OnViewHidden)
    }

    private fun processState(state: HomeState) {
        when(state) {
            is HomeState.Success -> {
                showPokemonList(state.value)
            }
            is HomeState.OpenDetail -> {
                val intent =
                    Intent(
                        this,
                        DetailActivity::class.java
                    )
                intent.putExtra(Constants.NAME, state.name)
                startActivity(intent)
            }
            is HomeState.Error -> {
                showErrorView(state.error)
            }
            is HomeState.EmptyList -> {
                showEmptyView()
            }
            is HomeState.Loading -> {
                showLoading()
            }
            is HomeState.OnViewHidden -> {
                //nothing
            }
        }
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
    fun showErrorView(error: Throwable) {
        pAdapter.pokemonList = emptyList()
        pAdapter.notifyDataSetChanged()
        swipeRefresh.isRefreshing = false
        recyclerContainer.visibility = View.GONE
        errorText.apply {
            text = "Error al recuperar los datos causado por ${error.javaClass.canonicalName}"
            visibility = View.VISIBLE
        }
    }

    fun showEmptyView() {
        pAdapter.pokemonList = emptyList()
        pAdapter.notifyDataSetChanged()
        swipeRefresh.isRefreshing = false
        recyclerContainer.visibility = View.GONE
        errorText.apply {
            text = "No hay más pokemon"
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

    override fun onItemSelected(pokemon: PokemonItemListEntity) {
        viewModel.processUIEvent(HomeEvent.OpenDetail(pokemon.name))
    }
}