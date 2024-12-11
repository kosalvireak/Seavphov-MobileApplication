package kh.edu.rupp.seavphov.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kh.edu.rupp.seavphov.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchBar = binding.searchBar
        val searchView = binding.searchView

        searchBar.setOnClickListener {
            searchBar.requestFocus() // Ensure SearchBar gets focus
            searchView.show()        // Expand SearchView
        }

        arguments?.let { args -> {
            val searchParams = args.getString("searchParams")
            searchBar.hint=searchParams
        } }
    }
}
