package com.example.ecoclan_v2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    //IT19073774
    //Unit test for Customer Estimated Cost View
    private ResourceDetailActivity RDActivity;
    public void setRDActivity(){
        RDActivity = new ResourceDetailActivity();
    }

    //If both parameter Zero
    @Test
    public void AllZerosCalculation_isCorrect(){
        double result = RDActivity.calEstimateCost(0.0,0.0);
        assertEquals(0.0,result,0.001);
    }

    //If User have input Estimate weight as Zero
    @Test
    public void EweightZeroCalculation_isCorrect(){
        double result = RDActivity.calEstimateCost(200.0,0.0);
        assertEquals(0.0,result,0.001);
    }

    //If User have input a one digit whole number estimate weight
    @Test
    public void EweightOneDigitWholeCalculation_isCorrect(){
        double result = RDActivity.calEstimateCost(200.0,3.0);
        assertEquals(600.0,result,0.001);
    }

    //If User have input a two digit whole number estimate weight
    @Test
    public void EweightTwoDigitWholeCalculation_isCorrect(){
        double result = RDActivity.calEstimateCost(200.0,12.0);
        assertEquals(2400.0,result,0.001);
    }

    //If User have input a estimate weight with decimal points
    @Test
    public void EweightDecimalPointCalculation_isCorrect(){
        double result = RDActivity.calEstimateCost(200.0,8.35);
        assertEquals(1670.0,result,0.001);
    }



    //IT19071480
    //Unit test for Estimating cost of request for Recycler
    private RecyclerRequestActivity recyclerRequestActivity;
    public void setRecyclerRequestActivity(){
        recyclerRequestActivity = new RecyclerRequestActivity();
    }

    //Both No chosen - default values for category id paper of rate 65 and weight is 0.00
    @Test
    public void WeightZero_isCorrect(){
        double result = recyclerRequestActivity.getEstimate(0.00, 65.0);
        assertEquals(0.0,result,0.001);
    }

    //Category Chosen (Assuming Glass is chosen) and Weight given as number with no decimals
    @Test
    public void NoDecimalWeight_isCorrect(){
        double result = recyclerRequestActivity.getEstimate(10.00, 150.0);
        assertEquals(1500.0,result,0.001);
    }

    //Category Chosen (Assuming Cloth is chosen) and Weight given as number with decimals
    @Test
    public void DecimalWeight_isCorrect(){
        double result = recyclerRequestActivity.getEstimate(15.25, 130.0);
        assertEquals(1982.5,result,0.001);
    }



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

    //If user inputs multiple inputs with decimals
    @Test
    public void DecimalsInputsCalculation_isCorrect(){
        double result = ecoCal.ecoCal_sum(100,120,200,145,60,0.2,10.2,2.8,0,2.8);
        assertEquals(1971.99,result,0.01);
    }




}