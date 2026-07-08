package id.my.masdepan.adatin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.my.masdepan.adatin.DetailItemActivity
import id.my.masdepan.adatin.R
import id.my.masdepan.adatin.model.Pakaian
import id.my.masdepan.adatin.toRupiahFormat

class PakaianAdapter(private var listPakaian: List<Pakaian>) :
    RecyclerView.Adapter<PakaianAdapter.PakaianViewHolder>() {

    class PakaianViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProductImage: ImageView = itemView.findViewById(R.id.ivProductImage)
        val tvNama: TextView = itemView.findViewById(R.id.tvNamaPakaian)
        val tvDaerah: TextView = itemView.findViewById(R.id.tvDaerah)
        val tvItemPakaianRating: TextView = itemView.findViewById(R.id.tvItemPakaianRating)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PakaianViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pakaian, parent, false)
        return PakaianViewHolder(view)
    }

    override fun onBindViewHolder(holder: PakaianViewHolder, position: Int) {
        val pakaian = listPakaian[position]

        holder.ivProductImage.load(pakaian.gambar) {
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_error)
        }

        holder.tvNama.text = pakaian.nama
        holder.tvDaerah.text = pakaian.daerah
        holder.tvItemPakaianRating.text = pakaian.rating.toString()
        holder.tvHarga.text = "Rp${pakaian.harga_per_hari.toRupiahFormat()} / hari"

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailItemActivity::class.java)
            intent.putExtra("productId", pakaian.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listPakaian.size
    }

    fun updateData(newList: List<Pakaian>) {
        this.listPakaian = newList
        notifyDataSetChanged()
    }
}