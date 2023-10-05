package com.example.di_hw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val resBitcoin:TextView = findViewById(R.id.resultBitcoin)
        val resultOunce:TextView = findViewById(R.id.resultOunce)
        val resultDash:TextView = findViewById(R.id.resultDash)
        val resultBinance:TextView = findViewById(R.id.resultBinance)
        val resultTitle: TextView = findViewById(R.id.result)
        val button: Button = findViewById(R.id.button)
        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        button.setOnClickListener {
            viewModel.getData()
        }
        viewModel.uiState.observe(this) { uiState ->
            when (uiState) {
                is MyViewModel.UIState.Empty -> Unit
                is MyViewModel.UIState.Result -> {
                    resBitcoin.text = uiState.curBitcoin
                    resultOunce.text = uiState.curOunce
                    resultDash.text = uiState.curDash
                    resultBinance.text = uiState.curBinance
                    resultTitle.text = uiState.text
                }
                is MyViewModel.UIState.Processing -> resultTitle.text = "Processing..."
                is MyViewModel.UIState.Error -> {
                    resultTitle.text = ""
                    Toast.makeText(this, uiState.description, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}