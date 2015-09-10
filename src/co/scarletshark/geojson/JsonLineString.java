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
 * Class representing the GeoJSON object LineString.
 * 
 * @author Alec Dhuse
 */
public class JsonLineString extends GeoJsonObject {
    
    public JsonLineString(JsonCoordinate[] coordinate) {
        this.coordinates = coordinate;
    }   
    
    /**
     * Returns a String representation of this Object.  
     * 
     * @return 
     */
    @Override
    public String toString() {
        return toString(0);   
    }    
    
    /**
     * Returns a String representation of this Object with a given indent.  
     * 
     * @param indent The indent level to add to this object.
     * @return 
     */
    public String toString(int indent) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("{ ");
        sb.append("\n");
                
        for (int i = 0; i < indent; i++) {
            sb.append("\t");
        }
        
        sb.append("\"type\": \"LineString\",");
        sb.append("\n");
        
        for (int i = 0; i < indent; i++)
            sb.append("\t");                               
        
        sb.append("\"coordinates\": [\n");
        
        for (int i = 0; i < coordinates.length; i++) {
            JsonCoordinate c = coordinates[i];
            
            for (int t = 0; t < (indent + 1); t++)
                sb.append("\t");      
            
            sb.append(c.toString());  
            
            if (i != (coordinates.length - 1)) {
                sb.append(",\n");                       
            } else {
                sb.append("\n");  
            }
        }
        
        for (int i = 0; i < indent; i++)
            sb.append("\t");   
        
        sb.append(" ]");
        sb.append(" }");
        
        return sb.toString();
    }      
}
