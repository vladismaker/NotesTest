package com.application.notestest.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.application.notestest.viewmodels.NotesViewModel
import com.application.notestest.R
import com.application.notestest.databinding.FragmentFullNoteBinding

class FullNoteFragment : Fragment() {
    private var _binding: FragmentFullNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NotesViewModel
    private var favoriteBoolean:Boolean = false
    private var id:Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[NotesViewModel::class.java]

        binding.buttonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.buttonFavorite.setOnClickListener{
            if (favoriteBoolean){
                favoriteBoolean = false
                binding.buttonFavorite.setImageResource(R.drawable.icon_favorite_unselect_40)
            }else{
                favoriteBoolean = true
                binding.buttonFavorite.setImageResource(R.drawable.icon_favorite_40)
            }
        }

        viewModel.liveDataNote.observe(viewLifecycleOwner) {
            id = it.id
            setTextToEditText(it.title, it.description, it.favorite)
        }

        binding.buttonSave.setOnClickListener {
            val textTitle = binding.layoutEditTextTitle.editText?.text.toString()
            val textDescription = binding.layoutEditTextDescription.editText?.text.toString()
            if (textTitle.isNotEmpty() && textDescription.isNotEmpty()) {
                parentFragmentManager.popBackStack()
                viewModel.changeArrayNotes(id, textTitle, textDescription, favoriteBoolean)
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }

        }

        binding.buttonDelete.setOnClickListener {
            parentFragmentManager.popBackStack()
            viewModel.deleteNote(id)
        }
    }

    private fun setTextToEditText(title:String, description:String, favorite:Boolean){
        binding.editTextTitle.setText(title)
        binding.editTextDescription.setText(description)
        favoriteBoolean = favorite
        if (favorite){
            binding.buttonFavorite.setImageResource(R.drawable.icon_favorite_40)
        }else{
            binding.buttonFavorite.setImageResource(R.drawable.icon_favorite_unselect_40)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}