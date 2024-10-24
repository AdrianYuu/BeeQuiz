package edu.bluejack23_2.beequiz.activity.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.bluejack23_2.beequiz.R
import edu.bluejack23_2.beequiz.databinding.ActivityMainBinding
import edu.bluejack23_2.beequiz.fragment.user.*
import edu.bluejack23_2.beequiz.helper.FragmentHelper
import edu.bluejack23_2.beequiz.`interface`.IActivity

class MainActivity : AppCompatActivity(), IActivity {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        this.init()
        this.setEventListener()
        this.setContentView(binding.root)
    }

    override fun init() {
        FragmentHelper.replaceFragment(supportFragmentManager, HomeFragment())
    }

    override fun setEventListener() {
        binding.bottomNavigationBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    FragmentHelper.replaceFragment(supportFragmentManager, HomeFragment())
                    true
                }
                R.id.search -> {
                    FragmentHelper.replaceFragment(supportFragmentManager, SearchFragment())
                    true
                }
                R.id.my_quiz -> {
                    FragmentHelper.replaceFragment(supportFragmentManager, MyQuizFragment())
                    true
                }
                R.id.scan_qr -> {
                    FragmentHelper.replaceFragment(supportFragmentManager, ScanQrFragment())
                    true
                }
                R.id.history -> {
                    FragmentHelper.replaceFragment(supportFragmentManager, HistoryFragment())
                    true
                }
                else -> false
            }
        }
    }
}