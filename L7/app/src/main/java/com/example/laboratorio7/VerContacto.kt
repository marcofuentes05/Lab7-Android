package com.example.laboratorio7

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_ver_contacto.*

class VerContacto : AppCompatActivity() {

    companion object {

        const val EDIT_CONTACT_REQUEST = 3

        lateinit var  contactViewModel: ContactoViewModel
        private var currentId:Int= -1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_contacto)

        contactViewModel = ViewModelProviders.of(this).get(ContactoViewModel::class.java)

        nombre.text = intent.getStringExtra(MainActivity.EXTRA_NAME)

        email.text = intent.getStringExtra(MainActivity.EXTRA_EMAIL)

        numero.text = intent.getStringExtra(MainActivity.EXTRA_NUMERO)

        prioridad.text = intent.getIntExtra(MainActivity.EXTRA_PRIORITY,1).toString()

        var uriFoto = intent.getStringExtra(MainActivity.EXTRA_FOTO).toString()

        foto.setImageURI(Uri.parse(uriFoto))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_contact_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.edit_contact -> {
                editC()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun editC() {
        var intent = Intent(baseContext, AddEditContactActivity::class.java)

        intent.putExtra(MainActivity.EXTRA_NAME, nombre.text)
        intent.putExtra(MainActivity.EXTRA_PRIORITY, Integer.parseInt(prioridad.text.toString()))
        intent.putExtra(MainActivity.EXTRA_NUMERO, numero.text)
        intent.putExtra(MainActivity.EXTRA_EMAIL, email.text)

        startActivity(intent)
    }
}