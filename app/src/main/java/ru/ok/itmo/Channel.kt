@file:Suppress("DEPRECATION")

package ru.ok.itmo

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class Channel : Fragment() {
    private lateinit var adapter: ChannelAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var channelList: ArrayList<String>
    private lateinit var channels: Response<List<String>>
    //private lateinit var mChannelViewModel: ChannelViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //mChannelViewModel = ViewModelProvider(this)[ChannelViewModel::class.java]
        return inflater.inflate(R.layout.fragment_channel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = context?.getSharedPreferences("app", 0)?.getString("token", "token")
        val retrofit = Retrofit()
        val dialog = ProgressDialog(requireContext())
        val createMessage: Button = view.findViewById(R.id.createMessage)
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog.setTitle("Загрузка")
        dialog.setMessage("Загрузка. Ожидайте результат загрузки...")
        dialog.isIndeterminate = true
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                channels = retrofit.api.getChannels()
                channelList = ArrayList()
                channelList.add("Личные сообщения")
                for (i in 0..< channels.body()?.size!!) {
                    val channel = channels.body()?.get(i)
                    if (channel != null) {
                        channelList.add(channel)
                    }
                }
            } catch (_: Exception) {
                channelList = ArrayList()
                channelList.add("Личные сообщения")
            }
            dialog.dismiss()
            withContext(Dispatchers.Main) {
                recyclerView = requireActivity().findViewById(R.id.recyclerView2)
                adapter = ChannelAdapter(requireContext())
                recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = adapter
                adapter.setList(channelList)
            }
        }

        createMessage.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, MessageSender())
                ?.commit()
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