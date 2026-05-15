package com.example.gokulahealth.ui.cattle

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.gokulahealth.GokulaApp
import com.example.gokulahealth.data.Cattle
import com.example.gokulahealth.databinding.ActivityAddCattleBinding
import com.example.gokulahealth.viewmodel.CattleViewModel
import com.example.gokulahealth.viewmodel.CattleViewModelFactory

class AddCattleActivity : AppCompatActivity() {
    private lateinit var b: ActivityAddCattleBinding
    private lateinit var vm: CattleViewModel
    private var photoUri: Uri? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { photoUri = it; Glide.with(this).load(it).circleCrop().into(b.ivCattlePhoto) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAddCattleBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.title = "Register Cattle"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        vm = ViewModelProvider(this, CattleViewModelFactory((application as GokulaApp).repository))[CattleViewModel::class.java]

        b.ivCattlePhoto.setOnClickListener { pickImage.launch("image/*") }

        b.btnSave.setOnClickListener {
            val earTag = b.etEarTag.text.toString().trim()
            val name = b.etName.text.toString().trim()
            val breed = b.etBreed.text.toString().trim()
            val dob = b.etDob.text.toString().trim()
            if (earTag.isBlank() || name.isBlank() || breed.isBlank() || dob.isBlank()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            vm.insertCattle(Cattle(earTag = earTag, name = name, breed = breed, dateOfBirth = dob,
                photoUri = photoUri?.toString() ?: "", notes = b.etNotes.text.toString().trim()))
            Toast.makeText(this, "✅ $name registered!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
