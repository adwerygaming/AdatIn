package id.my.masdepan.adatin

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load

class PenyewaanAdapter(private val semuaTransaksi: List<Penyewaan>) :
    RecyclerView.Adapter<PenyewaanAdapter.PenyewaanViewHolder>() {

    class PenyewaanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProductImage: ImageView = itemView.findViewById(R.id.ivProductImage)
        val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvCity: TextView = itemView.findViewById(R.id.tvCity)
        val tvSubtotal: TextView = itemView.findViewById(R.id.tvSubtotal)
        val tvDeliveryType: TextView = itemView.findViewById(R.id.tvDeliveryType)
        val tvProductSelectedSize: TextView = itemView.findViewById(R.id.tvProductSelectedSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenyewaanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_penyewaan, parent, false)
        return PenyewaanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PenyewaanViewHolder, position: Int) {
        val transaksi = semuaTransaksi[position]
        println(transaksi)
        val pakaian = daftarPakaian.find { it.id == transaksi.pakaianId }

        if (pakaian == null) {
            Toast.makeText(holder.itemView.context, "Pakaian Tidak Ditemukan", Toast.LENGTH_LONG).show()
            return
        }

        holder.ivProductImage.load("${pakaian.gambar}.jpg") {
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_error)
        }

        holder.tvProductName.text = pakaian.nama
        holder.tvCity.text = pakaian.daerah
        holder.tvSubtotal.text = "Rp${transaksi.subtotal}"
        holder.tvDeliveryType.text = "${transaksi.tipe_pengambilan}"
        holder.tvProductSelectedSize.text = "${transaksi.ukuran}"
    }

    override fun getItemCount(): Int {
        return semuaTransaksi.size
    }
}