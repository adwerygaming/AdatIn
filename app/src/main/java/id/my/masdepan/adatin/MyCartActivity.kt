package id.my.masdepan.adatin

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.my.masdepan.adatin.adapter.MyCartAdapter
import id.my.masdepan.adatin.model.GlobalVariable

class MyCartActivity : AppCompatActivity() {
    private lateinit var adapter: MyCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_cart)

        val activeAccount = GlobalVariable.activeAccount
        if (activeAccount == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_cart
        bottomNav.setupBottomNav(this)

        val rvCarts = findViewById<RecyclerView>(R.id.rvCarts)
        rvCarts.layoutManager = LinearLayoutManager(this)

        val allMyCarts = activeAccount.getMyCart()

        updateView()

        adapter = MyCartAdapter(allMyCarts)
        rvCarts.adapter = adapter

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private val background = ColorDrawable(ContextCompat.getColor(this@MyCartActivity, R.color.state_error))
            private val deleteIcon = ContextCompat.getDrawable(this@MyCartActivity, R.drawable.ic_delete)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val cartData = adapter.getData()
                val cartItem = cartData[position]

                activeAccount.removeCartItem(cartItem)

                val updatedList = activeAccount.getMyCart()
                adapter.updateData(updatedList)
                updateView()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top

                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(c)

                if (deleteIcon != null) {
                    val intrinsicWidth = deleteIcon.intrinsicWidth
                    val intrinsicHeight = deleteIcon.intrinsicHeight

                    val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                    val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
                    val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
                    val deleteIconRight = itemView.right - deleteIconMargin
                    val deleteIconBottom = deleteIconTop + intrinsicHeight

                    deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
                    deleteIcon.draw(c)
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvCarts)
    }

    override fun onResume() {
        super.onResume()

        val activeAccount = GlobalVariable.activeAccount
        if (activeAccount == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            return
        }

        val allMyCarts = activeAccount.getMyCart()
        adapter.updateData(allMyCarts)

        updateView()
    }

    fun updateView() {
        val MyCartScrollViewGroup = findViewById<LinearLayout>(R.id.MyCartScrollViewGroup)
        val MyCartEmptyDetailGroup = findViewById<LinearLayout>(R.id.MyCartEmptyDetailGroup)

        val activeAccount = GlobalVariable.activeAccount
        val allMyCarts = activeAccount?.getMyCart() ?: emptyList()

        if (allMyCarts.isEmpty()) {
            MyCartScrollViewGroup.visibility = View.GONE
            MyCartEmptyDetailGroup.visibility = View.VISIBLE
        } else {
            MyCartScrollViewGroup.visibility = View.VISIBLE
            MyCartEmptyDetailGroup.visibility = View.GONE
        }
    }
}