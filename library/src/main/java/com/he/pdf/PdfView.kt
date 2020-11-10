package com.he.pdf

import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
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

    @Throws(IOException::class, FileNotFoundException::class)
    fun fromFile(file: File) {
        require(file.exists())
        val render = PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY))
        binding.viewPager.adapter = PdfPagerAdapter(render)
    }

}


