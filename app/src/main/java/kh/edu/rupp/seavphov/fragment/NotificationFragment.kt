package kh.edu.rupp.seavphov.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kh.edu.rupp.seavphov.databinding.FragmentNotificationBinding
import android.annotation.SuppressLint
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.seavphov.adapter.NotificationListAdapter
import kh.edu.rupp.seavphov.model.Notification
import kh.edu.rupp.seavphov.model.State
import kh.edu.rupp.seavphov.viewmodel.NotificationListFragmentViewModel

class NotificationFragment : Fragment() {
    private val viewModel by viewModels<NotificationListFragmentViewModel>()
    private lateinit var binding: FragmentNotificationBinding;
    private var notificationListAdapter: NotificationListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.notificationListState.observe(viewLifecycleOwner) {notificationListState ->
            when (notificationListState.state) {
                State.loading -> showMainLoading()
                State.success -> {
                    hideMainLoading()
                    displayNotificationList(notificationListState.data!!)
                }
                State.error -> {
                    hideMainLoading()

                }

            }
        }
        viewModel.loadNotificationList()
    }

    private fun displayNotificationList(notificationList: ArrayList<Notification>) {
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        notificationListAdapter = NotificationListAdapter(notificationList);
        binding.notificationRecyclerView.adapter = notificationListAdapter
    }

    private fun showMainLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideMainLoading() {
        binding.progressBar.visibility = View.GONE
    }


}
