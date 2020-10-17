package com.example.archmigrationexample.view.home.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.archmigrationexample.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.java.KoinJavaComponent.inject

@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity(), HomeContract.View {

    private val presenter by inject(HomePresenter::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        presenter.view = this
        presenter.getPokemonList("0")
    }


    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showPokemonList() {
        TODO("Not yet implemented")
    }

    override fun showEmptyView() {
        TODO("Not yet implemented")
    }

    override fun moveLeft() {
        TODO("Not yet implemented")
    }

    override fun moveRight() {
        TODO("Not yet implemented")
    }
}