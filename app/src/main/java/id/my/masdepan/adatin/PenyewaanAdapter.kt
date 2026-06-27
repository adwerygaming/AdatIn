package id.my.masdepan.adatin

import androidx.recyclerview.widget.RecyclerView


class PenyewaanAdapter(private val semuaTransaksi: List<Penyewaan>) :
    RecyclerView.Adapter<PakaianAdapter.PakaianViewHolder>() {

    class PakaianViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNamaPakaian)
        val tvDaerah: TextView = itemView.findViewById(R.id.tvDaerah)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PakaianViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pakaian, parent, false)
        return PakaianViewHolder(view)
    }

    override fun onBindViewHolder(holder: PakaianViewHolder, position: Int) {
        val pakaian = listPakaian[position]

        val ivPakaian = holder.itemView.findViewById<ImageView>(R.id.ivKatalog)

        ivPakaian.load("${pakaian.gambar}.jpg") {
            println(pakaian.nama)
            println(pakaian.gambar)

            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_error)
        }

        holder.tvNama.text = pakaian.nama
        holder.tvDaerah.text = pakaian.daerah
        holder.tvHarga.text = "Rp${pakaian.harga_per_hari} / hari"

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
}