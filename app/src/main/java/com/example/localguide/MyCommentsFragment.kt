package com.example.localguide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.localguide.databinding.FragmentLeaderboardBinding
import com.example.localguide.databinding.FragmentMyCommentsBinding
import com.example.localguide.main.MainApp



class MyCommentsFragment : Fragment() {
    lateinit var app: MainApp
    private var _fragBinding: FragmentMyCommentsBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentMyCommentsBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        //return inflater.inflate(R.layout.fragment_leaderboard, container, false)
        activity?.title = "this is a temp title"
        fragBinding.textView3.text = "my comments fragment"
        return root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyCommentsFragment().apply {

            }
    }
}