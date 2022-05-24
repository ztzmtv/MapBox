package com.example.infograce

import android.graphics.Color.red
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.example.infograce.databinding.MainActivityBinding
import com.example.infograce.helper.LocationPermissionHelper
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.sources.generated.vectorSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var mapView: MapView
    private lateinit var locationPermissionHelper: LocationPermissionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupDrawerLayout()
        mapView = binding.mapView
        enableLocation()

        mapView.getMapboxMap().loadStyle(
            style
                (styleUri = Style.SATELLITE) {
                +vectorSource("1e6rib3l") {
                    url("mapbox://azmetov.1e6rib3l")
                }
                +fillLayer("METEO","1e6rib3l"){
                    sourceLayer("METEO")
                    fillColor(ContextCompat.getColor(this@MainActivity, R.color.dark_blue))
                    fillOpacity(0.5)
                    fillOutlineColor(ContextCompat.getColor(this@MainActivity, R.color.purple_200))

                }
            }
        )
    }

    private fun enableLocation() {
        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
    }

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()
            setupGesturesListener()
        }
    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    this@MainActivity,
                    R.drawable.navigation,
                ),
                shadowImage = AppCompatResources.getDrawable(
                    this@MainActivity,
                    R.drawable.note,
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        locationComponentPlugin.addOnIndicatorBearingChangedListener(
            onIndicatorBearingChangedListener
        )
    }

    private fun onCameraTrackingDismissed() {
        Toast.makeText(this, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        with(mapView) {
            location.apply {
                removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
                removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
            }
            gestures.removeOnMoveListener(onMoveListener)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setupDrawerLayout() {
        val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.nav_open, R.string.nav_close)
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}

