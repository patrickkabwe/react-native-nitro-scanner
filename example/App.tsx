import React, { useRef } from 'react';
import { StyleSheet, View } from 'react-native';
import { NitroScanner, NitroScannerRef } from 'react-native-nitro-scanner';

function App(): React.JSX.Element {
  const scannerRef = useRef<NitroScannerRef>(null);

  return (
    <View style={styles.container}>
      <NitroScanner
        ref={scannerRef}
        enabled={true}
        onScan={result => {
          console.log(result);
        }}
        style={styles.view}
      />
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
    flex: 1,
  },
});

export default App;
