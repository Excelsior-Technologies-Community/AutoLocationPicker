package com.ext.autolocationpicker

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import com.ext.locationpicker.AutoLocationPicker
import com.ext.locationpicker.ui.country.CountryPickerBottomSheet
import com.ext.locationpicker.ui.state.StatePickerBottomSheet
import com.ext.locationpicker.ui.city.CityPickerBottomSheet
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    // ✅ UI Components (New Premium UI)
    private lateinit var cardCountry: MaterialCardView
    private lateinit var cardState: MaterialCardView
    private lateinit var cardCity: MaterialCardView

    private lateinit var etCountry: TextView
    private lateinit var etState: TextView
    private lateinit var etCity: TextView

    private lateinit var ivArrowCountry: ImageView
    private lateinit var ivArrowState: ImageView
    private lateinit var ivArrowCity: ImageView

    // ✅ Selected Data
    private var selectedCountryId = 0
    private var selectedStateId = 0
    private var selectedCountryName = ""
    private var selectedStateName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Init Library
        AutoLocationPicker.init(applicationContext)

        // ✅ Bind Views
        cardCountry = findViewById(R.id.cardCountry)
        cardState = findViewById(R.id.cardState)
        cardCity = findViewById(R.id.cardCity)

        etCountry = findViewById(R.id.etCountry)
        etState = findViewById(R.id.etState)
        etCity = findViewById(R.id.etCity)

        ivArrowCountry = findViewById(R.id.ivArrowCountry)
        ivArrowState = findViewById(R.id.ivArrowState)
        ivArrowCity = findViewById(R.id.ivArrowCity)

        // ✅ COUNTRY CLICK → AUTO OPEN STATE
        cardCountry.setOnClickListener {

            rotateArrow(ivArrowCountry, true)

            CountryPickerBottomSheet { countryName, countryId ->

                // ✅ Save Country
                rotateArrow(ivArrowCountry, false)

                selectedCountryName = countryName
                selectedCountryId = countryId
                etCountry.text = countryName

                // ✅ Reset State & City
                selectedStateId = 0
                selectedStateName = ""
                etState.text = "Select State"
                etCity.text = "Select City"

                // ✅ AUTO OPEN STATE
                rotateArrow(ivArrowState, true)

                StatePickerBottomSheet(
                    countryId = selectedCountryId,
                    countryName = selectedCountryName
                ) { stateName, stateId ->

                    rotateArrow(ivArrowState, false)

                    selectedStateName = stateName
                    selectedStateId = stateId
                    etState.text = stateName

                    // ✅ AUTO OPEN CITY
                    rotateArrow(ivArrowCity, true)

                    CityPickerBottomSheet(
                        countryName = selectedCountryName,
                        stateName = selectedStateName,
                        stateId = selectedStateId
                    ) { cityName ->

                        rotateArrow(ivArrowCity, false)
                        etCity.text = cityName

                        // ✅ (Optional) Save to session if using SharedPreferences
                        // session.saveLocation(...)
                    }.show(supportFragmentManager, "city")

                }.show(supportFragmentManager, "state")

            }.show(supportFragmentManager, "country")
        }
    }

    // ✅ Smooth Arrow Rotation Animation
    private fun rotateArrow(arrow: ImageView, expand: Boolean) {
        arrow.animate()
            .rotation(if (expand) 180f else 0f)
            .setDuration(200)
            .start()
    }
}
