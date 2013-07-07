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
 * A String tokenizer that has more features.
 * 
 * @author Alec Dhuse
 */
public class SmartTokenizer {
    private int     currentIndex;
    private String  string;
    
    public SmartTokenizer(String string) {
        this.string       = string;
        this.currentIndex = 0;
    }
    
    /**
     * Returns the text between a delimiter of the same character, starting at
     * the current index pointer.  Handles if that character is escaped by '\'
     * 
     * @param delim
     * @return 
     */
    public String getContent(char delim) {
        boolean continueLoop;
        char    currentChar, prevChar;
        int     startIndex;
        String  content;
        
        startIndex   = string.indexOf(delim, currentIndex);
        
        if (startIndex >= 0) {
            currentIndex = startIndex + 1;
            continueLoop = true;
            prevChar     = ' ' ;
            content      = "";

            while (continueLoop && currentIndex < string.length()) {
                currentChar = string.charAt(currentIndex);

                if (currentIndex > 0)
                    prevChar = string.charAt(currentIndex - 1);

                if (currentChar == delim && prevChar != '\\') {
                    continueLoop = false;
                    content      = string.substring(startIndex + 1, currentIndex);
                    currentIndex++;
                } else {
                    currentIndex++;
                }
            }

            return content;
        } else {
            currentIndex = string.length();
            return "";
        }
    }
    
    /**
     * Returns the text between the two delimiting characters.  
     * Deals with nested characters.
     * 
     * @param startDelim
     * @param endDelim
     * @return 
     */
    public String getContent(char startDelim, char endDelim) {
        char currentChar;
        int  openDelims, startIndex;
        
        startIndex   = string.indexOf(startDelim, currentIndex);
        openDelims   = 1;
        currentIndex = startIndex + 1;
        
        while (openDelims > 0 && currentIndex < string.length()) {
            currentChar = string.charAt(currentIndex);
            
            if (currentChar == startDelim) {
                openDelims++;
            } else if (currentChar == endDelim) {
                openDelims--;
            }
            
            currentIndex++;
        }
                
        return string.substring(startIndex + 1, currentIndex - 1);        
    }
    
    /** 
     * Returns the next char in the tokenizer, without advancing the index 
     * pointer.  Spaces are ignored.
     * 
     * @return 
     */
    public char getNextChar() {        
        if (currentIndex < string.length()) {
            //Skip over whitespace 
            moveToNextNonWhiteSpace();

            return string.charAt(currentIndex);
        } else {
            //end of string, return blank char.
            return 0;
        }
    }
    
    /**
     * Returns text from the current pointer to the first instance of a given
     * char.  The index pointer is set to one past the stop point.
     * 
     * If the end of the String is reach without reaching that character, 
     * the substring from the index pointer to the String end will be returned.
     * 
     * @param character
     * @return 
     */
    public String getTextTo(char character) {
        String text;    
        int    charIndex = string.indexOf(character, currentIndex);
        
        if (charIndex > 0) {
            text = string.substring(currentIndex, charIndex);
            currentIndex = charIndex + 1;
        } else {
            text = string.substring(currentIndex);
            currentIndex = string.length();
        }        
        
        return text;
    }
    
    /**
     * Returns if there is more of the string after the index pointer.
     * 
     * @return 
     */
    public boolean hasMore() {
        if (currentIndex < string.length()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Jumps the current pointer index to after the next occurrence of a given 
     * char.
     * 
     * @param character 
     */
    public void jumpAfterChar(char character) {
        int newIndex = string.indexOf(character, currentIndex); 
        
        if (newIndex >= 0) {
            currentIndex = newIndex + 1;
        } else {
            currentIndex = string.length();
        }
    }
    
    /**
     * Increments the pointer to the next non whitespace character.
     * 
     */
    public void moveToNextNonWhiteSpace() {
        while (string.charAt(currentIndex) == ' '  ||
                string.charAt(currentIndex) == '\n' ||
                string.charAt(currentIndex) == '\t') {

            currentIndex++;        
        }       
    }    
    
    /**
     * Increments the pointer to the next whitespace character.
     * 
     */
    public void moveToNextWhiteSpace() {
        while (string.charAt(currentIndex) != ' '  &&
               string.charAt(currentIndex) != '\n' &&
               string.charAt(currentIndex) != '\t') {

            currentIndex++;        
        }          
    }
    
    /**
     * Returns the next white space delimited token.  If index pointer is in the
     * middle of a token, the pointer is incremented to the next whitespace 
     * character to start the token.
     * 
     * @return 
     */
    public String nextToken() {
        int startIndex;
        
        moveToNextWhiteSpace();
        startIndex = currentIndex;
                
        moveToNextNonWhiteSpace();
        
        //find the next whitespace
        moveToNextWhiteSpace();
        
        return (string.substring(startIndex, currentIndex)).trim();
    }
    
    /**
     * Returns the text after the index pointer.
     * 
     * @return 
     */
    @Override
    public String toString() {
        return string.substring(currentIndex);
    }
}
