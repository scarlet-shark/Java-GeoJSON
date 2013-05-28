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
package geojson;

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
     * Returns a String representation of this JsonValue.
     * This is usually accomplished by calling the toString() method on the value.
     * 
     * @return 
     */
    @Override
    public String toString() {

        if (valueType.equals(ARRAY)) {
            return valueType;
        } else if (valueType.equals(BBOX)) {            
            return ((JsonBoundingBox) value).toString();  
        } else if (valueType.equals(BOOLEAN)) {
            return ((Boolean) value).toString();                    
        } else if (valueType.equals(COORDINATES)) {
            return ((JsonCoordinate) value).toString();
        } else if (valueType.equals(NUMBER)) {
            return ((Double) value).toString();          
        } else if (valueType.equals(STRING)) {
            return (String) value;            
        } else {
            return valueType;
        }
    }
}
