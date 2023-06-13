package com.example.rtepandroid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rtepandroid.adapters.HomeGridAdapter
import com.example.rtepandroid.databinding.FragmentHomeBinding
import com.example.rtepandroid.models.HomeGrid

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    lateinit var homeGridRV: RecyclerView
    lateinit var homeGridAdapter: HomeGridAdapter
    lateinit var homeGrid: ArrayList<HomeGrid>
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding!!.root

        homeGridRV = binding!!.homeShortcuts

        homeGrid = ArrayList()
        val layoutManager = GridLayoutManager(this@HomeFragment.context, 2)

        homeGridRV.layoutManager = layoutManager
        homeGridAdapter = HomeGridAdapter(homeGrid, this@HomeFragment.context)
        homeGridRV.adapter = homeGridAdapter
        homeGrid.add(HomeGrid("User Registration", "register the users", "Register", UsersActivity::class.java))
        homeGrid.add(HomeGrid("Fuel Refill", "Fuel Refill Services", "Access"))
        homeGrid.add(HomeGrid("Order Fuel", "Access Fuel Services", "Access"))
        homeGrid.add(HomeGrid("Vehicle Registration", "Register your vehicle before accessing any vehicle service", "Register"))
        homeGrid.add(HomeGrid("Fuel Vendor", "Record your shipment in litres and the unit cost", "Record"))
        homeGrid.add(HomeGrid("Vehicle Maintenance", "Maintain your vehicle here now", "Vehicle Maintain"))
        homeGrid.add(HomeGrid("Vehicle Service", "Service your vehicle more conviniently", "Service now"))
        homeGridAdapter.notifyDataSetChanged()

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}