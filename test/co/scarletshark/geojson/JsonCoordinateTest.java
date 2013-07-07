/*
 *    Copyright 2013 Alec Dhuse
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */
package co.scarletshark.geojson;

import co.scarletshark.geojson.JsonCoordinate;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 * Test class for JsonCoordinate.
 * 
 * @author Alec Dhuse
 */
public class JsonCoordinateTest {
    
    public JsonCoordinateTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of equals method, of class JsonCoordinate.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        
        JsonCoordinate jc1 = new JsonCoordinate(-122.67, 45.52);
        JsonCoordinate jc2 = new JsonCoordinate(-122.67, 45.52, Double.NaN);
        JsonCoordinate jc3 = new JsonCoordinate(-122.67, 45.52, 10);
        
        assertEquals(true,  jc1.equals(jc2));
        assertEquals(false, jc1.equals(jc3));
    }

    /**
     * Test of getAltitude method, of class JsonCoordinate.
     */
    @Test
    public void testGetAltitude() {
        System.out.println("getAltitude");

        JsonCoordinate jc1 = new JsonCoordinate(-122.67, 45.52, Double.NaN);
        JsonCoordinate jc2 = new JsonCoordinate(-122.67, 45.52, 10);   
        JsonCoordinate jc3 = new JsonCoordinate(-122.67, 45.52, 11); 
        
        assertEquals(Double.NaN, jc1.getAltitude(), 0.0);
        assertEquals(10        , jc2.getAltitude(), 0.0);
        assertEquals(11        , jc3.getAltitude(), 0.0);
    }

    /**
     * Test of getLatitude method, of class JsonCoordinate.
     */
    @Test
    public void testGetLatitude() {
        System.out.println("getLatitude");

        JsonCoordinate jc1 = new JsonCoordinate(-122.67, 45.52);
        JsonCoordinate jc2 = new JsonCoordinate(-122.60, 45.50, 10);   
        JsonCoordinate jc3 = new JsonCoordinate(-121.00, 44.00, 11); 
        
        assertEquals(45.52, jc1.getLatitude(), 0.0);
        assertEquals(45.50, jc2.getLatitude(), 0.0);
        assertEquals(44.00, jc3.getLatitude(), 0.0);        
    }

    /**
     * Test of getLongitude method, of class JsonCoordinate.
     */
    @Test
    public void testGetLongitude() {
        System.out.println("getLongitude");

        JsonCoordinate jc1 = new JsonCoordinate(-122.67, 45.52);
        JsonCoordinate jc2 = new JsonCoordinate(-122.60, 45.50, 10);   
        JsonCoordinate jc3 = new JsonCoordinate(-121.00, 44.00, 11); 
        
        assertEquals(-122.67, jc1.getLongitude(), 0.0);
        assertEquals(-122.60, jc2.getLongitude(), 0.0);
        assertEquals(-121.00, jc3.getLongitude(), 0.0);  
    }

    /**
     * Test of is3D method, of class JsonCoordinate.
     */
    @Test
    public void testIs3D() {
        System.out.println("is3D");

        JsonCoordinate jc1 = new JsonCoordinate(-122.67, 45.52);
        JsonCoordinate jc2 = new JsonCoordinate(-122.60, 45.50, 10);   
        JsonCoordinate jc3 = new JsonCoordinate(-121.00, 44.00, 11); 
        
        assertEquals(false, jc1.is3D());
        assertEquals(true,  jc2.is3D());
        assertEquals(true,  jc3.is3D()); 
    }

    /**
     * Test of setAltitude method, of class JsonCoordinate.
     */
    @Test
    public void testSetAltitude() {
        System.out.println("setAltitude");

        JsonCoordinate jc1 = new JsonCoordinate(-122.67, 45.52);
        JsonCoordinate jc2 = new JsonCoordinate(-122.60, 45.50, 10);   
        JsonCoordinate jc3 = new JsonCoordinate(-121.00, 44.00, 11); 
        
        jc1.setAltitude(5);
        jc2.setAltitude(14);
        jc3.setAltitude(Double.NaN);
        
        assertEquals(5,          jc1.getAltitude(), 0.0);
        assertEquals(14,         jc2.getAltitude(), 0.0);
        assertEquals(Double.NaN, jc3.getAltitude(), 0.0);  
    }

    /**
     * Test of toString method, of class JsonCoordinate.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        JsonCoordinate jc1 = new JsonCoordinate(-122.67, 45.52);
        JsonCoordinate jc2 = new JsonCoordinate(-122.60, 45.50, 10);   
        JsonCoordinate jc3 = new JsonCoordinate(-121.00, 44.00, 11); 

        assertEquals("[-122.67 , 45.52]",      jc1.toString());
        assertEquals("[-122.6 , 45.5 , 10.0]", jc2.toString());
        assertEquals("[-121.0 , 44.0 , 11.0]", jc3.toString());  
    }
}
