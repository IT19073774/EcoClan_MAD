package com.example.ecoclan_v2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    //IT19056494
    //Unit test for ecoCal
    private ecoCal ECOCAL;

    public void setECOCAL(){
        ECOCAL = new ecoCal();
    }

    //Individual Calculations according to the material
    @Test
    public void GlassCalculation_isCorrect(){
        double result = ecoCal.ecoCal_sum(100,120,200,145,60,0,0,0,1,0);
        assertEquals(145.0,result,0.001);
    }

    @Test
    public void PlasticCalculation_isCorrect(){
        double result = ecoCal.ecoCal_sum(100,120,200,145,60,1,0,0,0,0);
        assertEquals(100.0,result,0.001);
    }

    @Test
    public void ClothCalculation_isCorrect(){
        double result = ecoCal.ecoCal_sum(100,120,200,145,60,0,1,0,0,0);
        assertEquals(120.0,result,0.001);
    }

    @Test
    public void MetalCalculation_isCorrect(){
        double result = ecoCal.ecoCal_sum(100,120,200,145,60,0,0,1,0,0);
        assertEquals(200.0,result,0.001);
    }

    @Test
    public void PaperCalculation_isCorrect(){
        double result = ecoCal.ecoCal_sum(100,120,200,145,60,0,0,0,0,1);
        assertEquals(60.0,result,0.001);
    }

    //If user inputs all zeros
    @Test
    public void AllZeroCalculation_isCorrect(){
        double result = ecoCal.ecoCal_sum(100,120,200,145,60,0,0,0,0,0);
        assertEquals(00.0,result,0.001);
    }

    //If user inputs multiple inputs
    @Test
    public void MultipleInputsCalculation_isCorrect(){
        double result = ecoCal.ecoCal_sum(100,120,200,145,60,10,1,32,21,2);
        assertEquals(10685.0,result,0.001);
    }

    //If user inputs multiple inputs
    @Test
    public void DecimalsInputsCalculation_isCorrect(){
        double result = ecoCal.ecoCal_sum(100,120,200,145,60,0.2,10.2,2.8,0,2.8);
        assertEquals(1971.99,result,0.01);
    }


}