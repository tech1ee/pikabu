package com.example.pikabu.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pikabu.R
import com.example.pikabu.databinding.ItemStoryBinding
import com.example.pikabu.entity.Story

class FeedAdapter(
    private val onOpenStoryClicked: (storyId: Int) -> Unit,
    private val onSaveClicked: (storyId: Int, save: Boolean) -> Unit
) :
    RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private lateinit var binding: ItemStoryBinding
    private val items = arrayListOf<Story>()

    fun setItems(items: List<Story>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemStoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedAdapter.ViewHolder, position: Int) =
        holder.bind(position)

    override fun getItemCount() = items.size

    private fun changeStoryStatus(storyId: Int, saved: Boolean) {
        onSaveClicked(storyId, saved)
        items.find { it.id == storyId }?.saved = saved
    }

    inner class ViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var item: Story

        fun bind(position: Int) {
            item = items[position]

            setTitle()
            setBody()
            setImage()
            setClicks()
            setButton()
        }

        private fun setTitle() {
            val title: String? = item.title ?: return
            binding.title.text = title
        }

        private fun setBody() {
            val body: String? = item.body ?: return
            binding.body.text = body
            binding.body.visibility = View.VISIBLE
        }

        private fun setImage() {
            if (item.images.isNullOrEmpty()) return
            val image: String? = item.images?.get(0) ?: return
            Glide.with(binding.root.context).load(image).into(binding.image)
            binding.image.visibility = View.VISIBLE
        }

        private fun setClicks() {
            val id = item.id ?: return
            binding.saveButton.setOnClickListener {
                changeStoryStatus(id, !item.saved)
                notifyItemChanged(adapterPosition)
                setButton()
            }
            binding.root.setOnClickListener { onOpenStoryClicked(id) }
        }

        private fun setButton() {
            if (item.saved) {
                binding.saveButton.text = itemView.context.getString(R.string.remove_from_saved)
                binding.saveButton.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        android.R.color.holo_green_light
                    )
                )
            } else {
                binding.saveButton.text = itemView.context.getString(R.string.save)
                binding.saveButton.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        android.R.color.holo_purple
                    )
                )
            }
        }
    }
}