package com.example.mykonter.ui.promo


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mykonter.R
import com.example.mykonter.databinding.ListItemBinding
import com.example.mykonter.model.Promo
import com.example.mykonter.network.PromoApi

class PromoAdapter : RecyclerView.Adapter<PromoAdapter.ViewHolder>() {

    private val data = mutableListOf<Promo>()

    fun updateData(newData: List<Promo>) {
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
        fun bind(promo: Promo) = with(binding) {
            namaTextView.text = promo.nama
            hargaTextView.text = promo.harga
            Glide.with(imageView.context)
                .load(PromoApi.getPromoUrl(promo.imageId))
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)
            root.setOnClickListener {
                val pesan = root.context.getString(R.string.pesan, promo.nama)
                Toast.makeText(root.context, pesan, Toast.LENGTH_LONG).show()
            }
        }
    }
}