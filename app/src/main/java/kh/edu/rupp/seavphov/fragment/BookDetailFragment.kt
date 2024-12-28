package kh.edu.rupp.seavphov.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import kh.edu.rupp.seavphov.R
import kh.edu.rupp.seavphov.activity.MainActivity
import kh.edu.rupp.seavphov.databinding.FragmentBookdetailBinding
import kh.edu.rupp.seavphov.model.BookDetail
import kh.edu.rupp.seavphov.model.State
import kh.edu.rupp.seavphov.viewmodel.BookDetailFragmentViewModel

class BookDetailFragment : Fragment() {
    private var mainActivity: MainActivity? = null

    private val viewModel by viewModels<BookDetailFragmentViewModel>()

    private lateinit var binding: FragmentBookdetailBinding

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
        binding = FragmentBookdetailBinding.inflate(inflater, container, false)
        binding.backArrow.setOnClickListener() {
            findNavController().navigate(R.id.action_bookDetailFragment_to_homeFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity?.hideBottomNavigation()

        viewModel.bookDetailState.observe(viewLifecycleOwner) { bookDetailState ->
            when (bookDetailState.state) {
                State.loading -> {
                    Log.d("Seavphov","loading")
                }
                State.success -> {
                    displayBookDetail(bookDetailState.data!!)
                }

                State.error -> {
                    showErrorContent()
                }
            }
        }
        viewModel.loadBookDetail()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Show bottom navigation when leaving the fragment
        mainActivity?.showBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        mainActivity?.showBottomNavigation()
    }

    private fun displayBookDetail(bookDetail: BookDetail) {
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

    @SuppressLint("SetTextI18n")
    private fun showErrorContent() {
        binding.bookTitle.text = "Something went wrong!";
    }

}