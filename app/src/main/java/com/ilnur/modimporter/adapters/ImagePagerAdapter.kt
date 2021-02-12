package com.ilnur.modimporter.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilnur.modimporter.R
import com.ldoublem.thumbUplib.ThumbUpView

class ImagePagerAdapter(
    val context: Context, val img_paths: List<String>,
) : RecyclerView.Adapter<ImagePagerAdapter.PagerViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(
            LayoutInflater.from(this.context)
            .inflate(R.layout.item_pager_detailed, parent, false))

    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bindItems()

    }

    override fun getItemCount() = img_paths.size

    inner class PagerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.item_page_img)

        fun bindItems() {
            Glide.with(context)
                .load(Uri.parse("file:///android_asset/images/"+img_paths[bindingAdapterPosition]))
                .into(image)
            /*  image.setImageDrawable(
                  Drawable.createFromStream(this.itemView.resources.assets
                  .open("images/"+modsInfo[adapterPosition].imgName), null))*/

        }
    }
}