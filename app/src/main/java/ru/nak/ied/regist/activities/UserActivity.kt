package ru.nak.ied.regist.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.nak.ied.regist.R
import ru.nak.ied.regist.databinding.ActivityUserBinding
import ru.nak.ied.regist.fragments.FragmentManager
import ru.nak.ied.regist.fragments.QrScannerFragment
import ru.nak.ied.regist.fragments.SettingsFragment

class UserActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater) // активируем binding
        //setContentView(R.layout.activity_user)
        setContentView(binding.root)
        setBottomNavListener()
    }

    private fun setBottomNavListener() {
        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> {
                    Log.d("MyLog", "Settings")
                    FragmentManager.setFragment(SettingsFragment.newInstance(), this)
                }

                R.id.notes -> {
                    Log.d("MyLog", "Notes")
                    //FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }

                R.id.list_agv -> {
                    Log.d("MyLog", "list_agv")
                }

                R.id.qrcode -> {
                    Log.d("MyLog", "qrcode")

                    FragmentManager.setFragment(QrScannerFragment.newInstance(), this)
                }
            }
            true
        }
    }
}
