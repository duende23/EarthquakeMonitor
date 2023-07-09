package com.villadevs.earthquakemonitor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.villadevs.earthquakemonitor.R
import com.villadevs.earthquakemonitor.databinding.EarthquakeListItemBinding
import com.villadevs.earthquakemonitor.model.Earthquake
import kotlinx.coroutines.withContext


class EarthquakeAdapter(private val context: Context, private val onItemClicked: (Earthquake) -> Unit) :
    ListAdapter<Earthquake, EarthquakeAdapter.EarthquakeViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarthquakeViewHolder {
        return EarthquakeViewHolder(EarthquakeListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: EarthquakeViewHolder, position: Int) {
        val currentEarthquake = getItem(position)
        holder.bind(currentEarthquake)

        holder.itemView.setOnClickListener {
            onItemClicked(currentEarthquake)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Earthquake>() {
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake): Boolean {
            return oldItem == newItem
        }
    }


    inner class EarthquakeViewHolder(private var binding: EarthquakeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(earthquake: Earthquake) {
            /* binding.ivAmphibian.load(amphibian.imageResourceId.toUri().buildUpon().scheme("https").build()){
                 placeholder(R.drawable.loading_animation)
                 error(R.drawable.ic_broken_image)
             }*/
            binding.tvEarthQMagnitude.text = context.getString(R.string.magnitude_format, earthquake.magnitude)
            binding.tvEarthQPlace.text = earthquake.place
            // Load the images into the ImageView using the Coil library.
            //binding.ivAmphibian.setImageResource(R.drawable.ic_broken_image)

        }
    }
}