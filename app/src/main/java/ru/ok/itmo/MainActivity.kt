package ru.ok.itmo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private var progressBar = 0
    private val handler = Handler()
    private var time = 100L

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bar: ProgressBar = findViewById(R.id.progressBar2)
        val buttonStart: Button = findViewById(R.id.button)
        val buttonRestart: Button = findViewById(R.id.button2)
        val radioButton50: RadioButton = findViewById(R.id.radioButton)
        val radioButton100: RadioButton = findViewById(R.id.radioButton2)
        val radioButton300: RadioButton = findViewById(R.id.radioButton3)
        val radioButton500: RadioButton = findViewById(R.id.radioButton4)

        radioButton100.isChecked = true

        fun setUnchecked() {
            radioButton50.isChecked = false
            radioButton100.isChecked = false
            radioButton300.isChecked = false
            radioButton500.isChecked = false
        }

        fun onClickHelper(radioButton: RadioButton, t: Long) {
            setUnchecked()
            radioButton.isChecked = true
            time = t
        }

        radioButton50.setOnClickListener {
            onClickHelper(radioButton50, 50)
        }

        radioButton100.setOnClickListener {
            onClickHelper(radioButton100, 100)
        }

        radioButton300.setOnClickListener {
            onClickHelper(radioButton300, 300)
        }

        radioButton500.setOnClickListener {
            onClickHelper(radioButton500, 500)
        }

        buttonRestart.setOnClickListener {
            Thread {
                if (progressBar == 100) {
                    progressBar = 0
                }
                handler.post {
                    bar.progress = progressBar
                }
            }.start()
        }

        fun thread() {
            buttonStart.setOnClickListener {
                Thread {
                    while (progressBar < 100) {
                        progressBar++
                        Thread.sleep(time)
                        handler.post {
                            bar.progress = progressBar
                        }
                        bar.progress = progressBar
                    }
                }.start()
            }
        }

        fun rxJava() {
            buttonStart.setOnClickListener {
                Observable.create { emitter: ObservableEmitter<Int> ->
                    for (i in 0..100) {
                        Thread.sleep(100)
                        emitter.onNext(i)
                    }
                    emitter.onComplete()
                }
                    .subscribeOn(Schedulers.io())
                    .subscribe { progress ->
                        bar.progress = progress
                    }
            }
        }

        thread()
    }

}