package com.example.baker_street.activities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baker_street.R
import com.example.baker_street.databinding.ActivityPdfUploadBinding

class Pdf_upload : AppCompatActivity() {
    private val PICK_PDF_REQUEST_CODE = 1

    private lateinit var binding: ActivityPdfUploadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_upload)

        binding = ActivityPdfUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
