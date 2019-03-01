package com.example.laboratorio7

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.laboratorio7.AddEditContactActivity.Companion.EXTRA_ID
import kotlinx.android.synthetic.main.activity_add_edit_contact.*


class AddEditContactActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "com.example.laboratorio7.EXTRA_ID"
        const val EXTRA_NAME = "com.example.laboratorio7.EXTRA_NAME"
        const val EXTRA_EMAIL = "com.example.laboratorio7.EXTRA_EMAIL"
        const val EXTRA_NUMERO = "com.example.laboratorio7.EXTRA_NUMERO"
        const val EXTRA_PRIORITY = "com.example.laboratorio7.EXTRA_PRIORITY"
        const val EXTRA_FOTO = "com.example.laboratorio7.EXTRA_FOTO"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_contact)

        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Editar Contacto"
            nombre.setText(intent.getStringExtra(EXTRA_NAME))
            email.setText(intent.getStringExtra(EXTRA_EMAIL))
            telefono.setText(intent.getIntExtra(EXTRA_NUMERO, 1))
        } else {
            title = "Agregar Contacto"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
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
        fun saveNote() {
            if (nombre.text.toString().trim().isBlank() || email.text.toString().trim().isBlank()) {
                Toast.makeText(this, "No puedes dejar campos en blanco", Toast.LENGTH_SHORT).show()
                return
            }
            val data = Intent().apply {
                putExtra(EXTRA_NAME, nombre.text.toString())
                putExtra(EXTRA_EMAIL, email.text.toString())
                putExtra(EXTRA_NUMERO, telefono.toString())

                if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                    putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
                }
            }

            setResult(Activity.RESULT_OK, data)
            finish()
        }

    }
}