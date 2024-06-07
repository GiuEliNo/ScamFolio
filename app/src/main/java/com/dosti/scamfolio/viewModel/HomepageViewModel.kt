package com.dosti.scamfolio.viewModel

import androidx.lifecycle.ViewModel
import com.dosti.scamfolio.dbStuff.Repository

class HomepageViewModel(repository: Repository) : ViewModel(){
    private val _username = "Giulio"
    val username=_username

    private var _balance= 55.5f
    val balance=_balance
}