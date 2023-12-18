package com.example.metroyab.presentation.ui.map

import android.Manifest
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.carto.BuildConfig
import com.carto.styles.MarkerStyleBuilder
import com.carto.utils.BitmapUtils
import com.example.metroyab.R
import com.example.metroyab.data.utlis.Resource
import com.example.metroyab.databinding.FragmentMapBinding
import com.example.metroyab.presentation.adapter.NearestMetroAdapter
import com.example.metroyab.presentation.viewmodel.MapViewModel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.OnFailureListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.model.Marker
import java.text.DateFormat
import java.util.Date
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var binding:FragmentMapBinding
    private  val viewModel: MapViewModel by viewModels()

    @Inject
    lateinit var nearestMetroAdapter: NearestMetroAdapter

    private var settingsClient: SettingsClient? = null
    private lateinit var locationSettingsRequest: LocationSettingsRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var userLocation: Location? =null
    var markerOnline: Marker? = null

    private var mRequestingLocationUpdates: Boolean? = null
    private var lastUpdateTime: String? = null

    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 1000

    // fastest updates interval - 1 sec
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = 1000

    val TAG = "MapFRAGMENT"
    private var isStyleLoaded = false

    private var dialogDismisses = false

    val REQUEST_CODE = 123

    var latitude = 0.0
    var longitude = 0.0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_map, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //config and start map
        onMapReady()

        binding.fabGps.setOnClickListener {
            startLocationUpdates()
            if (userLocation != null) {
                binding.map.moveCamera(
                    LatLng(userLocation!!.latitude, userLocation!!.longitude), 0.50f
                )
                binding.map.setZoom(18F, 0.50f)
            }
        }

            binding.btnOriginSelect.setOnClickListener {
                addMarker()
                search("ایستگاه مترو",latitude,longitude)
                getResponse()
            }

        nearestMetroAdapter.setOnItemClick {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=${latitude},${longitude}&daddr=${it.location.y},${it.location.x}")
            )
            startActivity(intent)
        }
    }

    private fun addMarker(){
        // when  clicked on map, a marker is added in clicked location
        latitude = binding.map.cameraTargetPosition.latitude
        longitude = binding.map.cameraTargetPosition.longitude
    }


    private fun setupRecyclerView() {
        binding.recyNearestMetro.apply {
            adapter = nearestMetroAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }


    private fun onMapReady() {
        isStyleLoaded = true
        if (isStyleLoaded) {
            initMap()
        }
    }



    private fun initMap() {
        // Setting map focal position to a fixed position and setting camera zoom
        binding.map.settings.setNeshanLogoMargins(-60, -60)
        binding.map.moveCamera(LatLng(35.767234, 51.330743), 0F)
        binding.map.setZoom(14F, 0F)
    }
    private fun startLocationUpdates() {
        settingsClient?.checkLocationSettings(locationSettingsRequest)
            ?.addOnSuccessListener(
                requireActivity()
            ) { _: LocationSettingsResponse? ->
                Log.i(
                    TAG,
                    "All location settings are satisfied."
                )
                fusedLocationClient?.requestLocationUpdates(
                    locationRequest!!,
                    locationCallback!!,
                    Looper.myLooper()
                )
//                onLocationChange()
            }
            ?.addOnFailureListener(requireActivity(), OnFailureListener { e: Exception ->
                Log.i(TAG, "startLocationUpdates:5 ")
                when ((e as ApiException).statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Log.i(TAG, "startLocationUpdates1: ")

                        try {
                            Log.i(TAG, "startLocationUpdates: wew")
                            // Show the dialog by calling startResolutionForResult(), and check the
                            // result in onActivityResult().
                            val rae = e as ResolvableApiException
                            rae.startResolutionForResult(requireActivity(), REQUEST_CODE)
                        } catch (sie: SendIntentException) {
                            Log.i(TAG, "startLocationUpdates2: ")
                        }
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        val errorMessage =
                            "Location settings are inadequate, and cannot be " +
                                    "fixed here. Fix in Settings."
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
//                onLocationChange()
            })
    }



    private fun addUserMarker(loc: LatLng) {
        //remove existing marker from map
        if (markerOnline != null) {
            binding.map.removeMarker(markerOnline)
        }
        try {
            // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
            // and then call buildStyle method on it. This method returns an object of type MarkerStyle
            val markStCr = MarkerStyleBuilder()
            markStCr.size = 60f
            markStCr.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(
                BitmapFactory.decodeResource(
                    resources, R.drawable.ic_launcher_background
                )
            )
            val markSt = markStCr.buildStyle()
            // Creating user marker
            markerOnline = Marker(loc, markSt)
            // Adding user marker to map!
            binding.map.addMarker(markerOnline)
        } catch (e: IllegalStateException) {
        }
    }


    private fun initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        settingsClient = LocationServices.getSettingsClient(requireActivity())
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                // location is received
                userLocation = locationResult.lastLocation
                lastUpdateTime = DateFormat.getTimeInstance().format(Date())
//                onLocationChange()
            }
        }
        mRequestingLocationUpdates = false
        locationRequest = LocationRequest()
        locationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
        locationSettingsRequest = builder.build()
    }
    override fun onStart() {
        super.onStart()
        initLocation()
        startReceivingLocationUpdates()
    }


    fun startReceivingLocationUpdates() {
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    if (!dialogDismisses) {
                        mRequestingLocationUpdates = true
                        startLocationUpdates()
                        Log.i(
                            TAG,
                            "onPermissionGranted: $response"
                        )
                    }
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if (response.isPermanentlyDenied()) {
                        // open device settings when the permission is
                        // denied permanently
                        Log.i(
                            TAG,
                            "onPermissionDenied: $response"
                        )
                        //open setting with permison location
                        openSettings()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogDismisses = true
    }

    private fun openSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        Log.i(TAG, "openSettings: ")
        startActivity(intent)
    }


    private fun getResponse() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResult.collect{ response->
                when(response){
                    is Resource.Loading -> {
                        binding.progressMap.visibility =View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressMap.visibility =View.GONE
                        response.data.let {
                            binding.btnOriginSelect.visibility =View.GONE
//                            val animation: Animation = TranslateAnimation(0f, 0f, 0f, -620f)
//                            animation.duration = 1000
//                            animation.fillAfter = true
//                            binding.fabGps.startAnimation(animation)
                            setupRecyclerView()
                            nearestMetroAdapter.differ.submitList(it!!.items.subList(0,5))
                        }
                    }
                    is Resource.Error -> {
                        binding.progressMap.visibility =View.GONE
                        response.message.let {
                            Toast.makeText(requireContext(),
                                "مبدا پیدا نشد، لطفا دوباره سعی کنید",
                                Toast.LENGTH_LONG).show()
                        }
                    }

                    else -> {}
                }
            }
        }
    }


    private fun search(term:String,lat:Double,lng:Double) {
            viewModel.searchData(
                term, lat, lng)
    }


}