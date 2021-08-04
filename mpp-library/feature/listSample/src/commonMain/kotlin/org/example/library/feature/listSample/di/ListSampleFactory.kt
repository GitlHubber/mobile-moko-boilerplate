/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.library.feature.listSample.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import org.example.library.feature.listSample.presentation.ListSampleViewModel

class ListSampleFactory(
    private val unitsFactory: ListSampleUnitFactory
) {
    fun createListViewModel(
        eventsDispatcher: EventsDispatcher<ListSampleViewModel.EventsListener>
    ) = ListSampleViewModel(
        eventsDispatcher = eventsDispatcher,
        unitFactory = unitsFactory
    )
}
