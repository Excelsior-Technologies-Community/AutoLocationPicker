package com.ext.locationpicker.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ext.locationpicker.AutoLocationPicker
import com.ext.locationpicker.databinding.BottomsheetPickerBinding
import com.ext.locationpicker.ui.common.SimplePickerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CountryPickerBottomSheet(
    private val onSelect: (countryName: String, countryId: Int) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetPickerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetPickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        AutoLocationPicker.getCountries(
            onResult = { list ->
                val names = list.map { it.name }

                val adapter = SimplePickerAdapter(names) { name, pos ->
                    onSelect(name, list[pos].id)
                    dismiss()
                }
                binding.recyclerView.adapter = adapter
            }
        )

    }
}
