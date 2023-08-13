package com.example.exchangerateweather.ui

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.exchangerateweather.R
import com.example.exchangerateweather.databinding.FragmentDetailBinding
import com.example.exchangerateweather.databinding.FragmentLoginBinding

/**
 * Author: Sheng Hsuen
 * Date: 12/08/2023
 *
 */
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var binding: FragmentLoginBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        binding.btnLogin.setOnClickListener{

            if(binding.inputUsername.text.isNullOrEmpty()){
                val dialogBuilder = AlertDialog.Builder(this.requireContext())
                dialogBuilder.setMessage("Invalid username, please try again")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { dialog, id ->
                        dialog.cancel()
                    }
                val alert = dialogBuilder.create()
                alert.setTitle("Error")
                alert.show()
            }else{
                val directions = LoginFragmentDirections.actionLoginFragmentToDetailFragment(binding.inputUsername.text.toString())
                findNavController().navigate(directions)
            }

        }
    }
}