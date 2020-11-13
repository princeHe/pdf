package com.he.pdf.sample

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.he.pdf.DisplayQuality
import com.he.pdf.sample.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.button.setOnClickListener {
            Executors.newSingleThreadExecutor().execute(downloadRunnable)
        }
    }

    private val downloadRunnable = Runnable {
        val bytes = URL("https://ad-wx.oss-cn-shanghai.aliyuncs.com/resources/燃动杠铃-542/I-PUMP 02/文件/I-PUMP的教练员套路手册 Q2  .pdf").openConnection().getInputStream().readBytes()
        val file = File(externalCacheDir, "aaa.pdf")
        FileOutputStream(file).write(bytes)
        binding.pdfView.post {
            binding.pdfView.fromFile(file, DisplayQuality.HIGH)
        }

    }
}