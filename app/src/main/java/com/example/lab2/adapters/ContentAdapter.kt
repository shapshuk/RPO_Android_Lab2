package com.example.lab2.adapters

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lab2.ItemDetailFragment
import com.example.lab2.ItemListFragment
import com.example.lab2.R
import com.example.lab2.databinding.ItemListContentBinding
import com.example.lab2.models.Content
import com.example.lab2.placeholder.PlaceholderContent
import java.net.URL

class ContentAdapter(
    private val values: LiveData<List<Content>>,
    private val itemDetailFragmentContainer: View?,
    private val onClick : (Content, View) -> Unit
) :
    RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    init {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values.value?.get(position)!!
        Glide.with(holder.itemView).load(item.imageUrl.toUri()).into(holder.image)
        holder.contentView.text = item.name

        holder.itemView.setOnClickListener { _ ->
            onClick(item, holder.itemView)
        }

//        with(holder.itemView) {
//            tag = item
//            setOnClickListener { itemView ->
//                val item = itemView.tag as PlaceholderContent.PlaceholderItem
//                val bundle = Bundle()
//                bundle.putString(
//                    ItemDetailFragment.ARG_ITEM_ID,
//                    item.id
//                )
//                if (itemDetailFragmentContainer != null) {
//                    itemDetailFragmentContainer.findNavController()
//                        .navigate(R.id.fragment_item_detail, bundle)
//                } else {
//                    itemView.findNavController().navigate(R.id.show_item_detail, bundle)
//                }
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                /**
//                 * Context click listener to handle Right click events
//                 * from mice and trackpad input to provide a more native
//                 * experience on larger screen devices
//                 */
//                setOnContextClickListener { v ->
//                    val item = v.tag as PlaceholderContent.PlaceholderItem
//                    Toast.makeText(
//                        v.context,
//                        "Context click of item " + item.id,
//                        Toast.LENGTH_LONG
//                    ).show()
//                    true
//                }
//            }
//
//            setOnLongClickListener { v ->
//                // Setting the item id as the clip data so that the drop target is able to
//                // identify the id of the content
//                val clipItem = ClipData.Item(item.id)
//                val dragData = ClipData(
//                    v.tag as? CharSequence,
//                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
//                    clipItem
//                )
//
//                if (Build.VERSION.SDK_INT >= 24) {
//                    v.startDragAndDrop(
//                        dragData,
//                        View.DragShadowBuilder(v),
//                        null,
//                        0
//                    )
//                } else {
//                    v.startDrag(
//                        dragData,
//                        View.DragShadowBuilder(v),
//                        null,
//                        0
//                    )
//                }
//            }
//        }
    }

    override fun getItemCount() = values.value?.size ?: 0

    inner class ViewHolder(binding: ItemListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image: ImageView = binding.image
        val contentView: TextView = binding.content
    }

}