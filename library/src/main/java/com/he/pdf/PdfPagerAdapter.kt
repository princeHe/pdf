package com.he.pdf

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView

class PdfPagerAdapter(
    val renderer: PdfRenderer, private val displayQuality: DisplayQuality
) : RecyclerView.Adapter<PdfPagerViewHolder>() {

    var currentPage: PdfRenderer.Page? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfPagerViewHolder {
        val photoView = PhotoView(parent.context)
        photoView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        return PdfPagerViewHolder(photoView, displayQuality)
    }

    override fun onBindViewHolder(holder: PdfPagerViewHolder, position: Int) {
        currentPage?.close()
        currentPage = renderer.openPage(position).apply {
            holder.bind(this)
        }
    }

    override fun getItemCount() = renderer.pageCount
}

class PdfPagerViewHolder(
    private val photoView: PhotoView, private val displayQuality: DisplayQuality
) : RecyclerView.ViewHolder(photoView) {

    fun bind(page: PdfRenderer.Page) {
        val bitmap = Bitmap.createBitmap(
            page.width * displayQuality.quality, page.height * displayQuality.quality,
            Bitmap.Config.ARGB_8888
        )
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        photoView.setImageBitmap(bitmap)
    }

}