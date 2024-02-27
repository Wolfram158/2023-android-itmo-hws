package ru.ok.itmo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class FragmentSample : Fragment() {
    private var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            this.text = savedInstanceState.getString("number")
            this.text?.let { Log.e("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD", it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            this.text = it.getString("text")
        }
        if (savedInstanceState == null) {
            val text: TextView? = view.findViewById(R.id.textView)
            if (text != null) {
                text.text = this.text
                text.text = (0..100).random().toString()
            }
        } else {
            this.text = savedInstanceState.getString("number")
        }
    }

    companion object {
        fun newInstance(param1: String) =
            FragmentSample().apply {
                arguments = Bundle().apply {
                    putString("text", param1)
                }
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("number", this.text)
    }
}