package com.davevarga.photogallery

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_gallery_image.view.*

class ImageRecyclerAdapter(private val c: Context, private val imageList: ArrayList<Uri>) :
    RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {

    private val itemLimit = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        return ImageViewHolder(
            LayoutInflater.from(c).inflate(R.layout.item_gallery_image, parent, false)
        )

    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind()

    }

    override fun getItemCount(): Int {
        if (imageList.size > itemLimit) {
            return itemLimit
        } else return imageList.size
    }

//    //to remove duplicates
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            val image = imageList.get(adapterPosition)

            Glide.with(c)
                .load(image)
                .centerCrop()
                .into(itemView.galleryImage)

        }


    }

}



