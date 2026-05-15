package com.example.gokulahealth.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.gokulahealth.GokulaApp
import com.example.gokulahealth.databinding.FragmentHomeBinding
import com.example.gokulahealth.viewmodel.CattleViewModel
import com.example.gokulahealth.viewmodel.CattleViewModelFactory
import java.time.LocalDate

class HomeFragment : Fragment() {
    private var _b: FragmentHomeBinding? = null
    private val b get() = _b!!
    private val vm: CattleViewModel by activityViewModels {
        CattleViewModelFactory((requireActivity().application as GokulaApp).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentHomeBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val today = LocalDate.now()
        b.tvDate.text = "📅 $today"

        vm.cattleCount.observe(viewLifecycleOwner) { b.tvCattleCount.text = it.toString() }
        vm.totalYieldToday.observe(viewLifecycleOwner) { b.tvTodayYield.text = String.format("%.1f L", it ?: 0.0) }
        vm.getOverdueCount().observe(viewLifecycleOwner) { overdue ->
            b.tvOverdueBadge.visibility = if (overdue > 0) View.VISIBLE else View.GONE
            b.tvOverdueBadge.text = "$overdue Overdue!"
        }
        vm.pendingVaccinations.observe(viewLifecycleOwner) { list ->
            val upcoming = list.filter {
                val d = LocalDate.parse(it.scheduledDate)
                !d.isBefore(today) && d.isBefore(today.plusDays(8))
            }
            b.tvUpcomingCount.text = upcoming.size.toString()
            b.tvNoUpcoming.visibility = if (upcoming.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
