//
//  HybridNitroScanner.swift
//  NitroScanner
//
//  Created by Patrick Kabwe on 06/07/2025.
//

import Foundation
import UIKit
import AVFoundation

class HybridNitroScanner : HybridNitroScannerSpec {
    private let scannerView = NitroScannerView()
    private var controller: NitroScannerController!
    
    override init() {
        super.init()
        controller = NitroScannerController(previewView: scannerView)
    }
    
    var view: UIView { scannerView }
    
    var enabled: Bool? = true {
        didSet {
            enabled == true ? controller.start() : controller.stop()
        }
    }
    
    var vibrateOnScan: Bool? {
        didSet {
            controller.vibrateOnScan = vibrateOnScan ?? false
        }
    }
    
    var onScan: ((NitroScannerResult) -> Void) = { _ in } {
        didSet {
            controller.onScan = onScan
        }
    }
    
    func startScanning() {
        controller.start()
    }
    
    func stopScanning() {
        controller.stop()
    }
}
