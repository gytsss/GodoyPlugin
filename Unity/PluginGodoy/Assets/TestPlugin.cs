using System;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class TestPlugin : MonoBehaviour
{
    private const string packageName = "com.godoy.mylibrary";
    const string className = packageName + ".GodoyLogger";
    
    #if UNITY_ANDROID 
    
    AndroidJavaClass _pluginClass;
    AndroidJavaObject _pluginInstance;

    public TextMeshProUGUI label;

    private void Start()
    {
        if (Application.platform == RuntimePlatform.Android)
        {
            _pluginClass = new AndroidJavaClass(className);
            _pluginInstance = _pluginClass.CallStatic<AndroidJavaObject>("getInstance");
        }
    }

    public void RunPlugin()
    {
        Debug.Log("RunPlugin()");
        
        if (Application.platform == RuntimePlatform.Android)
        {
            label.text = _pluginInstance.Call<string>("getLogtag");
        }

    }
    #endif  
}
