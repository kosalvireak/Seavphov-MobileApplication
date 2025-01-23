package kh.edu.rupp.seavphov.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kh.edu.rupp.seavphov.R
import kh.edu.rupp.seavphov.activity.MainActivity
import kh.edu.rupp.seavphov.databinding.FragmentNoPermissionBinding

class NoPermissionFragment : Fragment() {
    private lateinit var binding: FragmentNoPermissionBinding
    private var mainActivity: MainActivity? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Get reference to MainActivity
        mainActivity = context as? MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoPermissionBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener{
            findNavController().navigate(R.id.action_noPermissionFragment_to_LoginFragment)
        }

        binding.signupButton.setOnClickListener{
            findNavController().navigate(R.id.action_noPermissionFragment_to_signUpFragment)
        }
    }

}