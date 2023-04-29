package com.example.cardealershipkt

import android.app.AlertDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.cardealershipkt.databinding.ActivityMapBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider


class MapActivity: AppCompatActivity() {

    lateinit var mapView: MapView

    private val CENTER_LOCATION = Point(55.753210, 37.619055)
    private val LOCATIONS = mapOf(
        "TOP_LOCATION" to Point(55.838617, 37.666602),
        "BOTTOM_LOCATION" to Point(55.597541, 37.745295),
        "START_LOCATION" to Point(55.786394, 37.508715),
        "END_LOCATION" to Point(55.729321, 37.744720)
    )

    private val topTapListener: MapObjectTapListener = MapObjectTapListener { _, _ ->
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getText(R.string.map_top_title))
            .setMessage(getText(R.string.map_dealership_description))
            .setView(getSpanPhone("\t\t+7 (495) 777-77-11"))
            .setIcon(R.drawable.ic_car)
            .setPositiveButton(R.string.map_understand) {
                    dialog, id ->  dialog.cancel()
            }
        builder.create().show()
        return@MapObjectTapListener true
    }
    private val bottomTapListener: MapObjectTapListener = MapObjectTapListener { _, _ ->

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getText(R.string.map_bottom_title))
            .setMessage(getText(R.string.map_dealership_description))
            .setView(getSpanPhone("\t\t+7 (495) 777-77-22"))
            .setIcon(R.drawable.ic_car)
            .setPositiveButton(getText(R.string.map_understand)) {
                    dialog, id ->  dialog.cancel()
            }
        builder.create().show()
        return@MapObjectTapListener true
    }
    private val startTapListener: MapObjectTapListener = MapObjectTapListener { _, _ ->
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getText(R.string.map_start_title))
            .setMessage(getText(R.string.map_dealership_description))
            .setView(getSpanPhone("\t\t+7 (495) 777-77-33"))
            .setIcon(R.drawable.ic_car)
            .setPositiveButton(R.string.map_understand) {
                    dialog, id ->  dialog.cancel()
            }
        builder.create().show()
        return@MapObjectTapListener true
    }
    private val endTapListener: MapObjectTapListener = MapObjectTapListener { _, _ ->
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getText(R.string.map_end_title))
            .setMessage(getText(R.string.map_dealership_description))
            .setView(getSpanPhone("\t\t+7 (495) 777-77-44"))
            .setIcon(R.drawable.ic_car)
            .setPositiveButton(R.string.map_understand) {
                    dialog, id ->  dialog.cancel()
            }
        builder.create().show()
        return@MapObjectTapListener true
    }

    private fun getSpanPhone(str: String): TextView {
        val message = TextView(this)
        val s = SpannableString(str)
        Linkify.addLinks(s, Linkify.PHONE_NUMBERS)
        message.text = s
        message.movementMethod = LinkMovementMethod.getInstance()
        return message
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_map)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mapView = findViewById(R.id.mapview)
        mapView.map.move(
            CameraPosition(CENTER_LOCATION , 10.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 5f), null)

        var mapObjectCollection = mapView.map.addMapObjectLayer("layer")

        val markTop = mapObjectCollection.addPlacemark(LOCATIONS["TOP_LOCATION"]!!)
        markTop.setIcon(ImageProvider.fromBitmap(getDrawable(R.drawable.ic_location)!!.toBitmap()))
        markTop.addTapListener(topTapListener)
        val markBottom = mapObjectCollection.addPlacemark(LOCATIONS["BOTTOM_LOCATION"]!!)
        markBottom.setIcon(ImageProvider.fromBitmap(getDrawable(R.drawable.ic_location)!!.toBitmap()))
        markBottom.addTapListener(bottomTapListener)
        val markStart = mapObjectCollection.addPlacemark(LOCATIONS["START_LOCATION"]!!)
        markStart.setIcon(ImageProvider.fromBitmap(getDrawable(R.drawable.ic_location)!!.toBitmap()))
        markStart.addTapListener(startTapListener)
        val markEnd = mapObjectCollection.addPlacemark(LOCATIONS["END_LOCATION"]!!)
        markEnd.setIcon(ImageProvider.fromBitmap(getDrawable(R.drawable.ic_location)!!.toBitmap()))
        markEnd.addTapListener(endTapListener)
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }
}