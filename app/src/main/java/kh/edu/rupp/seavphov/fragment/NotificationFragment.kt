package kh.edu.rupp.seavphov.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kh.edu.rupp.seavphov.databinding.FragmentCategoryBinding
import kh.edu.rupp.seavphov.databinding.FragmentHomeBinding
import kh.edu.rupp.seavphov.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment() {
    private var binding: FragmentNotificationBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
