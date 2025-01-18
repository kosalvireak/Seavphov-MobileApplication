package kh.edu.rupp.seavphov.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kh.edu.rupp.seavphov.R
import kh.edu.rupp.seavphov.activity.MainActivity
import kh.edu.rupp.seavphov.databinding.FragmentLoginBinding
import kh.edu.rupp.seavphov.model.State
import kh.edu.rupp.seavphov.viewmodel.AuthenticationViewModel

class LoginFragment : Fragment() {

    private val viewModel by viewModels<AuthenticationViewModel>();
    private var mainActivity: MainActivity? = null
    private lateinit var binding: FragmentLoginBinding;

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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity?.hideBottomNavigation()
        mainActivity?.hideTopNavigation()
        hideMainLoading()

        binding.backIcon.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_HomeFragment);
            mainActivity?.showTopNavigation()
            mainActivity?.showBottomNavigation()
            mainActivity?.selectHomeNavigation()
        }
        binding.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_SignUpFragment)
        }

        binding.loginButton.setOnClickListener {
            processLogin()
        }

        viewModel.loginResponseState.observe(viewLifecycleOwner) { loginState ->
            when (loginState.state) {
                State.loading -> showMainLoading()
                State.success -> {
                    hideMainLoading()
                    loginState.data?.data?.let {
                        mainActivity?.saveLoginState(
                            requireContext(), true,
                            it
                        )
                    }
                }

                State.error -> {
                    hideMainLoading()
                    Toast.makeText(requireContext(), loginState.data?.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun processLogin() {
        val gmail = binding.loginEmail.text.toString();
        val password = binding.loginPassword.text.toString();
        viewModel.login(gmail, password)
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