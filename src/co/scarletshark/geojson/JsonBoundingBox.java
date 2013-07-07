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
public class JsonBoundingBox {
    private boolean is3D;
    private double  north, south, east, west;
    private double  altitudeMin, altitudeMax;
    
    /**
     * Constructor for a two dimensional bounding box.
     * 
     * @param west
     * @param south
     * @param east
     * @param north 
     */
    public JsonBoundingBox(double west , double south, 
                           double east,  double north) {
        
        this.north = north;
        this.south = south;
        this.east  = east;
        this.west  = west;
        this.is3D  = false;
        
        this.altitudeMax = Double.NaN;
        this.altitudeMin = Double.NaN;
    }
     
    /**
     * Constructor for a three dimensional bounding box.
     * 
     * @param west
     * @param south
     * @param altitudeMin
     * @param east
     * @param north
     * @param altitudeMax 
     */
    public JsonBoundingBox(double west,        double south, 
                           double altitudeMin, double east, 
                           double north,       double altitudeMax) {
        
        this.north       = north;
        this.south       = south;
        this.altitudeMin = altitudeMin;
        this.east        = east;
        this.west        = west;
        this.altitudeMax = altitudeMax;
        this.is3D        = true;
    }    
    
    /**
     * Return a String representation of the bounding box.
     * The format is: [west, south, east, north]
     * 
     * @return 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
                
        if (this.is3D) {
            sb.append("[");
            sb.append(west);
            sb.append(", ");
            sb.append(south);
            sb.append(", ");
            sb.append(altitudeMin);
            sb.append(", ");            
            sb.append(east);
            sb.append(", ");
            sb.append(north);
            sb.append(", ");
            sb.append(altitudeMax);            
            sb.append("]");            
        } else {
            sb.append("[");
            sb.append(west);
            sb.append(", ");
            sb.append(south);
            sb.append(", ");
            sb.append(east);
            sb.append(", ");
            sb.append(north);
            sb.append("]");
        }
                
        return sb.toString();
    }
}
