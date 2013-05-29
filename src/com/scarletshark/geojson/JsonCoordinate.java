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
package com.scarletshark.geojson;

/**
 * Represents an individual 2D or 3D coordinate.
 * 
 * @author Alec Dhuse
 */
public class JsonCoordinate {
    private boolean is3D;
    private double  altitude, latitude, longitude;
    
    /**
     * Constructor using an Object Array.
     * 
     * @param values 
     */
    public JsonCoordinate(Object[] values) {
        Double[] coordinates = new Double[values.length];

        try {
            if (values.length == 2) {
                coordinates[0]  = (Double) values[0];
                coordinates[1]  = (Double) values[1];
                this.latitude   = coordinates[1];
                this.longitude  = coordinates[0]; 
                this.altitude   = Double.NaN;
                this.is3D       = false;
            } else if (values.length == 3) {
                if (values[0] instanceof Double) 
                    coordinates[0]  = (Double) values[0];
                
                if (values[1] instanceof Double) 
                    coordinates[1]  = (Double) values[1];
                
                if (values[2] instanceof Double) {
                    coordinates[2]  = (Double) values[2];                
                } else {
                    coordinates[2]  = (Double) Double.NaN;
                }
                
                this.latitude   = coordinates[1];
                this.longitude  = coordinates[0];    
                this.altitude   = coordinates[2];
                this.is3D       = true;
            } else {
                //error 
            }    
        } catch (Exception e) {
            System.err.println("Error in JsonCoordinate Constructor(Object[]) - " + e);
        }
    }
    
    /**
     * Constructor for a 2D coordinate.
     * 
     * @param longitude
     * @param latitude 
     */
    public JsonCoordinate(double longitude, double latitude) {
        this.latitude  = latitude;
        this.longitude = longitude;
        this.altitude  = Double.NaN;
        this.is3D      = false;
    }
    
    /**
     * Constructor for a 3D coordinate.
     * 
     * @param longitude
     * @param latitude
     * @param altitude 
     */
    public JsonCoordinate(double longitude, double latitude, double altitude) {        
        this.latitude  = latitude;
        this.longitude = longitude;
        
        setAltitude(altitude);
    }    
    
    @Override
    public boolean equals(Object obj) {
        JsonCoordinate coordinate;
        
        if (obj instanceof JsonCoordinate) {
            coordinate = (JsonCoordinate) obj;
            
            if (this.longitude == coordinate.getLongitude() &&
                this.latitude == coordinate.getLatitude()) {                
                
                if (this.altitude == coordinate.getAltitude()) {
                    return true;
                } else {
                    if (this.is3D == false && coordinate.is3D == false) {
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
     * If this coordinate is a 3D coordinate, its altitude is returned.
     * If this coordinate is a 2D coordinate, the Double.NaN value is returned.
     * 
     * @return 
     */
    public double getAltitude() {
        return this.altitude;
    }      
    
    /**
     * Returns the Latitude of this coordinate.
     * 
     * @return 
     */
    public double getLatitude() {
        return this.latitude;
    }  
        
    
    /**
     * Returns the Longitude of this coordinate.
     * @return 
     */
    public double getLongitude() {
        return this.longitude;
    }
    
    /**
     * Returns if this coordinate has an altitude / elevation component.
     * @return 
     */
    public boolean is3D() {
        return this.is3D;
    }
    
    /**
     * Sets the Altitude for this coordinate.  If the coordinate was 2D it is
     * converted to a 3D point.  If the altitude is set to Double.NaN this
     * coordinate will be set as 2D.
     * 
     * @param altitude 
     */
    public final void setAltitude(double altitude) {
        if (altitude <= 0 || altitude > 0) {
            this.altitude = altitude;
            this.is3D     = true;
        } else {
            this.altitude = Double.NaN;
            this.is3D     = false;
        }
    }    
    
    /**
     * Returns a String representation of this object.
     * Information is returned one of the two formats:
     * 
     * 2D:  [longitude , latitude]
     * 3D:  [longitude , latitude , altitude]
     * 
     * @return 
     */
    @Override
    public String toString() {
        if (this.is3D) {
            return "[" + longitude + " , " + latitude + " , " + altitude + "]";
        } else {
            return "[" + longitude + " , " + latitude + "]";
        }
    }
}
