package kh.edu.rupp.seavphov.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kh.edu.rupp.seavphov.activity.MainActivity
import kh.edu.rupp.seavphov.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    private var mainActivity: MainActivity? = null

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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(binding.bookListContainer.id, BookListFragment("My books"))
                .commit()
        }
        setUserProfile()

        binding.pencilIcon.setOnClickListener {
            mainActivity?.clearLoginState()
        }
    }

    private fun setUserProfile() {
        val userInfo = mainActivity?.getUserInfo()

        Log.d("Seavphov", "userInfo $userInfo")
        binding.userName.text = userInfo?.name;
        binding.mail.text = userInfo?.gmail
        Picasso.get()
            .load(userInfo?.imgUrl)
            .into(binding.profilePicture);
    }
}
