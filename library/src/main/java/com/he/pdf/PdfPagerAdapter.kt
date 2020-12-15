package com.he.pdf

import android.graphics.Bitmap
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView

class PdfPagerAdapter(
    private val data: List<Bitmap>
) : RecyclerView.Adapter<PdfPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfPagerViewHolder {
        val photoView = PhotoView(parent.context)
        photoView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        return PdfPagerViewHolder(photoView)
    }

    override fun onBindViewHolder(holder: PdfPagerViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}

class PdfPagerViewHolder(
    private val photoView: PhotoView
) : RecyclerView.ViewHolder(photoView) {

    fun bind(bitmap: Bitmap) {
        photoView.setImageBitmap(bitmap)
    }

}