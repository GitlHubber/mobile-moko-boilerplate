package org.example.app.list_sample.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import dev.icerock.moko.mvvm.MvvmEventsFragment
import dev.icerock.moko.mvvm.ViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.units.adapter.UnitsRecyclerViewAdapter
import org.example.app.AppComponent
import org.example.app.BR
import org.example.app.R
import org.example.app.databinding.FragmentListSampleBinding
import org.example.library.feature.listSample.presentation.ListSampleViewModel

class FragmentListSample: MvvmEventsFragment<FragmentListSampleBinding, ListSampleViewModel, ListSampleViewModel.EventsListener>() {
    override val layoutId: Int
        get() = R.layout.fragment_list_sample
    override val viewModelClass: Class<ListSampleViewModel>
        get() = ListSampleViewModel::class.java
    override val viewModelVariableId: Int
        get() = BR.viewModel

    private lateinit var unitsRecyclerViewAdapter: UnitsRecyclerViewAdapter

    override fun viewModelFactory(): ViewModelProvider.Factory = ViewModelFactory {
        AppComponent.factory.listSampleFactory.createListViewModel(
            eventsDispatcher = eventsDispatcherOnMain()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        unitsRecyclerViewAdapter = UnitsRecyclerViewAdapter(this)
        unitsRecyclerViewAdapter.units = viewModel.settingUnitsData.value
        binding.recyclerView.adapter = unitsRecyclerViewAdapter

        viewModel.settingUnitsData.ld().observe(viewLifecycleOwner, {
            unitsRecyclerViewAdapter.units = it
        })

        binding.viewModel
    }
}