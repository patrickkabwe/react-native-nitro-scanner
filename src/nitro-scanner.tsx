import React from 'react'
import type { StyleProp, ViewStyle } from 'react-native'
import { getHostComponent, type HybridRef } from 'react-native-nitro-modules'
import NitroScannerConfig from '../nitrogen/generated/shared/json/NitroScannerConfig.json'
import type {
  NitroScannerMethods,
  NitroScannerProps,
} from './views/nitro-scanner.nitro'

export const HybridNitroScanner = getHostComponent<
  NitroScannerProps,
  NitroScannerMethods
>('NitroScanner', () => NitroScannerConfig)

export const NitroScanner = (
  props: NitroScannerProps & {
    ref: React.RefObject<NitroScannerRef | null>
    style: StyleProp<ViewStyle>
  }
) => {
  return (
    <HybridNitroScanner
      hybridRef={{
        f: (ref) => {
          props.ref.current = ref
        },
      }}
      onScan={{
        f: props.onScan,
      }}
      style={props.style}
      enabled={props.enabled}
      vibrateOnScan={props.vibrateOnScan}
    />
  )
}

export type NitroScannerRef = HybridRef<NitroScannerProps, NitroScannerMethods>
