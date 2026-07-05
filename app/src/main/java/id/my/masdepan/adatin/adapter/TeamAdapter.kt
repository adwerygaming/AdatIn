package id.my.masdepan.adatin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.my.masdepan.adatin.R
import id.my.masdepan.adatin.model.Team

class TeamAdapter(private val teamList: List<Team>) :
    RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.imgPhoto)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtNim: TextView = itemView.findViewById(R.id.txtNim)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teamList[position]
        holder.txtName.text = team.name
        holder.txtNim.text = team.nim
        holder.imgPhoto.setImageResource(team.photo)
    }

    override fun getItemCount(): Int = teamList.size
}
