Java-GeoJSON
============

A Java GeoJSON parser and eventual builder.

## Current state of the project:

  The JsonParser class can read in basic types of GeoJson including point, linestring, and polygon.
  
### Usage:

    JsonObject jRootObject = JsonParser.parseFile(File);
  
    or
  
    JsonObject jRootObject = JsonParser.parseObject(String);
  
