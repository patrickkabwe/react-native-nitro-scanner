import React, { useRef } from 'react';
import { Button, StyleSheet, View } from 'react-native';
import { NitroScanner, NitroScannerRef } from 'react-native-nitro-scanner';

function App(): React.JSX.Element {
  const scannerRef = useRef<NitroScannerRef>(null);

  return (
    <View style={styles.container}>
      <NitroScanner
        ref={scannerRef}
        enabled={true}
        vibrateOnScan={true}
        onScan={result => {
          console.log(result);
        }}
        style={styles.view}
      />
      <View style={styles.buttonContainer}>
        <Button
          title="Start Scanning"
          onPress={() => {
            scannerRef.current?.startScanning();
          }}
        />
        <Button
          title="Stop Scanning"
          onPress={() => {
            scannerRef.current?.stopScanning();
          }}
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  view: {
    flex: 0.5,
    width: '100%',
  },
  buttonContainer: {
    flexDirection: 'row',
    gap: 10,
  },
});

export default App;
