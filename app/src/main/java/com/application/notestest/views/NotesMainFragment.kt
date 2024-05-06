package com.application.notestest.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.notestest.dataclasses.DataNotes
import com.application.notestest.viewmodels.NotesViewModel
import com.application.notestest.interfaces.OnItemClickListenerNotes
import com.application.notestest.R
import com.application.notestest.adapters.RecyclerViewAdapterNotes
import com.application.notestest.databinding.FragmentNotesMainBinding

class NotesMainFragment : Fragment(), OnItemClickListenerNotes {
    private lateinit var viewModel: NotesViewModel
    private var _binding: FragmentNotesMainBinding? = null
    private val binding get() = _binding!!
    private var list:List<DataNotes> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[NotesViewModel::class.java]

        viewModel.liveDataArrayNotes.observe(viewLifecycleOwner) { it ->
            list = it.sortedByDescending { it.date }.sortedByDescending { it.favorite }
            setRecyclerViewNotes(list)
        }

        viewModel.liveDataEmpty.observe(viewLifecycleOwner){
            if (it){
                binding.hintEmpty.visibility = View.VISIBLE
            }else{
                binding.hintEmpty.visibility = View.GONE
            }
        }

        viewModel.getArrayNotes()

        binding.addButton.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.container_main, CreateNoteFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setRecyclerViewNotes(listNotes:List<DataNotes>){
        val adapter = RecyclerViewAdapterNotes(listNotes,this)

        with(binding){
            recyclerViewNotes.isNestedScrollingEnabled = false
            recyclerViewNotes.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewNotes.adapter = adapter
        }
    }

    override fun onItemClickNotes(position: Int) {
        viewModel.setFullNote(list[position])
        viewModel.liveDataNote.observe(viewLifecycleOwner) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container_main, FullNoteFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}