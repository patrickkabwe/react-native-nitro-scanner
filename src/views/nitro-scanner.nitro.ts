import type {
    HybridView,
    HybridViewMethods,
    HybridViewProps,
} from 'react-native-nitro-modules'

enum NitroScannerType {
    BARCODE,
    QR_CODE,
}

type NitroScannerResult = {
    type: NitroScannerType
    value: string
}

export interface NitroScannerProps extends HybridViewProps {
    enabled?: boolean
    vibrateOnScan?: boolean
    onScan?: (result: NitroScannerResult) => void
}

export interface NitroScannerMethods extends HybridViewMethods {
    startScanning(): void
    stopScanning(): void
}

export type NitroScanner = HybridView<NitroScannerProps, NitroScannerMethods, { ios: 'swift', android: 'kotlin' }>