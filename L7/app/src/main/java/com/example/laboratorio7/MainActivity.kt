package com.example.laboratorio7

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.arch.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }

    private lateinit var noteViewModel: ContactoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        agregarContacto.setOnClickListener {
            startActivityForResult(
                Intent(this, AddEditContactActivity::class.java),
                ADD_NOTE_REQUEST
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        var adapter = ContactoAdapter()

        recycler_view.adapter = adapter

        noteViewModel = ViewModelProviders.of(this).get(ContactoViewModel::class.java)

        noteViewModel.getAllC().observe(this, Observer<List<Contacto>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getContactoAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Note Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : ContactoAdapter.OnItemClickListener {
            override fun onItemClick(c: Contacto) {
                var intent = Intent(baseContext, AddEditContactActivity::class.java)
                intent.putExtra(AddEditContactActivity.EXTRA_ID, c.id)
                intent.putExtra(AddEditContactActivity.EXTRA_NAME, c.nombre)
                intent.putExtra(AddEditContactActivity.EXTRA_NUMERO, c.telefono)
                intent.putExtra(AddEditContactActivity.EXTRA_PRIORITY, c.prioridad)
                intent.putExtra(AddEditContactActivity.EXTRA_EMAIL, c.email)
                intent.putExtra(AddEditContactActivity.EXTRA_FOTO, c.foto)

                startActivityForResult(intent, EDIT_NOTE_REQUEST)
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
                noteViewModel.deleteAll()
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

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val newNote = Contacto(
                data!!.getStringExtra(AddEditContactActivity.EXTRA_NAME),
                data.getIntExtra(AddEditContactActivity.EXTRA_NUMERO, 1),
                data.getStringExtra(AddEditContactActivity.EXTRA_EMAIL),
                data.getIntExtra(AddEditContactActivity.EXTRA_PRIORITY, 1),
                data.getStringExtra(AddEditContactActivity.EXTRA_FOTO)
            )
            noteViewModel.insert(newNote)

            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditContactActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Could not update! Error!", Toast.LENGTH_SHORT).show()
            }

            val updateNote = Contacto(
                data!!.getStringExtra(AddEditContactActivity.EXTRA_NAME),
                data.getIntExtra(AddEditContactActivity.EXTRA_NUMERO, 1),
                data.getStringExtra(AddEditContactActivity.EXTRA_EMAIL),
                data.getIntExtra(AddEditContactActivity.EXTRA_PRIORITY, 1),
                data.getStringExtra(AddEditContactActivity.EXTRA_FOTO)
            )
            updateNote.id = data.getIntExtra(AddEditContactActivity.EXTRA_ID, -1)
            noteViewModel.update(updateNote)

        } else {
            Toast.makeText(this, "No se guardo el contacto!", Toast.LENGTH_SHORT).show()
        }


    }
}
