package com.example.tomorrow_home_app.ui.article

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.tomorrow_home_app.R
import com.example.tomorrow_home_app.databinding.FragmentWriteBinding

class WriteArticleFragment : Fragment(R.layout.fragment_write) {
    private lateinit var binding : FragmentWriteBinding

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWriteBinding.bind(view)

        // Launch the photo picker and let the user choose only images.
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}