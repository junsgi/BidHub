package com.example.bidhubandroid.auction

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.bidhubandroid.R
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.databinding.FragmentRegistBinding
import com.example.bidhubandroid.setOnSingleClickListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.Calendar

class RegistFragment : Fragment() {
    private var _binding: FragmentRegistBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegistViewModel by viewModels()
    private var auctionDate: String? = null
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val share = UserSharedPreferences.sharedPreferences
    private var uri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                binding.image.visibility = View.VISIBLE
                uri = selectedImageUri
                binding.image.setImageURI(selectedImageUri)
            }
        }
        _binding = FragmentRegistBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nick = share.getString("nickname", "login")
        binding.navi.loginBtn.text = nick

        // 경매기간
        val calendar = Calendar.getInstance()
        // DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = "$year-${month + 1}-$dayOfMonth"

                // TimePickerDialog
                val timePickerDialog = TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minute ->
                        val selectedTime = "$hourOfDay:$minute"
                        binding.date.text = "$selectedDate $selectedTime" // 날짜와 시간 모두 설정
                        auctionDate = "${selectedDate}T${selectedTime}"
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                timePickerDialog.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // 최소시간 설정
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        binding.date.setOnClickListener {
            datePickerDialog.show()
        }
        // 경매기간 end

        // 사진등록
        binding.imgRegist.setOnSingleClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryIntent.type = "image/*"
            resultLauncher.launch(galleryIntent)
        }

        binding.submit.setOnSingleClickListener {
            val title = binding.title.text.toString()
            val start = binding.start.text.toString()
            val imm = binding.immediate.text.toString()
            val bid = binding.bid.text.toString()
            if (title.isEmpty() || title.isBlank() || start.isEmpty() || start.isBlank() || bid.isEmpty() || bid.isBlank() || auctionDate.isNullOrBlank()) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Warning!")
                    .setMessage("필수 입력란이 비어있습니다.")
                    .show()
            } else {
                val content = binding.content.text.toString()
                val userId = share.getString("id", "error")!!

                val dataMap = prepareData(title, content, start, bid, imm, userId)
                var imagePart: MultipartBody.Part? = null
                if (uri != null) {
                    val file = File(getPathFromUri(uri!!)!!)
                    imagePart = prepareImagePart(file)
                }
                viewModel.submit(imagePart, dataMap) { status, message ->
                    if (status!!) {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Success!")
                            .setMessage(message!!)
                            .show()
                        findNavController().navigate(R.id.action_registFragment_to_auctionListFragment)
                    } else {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Warning!")
                            .setMessage(message!!)
                            .show()
                    }
                }
            }
        }// submit listener

        // to main
        binding.navi.BidHub.setOnSingleClickListener {
            findNavController().navigate(R.id.action_registFragment_to_auctionListFragment)
        }

        //to mypage
        binding.navi.loginBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.action_registFragment_to_mypageFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun createRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    private fun prepareData(
        title: String,
        content: String,
        start: String,
        bid: String,
        immediate: String,
        userId: String
    ): Map<String, RequestBody> {
        return mapOf(
            "title" to createRequestBody(title),
            "content" to createRequestBody(content),
            "start" to createRequestBody(start),
            "bid" to createRequestBody(bid),
            "date" to createRequestBody(auctionDate!!),
            "immediate" to createRequestBody(immediate),
            "userId" to createRequestBody(userId)
        )
    }

    private fun prepareImagePart(file: File): MultipartBody.Part {
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("img", file.name, requestBody)
    }

    private fun getPathFromUri(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context?.contentResolver?.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }
        return null
    }
}