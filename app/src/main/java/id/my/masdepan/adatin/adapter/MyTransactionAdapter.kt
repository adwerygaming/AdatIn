package id.my.masdepan.adatin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.my.masdepan.adatin.ConfirmCancelActivity
import id.my.masdepan.adatin.ConfirmPickupActivity
import id.my.masdepan.adatin.ConfirmReturnActivity
import id.my.masdepan.adatin.R
import id.my.masdepan.adatin.StatusSewa
import id.my.masdepan.adatin.TipePengambilan
import id.my.masdepan.adatin.TransactionItem
import id.my.masdepan.adatin.daftarPakaian
import id.my.masdepan.adatin.toRupiahFormat

class MyTransactionAdapter(private var semuaTransaksi: List<TransactionItem>) :
    RecyclerView.Adapter<MyTransactionAdapter.PenyewaanViewHolder>() {

    class PenyewaanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTransactionProductInvoiceID = itemView.findViewById<TextView>(R.id.tvTransactionProductInvoiceID)
        val tvTransactionProductStatus = itemView.findViewById<TextView>(R.id.tvTransactionProductStatus)
        val ivTransactionProductImage = itemView.findViewById<ImageView>(R.id.ivTransactionProductImage)
        val tvTransactionProductName = itemView.findViewById<TextView>(R.id.tvTransactionProductName)
        val tvTransactionProductSize = itemView.findViewById<TextView>(R.id.tvTransactionProductSize)
        val tvTransactionProductRentalDetails = itemView.findViewById<TextView>(R.id.tvTransactionProductRentalDetails)
        val tvTransactionProductSubtotal = itemView.findViewById<TextView>(R.id.tvTransactionProductSubtotal)

        val btnTransactionProductCancel = itemView.findViewById<Button>(R.id.btnTransactionProductCancel)
        val btnTransactionProductPickup = itemView.findViewById<Button>(R.id.btnTransactionProductPickup)
        val btnTransactionProductReturn = itemView.findViewById<Button>(R.id.btnTransactionProductReturn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenyewaanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaksi, parent, false)

        this.semuaTransaksi = semuaTransaksi
        return PenyewaanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PenyewaanViewHolder, position: Int) {
        val transaksi = this.semuaTransaksi[position]
        val pakaian = daftarPakaian.find { it.id == transaksi.pakaianId }

        if (pakaian == null) {
            Toast.makeText(holder.itemView.context, "Pakaian Tidak Ditemukan", Toast.LENGTH_LONG).show()
            return
        }

        holder.ivTransactionProductImage.load("${pakaian.gambar}.jpg") {
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_error)
        }

        val status = transaksi.status

        if (status == StatusSewa.SEDANG_DISEWA) {
            holder.btnTransactionProductReturn.visibility = View.VISIBLE
        } else {
            holder.btnTransactionProductReturn.visibility = View.GONE
        }

        if (status == StatusSewa.SEDANG_DIPROSES) {
            holder.btnTransactionProductCancel.visibility = View.VISIBLE
        } else {
            holder.btnTransactionProductCancel.visibility = View.GONE
        }

        if (status == StatusSewa.SIAP_DIAMBIL || status == StatusSewa.SAMPAI_TUJUAN) {
            holder.btnTransactionProductPickup.visibility = View.VISIBLE
        } else {
            holder.btnTransactionProductPickup.visibility = View.GONE
        }

        holder.btnTransactionProductPickup.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ConfirmPickupActivity::class.java)
            intent.putExtra("transactionId", transaksi.id)
            context.startActivity(intent)
        }

        holder.btnTransactionProductReturn.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ConfirmReturnActivity::class.java)
            intent.putExtra("transactionId", transaksi.id)
            context.startActivity(intent)
        }

        holder.btnTransactionProductCancel.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ConfirmCancelActivity::class.java)
            intent.putExtra("transactionId", transaksi.id)
            context.startActivity(intent)
        }

        val formattedStatus = when (transaksi.status) {
            StatusSewa.MENUNGGU_PEMBAYARAN -> "Menunggu Pembayaran"
            StatusSewa.SEDANG_DIPROSES -> "Sedang Diproses"
            StatusSewa.SIAP_DIAMBIL -> "Siap Diambil"
            StatusSewa.SEDANG_DIANTAR -> "Sedang Diantar"
            StatusSewa.SAMPAI_TUJUAN -> "Telah Sampai"
            StatusSewa.SEDANG_DISEWA -> "Sedang Disewa"
            StatusSewa.MENUNGGU_KONFIRMASI_PEMBATALAN -> "Menunggu Batal"
            StatusSewa.SELESAI -> "Selesai"
            StatusSewa.DIBATALKAN -> "Dibatalkan"
        }

        val diffMs = transaksi.tanggal_selesai_sewa_ms - transaksi.tanggal_mulai_sewa_ms
        var totalDays = (diffMs / (1000 * 60 * 60 * 24)).toInt()
        totalDays = totalDays + 1

        val deliveryString = if (transaksi.tipe_pengambilan == TipePengambilan.DELIVERY) {
            "Delivery"
        } else {
            "Ambil di Tempat"
        }

        holder.tvTransactionProductStatus.text = formattedStatus

        holder.tvTransactionProductInvoiceID.text = transaksi.id
        holder.tvTransactionProductName.text = pakaian.nama
        holder.tvTransactionProductSize.text = "${transaksi.ukuran}"

        holder.tvTransactionProductSubtotal.text = "Rp${transaksi.subtotal.toRupiahFormat()}"

        holder.tvTransactionProductRentalDetails.text = "${pakaian.daerah} • $deliveryString • $totalDays Hari"
    }

    override fun getItemCount(): Int {
        return this.semuaTransaksi.size
    }

    fun updateData(newList: List<TransactionItem>) {
        this.semuaTransaksi = newList
        notifyDataSetChanged()
    }
}