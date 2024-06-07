package com.dosti.scamfolio.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dosti.scamfolio.db.repositories.UserRepository
import com.dosti.scamfolio.dbStuff.Repository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if(modelClass.isAssignableFrom(LoginViewModel::class.java))
                LoginViewModel(repository) as T
        else if( modelClass.isAssignableFrom(CryptoScreenViewModel::class.java))
            CryptoScreenViewModel(repository) as T
        else if( modelClass.isAssignableFrom(HomepageViewModel::class.java))
            HomepageViewModel(repository) as T
        else if( modelClass.isAssignableFrom(ConverterViewModel::class.java))
            ConverterViewModel(repository) as T
        else
            SearchedCryptosViewModel(repository) as T
}