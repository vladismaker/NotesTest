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
import com.application.notestest.databinding.FragmentCreateNoteBinding

class CreateNoteFragment : Fragment() {
    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NotesViewModel
    private var favoriteBoolean:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
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

        binding.buttonFavorite.setImageResource(R.drawable.icon_favorite_unselect_40)

        binding.buttonCreate.setOnClickListener {
            val textTitle = binding.layoutEditTextTitle.editText?.text.toString()
            val textDescription = binding.layoutEditTextDescription.editText?.text.toString()
            if (textTitle.isNotEmpty() && textDescription.isNotEmpty()) {
                viewModel.addNote(textTitle, textDescription, favoriteBoolean)
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}