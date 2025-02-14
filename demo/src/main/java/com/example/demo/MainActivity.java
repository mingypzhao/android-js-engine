package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jsengine.Bridge;
import com.jsengine.JSCallback;
import com.jsengine.JavaScriptRuntime;
import com.jsengine.hermes.HermesRuntime;
import com.jsengine.jsc.JSCRuntime;
import com.jsengine.v8.V8Runtime;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "js-engine";

    ViewGroup hermesViewGroup;
    ViewGroup jscViewGroup;
    ViewGroup v8ViewGroup;

    Bridge hermesBridge;
    Bridge jscBridge;
    Bridge v8Bridge;

    JavaScriptRuntime hermesRuntime;
    JavaScriptRuntime jscRuntime;
    JavaScriptRuntime v8Runtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hermesViewGroup = findViewById(R.id.hermes);
        jscViewGroup = findViewById(R.id.jsc);
        v8ViewGroup = findViewById(R.id.v8);
        hermesBridge = new Bridge();
        hermesRuntime  = new HermesRuntime();
        hermesBridge.initialize(hermesRuntime);
        control(hermesViewGroup, hermesBridge, "hermes");
        jscBridge = new Bridge();
        jscRuntime = new JSCRuntime();
        jscBridge.initialize(jscRuntime);
        control(jscViewGroup, jscBridge, "jsc");
        v8Bridge = new Bridge();
        v8Runtime = new V8Runtime();
        v8Bridge.initialize(v8Runtime);
        control(v8ViewGroup, v8Bridge, "v8");
    }

    @Override
    public void finish(){
        super.finish();
        hermesBridge.destroy();
        jscBridge.destroy();
        v8Bridge.destroy();
        hermesRuntime.close();
        jscRuntime.close();
        v8Runtime.close();
    }

    private void control(ViewGroup viewGroup, Bridge bridge, String txt) {
        TextView tv = viewGroup.findViewById(R.id.groupText);
        tv.setText(txt);
        Button loadScriptFromStringBtn = viewGroup.findViewById(R.id.loadScriptFromString);
        loadScriptFromStringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String js = loadJSTemplateFromAssets();
                bridge.loadScriptFromString(js);
            }
        });
        Button loadScriptFromAssetsBtn = viewGroup.findViewById(R.id.loadScriptFromAssets);
        loadScriptFromAssetsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bridge.loadScriptFromAssets(getAssets(), "assets://app.js");
            }
        });
        Button callJSFunctionSyncBtn = viewGroup.findViewById(R.id.callJSFunctionSync);
        callJSFunctionSyncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Object> args = new ArrayList<>();
                args.add("arg1");
                args.add("arg2中文");
                Object result = bridge.callJSFunctionSync("myfunctionSync", args);
                if (result instanceof Map) {
                    Map map =  (Map) result;
                    for (Object key : map.keySet()) {
                        Object value = map.get(key);
                        Log.d(TAG, "key:" + key + ",value:" + value);
                    }
                }
            }
        });
        Button callJsAddFunc = viewGroup.findViewById(R.id.callJsAddFunc);
        callJsAddFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Object> args = new ArrayList<>();
                args.add(1);
                args.add(2);
                Object result = bridge.callJSFunctionSync("intAdd", args);
                if(result instanceof Integer){
                    Toast.makeText(MainActivity.this, "result = " + result, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "wft? " + result, Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button callJSFunctionBtn = viewGroup.findViewById(R.id.callJSFunction);
        callJSFunctionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> args = new ArrayList<>();
                args.add("1");
                args.add("2");
                bridge.callJSFunction("myfunction", args, new JSCallback() {
                    @Override
                    public void invoke(Object object) {
                        if (object instanceof String) {
                            Log.d(TAG, object.toString());
                        } else if (object instanceof Map) {
                            Log.d(TAG, ((Map) object).keySet().toString());
                        }
                    }
                });
            }
        });

    }

    private String loadJSTemplateFromAssets() {
        String js = null;
        try {
            InputStream is = getAssets().open("app.js");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            js = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return js;
    }
}