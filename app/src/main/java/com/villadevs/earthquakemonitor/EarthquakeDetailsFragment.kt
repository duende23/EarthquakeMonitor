package com.villadevs.earthquakemonitor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.villadevs.earthquakemonitor.databinding.FragmentEarthquakeDetailsBinding
import com.villadevs.earthquakemonitor.databinding.FragmentEarthquakeMainBinding
import com.villadevs.earthquakemonitor.model.Earthquake
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class EarthquakeDetailsFragment : Fragment() {


    private var _binding: FragmentEarthquakeDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: EarthquakeDetailsFragmentArgs by navArgs()

    companion object {
        private const val TIME_FORMAT = "dd/MMM/yyyy HH:mm:ss"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEarthquakeDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val earthquake = args.earthquakeObject

        binding.apply {
            magnitudeText.text = getString(R.string.magnitude_format, earthquake.magnitude)
            longitudeText.text = earthquake.longitude.toString()
            latitudeText.text = earthquake.latitude.toString()
            placeText.text = earthquake.place
            setupTime(binding, earthquake)
        }

        //ivPhotoBookDetails.setImageResource(R.drawable.ic_launcher_background)

        /*  binding.ivPhotoBookDetails.load(book.image) {
              //crossfade(true)
              //placeholder(R.drawable.image)
              //transformations(CircleCropTransformation())
          }*/

    }

    private fun setupTime(binding: FragmentEarthquakeDetailsBinding, earthquake: Earthquake, ) {
        val dateFormat = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        val date = Date(earthquake.time)
        binding.timeText.text = dateFormat.format(date)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}