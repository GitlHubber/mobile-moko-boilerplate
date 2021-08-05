package org.example.app.auth.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
import org.example.app.utils.USER_AUTH_ENTERED_KEY
import org.example.app.utils.setUserAuthorizedState
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

        binding.buttonLogin.setOnClickListener {
            viewModel.onLoginTap()
        }
    }

    override fun showError(errorText: StringDesc) {
        context?.let { context ->
            AlertDialog.Builder(context)
                .setMessage(errorText.toString(context))
                .setCancelable(true)
                .show()
        }
    }

    override fun routeToMain() {
        Toast.makeText(requireActivity(), "Успех!", Toast.LENGTH_SHORT).show()
        setUserAuthorizedState(USER_AUTH_ENTERED_KEY, true)
        findNavController().navigate(R.id.listSample)
    }

    override fun viewModelFactory(): ViewModelProvider.Factory = ViewModelFactory {
        AppComponent.factory.authFactory.createAuthViewModel(eventsDispatcherOnMain())
    }
}