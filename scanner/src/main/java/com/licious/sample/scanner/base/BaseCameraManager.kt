package com.licious.sample.scanner.base

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * This class is the base class to handle all the camera functionality .
 */
abstract class BaseCameraManager(
    private val owner: LifecycleOwner,
    private val context: Context,
    private val viewPreview: PreviewView,
    private var lensFacing: Int,
    private val showHideFlashIcon: (show: Int) -> Unit
) : DefaultLifecycleObserver {
    private var imgCapture: ImageCapture? = null
    private lateinit var cameraProvider: ProcessCameraProvider
    private var stopped: Boolean = false
    protected var camera: Camera?= null
    private var flashMode: Int = ImageCapture.FLASH_MODE_OFF

    protected val cameraExecutor: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    init {
        owner.lifecycle.addObserver(this)
        startCamera()
    }

    /**
     * Initialise Camera and call this method again when switch camera is clicked or you want to reinitialise camera.
     */
    private fun startCamera(isSwitchButtonClicked: Boolean = false) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            controlWhichCameraToDisplay(isSwitchButtonClicked)
            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(context))
    }

    /**
     * Return front or back camera based on which was open last.
     */
    private fun controlWhichCameraToDisplay(isSwitchButtonClicked: Boolean): Int {
        if (isSwitchButtonClicked) {
            lensFacing =
                if (lensFacing == CameraSelector.LENS_FACING_FRONT) CameraSelector.LENS_FACING_BACK
                else CameraSelector.LENS_FACING_FRONT
        } else lensFacing
        showHideFlashIcon(lensFacing)
        return lensFacing
    }

    /**
     * Bind Camera provider to lifecycler owner.
     */
    private fun bindCameraUseCases() {
        val cameraSelector = getCameraSelector()
        val previewView = getPreviewUseCase()
        imgCapture = getImageCapture()
        cameraProvider.unbindAll()
        try {
            imgCapture?.let {
                bindToLifecycle(cameraProvider, owner, cameraSelector, previewView, it)
            }
            previewView.setSurfaceProvider(viewPreview.surfaceProvider)
        } catch (exc: Exception) {
            Log.e(ContentValues.TAG, "Use case binding failed $exc")
        }
    }

    /**
     * unbind camera provider.
     */
    override fun onPause(owner: LifecycleOwner) {
        if (this::cameraProvider.isInitialized) {
            cameraProvider.unbindAll()
            stopped = true
            super.onPause(owner)
        }
    }

    /**
     * bind camera use case again.
     */
    override fun onResume(owner: LifecycleOwner) {
        if (stopped) {
            bindCameraUseCases()
            stopped = false
        }
        super.onResume(owner)
    }

    /**
     * Shutdown camera executor.
     */
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        cameraExecutor.shutdown()
    }

    /**
     * This is a abstract method that will be implemented in the barcode manager class.
     */
    protected abstract fun bindToLifecycle(
        cameraProvider: ProcessCameraProvider,
        owner: LifecycleOwner,
        cameraSelector: CameraSelector,
        previewView: Preview,
        imageCapture: ImageCapture
    )

    /**
     * Initialise Camera Selector.
     */
    private fun getCameraSelector(): CameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    /**
     * Initialise Preview Builder.
     */
    private fun getPreviewUseCase(): Preview = Preview.Builder()
        .build()

    /**
     * Initialise Image capture builder.
     */
    private fun getImageCapture(): ImageCapture = ImageCapture.Builder().setFlashMode(flashMode).build()

    fun enableFlashForQrCode(onFlashMode: Boolean) {
        camera?.cameraControl?.enableTorch(onFlashMode)
    }

    fun enableFlashForCamera(flashStatus: Boolean) {
        flashMode = if (flashStatus)
            ImageCapture.FLASH_MODE_ON
        else
            ImageCapture.FLASH_MODE_OFF
        // Re-bind use cases to include changes
        imgCapture?.flashMode = flashMode
    }
}