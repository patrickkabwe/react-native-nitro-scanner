import { type HybridRef } from 'react-native-nitro-modules'
import type {
    NitroScannerMethods,
    NitroScannerProps,
} from './views/nitro-scanner.nitro'
export { NitroScanner } from './nitro-scanner'

export type NitroScannerRef = HybridRef<NitroScannerProps, NitroScannerMethods>
