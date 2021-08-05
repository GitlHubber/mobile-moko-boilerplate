/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.app

import android.app.Application
import android.content.Context
import com.github.aakira.napier.DebugAntilog
import com.russhwolf.settings.AndroidSettings
import dev.icerock.moko.units.TableUnitItem
import org.example.library.SharedFactory
import org.example.library.feature.listSample.presentation.ListSampleViewModel

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // create factory of shared module - it's main DI component of application.
        // Provide ViewModels of all features.
        // Input is platform-specific:
        // * baseUrl - server url from platform build configs (allows use buildFlavors in configurations for server)
        // * settings - settings platform storage for https://github.com/russhwolf/multiplatform-settings
        AppComponent.factory = SharedFactory(
            baseUrl = BuildConfig.BASE_URL,
            antilog = DebugAntilog(),
            settings = AndroidSettings(getSharedPreferences("app", Context.MODE_PRIVATE)),
            unitsFactory = object : SharedFactory.UnitsFactory {
                override fun createTile(
                    id: Int,
                    title: String,
                    isChecked: Boolean,
                    listener: ListSampleViewModel.EventsListener
                ): TableUnitItem {
                    return TileSwitches().apply {
                        itemId = id.toLong()
                        this.title = title
                        this.isChecked = isChecked
                        this.listener = listener
                    }
                }
            }
        )
    }
}
