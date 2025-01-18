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
import kh.edu.rupp.seavphov.databinding.FragmentSignupBinding

class SignUpFragment : Fragment() {

    private var mainActivity: MainActivity? = null
    private lateinit var binding: FragmentSignupBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Get reference to MainActivity
        mainActivity = context as? MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        mainActivity?.showBottomNavigation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity?.hideBottomNavigation()
        mainActivity?.hideTopNavigation()
        hideMainLoading()

        binding.backIcon.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_LoginFragment)
        }
    }



    private fun showMainLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.bodySection.visibility = View.GONE
    }

    private fun hideMainLoading() {
        binding.progressBar.visibility = View.GONE
        binding.bodySection.visibility = View.VISIBLE
    }

}
