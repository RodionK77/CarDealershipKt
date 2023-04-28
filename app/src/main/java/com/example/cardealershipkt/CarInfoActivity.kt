package com.example.cardealershipkt

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.cardealershipkt.databinding.ActivityCarinfoBinding
import com.example.cardealershipkt.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.io.Serializable

class CarInfoActivity : AppCompatActivity(), Serializable {
    var str1 =
        "Марка\nСтрана марки\nМодель\nЦвет\nКласс автомобиля\nКол-во дверей\nКол-во мест\nРасположение руля"
    var str2 = "Тип двигателя\nОбъём\nМощность\nКоробка передач"
    var str3 = "Ускорение\nПотребление"
    var str4 = "Длина\nШирина\nВысота\nОбъём багажника\nОбъём топливного бака"

    lateinit var item: CarItem
    private lateinit var mDataBase: DatabaseReference
    var currentUser: FirebaseUser? = null
    private lateinit var user: User
    var bookstores: String? = null
    private var book_set = false
    lateinit var binding: ActivityCarinfoBinding

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        item = intent.getSerializableExtra("EXTRA_MESSAGE") as CarItem
        currentUser = viewModel.getUser()
        if (currentUser != null) {
            mDataBase = viewModel.getFirebaseDatabase("Users").child(currentUser!!.uid)
            mDataBase!!.get().addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(applicationContext, "Ошибка доступа", Toast.LENGTH_SHORT).show()
                } else {
                    user = task.result.getValue(User::class.java)!!
                    bookstores = user!!.bookstores
                    if (bookstores!!.contains("${item.id}-")) {
                        binding.ivBookmarkInfo.setColorFilter(ContextCompat.getColor(applicationContext, R.color.mark_true))
                        //bookmark.setBackgroundResource(R.drawable.ic_bookmark_true);
                        book_set = true
                    }
                }
            }
        }
        Picasso.get().load(item.image).into(binding.ivRvCardInfo)
        binding.rvCardPriceInfo.text = "${item.price} ₽"
        binding.tvRvCarName.text = item.brand.toString() + " " + item.name
        binding.infoFieldsMainData.text = (item.brand.toString() + "\n" + item.country + "\n" + item.name + "\n" + item.color + "\n" + item.car_class + "\n" + item.doors.toString() + "\n" + item.places.toString() + "\n" + item.wheel)
        binding.infoFieldsEngineData.text = item.engine.toString() + "\n" + item.capacity.toString() + " л.\n" + item.power.toString() + " л. с.\n" + item.transmission
        binding.infoFieldsExploitationData.text = item.acceleration.toString() + " с.\n" + item.consumption + " л."
        binding.infoFieldsSizeData.text = item.length.toString() + " мм.\n" + item.width.toString() + " мм.\n" + item.height.toString() + " мм.\n" + item.trunk + " л.\n" + item.fuel_tank + " л."
        binding.ivBookmarkInfo.setOnClickListener(View.OnClickListener {
            if (currentUser != null) {
                if (book_set) {
                    user!!.delBookstores(item.id!!)
                    //bookmark.setBackgroundResource(R.drawable.ic_bookmark_false);
                    binding.ivBookmarkInfo.setColorFilter(ContextCompat.getColor(applicationContext, R.color.mark_false))
                    Toast.makeText(applicationContext, "Закладка удалена", Toast.LENGTH_SHORT).show()
                    book_set = false
                    mDataBase!!.setValue(user)
                } else if (!book_set) {
                    user!!.addBookstores(item.id!!)
                    binding.ivBookmarkInfo.setColorFilter(ContextCompat.getColor(applicationContext, R.color.mark_true))
                    //bookmark.setBackgroundResource(R.drawable.ic_bookmark_true);
                    book_set = true
                    mDataBase!!.setValue(user)
                    Toast.makeText(applicationContext, "Закладка добавлена", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Для добавления закладок - зарегестрируйтесь",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}