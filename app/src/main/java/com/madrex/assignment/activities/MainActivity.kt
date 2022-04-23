package com.madrex.assignment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.madrex.assignment.R
import com.madrex.assignment.databinding.ActivityMainBinding
import com.madrex.assignment.utils.MobileUtils
import com.madrex.assignment.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.msg = mainViewModel.currentState
        binding.loader = false
        
        binding.start.setOnClickListener(View.OnClickListener {
            if(MobileUtils.isNetworkAvailable(applicationContext)){
                binding.loader = true
                mainViewModel.setOneTimeRequest()
            } else {
                mainViewModel.updateState(getString(R.string.unsuccessful))
                binding.msg = mainViewModel.currentState
                MobileUtils.shortToast(applicationContext,"No Network available")
            }
        });

        mainViewModel.usersLD.observe(this, Observer {
            if(MobileUtils.isNetworkAvailable(applicationContext)) {
                if (it.isNotEmpty()) {
                    mainViewModel.updateState(getString(R.string.successful))
                    MobileUtils.longToast(applicationContext, it.toString())
                } else {
                    mainViewModel.updateState(getString(R.string.unsuccessful))
                    MobileUtils.longToast(applicationContext, "Data fetching unsuccessful")
                }
                binding.msg = mainViewModel.currentState
                binding.loader = false
            }
        })

    }
}