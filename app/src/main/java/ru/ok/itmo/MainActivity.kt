package ru.ok.itmo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    private val regexEmail = Regex("[a-zA-Z0-9._+-]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]{1,6}")
    private val minPswLen = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        val button : Button = findViewById(R.id.button_password)
        val pswInputField : EditText = findViewById(R.id.editTextPassword)
        val emailInputField : EditText = findViewById(R.id.editTextEmailAddress)
        val lightTheme : Button = findViewById(R.id.button)
        val darkTheme : Button = findViewById(R.id.button2)
        val systemTheme : Button = findViewById(R.id.button3)

        systemTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

        darkTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        lightTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        pswInputField.setOnKeyListener(View.OnKeyListener {_, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                button.callOnClick()
                return@OnKeyListener true
            }
            false
        })

        button.setOnClickListener {
            if (pswInputField.text.toString().isEmpty()) {
                Toast.makeText(this, resources.getString(R.string.pswEmpty), Toast.LENGTH_SHORT).show()
            } else if (pswInputField.text.toString().length < minPswLen) {
                Toast.makeText(this, resources.getString(R.string.pswInvalid), Toast.LENGTH_SHORT).show()
            }
            if (emailInputField.text.toString().isEmpty()) {
                Toast.makeText(this, resources.getString(R.string.loginEmpty), Toast.LENGTH_SHORT).show()
            } else if (!emailInputField.text.toString().matches(regexEmail)) {
                Toast.makeText(this, resources.getString(R.string.loginInvalid), Toast.LENGTH_SHORT).show()
            }
        }

        val checkBox : CheckBox = findViewById(R.id.checkBox)
        checkBox.setOnClickListener { _ ->
            if (checkBox.isChecked) {
                pswInputField.transformationMethod = null
            } else {
                pswInputField.transformationMethod = PasswordTransformationMethod()
            }
        }

    }
}


