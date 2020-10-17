package com.example.archmigrationexample.data.source.remote

import com.example.archmigrationexample.data.PokemonDataSource
import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.util.Result
import com.example.archmigrationexample.util.exceptions.ConectionException
import com.example.archmigrationexample.util.exceptions.EmptyResponseException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PokemonRemoteDataSource(private val api: PokemonApi) : PokemonDataSource {

    override suspend fun getPokemonByName(name: String): Result<PokemonEntity> {
        lateinit var result: Result<PokemonEntity>
        api.getPokemonByName(name).enqueue(object : Callback<PokemonEntity> {
            override fun onResponse(
                call: Call<PokemonEntity>,
                response: Response<PokemonEntity>
            ) {
                if (response.isSuccessful) {
                    response.body().let {
                        it?.let {
                            result = Result.Success<PokemonEntity>(it)
                        } ?: run {
                            result = Result.Error(EmptyResponseException())
                        }
                    }
                } else {
                    result = Result.Error(ConectionException())
                }
            }
            override fun onFailure(call: Call<PokemonEntity>, t: Throwable) {
                result = Result.Error(t)
            }
        })
        return result
    }

    override suspend fun getPokemonsByPagination(offset: String): Result<PokemonListEntity> {
        lateinit var result: Result<PokemonListEntity>
        GlobalScope.launch {
            api.getPokemonsByPagination(offset).enqueue(object : Callback<PokemonListEntity> {
                override fun onResponse(
                    call: Call<PokemonListEntity>,
                    response: Response<PokemonListEntity>
                ) {
                    if (response.isSuccessful) {
                        response.body().let {
                            it?.let {
                                result = Result.Success<PokemonListEntity>(it)
                            } ?: run {
                                result = Result.Error(EmptyResponseException())
                            }
                        }
                    } else {
                        result = Result.Error(ConectionException())
                    }
                }
                override fun onFailure(call: Call<PokemonListEntity>, t: Throwable) {
                    result = Result.Error(t)
                }
            })
        }
        delay(2000)
        return result
    }

}