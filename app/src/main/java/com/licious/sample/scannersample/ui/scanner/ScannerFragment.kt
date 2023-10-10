package com.licious.sample.scannersample.ui.scanner

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.licious.sample.design.ui.base.BaseFragment
import com.licious.sample.scannersample.databinding.FragmentScannerBinding
import com.licious.sample.scannersample.ui.scanner.viewmodels.ScannerViewModel
import com.licious.sample.scanner.ScannerViewState
import dagger.hilt.android.AndroidEntryPoint

/**
 *  This Class will scan all qrcode and display it.
 */
@AndroidEntryPoint
class ScannerFragment : BaseFragment<FragmentScannerBinding>() {
    private val qrCodeViewModel: ScannerViewModel by viewModels()

    private val vibrator: Vibrator by lazy {
        requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun getLogTag(): String = TAG

    override fun getViewBinding(): FragmentScannerBinding =
        FragmentScannerBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        startAnimation()
    }

    override fun onDestroyView() {
        vibrator.cancel()
        super.onDestroyView()
    }

    /**
     *  Initialise views and and handle click listeners here
     */
    private fun initView() {
        qrCodeViewModel.startCamera(viewLifecycleOwner, requireContext(), binding.previewView, ::onResult)
    }

    /**
     * Success callback and error callback when barcode is successfully scanned. This method is also called while manually enter barcode
     */
    private fun onResult(state: ScannerViewState, result: String?) {
        when(state)
        {
            ScannerViewState.Success -> {
                vibrateOnScan()
                Toast.makeText(requireContext(), "result=${result}", Toast.LENGTH_SHORT).show()
            }
             ScannerViewState.Error -> {
                Toast.makeText(requireContext(), "error =${result}", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(requireContext(), "error =${result}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     *  Animation for the red bar.
     */
    private fun startAnimation() {
        val animation: Animation = AnimationUtils.loadAnimation(context, com.licious.sample.scanner.R.anim.barcode_animator)
        binding.llAnimation.startAnimation(animation)
    }

    /**
     *  Vibration mobile on Scan successful.
     */
    private fun vibrateOnScan() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        VIBRATE_DURATION,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(VIBRATE_DURATION)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "QrCodeReaderFragment"
        private const val VIBRATE_DURATION = 200L
    }
}