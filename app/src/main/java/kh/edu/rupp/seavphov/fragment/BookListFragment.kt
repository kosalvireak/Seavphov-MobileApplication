package kh.edu.rupp.seavphov.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import kh.edu.rupp.seavphov.adapter.BookInfoAdapter
import kh.edu.rupp.seavphov.databinding.FragmentBookListBinding
import kh.edu.rupp.seavphov.model.Book
import kh.edu.rupp.seavphov.model.State
import kh.edu.rupp.seavphov.viewmodel.BookListFragmentViewModel

class BookListFragment(var title: String = "You may also like") : Fragment() {
    private val _title : String = title;
    private val viewModel by viewModels<BookListFragmentViewModel>()
    private lateinit var binding: FragmentBookListBinding;
    private var bookInfoAdapter: BookInfoAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = this._title;

        viewModel.booksListState.observe(viewLifecycleOwner) { booksListState ->
            when (booksListState.state) {
                State.loading -> showMainLoading()
                State.success -> {
                    hideMainLoading()
                    displayBooksList(booksListState.data!!)
                }

                State.error -> {
                    hideMainLoading()
                    showErrorContent()
                }
            }
        }

        viewModel.loadBooksList()
    }

    private fun displayBooksList(bookList: ArrayList<Book>) {
        binding.bookListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        bookInfoAdapter = BookInfoAdapter(bookList);
        binding.bookListRecyclerView.adapter = bookInfoAdapter
    }

    private fun showMainLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.bodySection.visibility = View.GONE
    }

    private fun hideMainLoading() {
        binding.progressBar.visibility = View.GONE
        binding.bodySection.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun showErrorContent() {
        binding.title.text = "Something went wrong!";
    }

}