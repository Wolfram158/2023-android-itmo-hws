@file:Suppress("DEPRECATION")

package ru.ok.itmo

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

@Suppress("DEPRECATION")
class Chats(private val channel: String) : Fragment() {
    private lateinit var adapter: MessageAdapter
    private lateinit var recyclerView: RecyclerView
    //private lateinit var channel1Messages: Response<List<Message>>
    private lateinit var messageList: ArrayList<Message>
    private lateinit var messages: Response<List<Message>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val token = context?.getSharedPreferences("app", 0)?.getString("token", "token")
        val user = context?.getSharedPreferences("app", 0)?.getString("user", "user")
        val retrofit = Retrofit()
        val dialog = ProgressDialog(requireContext())

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog.setTitle("Загрузка")
        dialog.setMessage("Загрузка. Ожидайте результат загрузки...")
        dialog.isIndeterminate = true
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        CoroutineScope(Dispatchers.IO).launch {
            //channel1Messages = retrofit.api.get1ch()
            messages = if (channel != "Личные сообщения") {
                retrofit.api.getMessagesFromChannel(channel)
            } else {
                user?.let { retrofit.api.getInbox(token!!, it) }!!
            }
            messageList = ArrayList()
            for (i in 0..< messages.body()?.size!!) {
                val mes = messages.body()?.get(i)
                if (mes != null) {
                    messageList.add(mes)
                }
            }
            dialog.dismiss()
            withContext(Dispatchers.Main) {
                if (messages.body()?.isEmpty()!!) {
                    Toast.makeText(requireContext(), "Пусто", Toast.LENGTH_LONG).show()
                }
                recyclerView = requireActivity().findViewById(R.id.recyclerView)
                adapter = MessageAdapter(context, channel)
                recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = adapter
                adapter.setList(messageList)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, Channel())
               ?.commit()
        }
    }
}