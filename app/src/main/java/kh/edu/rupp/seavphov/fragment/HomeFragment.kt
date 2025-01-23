package kh.edu.rupp.seavphov.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kh.edu.rupp.seavphov.R
import kh.edu.rupp.seavphov.adapter.BookAdapter
import kh.edu.rupp.seavphov.adapter.BookInfoAdapter
import kh.edu.rupp.seavphov.databinding.FragmentHomeBinding
import kh.edu.rupp.seavphov.model.Book
import kh.edu.rupp.seavphov.model.State
import kh.edu.rupp.seavphov.viewmodel.HomeFragmentViewModel

class HomeFragment : Fragment() {
    private val viewModel by viewModels<HomeFragmentViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private var bookAdapter: BookAdapter? = null;
    private var bookInfoAdapter: BookInfoAdapter? = null

    lateinit var carouselId: String;
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
            navigateToBookDetail(carouselId)
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
        viewModel.carouselState.observe(viewLifecycleOwner) { carouselState ->
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
            // real data
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

        showCarouselLoading()
        showMainLoading()
        //Forward event to View Model
        viewModel.loadCarousel();
        viewModel.loadNewestAddition()
        viewModel.loadThisWeekHighlight()

    }

    private fun navigateToBookDetail(bookId: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToBookDetailFragment(bookId)
        findNavController().navigate(action)
    }

    private fun displayNewestAddition(bookList: ArrayList<Book>) {
        binding.newAdditionRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bookAdapter = BookAdapter(bookList, object : BookAdapter.OnBookClickListener {
            override fun onBookClick(book: Book) {
                Toast.makeText(
                    requireContext(),
                    "Selected Book title: ${book.title}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        });
        binding.newAdditionRecyclerView.adapter = bookAdapter
    }

    private fun displayThisWeekHighlight(bookList: ArrayList<Book>) {
        // real data
        binding.thisWeekHightlight.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bookAdapter = BookAdapter(bookList, object : BookAdapter.OnBookClickListener {
            override fun onBookClick(book: Book) {
                handleOnBookClick(book)
            }
        });
        binding.thisWeekHightlight.adapter = bookAdapter
    }

    private fun handleOnBookClick(book: Book) {
        navigateToBookDetail(book._id)
    }

//    private fun displayBooksList(bookList: ArrayList<Book>) {
//        binding.bookListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
//        bookInfoAdapter = BookInfoAdapter(bookList);
//        binding.bookListRecyclerView.adapter = bookInfoAdapter
//    }

    private fun displayCarousel(carousel: Book) {
        carouselId = carousel._id
        binding.carouselTitle.text = carousel.title
        binding.carouselDescription.text = if (carousel.description.length > 150) {
            carousel.description.take(200) + "..."
        } else {
            carousel.description
        }
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
        Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
    }
}
