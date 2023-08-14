package com.example.exchangerateweather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.exchangerateweather.R
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
    ): View {
        //Inflate view with ViewBinding
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)

        //Input validation and login
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
                //Go to Detail screen with argument
                val directions = LoginFragmentDirections.actionLoginFragmentToDetailFragment(binding.inputUsername.text.toString())
                findNavController().navigate(directions)
            }
        }
    }
}