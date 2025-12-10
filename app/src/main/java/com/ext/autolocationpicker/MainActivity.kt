package com.ext.autolocationpicker

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ext.locationpicker.AutoLocationPicker
import com.ext.locationpicker.ui.country.CountryPickerBottomSheet
import com.ext.locationpicker.ui.state.StatePickerBottomSheet
import com.ext.locationpicker.ui.city.CityPickerBottomSheet


class MainActivity : AppCompatActivity() {
    private lateinit var btnCountry: Button
    private lateinit var btnState: Button
    private lateinit var btnCity: Button

    private var selectedCountryId: Int = 0
    private var selectedStateId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AutoLocationPicker.init(applicationContext)
        btnCountry = findViewById(R.id.btnCountry)
        btnState = findViewById(R.id.btnState)
        btnCity = findViewById(R.id.btnCity)

        btnCountry.setOnClickListener {
            CountryPickerBottomSheet { name, id ->
                selectedCountryId = id
                btnCountry.text = name
                btnState.text = "Select State"
                btnCity.text = "Select City"
            }.show(supportFragmentManager, "country")
        }
        btnState.setOnClickListener {
            if (selectedCountryId == 0) {
                Toast.makeText(this, "Select country first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            StatePickerBottomSheet(selectedCountryId) { name, id ->
                selectedStateId = id
                btnState.text = name
                btnCity.text = "Select City"
            }.show(supportFragmentManager, "state")
        }

        btnCity.setOnClickListener {
            if (selectedStateId == 0) {
                Toast.makeText(this, "Select state first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CityPickerBottomSheet(selectedStateId) { name ->
                btnCity.text = name
            }.show(supportFragmentManager, "city")
        }
    }
}