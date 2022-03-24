package com.project.myexam.homepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.project.myexam.databinding.ActivityHomepageDetailBinding
import com.project.myexam.utils.DataModel

class HomepageDetailActivity : AppCompatActivity() {

    private var binding: ActivityHomepageDetailBinding? = null
    private lateinit var model: DataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        model = intent.getParcelableExtra<DataModel>(EXTRA_DATA) as DataModel

        Glide.with(this)
            .load(model.image)
            .into(binding!!.image)

        binding?.title?.text = model.name
        binding?.textView2?.text = model.address
        binding?.schedule?.text = model.schedule
        binding?.about?.text = model.description


        binding?.backButton?.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val EXTRA_DATA = "data"
    }
}