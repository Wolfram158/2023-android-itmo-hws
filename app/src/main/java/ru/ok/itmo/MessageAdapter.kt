package ru.ok.itmo

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    private var messageList = emptyList<Message>()

    class MessageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textFrom: TextView = view.findViewById(R.id.textFrom)
        val textMessage: TextView = view.findViewById(R.id.textMessage)
        val textTime: TextView = view.findViewById(R.id.textTime)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_layout, parent, false)
        return MessageViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.textFrom.text = "От: ${messageList[position].from}"
        holder.textMessage.text = "Сообщение: ${messageList[position].data.Text?.text}"
        if (messageList[position].time != null) {
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
            val netDate = messageList[position].time
            holder.textTime.text = "Время: ${netDate?.let { sdf.format(it) }}"
        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Message>) {
        messageList = list
        notifyDataSetChanged()
    }
}