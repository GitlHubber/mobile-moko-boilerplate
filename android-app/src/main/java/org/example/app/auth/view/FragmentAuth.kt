package org.example.app.auth.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import dev.icerock.moko.mvvm.MvvmEventsFragment
import dev.icerock.moko.mvvm.ViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import dev.icerock.moko.mvvm.livedata.bindToViewVisibleOrGone
import dev.icerock.moko.mvvm.livedata.bindTwoWayToEditTextText
import dev.icerock.moko.resources.desc.StringDesc
import org.example.app.AppComponent
import org.example.app.BR
import org.example.app.R
import org.example.app.databinding.FragmentAuthBinding
import org.example.library.feature.auth.presentation.AuthViewModel

class FragmentAuth: MvvmEventsFragment<FragmentAuthBinding, AuthViewModel, AuthViewModel.EventsListener>(),
    AuthViewModel.EventsListener {

    override val layoutId: Int
        get() = R.layout.fragment_auth
    override val viewModelClass: Class<AuthViewModel>
        get() = AuthViewModel::class.java
    override val viewModelVariableId: Int
        get() = BR.viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginField.bindTwoWayToEditTextText(viewLifecycleOwner, binding.login)
        viewModel.passwordField.bindTwoWayToEditTextText(viewLifecycleOwner, binding.password)
        viewModel.isLoading.bindToViewVisibleOrGone(viewLifecycleOwner, binding.loading)
        //viewModel.isButtonEnabled.bindToViewEnabled(viewLifecycleOwner, binding.buttonLogin)

        binding.buttonLogin.setOnClickListener {
            viewModel.onLoginTap()
        }
    }

//    override fun viewBindingInflate(
//        inflater: LayoutInflater,
//        container: ViewGroup?
//    ): FragmentAuthBinding {
//        return FragmentAuthBinding.inflate(layoutInflater, container, false)
//    }

    override fun showError(error: StringDesc) {
        context?.let { context ->
            AlertDialog.Builder(context)
                .setMessage(error.toString(context))
                .setCancelable(true)
                .show()
        }
    }

    override fun routeToMain() {
        Toast.makeText(requireContext(), "Успех!", Toast.LENGTH_SHORT).show()
    }

    override fun viewModelFactory(): ViewModelProvider.Factory = ViewModelFactory {
        AppComponent.factory.authFactory.createAuthViewModel(eventsDispatcherOnMain())
    }
}