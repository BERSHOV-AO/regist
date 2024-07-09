package ru.nak.ied.regist.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ru.nak.ied.regist.R
import ru.nak.ied.regist.activities.ScannerActivity


class QrScannerFragment : BaseFragment() {
    override fun onClickNew() {
        TODO("Not yet implemented")
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_qr_scanner, container, false)

        val button = view.findViewById<Button>(R.id.buttonQr)
        button.setOnClickListener {
            Log.d("MyLog", "qrcodeFragment")
            val intent = Intent(requireContext(), ScannerActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = QrScannerFragment()
    }
}