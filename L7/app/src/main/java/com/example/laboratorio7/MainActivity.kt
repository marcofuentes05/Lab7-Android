package com.example.laboratorio7

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private lateinit var recycler_view: RecyclerView

    companion object {
        const val EXTRA_ID = "com.example.laboratorio7.EXTRA_ID"
        const val EXTRA_NAME = "com.example.laboratorio7.EXTRA_NAME"
        const val EXTRA_EMAIL = "com.example.laboratorio7.EXTRA_EMAIL"
        const val EXTRA_NUMERO = "com.example.laboratorio7.EXTRA_NUMERO"
        const val EXTRA_PRIORITY = "com.example.laboratorio7.EXTRA_PRIORITY"
        const val EXTRA_FOTO = "com.example.laboratorio7.EXTRA_FOTO"

        const val ADD_CONTACT_REQUEST = 1
        const val EDIT_CONTACT_REQUEST = 2
    }

    private lateinit var contactViewModel: ContactoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        agregarContacto.setOnClickListener {
            startActivityForResult(
                Intent(this, AgregarContacto::class.java),
                ADD_CONTACT_REQUEST
            )
        }
        recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        var adapter = ContactoAdapter()

        recycler_view.adapter = adapter

        contactViewModel = ViewModelProviders.of(this).get(ContactoViewModel::class.java)


        contactViewModel.getAllC().observe(this, Observer <List<Contacto>> {adapter.submitList(it)})

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                contactViewModel.delete(adapter.getContactoAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Contacdo eliminado!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : ContactoAdapter.OnItemClickListener {
            override fun onItemClick(c: Contacto) {
                var intent = Intent(baseContext, VerContacto::class.java)
                intent.putExtra(EXTRA_ID, c.id)
                intent.putExtra(EXTRA_NAME, c.nombre)
                intent.putExtra(EXTRA_NUMERO, c.telefono)
                intent.putExtra(EXTRA_PRIORITY, c.prioridad)
                intent.putExtra(EXTRA_EMAIL, c.email)
                intent.putExtra(EXTRA_FOTO, c.foto)

                startActivityForResult(intent, EDIT_CONTACT_REQUEST)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_contacts -> {
                contactViewModel.deleteAll()
                Toast.makeText(this, "Todos los contactos han sido eliminados", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val newC = Contacto(
                data!!.getStringExtra(EXTRA_NAME),
                data.getStringExtra(EXTRA_NUMERO),
                data.getStringExtra(EXTRA_EMAIL),
                data.getIntExtra(EXTRA_PRIORITY, 1),
                data.getStringExtra(EXTRA_FOTO)
            )
            contactViewModel.insert(newC)

            Toast.makeText(this, "Tu contacto se ha guardado :D", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Hubo un error!", Toast.LENGTH_SHORT).show()
            }

            val updateNote = Contacto(
                data!!.getStringExtra(EXTRA_NAME),
                data.getStringExtra(EXTRA_NUMERO),
                data.getStringExtra(EXTRA_EMAIL),
                data.getIntExtra(EXTRA_PRIORITY, 1),
                data.getStringExtra(EXTRA_FOTO)
            )
            updateNote.id = data.getIntExtra(EXTRA_ID, -1)

            contactViewModel.update(updateNote)

        } else {
            Toast.makeText(this, "No se guardo el contacto!", Toast.LENGTH_SHORT).show()
        }


    }
}
