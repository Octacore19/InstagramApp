package com.octacore.instagramapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.octacore.instagramapp.R
import com.octacore.instagramapp.handlers.ApiCallHandler
import com.octacore.instagramapp.model.MediaID
import com.octacore.instagramapp.model.MediaModel
import com.octacore.instagramapp.model.UserMediaModel
import com.octacore.instagramapp.repository.ProfileRepository.getSingleMedia
import com.octacore.instagramapp.utils.PreferencesUtil

class ProfileAdapter(private val mediaIDs: List<MediaID>,
                     private val accessToken: String,
                     private val context: Context): RecyclerView.Adapter<ProfileAdapter.ProfileViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewholder {
        return ProfileViewholder(LayoutInflater.from(parent.context).inflate(R.layout.image_list, parent, false))
    }

    override fun getItemCount(): Int {
        return mediaIDs.size
    }

    override fun onBindViewHolder(holder: ProfileViewholder, position: Int) {
        getUserMedia(mediaIDs[position].id, accessToken, holder)
    }


    private fun getUserMedia(id: String, accessToken: String, holder: ProfileViewholder){
        getSingleMedia(id, accessToken, object : ApiCallHandler {
            override fun success(data: Any) {
                val model = data as MediaModel
                holder.bind(model,  context)
            }

            override fun failed(title: String, reason: String) {
                Log.e("ProfileAdapter", title + reason)
            }
        })
    }

    inner class ProfileViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var imageView: ImageView = itemView.findViewById(R.id.media_photo)

        fun bind(post: MediaModel, context: Context){
            val imageUrl = post.media_url
            Glide.with(context)
                .load(imageUrl)
                .into(imageView)
        }
    }
}