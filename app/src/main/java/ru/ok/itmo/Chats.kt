package ru.ok.itmo

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
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


class Chats : Fragment() {
    private lateinit var adapter: MessageAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var channel1Messages: Response<List<Message>>
    private lateinit var messageList: ArrayList<Message>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val token = context?.getSharedPreferences("app", 0)?.getString("token", "token")
        val retrofit = Retrofit()
        val dialog = ProgressDialog(requireContext())

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog.setTitle("Загрузка")
        dialog.setMessage("Загрузка. Ожидайте результат загрузки...")
        dialog.isIndeterminate = true
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        CoroutineScope(Dispatchers.IO).launch {
            channel1Messages = retrofit.api.get1ch()
            messageList = ArrayList()
            for (i in 0..<(channel1Messages.body()?.size!!)) {
                val mes = channel1Messages.body()?.get(i)
                if (mes != null) {
                    messageList.add(mes)
                }
            }
            dialog.dismiss()
            withContext(Dispatchers.Main) {
                if (channel1Messages.body()?.isEmpty()!!) {
                    Toast.makeText(requireContext(), "Пусто", Toast.LENGTH_LONG).show()
                }
                recyclerView = requireActivity().findViewById(R.id.recyclerView)
                adapter = MessageAdapter()
                recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = adapter
                adapter.setList(messageList)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, Authorization())
               ?.commit()
            CoroutineScope(Dispatchers.IO).launch {
                if (token != null) {
                    retrofit.api.logout(token)
                }
            }
        }
    }
}