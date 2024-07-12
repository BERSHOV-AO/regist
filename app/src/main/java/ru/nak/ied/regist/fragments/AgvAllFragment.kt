package ru.nak.ied.regist.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nak.ied.regist.R
import ru.nak.ied.regist.activities.AGVAdapter
import ru.nak.ied.regist.api.MainApi
import javax.inject.Inject

@AndroidEntryPoint
class AgvAllFragment : BaseFragment() {

    @Inject
    lateinit var mainApi: MainApi
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AGVAdapter

    override fun onClickNew() {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agv, container, false)

        recyclerView = view.findViewById(R.id.rcVewNote)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        CoroutineScope(Dispatchers.Main).launch {
            val listAgv = mainApi.getAllAGV()
            Log.d("MyLog", "listAgv: $listAgv")

            adapter = AGVAdapter(listAgv)
            recyclerView.adapter = adapter
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = AgvAllFragment()
    }
}