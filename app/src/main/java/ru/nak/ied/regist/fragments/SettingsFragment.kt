package ru.nak.ied.regist.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ru.nak.ied.regist.R
import ru.nak.ied.regist.ScannerActivity

class SettingsFragment : BaseFragment() {
    override fun onClickNew() {
        TODO("Not yet implemented")
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}
