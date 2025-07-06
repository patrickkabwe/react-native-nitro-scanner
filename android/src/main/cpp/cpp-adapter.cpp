#include <jni.h>
#include "NitroScannerOnLoad.hpp"

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void*) {
  return margelo::nitro::nitroscanner::initialize(vm);
}
