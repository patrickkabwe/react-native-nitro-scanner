package com.nitroscanner

import androidx.annotation.Keep
import com.facebook.proguard.annotations.DoNotStrip
import com.facebook.react.uimanager.ThemedReactContext
import com.margelo.nitro.nitroscanner.HybridNitroScannerSpec
import com.margelo.nitro.nitroscanner.NitroScannerResult

@Keep
@DoNotStrip
class HybridNitroScanner(val reactContext: ThemedReactContext): HybridNitroScannerSpec() {
    // View
    override val view = NitroScannerView(reactContext)

    // Props
    override var enabled: Boolean? = null
        set(value) {
            field = value
            if (value == true) {
                view.startCamera()
            } else {
                view.stopCamera()
            }
        }
    override var vibrateOnScan: Boolean? = true
        set(value) {
            field = value
            view.vibrateOnScan = value ?: true
        }

    override var onScan: ((NitroScannerResult) -> Unit) = {  }
        set(value) {
            field = value
            view.onScan = value
        }

    override fun startScanning() {
        view.startCamera()
    }

    override fun stopScanning() {
        view.startCamera()
    }
}
