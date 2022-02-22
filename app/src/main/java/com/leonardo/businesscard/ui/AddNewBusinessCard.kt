package com.leonardo.businesscard.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.leonardo.businesscard.App
import com.leonardo.businesscard.R
import com.leonardo.businesscard.data.BusinessCard
import top.defaults.colorpicker.ColorPickerPopup

class AddNewBusinessCard : AppCompatActivity() {
    private val binding by lazy {
        com.leonardo.businesscard.databinding.ActivityAddNewBusinessCardBinding.inflate(
            layoutInflater
        )
    }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val mDefaultColor = 0
        insertListener()
        // calling the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tilPhone.editText?.addTextChangedListener(PhoneNumberFormattingTextWatcher())

    }


    private fun insertListener() {
        binding.previewSelectedColor.setOnClickListener { v ->
            ColorPickerPopup.Builder(this@AddNewBusinessCard)
                .initialColor(Color.RED) // set initial color of the color picker dialog
                .enableBrightness(true) // enable color brightness slider or not
                .enableAlpha(true) // enable color alpha changer on slider or not
                .okTitle("Choose") // this is top right Choose button
                .cancelTitle("Cancel") // this is top left Cancel button which closes the
                .showIndicator(true) // this is the small box which shows the chosen
                // color by user at the bottom of the cancel button
                .showValue(false) // this is the value which shows the selected
                // color hex code the above all values can be made false to disable them on the
                // color picker dialog.
                .build()
                .show(v, object : ColorPickerPopup.ColorPickerObserver() {
                    override fun onColorPicked(color: Int) {
                        binding.previewSelectedColor.setBackgroundColor(color)
                        binding.previewSelectedColor.text = color.toString()
                    }

                    fun onColor(color: Int, fromUser: Boolean) {
                        binding.previewSelectedColor.setBackgroundColor(color)
                        binding.previewSelectedColor.text = color.toString()
                    }
                })
        }

        binding.btConfirm.setOnClickListener {
            val businessCard = BusinessCard(
                name = binding.tilName.editText?.text.toString(),
                phone = binding.tilPhone.editText?.text.toString(),
                email = binding.tilEmail.editText?.text.toString(),
                company = binding.tilCompany.editText?.text.toString(),
                customBackground = if (binding.previewSelectedColor.text == "") {
                    (ContextCompat.getColor(this, R.color.gray_700))
                } else binding.previewSelectedColor.text.toString().toInt()

            )
            mainViewModel.insert(businessCard)
            Toast.makeText(this, R.string.label_show_success, Toast.LENGTH_LONG).show()
            finish()
        }
    }


    // this event will enable the back
// function to the button on press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}