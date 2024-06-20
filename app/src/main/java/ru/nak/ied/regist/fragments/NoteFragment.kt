package ru.nak.ied.regist.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nak.ied.regist.R


class NoteFragment : BaseFragment() {
    override fun onClickNew() {
        TODO("Not yet implemented")
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}