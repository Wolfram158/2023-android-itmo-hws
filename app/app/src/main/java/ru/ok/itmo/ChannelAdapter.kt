package ru.ok.itmo

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ChannelAdapter(private val context: Context?): RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {
    private var channelList = emptyList<String>()

    class ChannelViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val channel: TextView = view.findViewById(R.id.channel)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_channel_layout, parent, false)
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.channel.text = channelList[position]
        holder.itemView.setOnClickListener {
            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Chats(channelList[position]))
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return channelList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<String>) {
        channelList = list
        notifyDataSetChanged()
    }
}