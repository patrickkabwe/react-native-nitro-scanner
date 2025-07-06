//
//  NitroScannerView.swift
//  NitroScanner
//
//  Created by Patrick Kabwe on 06/07/2025.
//

import UIKit
import AVFoundation

final class NitroScannerView: UIView {
    var previewLayer: AVCaptureVideoPreviewLayer? {
        didSet {
            if let layer = oldValue {
                layer.removeFromSuperlayer()
            }
            if let newLayer = previewLayer {
                newLayer.videoGravity = .resizeAspectFill
                newLayer.frame = bounds
                self.layer.insertSublayer(newLayer, at: 0)
            }
        }
    }

    override func layoutSubviews() {
        super.layoutSubviews()
        previewLayer?.frame = bounds
    }
}
