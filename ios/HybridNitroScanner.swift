//
//  HybridNitroScanner.swift
//  Pods
//
//  Created by Patrick Kabwe on 7/6/2025.
//

import Foundation
import UIKit

class HybridNitroScanner : HybridNitroScannerSpec {
    private let scannerView = UIView()

    var view: UIView = { scannerView }

    // Props
    var enabled: Bool? {
        didSet {
            // enabled == true ? controller.start() : controller.stop()
        }
    }

    var onScan: ((NitroScannerResult) -> Void)?

    func startScanning() {
        // start scanning
    }

    func stopScanning() {
        // stop scanning
    }
}
