package com.nitroscanner

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.margelo.nitro.nitroscanner.NitroScannerResult


fun Context.findLifecycleOwner(): LifecycleOwner? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is LifecycleOwner) return ctx
        ctx = ctx.baseContext
    }
    return null
}

class NitroScannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs), DefaultLifecycleObserver {

    private var cameraProvider: ProcessCameraProvider? = null
    private var previewView: PreviewView = PreviewView(context)
    private var analyzer: BarcodeScannerAnalyzer? = null

    var onScan: ((NitroScannerResult) -> Unit)? = null
    var vibrateOnScan: Boolean = false
    private var hasStarted = false

    init {
        addView(
            previewView,
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
    }

    fun startCamera(){
        if (hasStarted) return
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }

            val scanner = BarcodeScanning.getClient()
            analyzer = BarcodeScannerAnalyzer(scanner) { result ->
                if (vibrateOnScan) {
                    NitroScannerUtils.vibrate(context)
                }
                onScan?.invoke(result)
            }

            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build().also {
                    it.setAnalyzer(ContextCompat.getMainExecutor(context), analyzer!!)
                }

            cameraProvider?.unbindAll()

            val lifecycleOwner = context.findLifecycleOwner()

            if (lifecycleOwner != null) {
                cameraProvider?.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageAnalyzer
                )
            } else {
                Log.e("NitroScanner", "⚠️ No LifecycleOwner found. CameraX binding skipped.")
            }
            hasStarted = true

        }, ContextCompat.getMainExecutor(context))
    }

    fun stopCamera(){
        if (!hasStarted) return
        cameraProvider?.unbindAll()
    }

    private fun findLifecycleOwner(): LifecycleOwner? {
        var ctx = context
        while (ctx is ContextWrapper) {
            if (ctx is LifecycleOwner) return ctx
            ctx = ctx.baseContext
        }
        return null
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // Register to observe lifecycle when view is attached
        val lifecycleOwner = findLifecycleOwner()
        lifecycleOwner?.lifecycle?.addObserver(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        // Clean up observer to avoid leaks
        val lifecycleOwner = findLifecycleOwner()
        lifecycleOwner?.lifecycle?.removeObserver(this)

        stopCamera()
    }

    override fun onResume(owner: LifecycleOwner) {
        startCamera()
    }

    override fun onPause(owner: LifecycleOwner) {
        stopCamera()
    }
}