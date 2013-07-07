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
 *
 * @author Alec
 */
public class JsonPair {
    protected JsonValue value;
    protected String    name;
    
    /**
     * Creates an empty pair without a Name or Value.
     */
    public JsonPair() {
        this.name = "";
    }
    
    /**
     * Creates a pair with the given Name and no Value.
     * @param name 
     */
    public JsonPair(String name) {
        this.name     = name;
    }    
    
    /**
     * Creates a pair with the given Name and boolean value.
     * 
     * @param name 
     */
    public JsonPair(String name, boolean value) {
        this.name  = name;
        this.value = new JsonValue(new Boolean(value), JsonValue.BOOLEAN);
    }      
    
    /**
     * Creates a pair with the given Name and double value.
     * 
     * @param name 
     */
    public JsonPair(String name, double value) {
        this.name  = name;
        this.value = new JsonValue(new Double(value), JsonValue.NUMBER);
    }     
    
    /**
     * Creates a pair with the given Name and String value.
     * 
     * @param name 
     */
    public JsonPair(String name, String value) {
        this.name  = name;
        this.value = new JsonValue(value, JsonValue.STRING);
    }                
    
    /**
     * Creates a pair with the given Name and GeoJsonObject value.
     * 
     * @param name 
     */
    public JsonPair(String name, GeoJsonObject value) {
        this.name  = name;
        
        if (value instanceof JsonPoint) {
            this.value = new JsonValue(value, JsonValue.POINT);
        } else if (value instanceof JsonLineString) {
            this.value = new JsonValue(value, JsonValue.LINESTRING);
        } else if (value instanceof JsonPolygon) {
            this.value = new JsonValue(value, JsonValue.POLYGON); 
        } else {
            this.value = new JsonValue(value, JsonValue.OBJECT);
        }
    }    
    
    /**
     * Creates a pair with the given Name and JsonObject value.
     * 
     * @param name 
     */
    public JsonPair(String name, JsonObject value) {
        this.name  = name;
        this.value = new JsonValue(value, JsonValue.OBJECT);
    }      
    
    /**
     * Creates a pair with the given Name and Array value.
     * 
     * @param name 
     */
    public JsonPair(String name, Object[] value) {
        this.name  = name;
        this.value = new JsonValue(value, JsonValue.ARRAY);
    }      
    
    /**
     * Creates a pair with the given Name and JsonCoordinate value.
     * 
     * @param name 
     */
    public JsonPair(String name, JsonCoordinate value) {
        this.name  = name;
        this.value = new JsonValue(value, JsonValue.COORDINATES);
    }     
    
    @Override
    public boolean equals(Object obj) {
        JsonPair pair;
        
        if (obj instanceof JsonPair) {
            pair = (JsonPair) obj;
            
            if (pair.getName().equals(this.name)) {
                if (pair.getValue().equals(this.value)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    /**
     * Returns the name of this Pair.
     * 
     * @return 
     */
    public String getName() {
        return name;
    }    
    
    /**
     * Returns the JsonValue of this pair.
     * @return 
     */
    public JsonValue getValue() {
        return value;
    }
    
    /**
     * If the valueType is an ARRAY, a Object[]  array is returned.  
     * If the valueType is something else an empty Object[] array of length
     * zero is returned.
     * 
     * @return 
     */
    public Object[] getValueAsArray() {
        if (value.getValueType().equalsIgnoreCase(JsonValue.ARRAY)) {
            return (Object[]) value.getValue();
        } else {
            return new Object[0]; 
        }
    }    
    
    /**
     * If the valueType is OBJECT, then that value is returned as a JsonObject.
     * If the valueType is anything else then an empty JsonObject is returned.
     * 
     * @return 
     */
    public JsonObject getValueAsObject() {
        if (value.getValueType().equalsIgnoreCase(JsonValue.OBJECT)) {
            return (JsonObject) value.getValue();
        } else {
            return new JsonObject();
        }
        
    }       
    
    /**
     * If the valueType is STRING, then that value is returned as a String.
     * If the valueType is anything else then the result of toString() 
     * is returned.
     * 
     * @return 
     */
    public String getValueAsString() {
        if (value.getValueType().equalsIgnoreCase(JsonValue.STRING)) {
            return (String) value.getValue();
        } else {
            return value.getValue().toString();
        }
        
    }      
    
    /**
     * Sets the Name for this pair.
     * @param name 
     */
    public void setName(String name) {
        this.name    = name;
    }    
    
    /**
     * Sets the Value for this pair.
     * 
     * @param value 
     */
    public void setValue(JsonValue value) {
        this.value    = value;
    }
    
    /**
     * Returns a string representation of this Pair.
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
        StringBuilder sb = new StringBuilder();
        
        sb.append(GeoJsonObject.getIndent(indent));
        
        sb.append("\"");
        sb.append(name);
        sb.append("\": ");        

        if (value != null) {
            sb.append(value.toString());
        } else {
            sb.append("NULL");
        }
        
        return sb.toString();
    }    
}
