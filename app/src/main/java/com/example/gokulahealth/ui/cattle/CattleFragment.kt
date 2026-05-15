package com.example.gokulahealth.ui.cattle

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gokulahealth.GokulaApp
import com.example.gokulahealth.databinding.FragmentCattleBinding
import com.example.gokulahealth.viewmodel.CattleViewModel
import com.example.gokulahealth.viewmodel.CattleViewModelFactory

class CattleFragment : Fragment() {
    private var _b: FragmentCattleBinding? = null
    private val b get() = _b!!
    private val vm: CattleViewModel by activityViewModels {
        CattleViewModelFactory((requireActivity().application as GokulaApp).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentCattleBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = CattleAdapter(
            onItemClick = { cattle ->
                startActivity(Intent(requireContext(), CattleDetailActivity::class.java)
                    .putExtra("cattle_id", cattle.id))
            },
            onDeleteClick = { cattle -> vm.deleteCattle(cattle) }
        )
        b.recyclerCattle.layoutManager = LinearLayoutManager(requireContext())
        b.recyclerCattle.adapter = adapter
        vm.allCattle.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            b.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
        b.fabAddCattle.setOnClickListener {
            startActivity(Intent(requireContext(), AddCattleActivity::class.java))
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
