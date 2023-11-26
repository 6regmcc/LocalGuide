package com.example.localguide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.localguide.databinding.FragmentLeaderboardBinding
import com.example.localguide.main.MainApp


class LeaderboardFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentLeaderboardBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        //setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        _fragBinding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        activity?.title = "this is a temp title"
        fragBinding.textView2.text = "fragmet text fiview for leadboard set"
        //fragBinding.toolbar.inflateMenu(R.menu.menu_main)

        fragBinding.textView2.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_leaderboardFragment_to_myCommentsFragment2, null))



        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }






    override fun onResume() {
        super.onResume()

    }




    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaderboardFragment().apply {

            }
    }
}