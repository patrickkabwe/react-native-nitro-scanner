package com.nitroscanner

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.margelo.nitro.nitroscanner.NitroScannerResult
import com.margelo.nitro.nitroscanner.NitroScannerType

class BarcodeScannerAnalyzer(
    private val scanner: BarcodeScanner,
    private val onScanned: (NitroScannerResult) -> Unit
) : ImageAnalysis.Analyzer {

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image ?: return imageProxy.close()
        val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        scanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                barcodes.firstOrNull()?.let {
                    onScanned(NitroScannerResult(
                        type = getCodeType(it.format),
                        value = it.rawValue ?: ""
                    ))
                }
            }
            .addOnFailureListener {
                // Handle errors
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }

    private fun getCodeType(type: Int): NitroScannerType {
        when (type) {
            Barcode.FORMAT_QR_CODE -> return NitroScannerType.QR_CODE
            else -> throw Error("Unsupported barcode format")
        }
    }
}