package com.sam.gogoidea.repo.usecase

import com.sam.gogoidea.repo.data.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import java.lang.Exception

abstract class BaseFlowUseCase<Params, Type> {
    abstract suspend fun getFlow(params: Params): Flow<Type>

    open suspend fun getDataState(params: Params): Flow<State<Type>> {
        return getFlow(params)
            .transform {
                if (it == null) {
                    emit(State.Error(Exception("get error")))
                } else {
                    emit(State.Success<Type>(it))
                }
            }
            .flowOn(Dispatchers.IO)
            .onStart {
                emit(State.Loading)
            }
    }
}
