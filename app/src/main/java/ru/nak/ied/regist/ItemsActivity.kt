package ru.nak.ied.regist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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