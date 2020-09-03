package com.davevarga.photogallery

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build.*
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001
    private var imageList = ArrayList<Uri>()
    private lateinit var lm: GridLayoutManager
    private lateinit var myRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRecyclerView = recycler_view
        lm = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

        img_pick_btn.setOnClickListener {
            if (VERSION.SDK_INT >= VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else{
                    imageList.clear()
                    pickImageFromGallery()
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val count = data!!.clipData!!.itemCount
            for (i in 0 until count) {
                imageList.add(data.clipData?.getItemAt(i)!!.uri)
            }
            myRecyclerView.layoutManager = lm
            myRecyclerView.adapter = ImageRecyclerAdapter(this, imageList)
            myRecyclerView.setHasFixedSize(true)

        }
    }

}