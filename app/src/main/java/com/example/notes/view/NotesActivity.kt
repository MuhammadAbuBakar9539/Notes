package com.example.notes.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.common.INTENT_KEY
import com.example.notes.repository.RepositoryImpl
import com.example.notes.view.recyclerview.NotesAdapter
import com.example.notes.view.recyclerview.OnRecyclerItemClicked
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.content_notes.*


class NotesActivity : AppCompatActivity() {
    private lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        viewModel = ViewModelProvider(this, NotesViewModelFactory(RepositoryImpl(application)))
            .get(NotesViewModel::class.java)

        viewModel.getNotes()

        viewModel.getNotesObservable().observe(this, Observer { note ->
            rv_notes.layoutManager = LinearLayoutManager(this)
            pb_notes.visibility = View.GONE
            rv_notes.adapter = NotesAdapter(note, object : OnRecyclerItemClicked {
                override fun onNoteClicked(id: Int) {
                    val intent = Intent(this@NotesActivity, UpdateNoteActivity::class.java)
                    intent.putExtra(INTENT_KEY, id)
                    startActivity(intent)
                }
            })
        })

        viewModel.getNotesErrorObservable().observe(this, Observer { errorMessage ->
            pb_notes.visibility = View.GONE
            lil_error.visibility = View.VISIBLE
            tv_error.text = errorMessage
            btn_retry.setOnClickListener {
                pb_notes.visibility = View.VISIBLE
                viewModel.getNotes()
            }
        })
        fab.setOnClickListener {
            startActivity(
                Intent(this, NewNoteActivity::class.java)
            )
        }


    }
}
