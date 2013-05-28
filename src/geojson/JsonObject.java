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

import java.util.ArrayList;

/**
 *
 * @author Alec
 */
public class JsonObject {
    ArrayList<JsonPair> pairs;
    
    /**
     * Creates a new empty JsonObject.
     * 
     */
    public JsonObject() {
        pairs = new ArrayList<JsonPair>();
    }
    
    /**
     * Adds a JsonPair to this object.
     * 
     * @param newPair 
     */
    public void addPair(JsonPair newPair) {
        try {
            if (newPair.getName().equalsIgnoreCase("bbox")) {  
                JsonBoundingBox bbox = JsonParser.parseBoundingBox(newPair);
                
                if (bbox != null)
                    newPair.getValue().setValue(bbox, JsonValue.BBOX);
            } else if (newPair.getName().equalsIgnoreCase("coordinates")) { 
                if (!newPair.getValue().getValueType().equalsIgnoreCase(JsonValue.COORDINATES))
                    newPair.getValue().setValue(JsonParser.parseCoordinates(newPair), JsonValue.COORDINATES);            
            } else if (newPair.getName().equalsIgnoreCase("geometry")) {   
                JsonObject    object    = newPair.getValueAsObject();
                GeoJsonObject geoObject = JsonParser.parseGeometry(object);

                if (geoObject instanceof JsonPoint) {
                    newPair.getValue().setValue(geoObject, JsonValue.POINT);             
                } else if (geoObject instanceof JsonLineString) {
                    newPair.getValue().setValue(geoObject, JsonValue.LINESTRING);
                } else if (geoObject instanceof JsonPolygon) {
                    newPair.getValue().setValue(geoObject, JsonValue.POLYGON);
                }

            }

            this.pairs.add(newPair);
        } catch (Exception e) {
            System.err.println("Error in JsonObject.addPair(JsonPair) - " + e);
        }
    }
    
    /**
     * Returns if a given JsonObject is equal to this one.
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        boolean    isEqual = false;
        JsonObject jObject;
        
        if (obj instanceof JsonObject) {
            jObject = (JsonObject) obj;
            
            if (jObject.getPairs().size() == this.pairs.size()) {
                for (int i = 0; i < this.pairs.size(); i++) {
                    if (jObject.getPairs().get(i).equals(this.pairs.get(i))) {
                        isEqual = true;
                    } else {
                        isEqual = false;
                        break;
                    }
                }
            } else {
                isEqual = false;
            }
        } else {
            isEqual = false;
        }
        
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.pairs != null ? this.pairs.hashCode() : 0);
        return hash;
    }
    
    /**
     * Returns a JsonPair at the given index.
     * 
     * @param index
     * @return 
     */
    public JsonPair getPair(int index) {
        return pairs.get(index);
    }
    
    /**
     * Searches this JsonObject for a JsonPair with a given name.
     * If found that pair is returned, if not found, null is returned.
     * 
     * @param name
     * @return 
     */
    public JsonPair getPairByName(String name) {
        JsonPair returnPair = null;
        
        for (JsonPair pair: pairs) {
            if (pair.getName().equalsIgnoreCase(name)) {
                returnPair = pair;
                break;
            }
        }
        
        return returnPair;
    }
    
    /**
     * Returns an ArrayList containing all the JsonPairs in this object.
     * 
     * @return 
     */
    public ArrayList<JsonPair> getPairs() {
         return pairs;
    }
    
    /**
     * Returns a String representation of this object.
     * 
     * @return 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("{/n");
        
        for (int i = 0; i < this.pairs.size(); i++) {
            JsonPair jp = this.pairs.get(i);
            
            if (i > 0)
                sb.append(" , ");
            
            sb.append(jp.name);
            sb.append("\t:\t");
            sb.append(jp.getValue().toString());
        }
        
        sb.append("}");
        
        return sb.toString();
    }
}
