/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.library.feature.listSample.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.units.TableUnitItem
import org.example.library.feature.listSample.di.ListSampleUnitFactory

class ListSampleViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val unitFactory: ListSampleUnitFactory
) : ViewModel(), EventsDispatcherOwner<ListSampleViewModel.EventsListener> {

    //Объявляем класс для элемента настроек, по необходимости можно вынести его в отдельный файл/модуль
    data class SettingsItem(
        val id: Int,
        val name: String,
        val boolValue: Boolean
    )

    //Создаем тестовый список элементов
    private val _settingsData: MutableLiveData<List<SettingsItem>> = MutableLiveData(
        listOf(
            SettingsItem(id = 1, name = "Param 1", boolValue = true),
            SettingsItem(id = 2, name = "Param 2", boolValue = false),
            SettingsItem(id = 3, name = "Param 3", boolValue = false),
            SettingsItem(id = 4, name = "Param 4", boolValue = false)
        )
    )

    //Транслируем их в юниты
    val settingUnitsData: LiveData<List<TableUnitItem>> = _settingsData
        .readOnly()
        .map { settings ->
            settings.map { this.mapSettingsToUnit(it) }
        }

    //Функция для маппинга
    private fun mapSettingsToUnit(settings: SettingsItem): TableUnitItem {
        return unitFactory.createSettingsUnit(
            id = settings.id,
            name = settings.name,
            boolValue = settings.boolValue,
            listener = object : EventsListener {
                override fun onClick(boolValue: Boolean) {
                    onSettingChanges(settings, !boolValue)
                }
            }
        )
    }

    //Оставляем только первую серию включенных настроек и добавляем в конец следующий выключенный пункт
    private fun onSettingChanges(changedSetting: SettingsItem, newValue: Boolean) {
        val newSettings = _settingsData.value.map { currentSetting ->
            if (currentSetting.id == changedSetting.id) {
                currentSetting.copy(boolValue = newValue)
            } else {
                currentSetting
            }
        }
        val trueSettings = newSettings.takeWhile { it.boolValue }
        val lastSettingId = (trueSettings.lastOrNull()?.id ?: 0) + 1
        val resultSettings = trueSettings.plusElement(
            SettingsItem(
                lastSettingId,
                "Param ${lastSettingId}",
                boolValue = false
            )
        )
        _settingsData.value = resultSettings
    }

    interface EventsListener {
        fun onClick(boolValue: Boolean)
    }
}
