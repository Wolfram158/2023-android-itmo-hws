package ru.ok.itmo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class FragmentFlow : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flow, container, false)
        val progressBar : ProgressBar = view.findViewById(R.id.progressBar2)
        val startButton : Button = view.findViewById(R.id.button2)

        startButton.setOnClickListener {
            startProgress(progressBar)
        }

        return view
    }

    private fun startProgress(progressBar : ProgressBar) {
        CoroutineScope(Dispatchers.IO).launch {
            progressFlow()
                .flowOn(Dispatchers.Main)
                .collect { progress ->
                    progressBar.progress = progress
                }
        }
    }

    private fun progressFlow(): Flow<Int> = flow {
        for (i in 0..100) {
            delay(100)
            emit(i)
        }
    }
}