package id.my.masdepan.adatin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.my.masdepan.adatin.DetailItemActivity
import id.my.masdepan.adatin.model.GlobalVariable
import id.my.masdepan.adatin.R
import id.my.masdepan.adatin.daftarPakaian
import id.my.masdepan.adatin.model.CartItem
import id.my.masdepan.adatin.toRupiahFormat

class MyCartAdapter(private var listCart: List<CartItem>) :
    RecyclerView.Adapter<MyCartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCartProductName = itemView.findViewById<TextView>(R.id.tvCartProductName)
        val tvCartProductSize = itemView.findViewById<TextView>(R.id.tvCartProductSize)
        val tvCartProductDaerah = itemView.findViewById<TextView>(R.id.tvCartProductDaerah)
        val tvCartProductPrice = itemView.findViewById<TextView>(R.id.tvCartProductPrice)
        val tvCartProductQuantity = itemView.findViewById<TextView>(R.id.tvCartProductQuantity)
        val btnDecreaseQuantity = itemView.findViewById<Button>(R.id.btnDecreaseQuantity)
        val btnIncreaseQuantity = itemView.findViewById<Button>(R.id.btnIncreaseQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_keranjang, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = listCart[position]
        val ivPakaian = holder.itemView.findViewById<ImageView>(R.id.ivCartProductImage)
        val pakaian = daftarPakaian.find { it.id == cartItem.pakaian.id }

        val currentUser = GlobalVariable.activeAccount

        if (pakaian == null) {
            return
        }

        ivPakaian.load(pakaian.gambar) {
            placeholder(R.drawable.ic_loading)
            error(R.drawable.ic_error)
        }

        val totalPrice = pakaian.harga_sewa_per_hari

        holder.tvCartProductName.text = pakaian.nama
        holder.tvCartProductDaerah.text = pakaian.daerah
        holder.tvCartProductPrice.text = "Rp${totalPrice.toRupiahFormat()} / hari"
        holder.tvCartProductQuantity.text = "${cartItem.quantity}"
        holder.tvCartProductSize.text = "${cartItem.size}"

        holder.btnDecreaseQuantity.setOnClickListener {
            if (cartItem.quantity > 1) {
                cartItem.quantity = cartItem.quantity - 1
                holder.tvCartProductQuantity.text = "${cartItem.quantity}"
                notifyDataSetChanged()
            } else {
                currentUser?.removeCartItem(cartItem)

                listCart = listCart.filter { it != cartItem }
                updateData(listCart)
            }
        }

        holder.btnIncreaseQuantity.setOnClickListener {
            cartItem.quantity = cartItem.quantity + 1
            holder.tvCartProductQuantity.text = "${cartItem.quantity}"
            notifyDataSetChanged()
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailItemActivity::class.java)
            intent.putExtra("productId", pakaian.id)
            intent.putExtra("productSelectedSize", cartItem.size.toString())
            intent.putExtra("quantity", cartItem.quantity)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listCart.size
    }

    fun updateData(newList: List<CartItem>) {
        this.listCart = newList
        notifyDataSetChanged()
    }

    fun getData(): List<CartItem> {
        return listCart
    }
}
