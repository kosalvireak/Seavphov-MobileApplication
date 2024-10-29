package kh.edu.rupp.seavphov.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.seavphov.adapter.BookAdapter
import kh.edu.rupp.seavphov.databinding.FragmentHomeBinding
import kh.edu.rupp.seavphov.model.Book
import kh.edu.rupp.seavphov.viewmodel.HomeFragmentViewModel

class HomeFragment : Fragment() {
    private val viewModel by viewModels<HomeFragmentViewModel>()

    private lateinit var binding: FragmentHomeBinding;
    private var bookAdapter: BookAdapter? = null;
    private var bookList = listOf(
            Book("Percy Jackson", "$10", "https://m.media-amazon.com/images/M/MV5BMGMyZTI2MjUtODEzMi00M2JlLWEwZGEtYWE5MmY5OWNkYTRjXkEyXkFqcGc@._V1_QL75_UX380_CR0,1,380,562_.jpg"),
            Book("City of Bones", "$15", "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1432730315i/256683.jpg"),
            Book("The Fault in Our Stars", "$20", "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1660273739i/11870085.jpg"),
            Book("City of Bones", "$15", "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1432730315i/256683.jpg"),
            Book("The Fault in Our Stars", "$20", "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1660273739i/11870085.jpg"),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadHome();

        binding.highlightRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bookAdapter = BookAdapter(bookList);
        binding.highlightRecyclerView.adapter = bookAdapter

        binding.newAdditionRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bookAdapter = BookAdapter(bookList);
        binding.newAdditionRecyclerView.adapter = bookAdapter
    }
}
