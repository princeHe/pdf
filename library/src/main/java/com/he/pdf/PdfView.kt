package com.he.pdf

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.he.pdf.databinding.LayoutPdfBinding
import java.io.File


class PdfView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {

    init {
        LayoutPdfBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun fromFile(file: File) {
        require(file.exists())
        
    }
}


