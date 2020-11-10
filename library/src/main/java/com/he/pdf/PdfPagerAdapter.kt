package com.he.pdf

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView

class PdfPagerAdapter(private val renderer: PdfRenderer)
    : RecyclerView.Adapter<PdfPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PdfPagerViewHolder(
        PhotoView(parent.context)
    )

    override fun onBindViewHolder(holder: PdfPagerViewHolder, position: Int) {
        holder.bind(renderer.openPage(position))
    }

    override fun getItemCount() = renderer.pageCount
}

class PdfPagerViewHolder(private val photoView: PhotoView) : RecyclerView.ViewHolder(photoView) {

    fun bind(page: PdfRenderer.Page) {
        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.RGB_565)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        photoView.setImageBitmap(bitmap)
    }

}