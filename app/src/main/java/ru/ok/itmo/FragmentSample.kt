package ru.ok.itmo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels

class FragmentSample : Fragment() {
    private val viewModel by viewModels<NumberViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sample, container, false)
        val text: TextView = view.findViewById(R.id.textView)
        viewModel.number.observe(viewLifecycleOwner) {
            text.text = it.toString()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("FragmentSample", "OnViewCreated")
    }

    companion object {
        fun newInstance() =
            FragmentSample().apply {
                arguments = Bundle().apply {
                }
            }
    }

}