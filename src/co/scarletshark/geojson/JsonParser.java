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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Alec
 */
public class JsonParser {
    
    /**
     * Returns the root JSON object parsed from the given file.
     * 
     * @param file
     * @return
     * @throws IOException 
     */
    public static JsonObject parseFile(File file) throws IOException {
        BufferedReader  br;
        FileReader      fileReader;
        JsonObject      jsonObject;
        StringBuilder   text;
        
        fileReader = new FileReader(file);
        br         = new BufferedReader(fileReader);
        text       = new StringBuilder();
                
        while (br.ready()) {
            text.append(br.readLine());
            text.append("\n");
        }        
        
        jsonObject = parseObject(text.toString());
                
        return jsonObject;
    }
    
    /**
     * Parses a JSON object from the given text.
     * 
     * @param text
     * @return 
     */
    public static JsonObject parseObject(String text) {
        char            nextChar;
        JsonObject      object;
        JsonPair        pair;
        SmartTokenizer  st;
        String          pairName, stringValue;
        String          objectContent;
        
        st     = new SmartTokenizer(text.toString());
        object = new JsonObject();
        
        if (text.startsWith("{")) {        
            objectContent = st.getContent('{', '}');    
            st            = new SmartTokenizer(objectContent);
        }                
        
        while (st.hasMore()) {
            //Get the name of the next pair
            pairName = st.getContent('"');
            pair     = new JsonPair(pairName);

            st.jumpAfterChar(':');
            nextChar = st.getNextChar();

            if (nextChar == 0) {
                //blank char, do nothing
            } else if (nextChar == '"') {
                stringValue = st.getContent('"');
                pair.setValue(new JsonValue(stringValue, JsonValue.STRING));
                object.addPair(pair);            
            } else if (nextChar == '{') {
                stringValue    = st.getContent('{', '}');  
                JsonObject obj = parseObject(stringValue);
                pair.setValue(new JsonValue(obj, JsonValue.OBJECT));
                object.addPair(pair); 
            } else if (nextChar == '[') {
                stringValue = st.getContent('[', ']');  
                Object[] objArray = parseArray(stringValue);
                pair.setValue(new JsonValue(objArray, JsonValue.ARRAY));
                object.addPair(pair); 
            } else {
                //number or boolean
                stringValue = st.getTextTo(',');

                if (stringValue.isEmpty()) {
                    //empty string, do nothing
                } else if (stringValue.equalsIgnoreCase("true") ||
                    stringValue.equalsIgnoreCase("false")) {

                    Boolean boolVal = Boolean.parseBoolean(stringValue);
                    pair.setValue(new JsonValue(boolVal, JsonValue.BOOLEAN));
                    object.addPair(pair); 
                } else if (stringValue.equalsIgnoreCase("null")) {
                    pair.setValue(new JsonValue("null", JsonValue.NULL));
                    object.addPair(pair); 
                } else {
                    //Number
                    Double dblVal;
                    
                    try {
                        dblVal = Double.parseDouble(stringValue);
                    } catch (Exception e) {
                        dblVal = new Double(0);
                        System.out.println("Error in JsonParser.parseObject(String) - " + e);
                    }
                    
                    pair.setValue(new JsonValue(dblVal, JsonValue.NUMBER));
                    object.addPair(pair); 
                }
            }
        }
        
        return object;
    }
    
    /**
     * Parses a JSON array from the given text.
     * 
     * @param text
     * @return 
     */
    public static Object[] parseArray(String text) {
        ArrayList<Object> values;
        char              nextChar;
        SmartTokenizer    st;
        String            objectArray, stringValue;
        
        st     = new SmartTokenizer(text.toString());
        values = new ArrayList<Object>();
        
        if (text.startsWith("[") && text.endsWith("]")) {        
            objectArray = st.getContent('[', ']');    
            st          = new SmartTokenizer(objectArray);
        }                
        
        while (st.hasMore()) {
            nextChar = st.getNextChar();
            
            if (nextChar == '"') {
                stringValue = st.getContent('"');
                values.add(stringValue);  
                st.jumpAfterChar(',');
            } else if (nextChar == '{') {
                stringValue    = st.getContent('{', '}');  
                JsonObject obj = parseObject(stringValue);
                values.add(obj);   
                st.jumpAfterChar(',');
            } else if (nextChar == '[') {
                stringValue = st.getContent('[', ']');  
                Object[] objArray = parseArray(stringValue);
                values.add(objArray); 
                st.jumpAfterChar(',');
            } else {
                //number or boolean
                stringValue = st.getTextTo(',');

                if (stringValue.equalsIgnoreCase("true") ||
                    stringValue.equalsIgnoreCase("false")) {

                    Boolean boolVal = Boolean.parseBoolean(stringValue);
                    values.add(boolVal); 
                } else if (stringValue.equalsIgnoreCase("null")) {
                    values.add("null");     
                } else {
                    //Number
                    Double dblVal;
                    
                    try {
                        dblVal = Double.parseDouble(stringValue);                        
                    } catch (Exception e) {
                        dblVal = new Double(0);
                        System.out.println("Error in JsonParser.parseArray(String) - " + e);
                    }
                    
                    values.add(dblVal); 
                }
            }                             
        }
        
        return values.toArray();
    }
    
    /**
     * Takes a JsonPair with the name bbox and an array of 4 or 6 numbers
     * and creates a JsonBoundingBox object.
     * 
     * @param newPair
     * @return 
     */
    public static JsonBoundingBox parseBoundingBox(JsonPair newPair) {
        JsonBoundingBox bbox = null;
    
        if (newPair.getName().equalsIgnoreCase("bbox")) {  
            JsonValue bboxVal = newPair.getValue();

            if (bboxVal.getValueType().equalsIgnoreCase(JsonValue.ARRAY)) {
                
                Object[] array = (Object[]) bboxVal.getValue();

                if (array.length == 4) {
                    //2D Bounding Box
                    Double west  = (Double) array[0];
                    Double south = (Double) array[1];
                    Double east  = (Double) array[2];
                    Double north = (Double) array[3];

                    bbox = new JsonBoundingBox(west, south, east, north);
                } else if (array.length == 6) {
                    //3D Bounding Box
                    Double west  = (Double) array[0];
                    Double south = (Double) array[1];
                    Double min   = (Double) array[2];
                    Double east  = (Double) array[3];
                    Double north = (Double) array[4];
                    Double max   = (Double) array[5];

                    bbox = new JsonBoundingBox(west, south, min, east, north, max);
                }
            }
        }
        
        return bbox;
    }
    
    /**
     * Reads a JsonPair with a name equaling "coordinates" and an array value
     * to a JsonCoordinate object array.
     * 
     * @param newPair
     * @return 
     */
    public static JsonCoordinate[] parseCoordinates(JsonPair newPair) {
        JsonCoordinate[] jCoordinates = new JsonCoordinate[0];
        JsonValue        value;
        Object[]         array, arrayObjects;        
    
        try {
            if (newPair.getName().equalsIgnoreCase("coordinates")) {           
                value = newPair.getValue();

                if (value.getValueType().equals(JsonValue.ARRAY)) {
                    array = ((Object[]) value.getValue());

                    for (Object obj: array) {
                        if (obj instanceof Object[]) {
                            arrayObjects = (Object[]) obj;

                            if (arrayObjects[0] instanceof Object[]) {
                                //nested 
                                if (jCoordinates.length == 0)
                                    jCoordinates = new JsonCoordinate[arrayObjects.length];    

                                for (int i = 0; i < arrayObjects.length; i++) 
                                    jCoordinates[i] = new JsonCoordinate((Object[]) arrayObjects[i]);                            

                                break;                             
                            } else {
                                //unnested 
                                if (jCoordinates.length == 0)
                                    jCoordinates = new JsonCoordinate[array.length];

                                for (int i = 0; i < array.length; i++) {
                                    arrayObjects = (Object[]) array[i];
                                    jCoordinates[i] = new JsonCoordinate(arrayObjects);                            
                                }

                                break;                            
                            }                    
                        } else if (obj instanceof Double) {
                            jCoordinates    = new JsonCoordinate[1];                        
                            jCoordinates[0] = new JsonCoordinate(array);
                            break;
                        }
                    }  //end for loop                
                }    
            }
        } catch (Exception e) {
            System.err.println("Error in JsonParser.parseCoordinates(JsonPair) - " + e);
        }
        
        return jCoordinates;
    }
    
    /**
     * Reads a JsonObject containing geometry and returns a GeoJsonObject 
     * biased on what type of geometry is contained in the object.
     * @param object
     * @return 
     */
    public static GeoJsonObject parseGeometry(JsonObject object) {
        GeoJsonObject geoObject  = null;                        
            
        try {
            if (object.getPairs().size() > 0) {
                JsonPair pair = object.getPair(0);

                if (pair.getValueAsString().equalsIgnoreCase("Point")) {
                    pair = object.getPair(1);

                    if (pair.getValue().getValueType().equalsIgnoreCase(JsonValue.COORDINATES)) {
                        if (pair.getValue().getValue() instanceof JsonCoordinate) {
                            JsonCoordinate[] cArr = new JsonCoordinate[1];        
                            cArr[0] = (JsonCoordinate) pair.getValue().getValue();   
                            geoObject = new JsonPoint(cArr);
                        } else {
                            geoObject = new JsonPoint((JsonCoordinate[]) pair.getValue().getValue());
                        }
                    }
                } else if (pair.getValueAsString().equalsIgnoreCase("LineString")) {
                    pair = object.getPair(1);      

                    if (pair.getValue().getValueType().equalsIgnoreCase(JsonValue.COORDINATES)) 
                        geoObject = new JsonLineString((JsonCoordinate[]) pair.getValue().getValue());
                } else if (pair.getValueAsString().equalsIgnoreCase("Polygon")) {
                    pair = object.getPair(1);      

                    if (pair.getValue().getValueType().equalsIgnoreCase(JsonValue.COORDINATES)) 
                        geoObject = new JsonPolygon((JsonCoordinate[]) pair.getValue().getValue());
                }    
            }
        } catch (Exception e) {
            System.err.println("Error in JsonParser.parseGeometry(JsonObject) - " + e);
        }
        
        return geoObject;
    }    
}