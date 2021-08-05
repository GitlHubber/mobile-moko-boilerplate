/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.app.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import androidx.navigation.fragment.NavHostFragment
import dev.icerock.moko.mvvm.MvvmActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import org.example.app.BR
import org.example.app.R
import org.example.app.databinding.ActivitySplashBinding
import org.example.app.utils.USER_AUTH_ENTERED_KEY
import org.example.app.utils.getUserAuthorizedState
import org.example.library.feature.auth.presentation.SplashScreenViewModel

class SplashActivity: MvvmActivity<ActivitySplashBinding, SplashScreenViewModel>(), SplashScreenViewModel.EventsListener {

    private lateinit var navController: NavController
    private lateinit var host: NavHostFragment
    private lateinit var graphInflater: NavInflater
    private lateinit var navGraph: NavGraph

    override val layoutId: Int = R.layout.activity_splash
    override val viewModelVariableId: Int = BR.viewModel
    override val viewModelClass: Class<SplashScreenViewModel> = SplashScreenViewModel::class.java

    override fun viewModelFactory(): ViewModelProvider.Factory {
        return createViewModelFactory { SplashScreenViewModel(eventsDispatcherOnMain()) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.eventsDispatcher.bind(
            lifecycleOwner = this,
            listener = this
        )

        viewModel.checkAuth(getUserAuthorizedState(USER_AUTH_ENTERED_KEY, false))
    }

    private fun initNavController() {
        host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        graphInflater = host.navController.navInflater
        navController = host.navController
        navGraph = graphInflater.inflate(R.navigation.root_navigation)
    }

    override fun routeToList() {
        initNavController()
        navGraph.startDestination = R.id.listSample
        navController.graph = navGraph
    }

    override fun routeToAuth() {
        initNavController()
        navGraph.startDestination = R.id.auth
        navController.graph = navGraph
    }
}
