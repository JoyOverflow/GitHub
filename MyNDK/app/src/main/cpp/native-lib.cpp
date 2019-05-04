#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_pxgd_hyena_com_ndkdemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}