# react-native-nitro-scanner

A high-performance React Native barcode and QR code scanner built with Nitro Modules for optimal native performance.

[![Version](https://img.shields.io/npm/v/react-native-nitro-scanner.svg)](https://www.npmjs.com/package/react-native-nitro-scanner)
[![Downloads](https://img.shields.io/npm/dm/react-native-nitro-scanner.svg)](https://www.npmjs.com/package/react-native-nitro-scanner)
[![License](https://img.shields.io/npm/l/react-native-nitro-scanner.svg)](https://github.com/patrickkabwe/react-native-nitro-scanner/blob/main/LICENSE)

## Features

- 📱 **Cross-platform**: Works on both iOS and Android
- ⚡ **High Performance**: Built with Nitro Modules for native performance
- 🔍 **Multiple Formats**: Supports both barcodes and QR codes
- 🎯 **Simple API**: Easy-to-use component with intuitive props and methods
- 🔧 **Flexible Control**: Start and stop scanning programmatically
- 📋 **Type Safety**: Full TypeScript support with comprehensive types

## Requirements

- React Native v0.76.0 or higher
- Node 18.0.0 or higher

> [!IMPORTANT]  
> To Support `Nitro Views` you need to install React Native version v0.78.0 or higher.

## Installation

```bash
bun add react-native-nitro-scanner react-native-nitro-modules
```

```bash
npm install react-native-nitro-scanner react-native-nitro-modules
```

```bash
yarn add react-native-nitro-scanner react-native-nitro-modules
```

## Usage

### Basic Example

```tsx
import React, { useState } from 'react'
import { View, Text, StyleSheet } from 'react-native'
import { NitroScanner } from 'react-native-nitro-scanner'

export default function App() {
  const [scannedData, setScannedData] = useState<string | null>(null)

  const handleScan = (result: NitroScannerResult) => {
    console.log('Scanned:', result.value)
    setScannedData(result.value)
  }

  return (
    <View style={styles.container}>
      <NitroScanner style={styles.scanner} enabled={true} onScan={handleScan} />
      {scannedData && <Text style={styles.result}>Scanned: {scannedData}</Text>}
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  scanner: {
    width: 300,
    height: 300,
  },
  result: {
    marginTop: 20,
    fontSize: 16,
    fontWeight: 'bold',
  },
})
```

### Advanced Example with Manual Control

```tsx
import React, { useRef, useState } from 'react'
import { View, Button, StyleSheet } from 'react-native'
import { NitroScanner, NitroScannerRef } from 'react-native-nitro-scanner'

export default function AdvancedScanner() {
  const scannerRef = useRef<NitroScannerRef>(null)
  const [isScanning, setIsScanning] = useState(false)

  const startScanning = () => {
    scannerRef.current?.startScanning()
    setIsScanning(true)
  }

  const stopScanning = () => {
    scannerRef.current?.stopScanning()
    setIsScanning(false)
  }

  const handleScan = (result: NitroScannerResult) => {
    console.log(`Scanned ${result.type}: ${result.value}`)
    // Automatically stop scanning after successful scan
    stopScanning()
  }

  return (
    <View style={styles.container}>
      <NitroScanner
        ref={scannerRef}
        style={styles.scanner}
        enabled={isScanning}
        onScan={handleScan}
      />
      <View style={styles.controls}>
        <Button
          title="Start Scanning"
          onPress={startScanning}
          disabled={isScanning}
        />
        <Button
          title="Stop Scanning"
          onPress={stopScanning}
          disabled={!isScanning}
        />
      </View>
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  scanner: {
    width: 300,
    height: 300,
  },
  controls: {
    flexDirection: 'row',
    gap: 10,
    marginTop: 20,
  },
})
```

## API Reference

### NitroScanner Component

The main scanner component that renders a native camera view for scanning barcodes and QR codes.

#### Props

| Prop      | Type                                   | Required | Description                                                  |
| --------- | -------------------------------------- | -------- | ------------------------------------------------------------ |
| `enabled` | `boolean`                              | Yes      | Whether the scanner is actively scanning                     |
| `onScan`  | `(result: NitroScannerResult) => void` | Yes      | Callback function called when a code is successfully scanned |
| `style`   | `StyleProp<ViewStyle>`                 | No       | Style object to customize the scanner view                   |

#### Methods

Access these methods using a ref to the NitroScanner component:

| Method            | Description                                 |
| ----------------- | ------------------------------------------- |
| `startScanning()` | Programmatically start the scanning process |
| `stopScanning()`  | Programmatically stop the scanning process  |

#### Example with Ref

```tsx
import { useRef } from 'react'
import { NitroScanner, NitroScannerRef } from 'react-native-nitro-scanner'

function MyComponent() {
  const scannerRef = useRef<NitroScannerRef>(null)

  const handleStartScan = () => {
    scannerRef.current?.startScanning()
  }

  const handleStopScan = () => {
    scannerRef.current?.stopScanning()
  }

  return (
    <NitroScanner
      ref={scannerRef}
      enabled={true}
      onScan={(result) => console.log(result)}
    />
  )
}
```

## Types

### NitroScannerType

Enumeration of supported scanner types:

```typescript
enum NitroScannerType {
  BARCODE,
  QR_CODE,
}
```

### NitroScannerResult

The result object returned when a code is successfully scanned:

```typescript
type NitroScannerResult = {
  type: NitroScannerType // The type of code that was scanned
  value: string // The decoded value of the scanned code
}
```

## Platform Support

- ✅ **iOS**: Native Swift implementation
- ✅ **Android**: Native Kotlin implementation

## Performance

This library uses Nitro Modules for optimal performance:

- Direct native method calls without bridge overhead
- Efficient memory management
- Smooth real-time scanning experience

## Permissions

### iOS

Add camera permission to your `Info.plist`:

```xml
<key>NSCameraUsageDescription</key>
<string>This app needs access to camera to scan barcodes and QR codes</string>
```

### Android

Add camera permission to your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.CAMERA" />
```

## Troubleshooting

### Common Issues

1. **Scanner not visible**: Ensure you provide proper dimensions via the `style` prop
2. **Permission denied**: Make sure camera permissions are properly requested and granted
3. **Scanner not responding**: Check that `enabled` prop is set to `true`

### Debug Tips

```tsx
// Add logging to track scanner state
const handleScan = (result: NitroScannerResult) => {
  console.log('Scanner result:', {
    type: result.type,
    value: result.value,
    timestamp: new Date().toISOString(),
  })
}
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

### Development

1. Clone the repository
2. Install dependencies: `bun install`
3. Run the example: `cd example && bun install && bun run ios`

## Credits

Bootstrapped with [create-nitro-module](https://github.com/patrickkabwe/create-nitro-module).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
