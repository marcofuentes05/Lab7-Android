package com.example.laboratorio7

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_edit_contact.*

import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView

class AddEditContactActivity : AppCompatActivity() {

    lateinit var imagen : ImageView
    lateinit var boton : Button
    var path : Uri = Uri.parse("")
    /**companion object {
        const val EXTRA_ID = "com.example.laboratorio7.EXTRA_ID"
        const val EXTRA_NAME = "com.example.laboratorio7.EXTRA_NAME"
        const val EXTRA_EMAIL = "com.example.laboratorio7.EXTRA_EMAIL"
        const val EXTRA_NUMERO = "com.example.laboratorio7.EXTRA_NUMERO"
        const val EXTRA_PRIORITY = "com.example.laboratorio7.EXTRA_PRIORITY"
        const val EXTRA_FOTO = "com.example.laboratorio7.EXTRA_FOTO"
    }**/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_contact)

        prioridad.minValue = 1
        prioridad.maxValue = 10
        boton = findViewById(R.id.agregarFoto)
        imagen = findViewById(R.id.foto)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        title = "Editar Contacto"

        nombre.setText(intent.getStringExtra(MainActivity.EXTRA_NAME))
        telefono.setText(intent.getStringExtra(MainActivity.EXTRA_NUMERO))
        email.setText(intent.getStringExtra(MainActivity.EXTRA_EMAIL))
        prioridad.value = intent.getIntExtra(MainActivity.EXTRA_PRIORITY, 1)

    }

    //Metodos para cargar la imagen
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
                updateC()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateC(){
            if (nombre.text.toString().trim().isBlank() || email.text.toString().trim().isBlank()) {
                Toast.makeText(this, "No puedes dejar campos en blanco", Toast.LENGTH_SHORT).show()
                return
            }
            val data = Intent().apply {
                putExtra(MainActivity.EXTRA_NAME, nombre.text.toString())
                putExtra(MainActivity.EXTRA_NUMERO, telefono.text)
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