package kh.edu.rupp.seavphov.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kh.edu.rupp.seavphov.databinding.ActivityHolderBookBinding
import kh.edu.rupp.seavphov.model.Book

class BookAdapter(
    private val bookList: List<Book>,
    private val onBookClickListener: OnBookClickListener
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    // interface to handle action click
    interface OnBookClickListener {
        fun onBookClick(book: Book)
    }

    class BookViewHolder(val binding: ActivityHolderBookBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding =
            ActivityHolderBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.binding.bookTitle.text = book.title
        holder.binding.bookPrice.text = "$ " + book.price
        Picasso.get()
            .load(book.imgUrl)
            .into(holder.binding.bookImage);


        //add click listener
        holder.itemView.setOnClickListener {
            onBookClickListener.onBookClick(book)
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}