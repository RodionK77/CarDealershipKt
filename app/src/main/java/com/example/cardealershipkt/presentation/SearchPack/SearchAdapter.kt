package com.example.cardealershipkt.presentation.SearchPack

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cardealershipkt.presentation.HomePack.CarInfoActivity
import com.example.cardealershipkt.R
import com.example.cardealershipkt.data.Room.CarItem
import com.squareup.picasso.Picasso
import java.io.Serializable
import java.util.*

class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.CarsViewHolder>(), Serializable {
    var items = listOf<CarItem>()
    var itemsTmp = listOf<CarItem>()
    lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarsViewHolder {
        context = parent.context
        val layoutIdForListCars: Int = R.layout.rv_car_card
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layoutIdForListCars, parent, false)
        return CarsViewHolder(view)
    }

    fun setItemsList(carList: List<CarItem>) {
        this.items = carList
        this.itemsTmp = carList
    }

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val item = items[position]
        holder.item = item
        holder.name.text = item.brand + " " + item.name
        holder.price.text = item.price.toString() + " ₽"
        holder.infoLeft.text = item.engine + "\n" + item.capacity.toString() + " л.\n" + (item.power.toString() + " л. с.")
        holder.infoRight.text = item.drive + "\n" + item.transmission + "\n" + item.body
        Picasso.get().load(item.image).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun filter(charText: String) {
        var charText = charText
        charText = charText.lowercase(Locale.getDefault())
        if (charText.isEmpty()) {
            items = itemsTmp
        } else {
            val filteredList = mutableListOf<CarItem>()
            for (wp in itemsTmp) {
                val str = wp.brand + wp.name
                if (str.lowercase(Locale.getDefault()).contains(charText.filter { !it.isWhitespace() }) ) {
                    filteredList.add(wp)
                }
            }
            items = filteredList
        }
        notifyDataSetChanged()
    }

    inner class CarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Serializable {
        var name: TextView
        var price: TextView
        var infoLeft: TextView
        var infoRight: TextView
        var image: ImageView
        //var mark: ImageView
        lateinit var item: CarItem
        init {
            name = itemView.findViewById<View>(R.id.tv_rv_car_name) as TextView
            price = itemView.findViewById<View>(R.id.rv_card_price) as TextView
            infoLeft = itemView.findViewById<View>(R.id.rv_card_engine_capacity_power) as TextView
            infoRight = itemView.findViewById<View>(R.id.rv_card_drive_transmission_body) as TextView
            image = itemView.findViewById<View>(R.id.iv_rv_card_info) as ImageView
            //mark = itemView.findViewById<View>(R.id.iv_bookmark) as ImageView
            itemView.setOnClickListener {
                val intent = Intent(context, CarInfoActivity::class.java)
                intent.putExtra("EXTRA_MESSAGE", item)
                context.startActivity(intent)
            }
        }
    }

}