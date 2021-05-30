package com.example.android.marsrealestate.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty

class PhotoGridAdapter(private val onClickListener: OnclickListener) :
	ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {

	//ViewHolder
	class MarsPropertyViewHolder(private val binding: GridViewItemBinding) :

		RecyclerView.ViewHolder(binding.root) {
		fun bind(marsProperty: MarsProperty, clickListener: OnclickListener) {
			binding.property = marsProperty
			binding.clickListener = clickListener
			binding.executePendingBindings()
		}
	}

	// Class interface event clickListener
	class OnclickListener(val clickListener: (marsProperty: MarsProperty) -> Unit) {
		fun onClick(marsProperty: MarsProperty) = clickListener(marsProperty)
	}

	companion object DiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
		override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
			return oldItem === newItem
		}

		override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
			return oldItem.id == newItem.id
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPropertyViewHolder {
		return MarsPropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
	}

	override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
		val marsProperty = getItem(position)
		holder.bind(marsProperty, onClickListener)
	}

}


