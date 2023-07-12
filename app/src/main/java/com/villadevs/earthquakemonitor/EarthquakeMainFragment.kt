package com.villadevs.earthquakemonitor

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.media.MediaCodec.MetricsConstants.MODE
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.villadevs.earthquakemonitor.adapter.EarthquakeAdapter
import com.villadevs.earthquakemonitor.databinding.FragmentEarthquakeMainBinding
import com.villadevs.earthquakemonitor.model.Earthquake
import com.villadevs.earthquakemonitor.network.ApiResponseStatus
import com.villadevs.earthquakemonitor.viewmodel.EarthquakeViewModel
import com.villadevs.earthquakemonitor.viewmodel.EarthquakeViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences

private const val SORT_TYPE_KEY = "sort_type"

class EarthquakeMainFragment : Fragment() {

    private var _binding: FragmentEarthquakeMainBinding? = null
    private val binding get() = _binding!!

    //private lateinit var earthquakeCurrent: Earthquake
    //private val viewModel: EarthquakeViewModel by viewModels()

    private val sortType = getSortType()

    private val viewModel: EarthquakeViewModel by activityViewModels {
        EarthquakeViewModelFactory((activity?.application as EarthquakeApplication).database.earthquakeDao(), sortType)
    }


    


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

        /*   val eartquakeList = mutableListOf<Earthquake>()
           eartquakeList.add(Earthquake("1", "Madrid", 8.9, 1100, 37.8, 38.4))
           eartquakeList.add(Earthquake("2", "Londres", 4.6, 300, 67.8, 39.4))
           eartquakeList.add(Earthquake("3", "Buenos Aires", 3.5, 300, 67.8, 35.4))
           eartquakeList.add(Earthquake("4", "Berlín", 6.9, 3900, 37.8, 25.4))
           eartquakeList.add(Earthquake("5", "Portugal", 5.6, 6400, 17.8, 55.4))
           eartquakeList.add(Earthquake("6", "Francia", 3.9, 5400, 27.8, 35.4))*/


        // Initialize the adapter and set it to the RecyclerView.
        val adapter = EarthquakeAdapter(requireContext()) { currentEarthquake ->
            //sharedViewModel.updateCurrentAmphibian(it)
            // Navigate to the details screen
            val action =
                EarthquakeMainFragmentDirections.actionEarthquakeMainFragmentToEarthquakeDetailsFragment(
                    currentEarthquake
                )
            this.findNavController().navigate(action)
        }
        binding.rvEarthquake.adapter = adapter


        viewModel.earthqueakes.observe(viewLifecycleOwner) { earthquakeList ->
            adapter.submitList(earthquakeList)
            handleEmptyView(earthquakeList)
        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ApiResponseStatus.LOADING -> binding.pbProgressbar.visibility = View.VISIBLE
                ApiResponseStatus.DONE -> binding.pbProgressbar.visibility = View.GONE
                ApiResponseStatus.ERROR -> binding.pbProgressbar.visibility = View.GONE
                ApiResponseStatus.NOT_INTERNET_CONNECTION -> binding.pbProgressbar.visibility =
                    View.GONE
            }
        }


        // The usage of an interface lets you inject your own implementation
        val menuHost: MenuHost = requireActivity()
        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                when (menuItem.itemId) {
                    //Ordenar listado por magnitud del terrmoto
                    R.id.itSortMagnitude ->{
                        viewModel.reloadEarthquakeFromDB(true)
                        saveSortType(true)
                    }
                    //Ordenar listado por hora
                    R.id.itSortTime -> {
                        viewModel.reloadEarthquakeFromDB(false)
                        saveSortType(false)
                    }
                    //Actualizar lista de terremotos de internet (por defecto por horas)
                    R.id.itUpdateData -> viewModel.reloadEarthquake(sortType)
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


    }

    private fun handleEmptyView(earthquakeList: List<Earthquake>) {
        if (earthquakeList.isEmpty()) {
            binding.tvEarthEmpty.visibility = View.VISIBLE
        } else {
            binding.tvEarthEmpty.visibility = View.GONE
        }
    }



    //Para guardar nuestra preferencias a la hora de ordenar los terremotos
    private fun saveSortType(sortByMagnitude:Boolean){
      val prefs = context?.getSharedPreferences("earthquakes_prefs", MODE_PRIVATE)
        val editor = prefs?.edit()
        editor?.putBoolean(SORT_TYPE_KEY, sortByMagnitude)
        editor?.apply()
    }

    private fun getSortType(): Boolean {
        val prefs = context?.getSharedPreferences("earthquakes_prefs", MODE_PRIVATE)
        if (prefs != null) {
            return prefs.getBoolean(SORT_TYPE_KEY, false)
        }else{
            return false
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}