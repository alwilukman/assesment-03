package com.example.mykonter.ui.Phone


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.bumptech.glide.Glide
import com.example.mykonter.R
import com.example.mykonter.databinding.ListItemBinding
import com.example.mykonter.model.Phone
import com.example.mykonter.network.PhoneApi

class PhoneAdapter : Adapter<PhoneAdapter.ViewHolder>() {

    private val data = mutableListOf<Phone>()

    fun updateData(newData: List<Phone>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
    override fun getItemCount(): Int {
        return data.size
    }
    class ViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(phone: Phone) = with(binding) {
            namaTextView.text = phone.nama
            hargaTextView.text = phone.harga
            Glide.with(imageView.context)
                .load(PhoneApi.getPhoneUrl(phone.imageId))
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)
            root.setOnClickListener {
                val message = root.context.getString(R.string.message, phone.nama)
                Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}