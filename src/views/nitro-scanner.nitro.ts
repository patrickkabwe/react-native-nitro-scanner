import type {
  HybridView,
  HybridViewProps,
  HybridViewMethods,
} from 'react-native-nitro-modules'

export interface NitroScannerProps extends HybridViewProps {
   isRed: boolean
}

export interface NitroScannerMethods extends HybridViewMethods {}

export type NitroScanner = HybridView<NitroScannerProps, NitroScannerMethods, { ios: 'swift', android: 'kotlin' }>