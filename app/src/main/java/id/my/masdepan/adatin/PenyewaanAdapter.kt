package id.my.masdepan.adatin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load

class PenyewaanAdapter(private val semuaTransaksi: List<Penyewaan>) :
    RecyclerView.Adapter<PenyewaanAdapter.PenyewaanViewHolder>() {

    class PenyewaanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProductImage = itemView.findViewById<ImageView>(R.id.ivProductImage)
        val tvProductName = itemView.findViewById<TextView>(R.id.tvProductName)
        val tvCity = itemView.findViewById<TextView>(R.id.tvCity)
        val tvSubtotal = itemView.findViewById<TextView>(R.id.tvSubtotal)
        val tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)
        val tvProductSelectedSize = itemView.findViewById<TextView>(R.id.tvProductSelectedSize)
        val tvProductInvoiceID = itemView.findViewById<TextView>(R.id.tvProductInvoiceID)
        val btnReturn: Button = itemView.findViewById(R.id.btnReturn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenyewaanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_penyewaan, parent, false)
        return PenyewaanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PenyewaanViewHolder, position: Int) {
        val transaksi = semuaTransaksi[position]
        val pakaian = daftarPakaian.find { it.id == transaksi.pakaianId }

        if (pakaian == null) {
            Toast.makeText(holder.itemView.context, "Pakaian Tidak Ditemukan", Toast.LENGTH_LONG).show()
            return
        }

        holder.ivProductImage.load("${pakaian.gambar}.jpg") {
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_error)
        }

        val status = transaksi.status

        if (status == StatusSewa.SEDANG_DISEWA) {
            holder.btnReturn.visibility = View.VISIBLE
        } else {
            holder.btnReturn.visibility = View.GONE
        }

        holder.tvProductInvoiceID.text = transaksi.id
        holder.tvProductName.text = pakaian.nama
        holder.tvCity.text = pakaian.daerah
        holder.tvSubtotal.text = "Rp${transaksi.subtotal}"
        holder.tvStatus.text = "${transaksi.status}"
        holder.tvProductSelectedSize.text = "${transaksi.ukuran}"
    }

    override fun getItemCount(): Int {
        return semuaTransaksi.size
    }
}