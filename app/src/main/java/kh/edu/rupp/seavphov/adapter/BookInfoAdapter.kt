package kh.edu.rupp.seavphov.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kh.edu.rupp.seavphov.databinding.ActivityHolderBookInfoBinding
import kh.edu.rupp.seavphov.model.Book

class BookInfoAdapter(private val bookList: List<Book>) : RecyclerView.Adapter<BookInfoAdapter.BookViewHolder>() {
    class BookViewHolder(val binding: ActivityHolderBookInfoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ActivityHolderBookInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.binding.bookTitle.text = book.title
        holder.binding.bookPrice.text = book.price
        holder.binding.bookCondition.text = book.condition
        Picasso.get()
            .load(book.imgUrl)
            .into(holder.binding.bookImage);
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}