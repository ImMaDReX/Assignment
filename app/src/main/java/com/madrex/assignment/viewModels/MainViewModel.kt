package com.madrex.assignment.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.madrex.assignment.R
import com.madrex.assignment.model.User
import com.madrex.assignment.repository.UserRepository
import com.madrex.assignment.workerManager.UserWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(@ApplicationContext private val context: Context, private val userRepository: UserRepository): ViewModel() {

    private var state:String = context.getString(R.string.welcome)
    val currentState : String
    get() = state

    val usersLD : LiveData<List<User>>
        get() = userRepository.usersLD

    fun updateState(state:String){
        this.state = state
    }

    private fun getUsers(){
        viewModelScope.launch {
            userRepository.getUsers()
        }
    }

    fun setOneTimeRequest(){
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        /*val request = OneTimeWorkRequest.Builder(UserWorker::class.java)
            .setConstraints(constraint)
            .build()*/
        val workManager: WorkManager = WorkManager.getInstance(context)
        val request = OneTimeWorkRequest.Builder(UserWorker::class.java)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance(context).enqueue(request)
        workManager.getWorkInfoByIdLiveData(request.id).observeForever(Observer {
            if(it.state.isFinished){
                getUsers()
            }
        })
    }
}