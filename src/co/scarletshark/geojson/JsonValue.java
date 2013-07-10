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

/**
 * A class for holding various JsonPair values.
 * 
 * @author Alec Dhuse
 */
public class JsonValue {
    
    //Standard JSON Value Types
    public static final String STRING  = "string";
    public static final String NUMBER  = "number";
    public static final String OBJECT  = "object";
    public static final String ARRAY   = "array";
    public static final String BOOLEAN = "boolean";
    public static final String NULL    = "null";
    
    //GeoJSON Value Types
    public static final String BBOX        = "bbox";
    public static final String COORDINATES = "coordinates";
    public static final String POINT       = "point";
    public static final String LINESTRING  = "linestring";
    public static final String POLYGON     = "polygon";
            
    protected Object   value;
    protected String   valueType;    
        
    /**
     * Constructor for creating a value of a given type.
     * 
     * @param value
     * @param valueType 
     */
    public JsonValue(Object value, String valueType) {
        this.setValue(value, valueType);
    }
    
    /**
     * Returns if this JsonValue is equal to another.
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        JsonValue objValue;
        
        if (obj instanceof JsonValue) {
            objValue = (JsonValue) obj;
            
            if (this.valueType.equalsIgnoreCase(objValue.getValueType())) {
                if (value instanceof Object[]) {
                    boolean  isEqual = false;
                    Object[] array1, array2;
                    
                    array1 = (Object[]) objValue.getValue();
                    array2 = (Object[]) value;
                    
                    for (int i = 0; i < array1.length; i++) {
                        if (array1[i].equals(array2[i])) {
                            isEqual = true;
                        } else {
                            isEqual = false;
                            break;
                        }
                    }
                    
                    return isEqual;                
                } else {
                    if (value.equals(objValue.getValue())) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    /**
     * Returns the actual Object of this value.
     * 
     * @return 
     */
    public Object getValue() {
        return value;
    }               
    
    /**
     * Returns the value type of this value.
     * 
     * @return 
     */
    public String getValueType() {
        return this.valueType;
    }        
    
    /**
     * Sets the value and value type of this JsonValue.
     * 
     * @param value
     * @param valueType 
     */
    public final void setValue(Object value, String valueType) {
        this.value     = value;
        this.valueType = valueType;        
    }
    
    /**
     * Returns a String value for an Array value
     * 
     * @param object
     * @return 
     */
    private String toArrayString() {
        if (value instanceof JsonCoordinate[]) {
            return toCoodinateString();
        } else if (value instanceof Object[]) {
            Object[]      array = (Object[]) value;
            StringBuilder sb    = new StringBuilder();

                sb.append("[");

                for (int i = 0; i < array.length; i++) { 
                    sb.append(array[i].toString());                
                    
                    if (i < (array.length - 1))
                        sb.append(", ");    
                }
                
                sb.append("]");
            
            
            return sb.toString();
        } else {
            return "";
        }        
    }    
    
    /**
     * Returns a String value for a JsonCoordinate value
     * 
     * @param object
     * @return 
     */
    private String toCoodinateString() {
        if (value instanceof JsonCoordinate[]) {
            JsonCoordinate[] cArray = (JsonCoordinate[]) value;
            StringBuilder    sb     = new StringBuilder();

            if (cArray.length == 1) {
                sb.append(cArray[0].toString());
            } else if (cArray.length > 1) {
                sb.append("[");

                for (JsonCoordinate jc: cArray) 
                    sb.append(jc.toString());                

                sb.append("]");
            }
            
            return sb.toString();
        } else {
            return ((JsonCoordinate) value).toString();
        }        
    }    
    
    /**
     * Returns a String representation of this JsonValue.
     * This is usually accomplished by calling the toString() method on the value.
     * 
     * @return 
     */
    @Override
    public String toString() {
        return toString(0);
    }
    
    /**
     * Returns the raw JSON text for this object at a given indent level.
     * 
     * @param indent    The number of tabs in to start this text.
     * @return 
     */
    public String toString(int indent) {

        if (valueType.equals(ARRAY)) {
            return toArrayString();
        } else if (valueType.equals(BBOX)) {            
            return ((JsonBoundingBox) value).toString();  
        } else if (valueType.equals(BOOLEAN)) {
            return ((Boolean) value).toString();                    
        } else if (valueType.equals(COORDINATES)) {
            return toCoodinateString();
        } else if (valueType.equals(NUMBER)) {
            return ((Double) value).toString();    
        } else if (valueType.equals(OBJECT)) {
            return ((JsonObject) value).toString(indent + 1);
        } else if (valueType.equals(POINT)) {
            return ((JsonPoint) value).toString(indent + 1);            
        } else if (valueType.equals(STRING)) {
            String sVal = (String) value;       
            return "\"" + sVal + "\"";         
        } else {
            return value.toString();
        }
    }    
    

}
