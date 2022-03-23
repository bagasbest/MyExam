package com.project.myexam.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.myexam.databinding.FragmentHomepageBinding
import com.project.myexam.utils.DataAdapter
import com.project.myexam.utils.DataModel
import com.project.myexam.utils.DataRepository


class HomepageFragment : Fragment() {

    private var binding: FragmentHomepageBinding? = null
    private var adapter: DataAdapter? = null
    private var dataList: ArrayList<DataModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomepageBinding.inflate(layoutInflater, container, false)

        initRecyclerView()
        DataRepository.addItemFromJSON(dataList, requireActivity())

        return binding?.root
    }

    private fun initRecyclerView() {
        binding?.rvData?.setHasFixedSize(true);
        binding?.rvData?.layoutManager = LinearLayoutManager(activity)
        adapter = DataAdapter(dataList)
        binding?.rvData?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.root
    }
}