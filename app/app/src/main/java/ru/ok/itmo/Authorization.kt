package ru.ok.itmo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Authorization() : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_authorization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button: Button = view.findViewById(R.id.button)
        val login: EditText = view.findViewById(R.id.editText)
        val password: TextView = view.findViewById(R.id.editTextPassword)
        val textWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val name = login.text.toString()
                val pwd = password.text.toString()
                button.isEnabled = name.isNotEmpty() && pwd.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        login.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)
        val token = Token(null)
        val retrofit = Retrofit()

        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    token.save(retrofit.api.login(AuthRequest(login.text.toString(),
                        password.text.toString())))
                    val sharedPreferences = context?.getSharedPreferences("app", 0)
                    sharedPreferences?.edit()?.putString("token", token.body())?.apply()
                    sharedPreferences?.edit()?.putString("user", login.text.toString())?.apply()
                    withContext(Dispatchers.Main) {
                        when (token.code()) {
                            200 -> activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.fragment_container, Channel())
                                ?.commit()
                            401 -> Toast.makeText(requireActivity(), "Введены неверные данные", Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(requireActivity(), "Неопознанная ошибка", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (_: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireActivity(), "Отсутствует интернет-соединение", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}