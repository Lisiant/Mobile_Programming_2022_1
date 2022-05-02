package com.example.app17_fragment_communication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.app17_fragment_communication.databinding.FragmentImageBinding

class ImageFragment : Fragment() {
    /**
     * binding이 nullable인 이유: view가 삭제 가능하고 binding 이 남아있으면 메모리 누수.
     *
     * Fragment-> 액티비티와 연동되는 activityViewModels 사용.
     */
    private var binding: FragmentImageBinding? = null
    private val myViewModel: MyViewModel by activityViewModels()
    private val imgList = arrayListOf<Int>(R.drawable.img1, R.drawable.img2, R.drawable.img3)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.apply {
            imageView.setOnClickListener {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                val textFragment = TextFragment()
                fragment.replace(R.id.frameLayout, textFragment)
                fragment.addToBackStack(null)
                fragment.commit()

            }
            radioGroup.setOnCheckedChangeListener { radioGroup, i ->
                when (i) {
                    R.id.radioButton1 -> {
                        myViewModel.setLiveData(0)
                    }
                    R.id.radioButton2 -> {
                        myViewModel.setLiveData(1)
                    }
                    R.id.radioButton3 -> {
                        myViewModel.setLiveData(2)
                    }
                }
                imageView.setImageResource(imgList[myViewModel.selectedNum.value!!])
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}