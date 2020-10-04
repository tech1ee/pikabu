package com.example.pikabu

import android.app.Application
import android.content.Context
import com.example.pikabu.di.*
import com.example.pikabu.di.all.AllFeedComponent
import com.example.pikabu.di.all.AllFeedModule
import com.example.pikabu.di.saved.SavedFeedComponent
import com.example.pikabu.di.saved.SavedFeedModule

class App: Application() {

    private var appComponent: AppComponent? = null
    private var allFeedComponent: AllFeedComponent? = null
    private var savedFeedComponent: SavedFeedComponent? = null

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .dataModule(DataModule())
            .repositoryModule(RepositoryModule())
            .build()

        appComponent?.inject(this)

        allFeedComponent = appComponent?.createAllFeedComponent(AllFeedModule())
        savedFeedComponent = appComponent?.createSavedFeedComponent(SavedFeedModule())
    }

    companion object {

        fun appComponent(context: Context?): AppComponent? {
            return (context?.applicationContext as? App)?.appComponent
        }

        fun allFeedComponent(context: Context?): AllFeedComponent? {
            return (context?.applicationContext as? App)?.allFeedComponent
        }

        fun savedFeedComponent(context: Context?): SavedFeedComponent? {
            return (context?.applicationContext as? App)?.savedFeedComponent
        }
    }
}