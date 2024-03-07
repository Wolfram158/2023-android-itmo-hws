package ru.ok.itmo

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class MessageAdapter(private val context: Context?, private val channel: String): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    private var messageList = emptyList<Message>()

    class MessageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textFrom: TextView = view.findViewById(R.id.textFrom)
        val textMessage: TextView = view.findViewById(R.id.textMessage)
        val textTime: TextView = view.findViewById(R.id.textTime)
        val BASE_URL = "https://faerytea.name:8008/img/"
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
        if (messageList[position].data.Text != null) {
            holder.textMessage.text = "Сообщение: ${messageList[position].data.Text?.text}"
        } else {
            holder.textMessage.text = "Нажмите на сообщение, чтобы открыть изображение."
        }
        if (messageList[position].time != null) {
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
            val netDate = messageList[position].time
            holder.textTime.text = "Время: ${netDate?.let { sdf.format(it) }}"
        }
        holder.itemView.setOnClickListener {
            if (messageList[position].data.Text == null) {
                (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ImageFragment(channel,
                        holder.BASE_URL + messageList[position].data.Image?.link
                    ))
                    .commit()
            }
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