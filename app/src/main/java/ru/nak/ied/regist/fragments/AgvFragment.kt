package ru.nak.ied.regist.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import ru.nak.ied.regist.R
import ru.nak.ied.regist.activities.MainApp
import ru.nak.ied.regist.activities.NewAgvActivity
import ru.nak.ied.regist.databinding.FragmentAgvBinding
import ru.nak.ied.regist.db.AgvAdapter
import ru.nak.ied.regist.db.MainViewModel
import ru.nak.ied.regist.entities.AGVItem
import androidx.recyclerview.widget.LinearLayoutManager

class AgvFragment : BaseFragment(), AgvAdapter.Listener {
    private lateinit var binding: FragmentAgvBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: AgvAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew() {
        editLauncher.launch(Intent(activity, NewAgvActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    // для обнавления в базе данных
    private fun initRcView() = with(binding) {
        rcVewNote.layoutManager = LinearLayoutManager(activity)
        adapter = AgvAdapter(this@AgvFragment)
        rcVewNote.adapter = adapter
    }

    // функция наблюдателя за изменениями
    private fun observer() {
        mainViewModel.allAGV.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun onEditResult() {
        editLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val editState = it.data?.getStringExtra(EDIT_STATE_KEY)
                if (editState == "update") {
                    mainViewModel.updateAGV(it.data?.getSerializableExtra(NEW_NOTE_KEY) as AGVItem)
                } else {
                    mainViewModel.insertAGV(it.data?.getSerializableExtra(NEW_NOTE_KEY) as AGVItem)
                }
            }
        }
    }

    override fun deleteItem(id: Int) {
        mainViewModel.deleteAGV(id)
    }

    override fun onClickItem(agv: AGVItem) {
        val intent = Intent(activity, NewAgvActivity::class.java).apply {
            putExtra(NEW_NOTE_KEY, agv)
        }
        editLauncher.launch(intent)
    }

    companion object {
        const val NEW_NOTE_KEY = "new_note_key"
        const val EDIT_STATE_KEY = "edit_state_key"

        @JvmStatic
        fun newInstance() = AgvFragment()
    }
}