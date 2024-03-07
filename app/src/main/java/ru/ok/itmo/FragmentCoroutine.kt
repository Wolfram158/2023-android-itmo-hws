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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentCoroutine : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_coroutine, container, false)
        val progressBar : ProgressBar = view.findViewById(R.id.progressBar)
        val startButton : Button = view.findViewById(R.id.button)

        startButton.setOnClickListener {
            startProgress(progressBar)
        }

        return view
    }

    private fun startProgress(progressBar : ProgressBar) {
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0..100) {
                delay(100)
                withContext(Dispatchers.Main) {
                    progressBar.progress = i
                }
            }
        }
    }
}