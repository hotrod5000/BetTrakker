package com.arliss.trakker.pojo.library.test;

import com.arliss.trakker.pojo.library.Class1;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: rodney
 * Date: 9/10/13
 * Time: 7:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Class1Test extends TestCase {

    public void test1(){
        Class1 sut = new Class1();
        assertEquals(42, sut.getTheNumber());
    }
}
