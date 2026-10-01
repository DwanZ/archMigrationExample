package com.example.archmigrationexample.view.home.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.archmigrationexample.R
import com.example.archmigrationexample.data.entity.PokemonItemListEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.databinding.ActivityHomeBinding
import com.example.archmigrationexample.util.Constants
import com.example.archmigrationexample.util.Constants.Companion.limit
import com.example.archmigrationexample.view.detail.ui.DetailActivity
import com.example.archmigrationexample.view.home.HomeEffect
import com.example.archmigrationexample.view.home.HomeEvent
import com.example.archmigrationexample.view.home.HomeState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Legacy XML screen kept for comparison with Compose ([com.example.archmigrationexample.ui.home.HomeRoute]).
 * Launcher entry is now [com.example.archmigrationexample.MainActivity].
 */
class HomeActivity : AppCompatActivity(), PokemonAdapter.Interaction {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val pAdapter = PokemonAdapter(emptyList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pokemonRecycler.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 3)
            adapter = pAdapter
        }
        binding.swipeRefresh.apply {
            setColorSchemeColors(ContextCompat.getColor(this@HomeActivity, R.color.colorAccent))
            setOnRefreshListener {
                viewModel.processUIEvent(HomeEvent.RefreshPage)
            }
        }
        binding.arrowRight.setOnClickListener {
            viewModel.processUIEvent(HomeEvent.NextPage(limit))
        }
        binding.arrowLeft.setOnClickListener {
            viewModel.processUIEvent(HomeEvent.PreviousPage(-limit))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.viewState.collect(::processState) }
                launch {
                    viewModel.effects.collect { effect ->
                        if (effect is HomeEffect.NavigateToDetail) {
                            startActivity(
                                Intent(this@HomeActivity, DetailActivity::class.java)
                                    .putExtra(Constants.NAME, effect.name)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun processState(state: HomeState) {
        when (state) {
            is HomeState.Success -> showPokemonList(state.value)
            is HomeState.Error -> showErrorView(state.error)
            is HomeState.EmptyList -> showEmptyView()
            is HomeState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        binding.arrowLeft.visibility = View.GONE
        binding.arrowRight.visibility = View.GONE
        binding.swipeRefresh.isRefreshing = true
        binding.recyclerContainer.visibility = View.GONE
        binding.errorText.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun showPokemonList(list: PokemonListEntity) {
        pAdapter.offset = viewModel.offset
        pAdapter.pokemonList = list.results
        pAdapter.notifyDataSetChanged()
        binding.errorText.visibility = View.GONE
        binding.swipeRefresh.isRefreshing = false
        binding.recyclerContainer.visibility = View.VISIBLE
        binding.pagCounter.text = "${viewModel.pageNumber} / ${(list.count + limit - 1) / limit}"
        paginationVisibility(list.count)
    }

    @SuppressLint("SetTextI18n")
    private fun showErrorView(error: Throwable) {
        pAdapter.pokemonList = emptyList()
        pAdapter.notifyDataSetChanged()
        binding.swipeRefresh.isRefreshing = false
        binding.recyclerContainer.visibility = View.GONE
        binding.errorText.apply {
            text = "Error al recuperar los datos causado por ${error.javaClass.canonicalName}"
            visibility = View.VISIBLE
        }
    }

    private fun showEmptyView() {
        pAdapter.pokemonList = emptyList()
        pAdapter.notifyDataSetChanged()
        binding.swipeRefresh.isRefreshing = false
        binding.recyclerContainer.visibility = View.GONE
        binding.errorText.apply {
            text = "No hay más pokemon"
            visibility = View.VISIBLE
        }
    }

    private fun paginationVisibility(count: Int) {
        binding.arrowLeft.visibility = if (viewModel.offset > 0) View.VISIBLE else View.GONE
        binding.arrowRight.visibility =
            if (viewModel.offset + limit < count) View.VISIBLE else View.GONE
    }

    override fun onItemSelected(pokemon: PokemonItemListEntity) {
        viewModel.processUIEvent(HomeEvent.OpenDetail(pokemon.name))
    }
}
