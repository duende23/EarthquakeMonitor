package com.villadevs.earthquakemonitor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.villadevs.earthquakemonitor.adapter.EarthquakeAdapter
import com.villadevs.earthquakemonitor.databinding.FragmentEarthquakeMainBinding
import com.villadevs.earthquakemonitor.model.Earthquake


class EarthquakeMainFragment : Fragment() {

    private var _binding: FragmentEarthquakeMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEarthquakeMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvEarthquake.layoutManager = LinearLayoutManager(view.context)

        val eartquakeList = mutableListOf<Earthquake>()
        eartquakeList.add(Earthquake("1", "Madrid", 8.9, 1100, 37.8, 38.4))
        eartquakeList.add(Earthquake("2", "Londres", 4.6, 300, 67.8, 39.4))
        eartquakeList.add(Earthquake("3", "Buenos Aires", 3.5, 300, 67.8, 35.4))
        eartquakeList.add(Earthquake("4", "Berlín", 6.9, 3900, 37.8, 25.4))
        eartquakeList.add(Earthquake("5", "Portugal", 5.6, 6400, 17.8, 55.4))
        eartquakeList.add(Earthquake("6", "Francia", 3.9, 5400, 27.8, 35.4))


        // Initialize the adapter and set it to the RecyclerView.
        val adapter = EarthquakeAdapter {
            //sharedViewModel.updateCurrentAmphibian(it)
            // Navigate to the details screen
            val action =
                EarthquakeMainFragmentDirections.actionEarthquakeMainFragmentToEarthquakeDetailsFragment()
            this.findNavController().navigate(action)
        }


        binding.rvEarthquake.adapter = adapter
        adapter.submitList(eartquakeList)

        if(eartquakeList.isEmpty()){
            binding.tvEarthEmpty.visibility = View.VISIBLE
        }else{
            binding.tvEarthEmpty.visibility = View.GONE
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}