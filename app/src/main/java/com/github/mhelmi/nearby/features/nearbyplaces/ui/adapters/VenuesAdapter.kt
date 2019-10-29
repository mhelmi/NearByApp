package com.github.mhelmi.nearby.features.nearbyplaces.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mhelmi.nearby.R
import com.github.mhelmi.nearby.utils.extentions.load
import com.github.mhelmi.nearby.features.nearbyplaces.data.requests.Venue
import kotlinx.android.synthetic.main.item_venue.view.*

class VenuesAdapter : ListAdapter<Venue, VenueViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        return VenueViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_venue, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class VenueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(venue: Venue) = with(itemView) {
        tvTitle.text = venue.name
        venue.photoUrl.let { ivPhoto.load(it) }

        val category = venue.categories.let {
            it.forEach { category ->
                if (category.primary) return@let category.shortName
            }
            if (it.isNotEmpty()) it[0].shortName else ""
        }
        val address = "(${venue.location.city} - ${venue.location.address})"
        val categoryAndAddress = "$category - $address"
        tvCategoryAndAddress.text = categoryAndAddress
    }
}

class DiffCallback : DiffUtil.ItemCallback<Venue>() {
    override fun areItemsTheSame(oldItem: Venue, newItem: Venue): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Venue, newItem: Venue): Boolean {
        return oldItem.name == newItem.name
                && oldItem.photoUrl == newItem.photoUrl
                && oldItem.rating == newItem.rating
                && oldItem.categories.size == newItem.categories.size
                && oldItem.location.lat == newItem.location.lat
                && oldItem.location.lng == newItem.location.lng
    }
}