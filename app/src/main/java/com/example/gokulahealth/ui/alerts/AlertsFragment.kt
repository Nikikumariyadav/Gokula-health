package com.example.gokulahealth.ui.alerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gokulahealth.GokulaApp
import com.example.gokulahealth.databinding.FragmentAlertsBinding
import com.example.gokulahealth.ui.vaccination.VaccinationAdapter
import com.example.gokulahealth.viewmodel.CattleViewModel
import com.example.gokulahealth.viewmodel.CattleViewModelFactory

class AlertsFragment : Fragment() {
    private var _b: FragmentAlertsBinding? = null
    private val b get() = _b!!
    private val vm: CattleViewModel by activityViewModels {
        CattleViewModelFactory((requireActivity().application as GokulaApp).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentAlertsBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = VaccinationAdapter(onCompleteClick = { vm.markCompleted(it.id) }, onDeleteClick = { vm.deleteVaccination(it) })
        b.recyclerAlerts.layoutManager = LinearLayoutManager(requireContext())
        b.recyclerAlerts.adapter = adapter
        vm.pendingVaccinations.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            b.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
