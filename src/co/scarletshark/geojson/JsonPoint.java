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
public class JsonPoint extends GeoJsonObject {
    
    /**
     * Constructor
     * 
     * @param coordinate 
     */
    public JsonPoint(JsonCoordinate[] coordinate) {
        this.coordinates = coordinate;
    }
    
    /**
     * Returns the raw JSON text for this object
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
        StringBuilder sb = new  StringBuilder();
        
        sb.append("{");
        sb.append("\n");
        sb.append(getIndent(indent));
        sb.append("\"type\":");
        sb.append("\t");
        sb.append("\"Point\",");
        sb.append("\n");
        
        sb.append(getIndent(indent));
        sb.append("\"coordinates\":");
        sb.append("\t");
        
        if (coordinates.length > 0) {
            sb.append(coordinates[0].toString());
        } else {
            sb.append("[]");
        }
        
        sb.append("\n");
        sb.append(getIndent(indent));
        sb.append("}");
         
        return sb.toString();
    }    
}