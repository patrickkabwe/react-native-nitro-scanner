import { getHostComponent, type HybridRef } from 'react-native-nitro-modules'
import NitroScannerConfig from '../nitrogen/generated/shared/json/NitroScannerConfig.json'
import type {
  NitroScannerProps,
  NitroScannerMethods,
} from './views/nitro-scanner.nitro'


export const NitroScanner = getHostComponent<NitroScannerProps, NitroScannerMethods>(
  'NitroScanner',
  () => NitroScannerConfig
)

export type NitroScannerRef = HybridRef<NitroScannerProps, NitroScannerMethods>
