package com.example.presetsettings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_new_file.*

/**
 * A simple [Fragment] subclass.
 * Use the [NewFileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewFileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_file, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.ok).setOnClickListener {
            val filename = filename.text.toString()
            val action = NewFileFragmentDirections.actionNewFileFragmentToFirstFragment(filename)
            findNavController().navigate(action)
        }
    }
}
