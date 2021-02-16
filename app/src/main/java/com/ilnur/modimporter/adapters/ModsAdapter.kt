package com.ilnur.modimporter.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilnur.modimporter.R
import com.ilnur.modimporter.database.ModInfo
import com.ldoublem.thumbUplib.ThumbUpView

class ModsAdapter(
    val context: Context, val modsInfo: List<ModInfo>,
    val listener: (ModInfo) -> Unit, val likeListener: (ModInfo) -> Unit,
) : RecyclerView.Adapter<ModsAdapter.ModViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModViewHolder {
        return ModViewHolder(LayoutInflater.from(this.context)
            .inflate(R.layout.item_mod_short, parent, false))
    }

    override fun onBindViewHolder(holder: ModViewHolder, position: Int) {
        holder.bindItems()

        holder.like.setOnThumbUp {
            //add to favorites
            if (it) {
                //Toast.makeText(context, "liked", Toast.LENGTH_SHORT).show()
                likeListener(modsInfo[position].copy(isFave = true))
            }
            else {
                //Toast.makeText(context, "unliked", Toast.LENGTH_SHORT).show()
                likeListener(modsInfo[position].copy(isFave = false))
            }
        }

    }

    override fun getItemCount() = modsInfo.size


    inner class ModViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.item_name)
        val description: TextView = itemView.findViewById(R.id.item_description)
        val image: ImageView = itemView.findViewById(R.id.item_img)
        val like: ThumbUpView = itemView.findViewById(R.id.like)

        fun bindItems() {
            if (modsInfo[absoluteAdapterPosition].isFave) like.setLike() else like.setUnlike()
            name.text = modsInfo[absoluteAdapterPosition].title
            description.text = modsInfo[absoluteAdapterPosition].desc
            ViewCompat.setTransitionName(image, "image_${modsInfo[absoluteAdapterPosition].filename}")
            Glide.with(context)
                .load(Uri.parse("file:///android_asset/images/"
                        +modsInfo[absoluteAdapterPosition].imgPath))
                .into(image)
          /*  image.setImageDrawable(
                Drawable.createFromStream(this.itemView.resources.assets
                .open("images/"+modsInfo[adapterPosition].imgName), null))*/
            itemView.setOnClickListener {
                listener(modsInfo[absoluteAdapterPosition])
            }
        }
    }

}

/*
class ModViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val name: TextView = itemView.findViewById(R.id.item_name)
    val description: TextView = itemView.findViewById(R.id.item_description)
    val image: ImageView = itemView.findViewById(R.id.item_img)
}*/
