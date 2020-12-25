package com.systemvx.androiddevtest.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.ToggleButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.FragmentSOptionsBinding

class SOptionsFragment(private val viewModel: SOptionViewModel) : Fragment() {

    private lateinit var mBinding: FragmentSOptionsBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_s_options, container, false)
        mBinding.priceSelectBar.setOnSeekBarChangeListener(SeekBarEvent(::noticeChange))
        return mBinding.root
    }

    private fun noticeChange(progress: Int) {
        mBinding.priceValueIndicator.text = "$progress"
        viewModel.priceMin = progress.toDouble()
    }

    private fun testTags() {
        val tags = HashMap<String, Int>()
        tags["跑腿"] = 1

        for (tag in tags) {
            var button: ToggleButton = ToggleButton(this.context)
            button.textOff = tag.key
            button.textOn = tag.key
            button.background = this.requireActivity().getDrawable(R.drawable.style_round_corner_bg)
            button.tag = tag.value
            viewModel.typeTagList.add(button)
            mBinding.typeHolder.addView(button)
        }
    }

    companion object {
        //监听器
        class SeekBarEvent(val method: (Int) -> Unit) : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                this.method(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        }
    }
}