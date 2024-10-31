package kh.edu.rupp.seavphov.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import kh.edu.rupp.seavphov.databinding.FragmentBookdetailBinding
import kh.edu.rupp.seavphov.model.BookDetail
import kh.edu.rupp.seavphov.viewmodel.BookDetailFragmentViewModel

class BookDetailFragment:Fragment() {

//    private val viewModel = BookDetailFragmentViewModel()
    private val viewModel by viewModels<BookDetailFragmentViewModel>()

    private lateinit var binding: FragmentBookdetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookdetailBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.bookDetailState.observe(viewLifecycleOwner) {bookDetail ->
            displayBookDetail(bookDetail)
        }

        viewModel.loadBookDetail()
    }

    private fun displayBookDetail(bookDetail: BookDetail){
        binding.bookTitle.text = bookDetail.title
        binding.bookDescription.text = bookDetail.description
        binding.bookAuthor.text = bookDetail.author
        binding.bookCategory.text = bookDetail.category
        binding.bookCondition.text = bookDetail.condition
        binding.bookLocation.text = bookDetail.location
        Picasso.get()
            .load(bookDetail.imgUrl)
            .into(binding.bookImage)
    }

}