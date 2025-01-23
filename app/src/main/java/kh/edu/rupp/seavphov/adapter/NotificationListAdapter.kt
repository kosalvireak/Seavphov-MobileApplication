package kh.edu.rupp.seavphov.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kh.edu.rupp.seavphov.databinding.ActivityHolderNotificationInfoBinding
import kh.edu.rupp.seavphov.model.Notification

class NotificationListAdapter(private val notificationList: List<Notification>) :
    RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder>() {
    class NotificationViewHolder(val binding: ActivityHolderNotificationInfoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ActivityHolderNotificationInfoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NotificationViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val book = notificationList[position]
        Picasso.get()
            .load(book.imgUrl)

    }
}