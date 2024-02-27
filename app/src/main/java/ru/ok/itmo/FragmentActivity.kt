package ru.ok.itmo

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.random.Random

class FragmentActivity : FragmentActivity(R.layout.fragment_) {
    private val fragments = mutableListOf<String>()
    private val idToTag = mutableMapOf(R.id.A to "A", R.id.B to "B",
        R.id.C to "C", R.id.D to "D", R.id.E to "E")
    private val tagToId = mutableMapOf("A" to R.id.A, "B" to R.id.B,
        "C" to R.id.C, "D" to R.id.D, "E" to R.id.E)
    private lateinit var itemToFragment: Map<String, Fragment>
    private lateinit var fragmentA: FragmentSample
    private lateinit var fragmentB: FragmentSample
    private lateinit var fragmentC: FragmentSample
    private lateinit var fragmentD: FragmentSample
    private lateinit var fragmentE: FragmentSample
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var last: FragmentSample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fragmentA = FragmentSample.newInstance("A")
        fragmentB = FragmentSample.newInstance("B")
        fragmentC = FragmentSample.newInstance("C")
        fragmentD = FragmentSample.newInstance("D")
        fragmentE = FragmentSample.newInstance("E")

        val count: Int = (3..5).random()

        if (count < 5) {
            bottomNavigationView.menu.removeItem(R.id.E)
        }

        if (count == 3) {
            bottomNavigationView.menu.removeItem(R.id.D)
        }

        bottomNavigationView.setOnItemSelectedListener {
            listener(idToTag[it.itemId])
            true
        }

        fragments.add("A")
        last = fragmentA

        itemToFragment = mutableMapOf("A" to fragmentA, "B" to fragmentB, "C" to fragmentC, "D" to fragmentD,
            "E" to fragmentE)

        supportFragmentManager.beginTransaction().add(R.id.frame_layout, fragmentA, "A").commit()

    }

    private fun listener(item: String?) {
        val find = supportFragmentManager.findFragmentByTag(item)
        if (find == null) {
            itemToFragment[item]?.let {
                supportFragmentManager.beginTransaction().add(R.id.frame_layout,
                    it, item).hide(last).addToBackStack(item).commit()
            }
            if (item != null) {
                fragments.add(item)
            }
        } else {
            while (fragments.last() != item) {
                supportFragmentManager.popBackStack()
                fragments.removeLast()
            }
        }
        last = itemToFragment[item] as FragmentSample

        onBackPressed(true) {
            if (fragments.size > 1) {
                supportFragmentManager.popBackStack()
                fragments.removeLast()
                bottomNavigationView.selectedItemId = tagToId[fragments.last()]!!
                last = itemToFragment[fragments.last()] as FragmentSample
            } else {
                finish()
            }
        }
    }

    private fun onBackPressed(isEnabled: Boolean, callback: () -> Unit) {
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(isEnabled) {
                override fun handleOnBackPressed() {
                    callback()
                }
            })
    }
}