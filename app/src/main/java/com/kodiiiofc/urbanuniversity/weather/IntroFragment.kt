package com.kodiiiofc.urbanuniversity.weather

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class IntroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intro = arguments?.getSerializable("intro") as Intro
        view.findViewById<ImageView>(R.id.iv_intro).setImageResource(intro.imageResource)
        view.findViewById<TextView>(R.id.tv_intro).text = intro.text
        val startBTN = view.findViewById<Button>(R.id.btn_start)
        startBTN.visibility = if (intro.isLastPage) View.VISIBLE else View.GONE
        startBTN.setOnClickListener {
            startActivity(Intent(context,MainActivity::class.java))
            activity?.finish()
        }

    }

}