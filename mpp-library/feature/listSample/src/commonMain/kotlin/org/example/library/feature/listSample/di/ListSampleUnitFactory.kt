package org.example.library.feature.listSample.di

import dev.icerock.moko.units.TableUnitItem
import org.example.library.feature.listSample.presentation.ListSampleViewModel

interface ListSampleUnitFactory {
    fun createSettingsUnit(
        id: Int,
        name: String,
        isChecked: Boolean,
        listener: ListSampleViewModel.EventsListener
    ): TableUnitItem
}