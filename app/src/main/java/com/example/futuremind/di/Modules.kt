package com.example.futuremind.di

import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.example.futuremind.database.DatabaseAccess
import com.example.futuremind.database.DatabaseAccessImpl
import com.example.futuremind.database.ItemDatabase
import com.example.futuremind.repository.ItemRepository
import com.example.futuremind.repository.ItemRepositoryImpl
import com.example.futuremind.network.ItemApi
import com.example.futuremind.network.ItemApiImpl
import com.example.futuremind.network.RetrofitApi
import com.example.futuremind.network.mapper.ItemNetworkMapper
import com.example.futuremind.view.list.ListViewModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val mainModule = module {
    factory { ItemNetworkMapper() }
    factory<ItemRepository> { ItemRepositoryImpl(
        get(CoroutineDispatcher::class.java, named("IO")),
        get(ItemApi::class.java),
        get(DatabaseAccess::class.java),
        get(ItemNetworkMapper::class.java)
    )
    }
    viewModel {
        ListViewModel(
            get(ItemRepository::class.java),
            get(SavedStateHandle::class.java),
            get(CoroutineDispatcher::class.java, named("Main"))
        )
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), ItemDatabase::class.java, "itemDb")
            .build()
    }

    factory<DatabaseAccess> {
        DatabaseAccessImpl(get())
    }
}

val coroutineDispatcherModule = module {
    factory<CoroutineDispatcher>(named("Main")) { Dispatchers.Main }
    factory<CoroutineDispatcher>(named("IO")) { Dispatchers.IO }
    factory<CoroutineDispatcher>(named("Default")) { Dispatchers.Default }
}

val networkModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    single<Converter.Factory> { GsonConverterFactory.create(GsonBuilder().create()) }

    single<RetrofitApi> {
        Retrofit.Builder()
            .baseUrl("https://recruitment-task.futuremind.dev/")
            .addConverterFactory(get(Converter.Factory::class.java))
            .client(get(OkHttpClient::class.java)).build().create(RetrofitApi::class.java)
    }

    single<ItemApi> { ItemApiImpl(get(RetrofitApi::class.java)) }
}