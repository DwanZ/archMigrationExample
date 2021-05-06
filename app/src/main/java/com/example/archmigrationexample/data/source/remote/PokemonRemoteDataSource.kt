package com.example.archmigrationexample.data.source.remote

import com.example.archmigrationexample.data.PokemonDataSource
import com.example.archmigrationexample.data.entity.PokemonEntity
import com.example.archmigrationexample.data.entity.PokemonListEntity
import com.example.archmigrationexample.util.ApiResponse
import com.example.archmigrationexample.util.exceptions.ConectionException
import com.example.archmigrationexample.util.exceptions.EmptyResponseException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeoutException

class PokemonRemoteDataSource(private val api: PokemonApi) : PokemonDataSource {

    override suspend fun getPokemonByName(name: String): ApiResponse<PokemonEntity> {
        var apiResponse: ApiResponse<PokemonEntity> = ApiResponse.Error(TimeoutException())
        api.getPokemonByName(name).enqueue(object : Callback<PokemonEntity> {
            override fun onResponse(
                call: Call<PokemonEntity>,
                response: Response<PokemonEntity>
            ) {
                if (response.isSuccessful) {
                    response.body().let {
                        it?.let {
                            apiResponse = ApiResponse.Success<PokemonEntity>(it)
                        } ?: run {
                            apiResponse = ApiResponse.Error(EmptyResponseException())
                        }
                    }
                } else {
                    apiResponse = ApiResponse.Error(ConectionException())
                }
            }
            override fun onFailure(call: Call<PokemonEntity>, t: Throwable) {
                apiResponse = ApiResponse.Error(t)
            }
        })
        delay(3000)
        return apiResponse
    }

    override suspend fun getPokemonsByPagination(offset: String): ApiResponse<PokemonListEntity> {
        var apiResponse: ApiResponse<PokemonListEntity> = ApiResponse.Error(TimeoutException())
        GlobalScope.launch {
            api.getPokemonsByPagination(offset).enqueue(object : Callback<PokemonListEntity> {
                override fun onResponse(
                    call: Call<PokemonListEntity>,
                    response: Response<PokemonListEntity>
                ) {
                    if (response.isSuccessful) {
                        response.body().let {
                            it?.let {
                                apiResponse = ApiResponse.Success<PokemonListEntity>(it)
                            } ?: run {
                                apiResponse = ApiResponse.Error(EmptyResponseException())
                            }
                        }
                    } else {
                        apiResponse = ApiResponse.Error(ConectionException())
                    }
                }
                override fun onFailure(call: Call<PokemonListEntity>, t: Throwable) {
                    apiResponse = ApiResponse.Error(t)
                }
            })
        }
        delay(3000)
        return apiResponse
    }

}