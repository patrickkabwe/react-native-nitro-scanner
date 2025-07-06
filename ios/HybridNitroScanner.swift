//
//  HybridNitroScanner.swift
//  NitroScanner
//
//  Created by Patrick Kabwe on 06/07/2025.
//

import Foundation
import UIKit
import AVFoundation

class HybridNitroScanner : HybridNitroScannerSpec, NitroScannerControllerDelegate {
    private let scannerView = NitroScannerView()
    private var controller: NitroScannerController!
    
    override init() {
        super.init()
        controller = NitroScannerController(previewView: scannerView, delegate: self)
    }
    
    var view: UIView { scannerView }
    
    var enabled: Bool? {
        didSet {
            enabled == true ? controller.start() : controller.stop()
        }
    }
    
    var onScan: ((NitroScannerResult) -> Void)?
    
    func didScan(type: AVMetadataObject.ObjectType, value: String) {
        do {
            let codeType = try controller.getCodeType(type: type)
            onScan?(NitroScannerResult(
                type: codeType,
                value: value)
            )
        } catch {
            print("Error parsing code type: \(error.localizedDescription)")
        }
    }
    
    func startScanning() {
        controller.start()
    }
    
    func stopScanning() {
        controller.stop()
    }
}
