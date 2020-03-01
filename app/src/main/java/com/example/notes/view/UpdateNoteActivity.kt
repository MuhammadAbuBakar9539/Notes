package com.example.notes.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.common.INTENT_KEY
import com.example.notes.model.NoteEntity
import com.example.notes.repository.RepositoryImpl
import com.example.notes.viewmodel.NotesViewModel
import com.example.notes.viewmodel.NotesViewModelFactory
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.activity_update_note.*

class UpdateNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        title = getString(R.string.update_note)

        val id = intent.getIntExtra(INTENT_KEY, 0)

        val viewModel =
            ViewModelProvider(this, NotesViewModelFactory(RepositoryImpl(application))).get(
                NotesViewModel::class.java
            )

        viewModel.getNote(id)

        btn_update_note.setOnClickListener {
            val title = et_title_update.text.toString()
            val detail = et_note_detail_update.text.toString()
            if(title.isNotEmpty() && detail.isNotEmpty()){
                viewModel.updateNote(NoteEntity(id, title, detail))
                startActivity(
                    Intent(this, NotesActivity::class.java)
                )
                finish()//after updating note into database we don't need this activity therefore it should be finish
            }else{
                Toast.makeText(this, getString(R.string.update_note_empty), Toast.LENGTH_LONG).show()
            }
        }

        viewModel.getNoteObservable().observe(this, Observer { note ->
            pb_notes_update.visibility = View.GONE
            et_title_update.setText(note.title)
            et_note_detail_update.setText(note.detail)
        })

        viewModel.getNoteErrorObservable().observe(this, Observer { errorMessage ->
            pb_notes_update.visibility = View.GONE
            lil_error_update.visibility = View.VISIBLE
            tv_error_update.text = errorMessage
            btn_retry_update.setOnClickListener {
                pb_notes_update.visibility = View.VISIBLE
                viewModel.getNote(id)
            }
        })

        viewModel.getUpdateSuccessObservable().observe(this, Observer {
            if (it == true){
                Toast.makeText(this, getString(R.string.update_note_message), Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, getString(R.string.update_note_error), Toast.LENGTH_LONG).show()
            }
        })
    }
}
