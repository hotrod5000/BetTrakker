package com.arliss.trakker.android.test;

import android.test.ActivityInstrumentationTestCase2;
import com.arliss.trakker.android.*;
import com.arliss.trakker.android.library.Library;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<HelloAndroidActivity> {

    public HelloAndroidActivityTest() {
        super(HelloAndroidActivity.class); 
    }

    public void testActivity() {
        HelloAndroidActivity activity = getActivity();
        assertNotNull(activity);
    }
    public void testLibrary() {
        String res = Library.getHelloMessage(getActivity());
        assertEquals("Hello trakker-android-project!", res);
    }
}

