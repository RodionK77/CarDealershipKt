package com.example.cardealershipkt

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.io.Serializable

class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.CarsViewHolder>(), Serializable {
    var items = listOf<CarItem>()
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

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) {
        val item = items[position]
        holder.item = item
        holder.name.text = item.brand + " " + item.name
        holder.price.text = String.format("%,.0f", item.price?.let { Integer.valueOf(it) }) + " ₽"
        holder.infoLeft.text = item.engine + "\n" + java.lang.String.valueOf(item.capacity) + " л.\n" + java.lang.String.valueOf(item.power) + " л. с."
        holder.infoRight.text = item.drive + "\n" + item.transmission + "\n" + item.body
        Picasso.get().load(item.image).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
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
                val intent = Intent(context, CarInfo::class.java)
                intent.putExtra("EXTRA_MESSAGE", item)
                context.startActivity(intent)
            }
        }
    }

}