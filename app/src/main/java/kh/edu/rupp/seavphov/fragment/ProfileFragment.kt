package kh.edu.rupp.seavphov.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kh.edu.rupp.seavphov.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(binding.bookListContainer.id, BookListFragment("My books"))
                .commit()
        }
    }
}
