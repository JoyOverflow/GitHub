#include <jni.h>

extern "C" JNIEXPORT jstring JNICALL
Java_pxgd_hyena_com_ndkdemo_MainActivity_sayHello(JNIEnv *env, jobject /* this */) {
    return env->NewStringUTF("Hello NDK From C");
}

jlong fibN(jlong n) {
    if(n<=0) return 0;
    if(n==1) return 1;
    return fibN(n-1) + fibN(n-2);
}

extern "C" JNIEXPORT jlong JNICALL Java_pxgd_hyena_com_ndkdemo_MainActivity_myFibN
        (JNIEnv *env, jobject clazz, jlong  n) {
    return fibN(n);
}


jlong fibNI(jlong n) {
    jlong previous = -1;
    jlong result = 1;
    jlong i=0;
    int sum=0;
    for (i = 0; i <= n; i++) {
        sum = result + previous;
        previous = result;
        result = sum;
    }
    return result;
}
extern "C" JNIEXPORT jlong JNICALL Java_pxgd_hyena_com_ndkdemo_MainActivity_myFibNI
        (JNIEnv *env, jobject instance, jlong n) {
    return fibNI(n);
}



extern "C"
JNIEXPORT jlong JNICALL
Java_pxgd_hyena_com_ndkdemo_NdkActivity_myFibN(JNIEnv *env, jobject instance, jlong n) {
    return fibN(n);
}
extern "C"
JNIEXPORT jlong JNICALL
Java_pxgd_hyena_com_ndkdemo_NdkActivity_myFibNI(JNIEnv *env, jobject instance, jlong n) {
    return fibNI(n);
}




