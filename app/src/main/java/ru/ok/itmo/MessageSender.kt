package ru.ok.itmo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageSender() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message_sender, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textToWhom: EditText = view.findViewById(R.id.textToWhom)
        val textCreatedMessage: EditText = view.findViewById(R.id.textCreatedMessage)
        val send: Button = view.findViewById(R.id.buttonSend)
        val retrofit = Retrofit()
        val token = context?.getSharedPreferences("app", 0)?.getString("token", "token")
        val user = context?.getSharedPreferences("app", 0)?.getString("user", "user")

        send.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val msg = Message(1, user.toString(), textToWhom.text.toString(),
                    Data(Text(textCreatedMessage.text.toString()), null), System.currentTimeMillis())
                if (token != null) {
                    retrofit.api.message(token, msg)
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, Channel())
                ?.commit()
        }
    }

}