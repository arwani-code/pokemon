package com.arwani.pokemon.data.helper


import com.arwani.pokemon.data.UiResult
import com.arwani.pokemon.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundUiResult<ResultType, RequestType> {

    private var result: Flow<UiResult<ResultType>> = flow {
//        emit(UiResult.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(UiResult.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { UiResult.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { UiResult.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(UiResult.Error(apiResponse.errorMessage))
                    emitAll(loadFromDB().map { UiResult.Success(it) })
                }
            }
        } else {
            emitAll(loadFromDB().map { UiResult.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<UiResult<ResultType>> = result
}