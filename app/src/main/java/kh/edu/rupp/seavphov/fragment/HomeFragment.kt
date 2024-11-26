package kh.edu.rupp.seavphov.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
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
//    private var bookList = listOf(
//        Book("Percy Jackson", "$10", "https://m.media-amazon.com/images/M/MV5BMGMyZTI2MjUtODEzMi00M2JlLWEwZGEtYWE5MmY5OWNkYTRjXkEyXkFqcGc@._V1_QL75_UX380_CR0,1,380,562_.jpg"),
//        Book("City of Bones", "$15", "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1432730315i/256683.jpg"),
//        Book("The Fault in Our Stars", "$20", "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1660273739i/11870085.jpg"),
//        Book("City of Bones", "$15", "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1432730315i/256683.jpg"),
//        Book("The Fault in Our Stars", "$20", "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1660273739i/11870085.jpg"),
//    )
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

        // observe data in view model
        viewModel.homeState.observe(viewLifecycleOwner){ carouselState ->
            when(carouselState.state) {
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

        viewModel.newestAdditionState.observe(viewLifecycleOwner){ newestAdditionState ->
            when(newestAdditionState.state) {
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

        viewModel.thisWeekHighlightState.observe(viewLifecycleOwner){ thisWeekHighlightState ->
            when(thisWeekHighlightState.state) {
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

        viewModel.booksListState.observe(viewLifecycleOwner){ booksListState ->
            when(booksListState.state){
                State.loading -> hideMainLoading()
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



        showCarouselLoading()
        showMainLoading()
        //Forward event to View Model
        viewModel.loadHome();
        viewModel.loadNewestAddition()
        viewModel.loadThisWeekHighlight()
        viewModel.loadBooksList()

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

    private fun displayBooksList(bookList: ArrayList<Book>) {
//        binding.bookListRecyclerView.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.bookListRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        bookInfoAdapter = BookInfoAdapter(bookList);
        binding.bookListRecyclerView.adapter = bookInfoAdapter
    }

    private fun displayCarousel(carousel: Carousel){
        binding.bookTitle.text = carousel.title
        binding.bookDescription.text = carousel.description
        Picasso.get()
            .load(carousel.imgUrl)
            .into(binding.bookImage)
    }

    private fun showCarouselLoading(){
        binding.carouselProgressBar.visibility = View.VISIBLE
        binding.carouselSection.visibility = View.GONE
    }
    private fun hideCarouselLoading(){
        binding.carouselProgressBar.visibility = View.GONE
        binding.carouselSection.visibility = View.VISIBLE
    }

    private fun showMainLoading(){
        binding.progressBar.visibility = View.VISIBLE
        binding.bodySection.visibility = View.GONE
    }
    private fun hideMainLoading(){
        binding.progressBar.visibility = View.GONE
        binding.bodySection.visibility = View.VISIBLE
    }


    @SuppressLint("SetTextI18n")
    private fun showErrorContent(){
        binding.bookTitle.text = "Something went wrong!";
    }
}
