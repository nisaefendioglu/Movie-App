package com.nisaefendioglu.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.nisaefendioglu.movieapp.model.DataModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nisaefendioglu.movieapp.R
import com.nisaefendioglu.movieapp.databinding.FragmentItemDetailsBinding

class MovieDetails : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentItemDetailsBinding

    companion object {
        const val DATA_KEY = "key"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_item_details, container, false)
        val dataNew = arguments?.getParcelable<DataModel>(DATA_KEY)
        context?.let {
            Glide.with(it).load(dataNew?.poster).into(binding.itemImage)
            binding.fragmentItemName.text = dataNew?.title
            binding.fragmentItemYear.text = "Year : " + dataNew?.year
            binding.fragmentItemType.text = "Genre : " + dataNew?.type
            binding.imdbId.text = "IMDB ID : " + dataNew?.imdbID
        }
        binding.fragmentCrossBtn.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}
