package com.example.laboratorio7

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_agregar_contacto.*

class AgregarContacto : AppCompatActivity() {

    var path : Uri = Uri.parse("")
    companion object {}


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_contacto)
        prioridad.minValue = 1
        prioridad.maxValue = 10
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = "Agregar Contacto"
    }

    fun onClick(view: View){
        cargarImagen()
    }

    private fun cargarImagen() {
        var intent : Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/"
        startActivityForResult(Intent.createChooser(intent, "Seleccione su foto"),10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            if (data != null) {
                path  = data.data
                foto.setImageURI(path)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_contact -> {
                saveC()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveC(){
        if (name.text.toString().trim().isBlank() || email.text.toString().trim().isBlank()
            || telefono.text.toString().trim().isBlank() || path.toString().trim().isBlank()){

            Toast.makeText(this, "No puedes dejar campos en blanco", Toast.LENGTH_SHORT).show()
            return
        }
        val data = Intent().apply {
            putExtra(MainActivity.EXTRA_NAME, name.text.toString())
            putExtra(MainActivity.EXTRA_NUMERO, telefono.text.toString())
            putExtra(MainActivity.EXTRA_EMAIL, email.text.toString())
            putExtra(MainActivity.EXTRA_PRIORITY, prioridad.value)
            putExtra(MainActivity.EXTRA_FOTO, path.toString())
            if (intent.getIntExtra(MainActivity.EXTRA_ID, -1) != -1) {
                putExtra(MainActivity.EXTRA_ID, intent.getIntExtra(MainActivity.EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}