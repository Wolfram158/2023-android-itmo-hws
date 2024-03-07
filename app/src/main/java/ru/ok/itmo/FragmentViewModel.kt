package ru.ok.itmo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class FragmentViewModel : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_model, container, false)
        val progressBar : ProgressBar = view.findViewById(R.id.progressBar3)
        val startButton : Button = view.findViewById(R.id.button3)

        startButton.setOnClickListener {
            startProgress(progressBar)
        }

        return view
    }

    private fun startProgress(progressBar: ProgressBar) {
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.run().also {
            lifecycleScope.launch {
                viewModel.i.collect {
                    progressBar.progress = it
                }
            }
        }
    }
}