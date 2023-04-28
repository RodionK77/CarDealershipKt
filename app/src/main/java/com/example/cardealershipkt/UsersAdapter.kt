package com.example.cardealershipkt

import android.content.Context
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class UsersAdapter() : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(), Serializable {
    var context: Context? = null
    var items = listOf<User>()

    var mOnLongItemClickListener: OnLongItemClickListener? = null

    fun setOnLongItemClickListener(onLongItemClickListener: OnLongItemClickListener?) {
        mOnLongItemClickListener = onLongItemClickListener
    }

    interface OnLongItemClickListener {
        fun itemLongClicked(v: View?, position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersViewHolder {
        context = parent.context
        val layoutIdForListCars = R.layout.rv_card_user
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layoutIdForListCars, parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val item = items[position]
        holder.item = item
        holder.name.text = item.name + " " + item.lastname
        holder.mail.text = item.email
        holder.phone.text = item.phone
        holder.role.text = item.role

        holder.itemView.setOnCreateContextMenuListener { menu, _, _ ->
            menu.setHeaderTitle("Изменить роль на:")
            if (item!!.role == "user") {
                menu.add(0, items.indexOf(item), 0, "Администратора")
            }
            if (item!!.role == "admin") {
                menu.add(0, items.indexOf(item), 0, "Пользователя")
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), Serializable {
        var name: TextView
        var mail: TextView
        var phone: TextView
        var role: TextView
        lateinit var item: User
        var cardView: CardView


        /*override fun onCreateContextMenu(
            menu: ContextMenu,
            view: View,
            contextMenuInfo: ContextMenuInfo
        ) {
            menu.setHeaderTitle("Изменить роль на:")
            if (item!!.role == "user") {
                menu.add(0, items.indexOf(item), 0, "Администратора")
            }
            if (item!!.role == "admin") {
                menu.add(0, items.indexOf(item), 0, "Пользователя")
            }
        }*/

        init {
            name = itemView.findViewById<View>(R.id.rv_user_name) as TextView
            mail = itemView.findViewById<View>(R.id.rv_user_mail) as TextView
            phone = itemView.findViewById<View>(R.id.rv_user_phone) as TextView
            role = itemView.findViewById<View>(R.id.rv_user_role) as TextView
            cardView = itemView.findViewById<View>(R.id.cv_rv_card_user) as CardView
            //cardView.setOnCreateContextMenuListener(this)


        }
    }
}