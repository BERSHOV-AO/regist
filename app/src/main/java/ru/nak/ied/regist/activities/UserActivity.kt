package ru.nak.ied.regist.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.nak.ied.regist.R
import ru.nak.ied.regist.databinding.ActivityUserBinding
import ru.nak.ied.regist.fragments.AgvFragment
import ru.nak.ied.regist.fragments.FragmentManager
import ru.nak.ied.regist.fragments.NoteFragment
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
                R.id.addAGV -> {
                    Log.d("MyLog", "addAGV")
                    FragmentManager.currentFrag?.onClickNew()
                }

//                R.id.notes -> {
//                    Log.d("MyLog", "Notes")
//
//                }

                R.id.list_agv -> {
                    Log.d("MyLog", "list_agv")
                    FragmentManager.setFragment(AgvFragment.newInstance(), this)
                }

                R.id.qrcode -> {
                    Log.d("MyLog", "qrcode")

                    val intent = Intent(this, ScannerActivity::class.java)
                    startActivity(intent);

                      //FragmentManager.setFragment(QrScannerFragment.newInstance(), this)
                }
            }
            true
        }
    }
}
