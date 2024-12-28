package kh.edu.rupp.seavphov.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kh.edu.rupp.seavphov.R
import kh.edu.rupp.seavphov.adapter.BookAdapter
import kh.edu.rupp.seavphov.adapter.BookInfoAdapter
import kh.edu.rupp.seavphov.databinding.FragmentHomeBinding
import kh.edu.rupp.seavphov.model.Book
import kh.edu.rupp.seavphov.model.Carousel
import kh.edu.rupp.seavphov.model.State
import kh.edu.rupp.seavphov.viewmodel.HomeFragmentViewModel

class HomeFragment : Fragment() {
    private val viewModel by viewModels<HomeFragmentViewModel>()
    private lateinit var binding: FragmentHomeBinding;
    private var bookAdapter: BookAdapter? = null;
    private var bookInfoAdapter: BookInfoAdapter? = null
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

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(binding.bookListContainer.id, BookListFragment())
                .commit()
        }

        // Handle Button
        binding.readMoreButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_bookDetailFragment)
        }

        binding.fantasyButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("searchParams", "Fantasy")
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
        }

        binding.historyButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("searchParams", "History")
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
        }

        binding.horrorButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("searchParams", "Horror")
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
        }


        // observe data in view model
        viewModel.homeState.observe(viewLifecycleOwner) { carouselState ->
            when (carouselState.state) {
                State.loading -> showCarouselLoading()
                State.success -> {
                    hideCarouselLoading()
                    displayCarousel(carouselState.data!!)
                }

                State.error -> {
                    hideCarouselLoading()
                    showErrorContent()
                }
            }
        }

        viewModel.newestAdditionState.observe(viewLifecycleOwner) { newestAdditionState ->
            when (newestAdditionState.state) {
                State.loading -> hideMainLoading()
                State.success -> {
                    hideMainLoading()
                    displayNewestAddition(newestAdditionState.data!!)
                }

                State.error -> {
                    hideMainLoading()
                    showErrorContent()
                }
            }
        }

        viewModel.thisWeekHighlightState.observe(viewLifecycleOwner) { thisWeekHighlightState ->
            when (thisWeekHighlightState.state) {
                State.loading -> hideMainLoading()
                State.success -> {
                    hideMainLoading()
                    displayThisWeekHighlight(thisWeekHighlightState.data!!)
                }

                State.error -> {
                    hideMainLoading()
                    showErrorContent()
                }
            }
        }

//        viewModel.booksListState.observe(viewLifecycleOwner) { booksListState ->
//            when (booksListState.state) {
//                State.loading -> showMainLoading()
//                State.success -> {
//                    hideMainLoading()
//                    displayBooksList(booksListState.data!!)
//                }
//
//                State.error -> {
//                    hideMainLoading()
//                    showErrorContent()
//                }
//            }
//        }



        showCarouselLoading()
        showMainLoading()
        //Forward event to View Model
        viewModel.loadHome();
        viewModel.loadNewestAddition()
        viewModel.loadThisWeekHighlight()
//        viewModel.loadBooksList()

    }

    private fun displayNewestAddition(bookList: ArrayList<Book>) {
        binding.newAdditionRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bookAdapter = BookAdapter(bookList);
        binding.newAdditionRecyclerView.adapter = bookAdapter
    }

    private fun displayThisWeekHighlight(bookList: ArrayList<Book>) {
        binding.thisWeekHightlight.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bookAdapter = BookAdapter(bookList);
        binding.thisWeekHightlight.adapter = bookAdapter
    }

//    private fun displayBooksList(bookList: ArrayList<Book>) {
//        binding.bookListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
//        bookInfoAdapter = BookInfoAdapter(bookList);
//        binding.bookListRecyclerView.adapter = bookInfoAdapter
//    }

    private fun displayCarousel(carousel: Carousel) {
        binding.bookTitle.text = carousel.title
        binding.bookDescription.text = carousel.description
        Picasso.get()
            .load(carousel.imgUrl)
            .into(binding.bookImage)
    }

    private fun showCarouselLoading() {
        binding.carouselProgressBar.visibility = View.VISIBLE
        binding.carouselSection.visibility = View.GONE
    }

    private fun hideCarouselLoading() {
        binding.carouselProgressBar.visibility = View.GONE
        binding.carouselSection.visibility = View.VISIBLE
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
        binding.bookTitle.text = "Something went wrong!";
    }
}
