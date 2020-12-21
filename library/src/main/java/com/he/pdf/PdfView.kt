package com.he.pdf

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.he.pdf.databinding.LayoutPdfBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class PdfView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {

    private val binding: LayoutPdfBinding = LayoutPdfBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    @SuppressLint("SetTextI18n")
    @Throws(IOException::class, FileNotFoundException::class)
    fun fromFile(file: File, displayQuality: DisplayQuality) {
        require(file.exists())
        val bitmaps = pdfLoaderExecutor.submit(Callable<List<Bitmap>> {
            val render = PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY))
            val bitmaps = (0 until render.pageCount).map {
                val page = render.openPage(it)
                val bitmap = Bitmap.createBitmap(
                    page.width * displayQuality.quality, page.height * displayQuality.quality,
                    Bitmap.Config.ARGB_8888
                )
                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                page.close()
                bitmap
            }
            render.close()
            println(System.currentTimeMillis().toString() + " 1 " + Thread.currentThread().name)
            bitmaps
        }).get()
        binding.tvPosition.text = "1/${bitmaps.size}"
        binding.viewPager.adapter = PdfPagerAdapter(bitmaps)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvPosition.text = "${position + 1}/${bitmaps.size}"
            }
        })
    }

    companion object {
        private val pdfLoaderExecutor = Executors.newSingleThreadExecutor()
    }

}