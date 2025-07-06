package com.nitroscanner

import android.view.View
import androidx.annotation.Keep
import com.facebook.proguard.annotations.DoNotStrip
import com.facebook.react.uimanager.ThemedReactContext
import com.margelo.nitro.nitroscanner.HybridNitroScannerSpec
import com.margelo.nitro.nitroscanner.NitroScannerResult

@Keep
@DoNotStrip
class HybridNitroScanner(val context: ThemedReactContext): HybridNitroScannerSpec() {
    // View
    override val view: View = View(context)

    // Props
    override var enabled: Boolean? = null

    override var onScan: ((NitroScannerResult) -> Unit)? = null

    override fun startScanning() {
        // start scanning
    }

    override fun stopScanning() {
        // stop scanning
    }
}
