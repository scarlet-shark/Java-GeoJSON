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

import co.scarletshark.geojson.JsonParser;
import co.scarletshark.geojson.JsonCoordinate;
import co.scarletshark.geojson.JsonPolygon;
import co.scarletshark.geojson.JsonObject;
import co.scarletshark.geojson.JsonLineString;
import co.scarletshark.geojson.JsonValue;
import co.scarletshark.geojson.JsonPoint;
import co.scarletshark.geojson.JsonPair;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * Test Class for JsonParser.
 * 
 * @author Alec Dhuse
 */
public class JsonParserTest {
    
    public JsonParserTest() {
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
     * Test of parse method, of class JsonParser.
     */
    @Test
    public void testParseObject() {
        System.out.println("parseObject");
        
        String     text      = getExampleString1();
        JsonObject expResult = getExampleJson1();
        JsonObject result    = JsonParser.parseObject(text);
        
        assertEquals(expResult, result);
    }
    
    public static JsonCoordinate getExCoordinate(int i) {
        Object[] coordinates = new Object[2];
        
        switch (i) {
            case 0:
                coordinates[0] = new Double(102.0);
                coordinates[1] = new Double(0.0);  
                break;
            case 1:
                coordinates[0] = new Double(103.0);
                coordinates[1] = new Double(1.0); 
                break;
            case 2:
                coordinates[0] = new Double(104.0);
                coordinates[1] = new Double(0.0); 
                break;
            case 3:
                coordinates[0] = new Double(105.0);
                coordinates[1] = new Double(1.0);        
                break;
            case 4:
                coordinates[0] = new Double(100.0);
                coordinates[1] = new Double(0.0);  
                break;
            case 5:
                coordinates[0] = new Double(101.0);
                coordinates[1] = new Double(0.0); 
                break;
            case 6:
                coordinates[0] = new Double(101.0);
                coordinates[1] = new Double(1.0); 
                break;
            case 7:
                coordinates[0] = new Double(100.0);
                coordinates[1] = new Double(1.0);  
                break;
            case 8:
                coordinates[0] = new Double(102.0);
                coordinates[1] = new Double(0.5);   
                break;
        }

        
        return new JsonCoordinate(coordinates);
    }
    
    public static JsonLineString getExLine(int i) {
        JsonCoordinate[] cArray;
        JsonLineString   line;
        
        line = null;
        
        switch (i) {
            case 0:
                cArray    = new JsonCoordinate[4];
                cArray[0] = getExCoordinate(0);
                cArray[1] = getExCoordinate(1);
                cArray[2] = getExCoordinate(2);
                cArray[3] = getExCoordinate(3); 
                line      = new JsonLineString(cArray);
                break;
        }
        
        return line;
    }    
    
    public static JsonPoint getExPoint(int i) {
        JsonCoordinate[] cArray;
        JsonPoint        point;
        
        point = null;
        
        switch (i) {
            case 0:
                cArray    = new JsonCoordinate[1];
                cArray[0] = getExCoordinate(8);
                point     = new JsonPoint(cArray);
                break;
        }
        
        return point;
    }
    
    public static JsonPolygon getExPolygon(int i) {
        JsonCoordinate[] cArray;
        JsonPolygon      poly;
        
        poly = null;
        
        switch (i) {
            case 0:
                cArray    = new JsonCoordinate[5];
                cArray[0] = getExCoordinate(4);
                cArray[1] = getExCoordinate(5);
                cArray[2] = getExCoordinate(6);
                cArray[3] = getExCoordinate(7);   
                cArray[4] = getExCoordinate(4);
                poly      = new JsonPolygon(cArray);
                break;
        }
        
        return poly;
    }      
    
    public static JsonPair getExProp(int i) {
        JsonObject properties, subObject;
        JsonPair   propPair;
        
        propPair   = new JsonPair("properties");
        properties = new JsonObject();
        
        switch (i) {
            case 0:
                properties.addPair(new JsonPair("prop0", "value0"));
                propPair = new JsonPair("properties", properties);
                break;
            case 1:
                properties.addPair(new JsonPair("prop0", "value0"));        
                properties.addPair(new JsonPair("prop1", 0.0));    
                propPair = new JsonPair("properties", properties);
                break;
            case 2:
                subObject = new JsonObject();                                
                subObject.addPair(new JsonPair("this", "that"));
                properties.addPair(new JsonPair("prop0", "value0"));        
                properties.addPair(new JsonPair("prop1", subObject));    
                propPair = new JsonPair("properties", properties);
                break;
        }       
        
        return propPair;
    }
    
    public static String getExampleString1() {
        String example1;
        
        example1 = "{ \"type\": \"FeatureCollection\",\n\t\"features\": [\n\t\t{ \"type\": \"Feature\",\n\t\t\"geometry\": {\"type\": \"Point\", \"coordinates\": [102.0, 0.5]},\n\t\t\"properties\": {\"prop0\": \"value0\"}\n\t\t},\n\t\t{ \"type\": \"Feature\",\n\t\t\t\"geometry\": {\n\t\t\t\t\"type\": \"LineString\",\n\t\t\t\t\"coordinates\": [\n\t\t\t\t\t[102.0, 0.0], [103.0, 1.0], [104.0, 0.0], [105.0, 1.0]\n\t\t\t\t\t]\n\t\t\t\t},\n\t\t\t\"properties\": {\n\t\t\t\t\"prop0\": \"value0\",\n\t\t\t\t\"prop1\": 0.0\n\t\t\t\t}\n\t\t\t},\n\t\t{ \"type\": \"Feature\",\n\t\t\t\"geometry\": {\n\t\t\t\t\"type\": \"Polygon\",\n\t\t\t\t\"coordinates\": [\n\t\t\t\t[ [100.0, 0.0], [101.0, 0.0], [101.0, 1.0],\n\t\t\t\t\t[100.0, 1.0], [100.0, 0.0] ]\n\t\t\t\t]\n\t\t\t},\n\t\t\t\"properties\": {\n\t\t\t\"prop0\": \"value0\",\n\t\t\t\"prop1\": {\"this\": \"that\"}\n\t\t\t}\n\t\t}\n\t]\n}";
    
        return example1;
    }
    
    public static JsonObject getExampleJson1() {
        JsonObject arrayObj, root;
        JsonPair   pair;
        Object[]   features;
        
        root = new JsonObject();
        pair = new JsonPair("type", "FeatureCollection");
        
        root.addPair(pair);
        pair     = new JsonPair("features");
        features = new Object[3];
                
        //first Object
        arrayObj = new JsonObject();
        arrayObj.addPair(new JsonPair("type", "Feature"));
        arrayObj.addPair(new JsonPair("geometry", getExPoint(0)));
        arrayObj.addPair(getExProp(0));        
        features[0] = arrayObj;
        
        //second Object
        arrayObj = new JsonObject();                     
        arrayObj.addPair(new JsonPair("type", "Feature"));
        arrayObj.addPair(new JsonPair("geometry", getExLine(0)));
        arrayObj.addPair(getExProp(1));        
        features[1] = arrayObj;        
        
        //third Object
        arrayObj = new JsonObject();                       
        arrayObj.addPair(new JsonPair("type", "Feature"));
        arrayObj.addPair(new JsonPair("geometry", getExPolygon(0)));
        arrayObj.addPair(getExProp(2));        
        features[2] = arrayObj;           
        
        pair.setValue(new JsonValue(features, JsonValue.ARRAY));
        root.addPair(pair);
        
        return root;
    }
}
