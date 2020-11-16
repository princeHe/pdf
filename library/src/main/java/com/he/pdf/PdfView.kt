package com.he.pdf

import android.annotation.SuppressLint
import android.content.Context
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
import kotlin.jvm.Throws

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
        val render = PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY))
        binding.tvPosition.text = "1/${render.pageCount}"
        binding.viewPager.adapter = PdfPagerAdapter(render, displayQuality)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvPosition.text = "${position + 1}/${render.pageCount}"
            }
        })
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        (binding.viewPager.adapter as? PdfPagerAdapter)?.let {
            it.currentPage?.close()
            it.renderer.close()
        }
    }

}