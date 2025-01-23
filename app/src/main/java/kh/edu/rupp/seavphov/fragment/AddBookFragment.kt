package kh.edu.rupp.seavphov.fragment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kh.edu.rupp.seavphov.databinding.FragmentAddbookBinding
import java.util.UUID

class AddBookFragment : Fragment() {
    private lateinit var binding: FragmentAddbookBinding;

    lateinit var storage: FirebaseStorage
    lateinit var storageRef: StorageReference
    lateinit var imageview: ImageView
    lateinit var imgUrl: String;
    private var uri: Uri? = null;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddbookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideMainLoading()

        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        val categories = listOf("Select Category", "Fantasy", "Romance", "Education", "History", "Science")
        val conditions = listOf("Select Condition", "New", "Used - Like new", "Good" , "Damaged")

        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val conditionAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, conditions)
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerCategory.adapter = categoryAdapter
        binding.spinnerCondition.adapter = conditionAdapter

        binding.spinnerCategory.setSelection(0)
        binding.spinnerCondition.setSelection(0)

        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = categories[position]
                if (selectedCategory == "Select Category") {
                    return
                }
                Toast.makeText(requireContext(),  "$selectedCategory selected", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        binding.spinnerCondition.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCondition = categories[position]
                if (selectedCondition == "Select Condition") {
                    return
                }
                Toast.makeText(requireContext(), "$selectedCondition selected", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            binding.imageView.setImageURI(it)
            if(it != null){
                uri = it;
                uploadImage(uri!!)
            }
        }

        binding.uploadFile.setOnClickListener{
//            when click upload button user is able to get image from their mobile device and store in uri
            pickImage.launch("image/*")
        }

        binding.addBookButton.setOnClickListener{
            onAddBook();
        }
    }

    private fun uploadImage(filePath: Uri){
        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")
        val uploadTask = imageRef.putFile(filePath)

        uploadTask.addOnProgressListener { taskSnapshot ->
            val progress = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
            Toast.makeText(context, "Image is uploading", Toast.LENGTH_SHORT).show()
            Log.d("seavphov", "Upload is $progress% complete")
        }

        uploadTask.addOnSuccessListener {
            // Get the download URL of the uploaded image
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                Log.d("TAG", "Image uploaded successfully: ${uri.toString()}")
                imgUrl = uri.toString()
                Toast.makeText(context, "Image upload success", Toast.LENGTH_SHORT).show()
                // Handle the download URL (e.g., save it to a database)
            }
        }

        // On upload failure
        uploadTask.addOnFailureListener { exception ->
            Log.e("TAG", "Image upload failed", exception)
            Toast.makeText(context, "Image upload failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onAddBook(){
        val title = binding.bookTitle.text.toString();
        val author = binding.bookAuthor.text.toString();
        val category = binding.bookCategory.text.toString();
        val condition = binding.bookCondition.text.toString();
        val description = binding.bookDescription.text.toString();
        val location = binding.bookLocation.text.toString();
        val imgUrl = imgUrl

    }

    private fun showMainLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.bodySection.visibility = View.GONE
    }

    private fun hideMainLoading() {
        binding.progressBar.visibility = View.GONE
        binding.bodySection.visibility = View.VISIBLE
    }

}
