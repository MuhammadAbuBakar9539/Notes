package com.example.notes.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.model.NoteEntity
import com.example.notes.repository.RepositoryImpl
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory
import kotlinx.android.synthetic.main.activity_new_note.*

class NewNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        title = getString(R.string.new_note)

        val viewModel =
            ViewModelProvider(this, NotesViewModelFactory(RepositoryImpl(application))).get(
                NotesViewModel::class.java
            )
        btn_save_note.setOnClickListener {
            val title = et_title_new.text.toString()
            val detail = et_note_detail_new.text.toString()
            if (title.isNotEmpty() && detail.isNotEmpty()) {
                viewModel.addNote(NoteEntity(0, title = title, detail = detail))
                startActivity(
                    Intent(this, NotesActivity::class.java)
                )
                finish()//after adding note into database we don't need this activity therefore it should be finish
            }else{
                Toast.makeText(this, getString(R.string.add_note_empty), Toast.LENGTH_LONG).show()
            }
        }

        viewModel.getAddSuccessObservable().observe(this, Observer {
            if (it == true){
                Toast.makeText(this, getString(R.string.add_note_message), Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, getString(R.string.add_note_error), Toast.LENGTH_LONG).show()
            }
        })
    }
}
