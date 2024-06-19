package ru.nak.ied.regist.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import ru.nak.ied.regist.R

class ItemsActivity : AppCompatActivity() {

    var bScannerQR: ConstraintLayout? = null;
    var textViewQR: TextView? = null;
    var bAddOneUser: Button? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        bScannerQR = findViewById(R.id.bScanQR)
        textViewQR = findViewById(R.id.textViewQtCode)
        bScannerQR?.setOnClickListener {
            startActivity(Intent(this, ScannerActivity::class.java))
            finish()
        }

        val responseValue = intent.getStringExtra("key");
        textViewQR?.setText(responseValue)
    }
}