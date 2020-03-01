package com.example.notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.notes.model.NoteEntity
import com.example.notes.repository.Repository
import com.example.notes.viewmodel.NotesViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class NotesViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule =InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    private lateinit var viewModelTest:NotesViewModel

    @Mock
    private lateinit var notesObserver:Observer<List<NoteEntity>>

    @Mock
    private lateinit var notesErrorObserver:Observer<String>

    @Mock
    private lateinit var noteObserver:Observer<NoteEntity>

    @Mock
    private lateinit var noteErrorObserver:Observer<String>

    @Mock
    private lateinit var addSuccessObserver:Observer<Boolean>

    @Mock
    private lateinit var updateSuccessObserver:Observer<Boolean>

    private lateinit var entity: NoteEntity

    @Before
    fun setup(){
        viewModelTest = NotesViewModel(repository)
        viewModelTest.getNotesObservable().observeForever(notesObserver)
        viewModelTest.getNotesErrorObservable().observeForever(notesErrorObserver)
        viewModelTest.getNoteObservable().observeForever(noteObserver)
        viewModelTest.getNoteErrorObservable().observeForever(noteErrorObserver)
        viewModelTest.getAddSuccessObservable().observeForever(addSuccessObserver)
        viewModelTest.getUpdateSuccessObservable().observeForever(updateSuccessObserver)
        entity = NoteEntity(1, "abc", "abc")
    }

    @Test
    fun `get Notes load successfully`(){
        //Given
        `when`(repository.getNotes()).thenReturn(Flowable.just(listOf(entity)))

        //When
        viewModelTest.getNotes()

        //Then
        Assert.assertEquals(listOf(entity), viewModelTest.getNotesObservable().value)
        verify(notesObserver).onChanged(listOf(entity))
    }

    @Test
    fun `get Notes load unSuccessful`(){
        //Given
        val error = "error"
        `when`(repository.getNotes()).thenReturn(Flowable.error(RuntimeException(error)))

        //When
        viewModelTest.getNotes()

        //Then
        Assert.assertEquals(error, viewModelTest.getNotesErrorObservable().value)
        verify(notesErrorObserver).onChanged(error)
    }

    @Test
    fun `get Note load successfully`(){
        //Given
        `when`(repository.getNote(1)).thenReturn(Flowable.just(entity))

        //When
        viewModelTest.getNote(1)

        //Then
        Assert.assertEquals(entity, viewModelTest.getNoteObservable().value)
        verify(noteObserver).onChanged(entity)
    }

    @Test
    fun `get Note load unSuccessful`(){
        //Given
        val error = "error"
        `when`(repository.getNote(1)).thenReturn(Flowable.error(RuntimeException(error)))

        //When
        viewModelTest.getNote(1)

        //Then
        Assert.assertEquals(error, viewModelTest.getNoteErrorObservable().value)
        verify(noteErrorObserver).onChanged(error)
    }

    @Test
    fun `add Note load successfully`(){
        //Given
        `when`(repository.addNote(entity)).thenReturn(Completable.complete())

        //When
        viewModelTest.addNote(entity)

        //Then
        verify(addSuccessObserver).onChanged(true)
    }

    @Test
    fun `add Note load unSuccessful`(){
        //Given
        val error = "error"
        `when`(repository.addNote(entity)).thenReturn(Completable.error(RuntimeException(error)))

        //When
        viewModelTest.addNote(entity)

        //Then
        verify(addSuccessObserver).onChanged(false)
    }

    @Test
    fun `update Note load successfully`(){
        //Given
        `when`(repository.updateNote(entity)).thenReturn(Completable.complete())

        //When
        viewModelTest.updateNote(entity)

        //Then
        verify(updateSuccessObserver).onChanged(true)
    }

    @Test
    fun `update Note load unSuccessful`(){
        //Given
        val error = "error"
        `when`(repository.updateNote(entity)).thenReturn(Completable.error(RuntimeException(error)))

        //When
        viewModelTest.updateNote(entity)

        //Then
        verify(updateSuccessObserver).onChanged(false)
    }
}