package com.ext.locationpicker.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.ext.locationpicker.AutoLocationPicker
import com.ext.locationpicker.databinding.BottomsheetPickerBinding
import com.ext.locationpicker.ui.common.SimplePickerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CityPickerBottomSheet(
    private val countryName: String,
    private val stateName: String,
    private val stateId: Int,
    private val onSelect: (String) -> Unit
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

        AutoLocationPicker.getCities(
            countryName = countryName,
            stateName = stateName,
            stateId = stateId,
            onResult = { list ->
                val names = list.map { it.name }

                val adapter = SimplePickerAdapter(names) { name, _ ->
                    onSelect(name)
                    dismiss()
                }
                binding.recyclerView.adapter = adapter
                binding.etSearch.addTextChangedListener {
                    adapter.filter(it.toString())
                }
            }
        )
    }
}
