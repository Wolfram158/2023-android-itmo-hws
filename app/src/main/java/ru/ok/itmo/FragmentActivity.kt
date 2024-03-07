package ru.ok.itmo

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.properties.Delegates

class FragmentActivity : FragmentActivity(R.layout.fragment_) {
    private var fragments = mutableListOf<String>()
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
    private var count by Delegates.notNull<Int>()
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var last: FragmentSample

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("FragmentActivity", "onCreate")

        if (savedInstanceState == null) {
            fragmentA = FragmentSample.newInstance()
            fragmentB = FragmentSample.newInstance()
            fragmentC = FragmentSample.newInstance()
            fragmentD = FragmentSample.newInstance()
            fragmentE = FragmentSample.newInstance()
            count = (3..5).random()
            fragments.add("A")
            last = fragmentA
            supportFragmentManager.beginTransaction().add(R.id.frame_layout, fragmentA, "A").commit()
        } else {
            fragmentA = Fragments.a
            fragmentB = Fragments.b
            fragmentC = Fragments.c
            fragmentD = Fragments.d
            fragmentE = Fragments.e
            last = Fragments.last
            fragments = Fragments.fragments
            Log.e(fragments.size.toString(), fragments.size.toString())
            for (fragment in fragments) {
                supportFragmentManager.beginTransaction().addToBackStack(fragment).commit()
            }
            count = Fragments.count
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {
            listener(idToTag[it.itemId])
            true
        }

        if (count < 5) {
            bottomNavigationView.menu.removeItem(R.id.E)
        }

        if (count == 3) {
            bottomNavigationView.menu.removeItem(R.id.D)
        }

        itemToFragment = mutableMapOf("A" to fragmentA, "B" to fragmentB, "C" to fragmentC, "D" to fragmentD,
            "E" to fragmentE)

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

    }

    private fun onBackPressed(isEnabled: Boolean, callback: () -> Unit) {
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(isEnabled) {
                override fun handleOnBackPressed() {
                    callback()
                }
            })
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Fragments.last = last
        Fragments.fragments = fragments
        Log.e("onSaveInstanceState", fragments.size.toString())
        Fragments.a = fragmentA
        Fragments.b = fragmentB
        Fragments.c = fragmentC
        Fragments.d = fragmentD
        Fragments.e = fragmentE
        Fragments.count = count
    }

}