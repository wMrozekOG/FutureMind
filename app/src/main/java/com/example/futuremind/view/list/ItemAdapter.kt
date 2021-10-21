package com.example.futuremind.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.futuremind.R
import com.example.futuremind.databinding.ItemListEntryBinding
import com.example.futuremind.extensions.extractUrl
import com.example.futuremind.extensions.formatDate
import com.example.futuremind.extensions.visible
import com.example.futuremind.model.Item
import com.squareup.picasso.Picasso

class ItemAdapter(
    private val onItemClick: (String) -> Unit
) : ListAdapter<Item, ItemAdapter.ItemEntryVH>(MovieEntryDiffCallback()) {

    private var expandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemEntryVH {
        val binding = ItemListEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemEntryVH(
            binding,
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: ItemEntryVH, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ItemEntryVH(
        private val binding: ItemListEntryBinding,
        private val onItemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: Item, position: Int) {
            with(binding) {
                val context = binding.root.context
                val extractedUrl = entry.description.extractUrl()
                val itemDescription = entry.description.replace(extractedUrl, "")
                val expanded = expandedPosition == position

                toggleButton(expanded)
                title.text = entry.title
                description.run {
                    text = context.resources.getString(R.string.list_item_description).format(itemDescription)
                    visible(expanded)
                }
                modificationDate.run {
                    text = context.resources.getString(R.string.list_item_modification_date).format(entry.modificationDate.formatDate("dd MMM, yy"))
                    visible(expanded)
                }
                binding.root.setOnClickListener {
                    onItemClick(extractedUrl)
                }

                binding.expandButton.setOnClickListener {
                    if (position == expandedPosition) {
                        expandedPosition = -1
                        notifyItemChanged(position)
                    } else {
                        if (expandedPosition != -1) {
                            notifyItemChanged(expandedPosition);
                        }
                        expandedPosition = position;
                        notifyItemChanged(position);
                    }
                }

                Picasso.get().load(entry.imageUrl).into(binding.backgroundImage);
            }
        }

        private fun toggleButton(expanded: Boolean) {
            val drawable = if (expanded) {
                AppCompatResources.getDrawable(binding.expandButton.context, R.drawable.ic_arrow_drop_up)
            } else {
                AppCompatResources.getDrawable(binding.expandButton.context, R.drawable.ic_arrow_drop_down)
            }
            binding.expandButton.background = drawable
        }
    }
}

class MovieEntryDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem
}