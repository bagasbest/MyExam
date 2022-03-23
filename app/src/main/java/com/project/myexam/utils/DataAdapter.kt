package com.project.myexam.utils

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.myexam.R
import com.project.myexam.homepage.HomepageDetailActivity

class DataAdapter(private val dataList: ArrayList<DataModel>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    inner class ViewHolder(layoutView: View) : RecyclerView.ViewHolder(layoutView) {

        private val name: TextView = itemView.findViewById(R.id.name)
        private val schedule: TextView = itemView.findViewById(R.id.schedule)
        private val image: ImageView = itemView.findViewById(R.id.image)
        private val cv: CardView = itemView.findViewById(R.id.cv)

        @SuppressLint("SetTextI18n")
        fun bind(model: DataModel?) {

            Glide.with(itemView.context)
                .load(model?.image)
                .into(image)

            name.text = model?.name
            schedule.text = "Examination Schedule:\n${model?.schedule}"

            cv.setOnClickListener {
                val intent = Intent(itemView.context, HomepageDetailActivity::class.java)
                intent.putExtra(HomepageDetailActivity.EXTRA_DATA, model)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_data, parent, false
        )
        return ViewHolder(layoutView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}