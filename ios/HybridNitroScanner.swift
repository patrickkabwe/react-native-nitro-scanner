//
//  HybridNitroScanner.swift
//  Pods
//
//  Created by Patrick Kabwe on 7/6/2025.
//

import Foundation
import UIKit

class HybridNitroScanner : HybridNitroScannerSpec {
  // UIView
  var view: UIView = UIView()

  // Props
  var isRed: Bool = false {
    didSet {
      view.backgroundColor = isRed ? .red : .black
    }
  }
}
