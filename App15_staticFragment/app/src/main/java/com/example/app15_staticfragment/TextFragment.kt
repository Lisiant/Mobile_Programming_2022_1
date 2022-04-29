package com.example.app15_staticfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.app15_staticfragment.databinding.FragmentTextBinding

class TextFragment : Fragment() {
    private var binding: FragmentTextBinding? = null
    private val myViewModel: MyViewModel by activityViewModels()
    private val data = arrayListOf<String>("ImageData1", "ImageData2", "ImageData3")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTextBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val i = requireActivity().intent
        val imgNum = i.getIntExtra("imgNum", -1)
        if (imgNum != -1) {
            binding!!.textView.text = data[imgNum]
        } else {
            myViewModel.selectedNum.observe(viewLifecycleOwner, Observer {
                binding!!.textView.text = data[it]
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}