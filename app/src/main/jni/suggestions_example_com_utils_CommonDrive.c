/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class suggestions_example_com_utils_CommonDrive */
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>

#define LOG_TAG "JNICIT-Hw"
#ifndef _Included_suggestions_example_com_utils_CommonDrive
#define _Included_suggestions_example_com_utils_CommonDrive
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     suggestions_example_com_utils_CommonDrive
 * Method:    getHWVersion
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_suggestions_example_com_utils_CommonDrive_getHWVersion
  (JNIEnv *env, jobject obj){

    FILE *handle = fopen("/sys/firmware/devicetree/base/cm-pcba-id", "rb");
    int pcba_id = 0;
    int iret = 0;
    char hwver[20] = {0};

    if (handle == -1) {
    } else {
        iret = fread(&pcba_id, sizeof(int), 1, handle);

    }
    fclose(handle);
    pcba_id = (pcba_id & 0xff000000) >> 24;

    switch (pcba_id) {
        case 0:
            sprintf(hwver, "%s", "winboard");
            break;
        case 1:
            sprintf(hwver, "%s", "SP1");
            break;
        case 2:
            sprintf(hwver, "%s", "SP1.1");
            break;
        case 3:
            sprintf(hwver, "%s", "AP1");
            break;
        case 4:
            sprintf(hwver, "%s", "PQ");
            break;
        case 5:
            sprintf(hwver, "%s", "MS");
            break;
        default:
            sprintf(hwver, "%s", "XXXX");
    }
    //sprintf(hwver, "%d", pcba_id);

    return (*env)->NewStringUTF(env, hwver);
};

/*
 * Class:     suggestions_example_com_utils_CommonDrive
 * Method:    getHWVersionOld
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_suggestions_example_com_utils_CommonDrive_getHWVersionOld
  (JNIEnv *env, jobject obj, jstring str){


    const char *vres = (*env)->GetStringUTFChars(env, str, 0);
    // int handle = open("/sys/devices/soc0/platform_version", O_RDONLY);
    FILE *handle = fopen(vres, "rb");
    int pcba_id = 0;
    int iret = 0;
    char hwver[20] = {0};

    if (handle == -1) {
    } else {
        iret = fread(&pcba_id, sizeof(int), 1, handle);
    }
    fclose(handle);
    pcba_id = (pcba_id & 0xff000000) >> 24;

    switch (pcba_id) {
        case 0:
            sprintf(hwver, "%s", "winboard");
            break;
        case 1:
            sprintf(hwver, "%s", "SP1");
            break;
        case 2:
            sprintf(hwver, "%s", "SP1.1");
            break;
        case 3:
            sprintf(hwver, "%s", "AP1");
            break;
        case 4:
            sprintf(hwver, "%s", "PQ");
            break;
        case 5:
            sprintf(hwver, "%s", "MS");
            break;
        default:
            sprintf(hwver, "%s", "XXXX");
    }
    //sprintf(hwver, "%d", pcba_id);

    return (*env)->NewStringUTF(env, hwver);
};

#ifdef __cplusplus
}
#endif
#endif
