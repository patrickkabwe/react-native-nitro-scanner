//
//  NitroScannerController.swift
//  NitroScanner
//
//  Created by Patrick Kabwe on 06/07/2025.
//

import AVFoundation
import NitroModules

final class NitroScannerController: NSObject {
    private let session = AVCaptureSession()
    private weak var previewView: NitroScannerView?
    var vibrateOnScan: Bool = true
    var onScan: ((NitroScannerResult) -> Void) = { _ in }

    init(previewView: NitroScannerView) {
        self.previewView = previewView
        super.init()
        configureSession()
    }

    private func configureSession() {
        guard let device = AVCaptureDevice.default(for: .video) else {
            print("No video device found")
            return
        }

        do {
            let input = try AVCaptureDeviceInput(device: device)
            if session.canAddInput(input) {
                session.addInput(input)
            }

            let output = AVCaptureMetadataOutput()
            if session.canAddOutput(output) {
                session.addOutput(output)
                output.setMetadataObjectsDelegate(self, queue: .main)
                output.metadataObjectTypes = [.qr]
            }

            let previewLayer = AVCaptureVideoPreviewLayer(session: session)
            DispatchQueue.main.async {
                self.previewView?.previewLayer = previewLayer
            }

        } catch {
            print("Camera setup error: \(error)")
        }
    }

    func start() {
        guard !session.isRunning else { return }
        DispatchQueue.global(qos: .userInitiated).async {
            self.session.startRunning()
        }
    }

    func stop() {
        guard session.isRunning else { return }
        DispatchQueue.global(qos: .userInitiated).async {
            self.session.stopRunning()
        }
    }
    
    func getCodeType(type: AVMetadataObject.ObjectType) throws -> NitroScannerType {
        switch type {
            case .qr:
            return NitroScannerType.qrCode
        default:
            throw RuntimeError.error(withMessage: "Unsupported barcode type")
        }
    }
}

// MARK: - Metadata Delegate

extension NitroScannerController: AVCaptureMetadataOutputObjectsDelegate {
    func metadataOutput(_ output: AVCaptureMetadataOutput,
                        didOutput metadataObjects: [AVMetadataObject],
                        from connection: AVCaptureConnection) {

        guard let object = metadataObjects.first as? AVMetadataMachineReadableCodeObject,
              let value = object.stringValue else {
            return
        }
        if (vibrateOnScan) {
            AudioServicesPlaySystemSound(SystemSoundID(kSystemSoundID_Vibrate))
        }
        do {
            let codeType = try getCodeType(type: object.type)
            onScan(NitroScannerResult(
                type: codeType,
                value: value)
            )
        } catch {
            print("Error parsing code type: \(error.localizedDescription)")
        }
    }
}
