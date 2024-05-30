package com.dosti.scamfolio.viewModel

import androidx.lifecycle.ViewModel
import com.dosti.scamfolio.dbStuff.Repository

class HomepageViewModel(repository: Repository) : ViewModel(){
    private val username = "user"
    val _username=username
}