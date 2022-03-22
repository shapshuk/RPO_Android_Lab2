package com.example.lab2

import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.lab2.placeholder.PlaceholderContent
import com.example.lab2.databinding.FragmentItemDetailBinding
import com.example.lab2.models.Content
import com.example.lab2.services.DataService
import com.example.lab2.services.FirebaseDataService
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.firebase.ktx.Firebase

class ItemDetailFragment : Fragment() {

    /**
     * The placeholder content this fragment is presenting.
     */
    private var item: Content? = null

    private lateinit var localId : String

    private var dataService : DataService = FirebaseDataService()

    lateinit var itemIdTextView: TextView
    lateinit var itemNameTextView: TextView
    lateinit var itemVideoPlayer: StyledPlayerView
    lateinit var itemImageUrlImageView: ImageView

    lateinit var player : ExoPlayer

    private var toolbarLayout: CollapsingToolbarLayout? = null

    private var _binding: FragmentItemDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { bundle ->
            if (bundle.containsKey(ARG_ITEM_ID)) {
                // Load the placeholder content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                localId =  bundle.getString(ARG_ITEM_ID)!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)

        player = ExoPlayer.Builder(requireActivity()).build()

        val rootView = binding.root

        toolbarLayout = binding.toolbarLayout

        itemIdTextView = binding.itemId!!
        itemImageUrlImageView = binding.toolbarImage!!
        itemNameTextView = binding.itemName!!

        itemVideoPlayer = binding.itemVideoPlayer!!
        itemVideoPlayer.player = player

        dataService.getEntityById(localId).observe(viewLifecycleOwner) {
            item = it
            updateContent()
        }

        return rootView
    }

    private fun updateContent() {
        toolbarLayout?.title = item?.name

//         Show the placeholder content as text in a TextView.
        item?.let {
            itemNameTextView.text = it.name
            itemIdTextView.text = it.id

            Glide.with(this).load(it.imageUrl.toUri()).into(itemImageUrlImageView)
            
            player.setMediaItem(MediaItem.fromUri(it.videoUrl.toUri()))
            player.prepare()
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

