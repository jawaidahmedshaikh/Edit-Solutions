package conversion;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * A convenience wrapper class specifically written to support the XFlat
 * API. The XFlat API automatically puts in a '\n' after each Element. This
 * is not desirable. When used, any characters written to the output stream
 * are scanned for '\n', where it is readily removed.
 */
public class ConversionBufferedWriter extends BufferedWriter
{
    /**
     * Unicode decimal for a Space.
     */
    private static int SPACE = 32;
    
    /**
     * Unicode decimal for a Carriage Return.
     */
    private static int CARRIAGERETURN = 13;
    
    /**
     * Unicode decimal for Line Feed.
     */
    private static int LINEFEED = 10;
    
    /**
     * Unicde decimal for Null.
     */
    private static int NULL = 0;
    
    public ConversionBufferedWriter(Writer writer)
    {
        super(writer);
    }

    /**
     * Checks for newline ('\n') characters in the specified characters input.
     * If found, they are removed.
     * @param characters
     * @param off
     * @param len
     * @throws IOException
     */
    @Override
    public void write(char[] characters, int off, int len) throws IOException
    {
//        String characterString = new String(characters).   
        
//        System.out.println("Dude:" + characterString);
        
//        char[] characterStringChars = characterString.toCharArray();
        
//        CharArrayWriter charArrayWriter = null;
//        
//        BufferedWriter bufferedWriter = new BufferedWriter(charArrayWriter = new CharArrayWriter());
//        
//        boolean stripWhiteSpace = false;
//        
//        for (char character:characters)
//        {
//            if (character == '>')
//            {
//                stripWhiteSpace = true;
//            }
//            else if (character == '<')
//            {
//                stripWhiteSpace = false;
//            }
//                
//            if (validCharacter(character, stripWhiteSpace))
//            {
//                bufferedWriter.write((int) character);
//            }
//        }
//        
//        if (bufferedWriter != null)
//        {
//            bufferedWriter.flush();
//            
//            bufferedWriter.close();
//            
//            charArrayWriter.close();
//        }
//        
//        char[] charactersWithoutNewLine = charArrayWriter.toCharArray();
//        
//        super.write(charactersWithoutNewLine, 0, charactersWithoutNewLine.length);
        
          super.write(characters, off, characters.length);
        
    }
    
    /**
     * The character can not be a newline, carriage return, or a null character.
     * Additionally, if the character is a whitespace, and stripWhiteSpace == true, 
     * then the character is invalid.
     * @param character
     * @param stripWhiteSpace
     * @return
     */
    private boolean validCharacter(char character, boolean stripWhiteSpace)
    {
        boolean validCharacter = false;
        
        int decimalValue = (int) character;
        
//        if (decimalValue != LINEFEED && decimalValue != CARRIAGERETURN && decimalValue != NULL)
        if (true)
        {
            validCharacter = true;
            
            if (decimalValue == SPACE)
            {
                if (stripWhiteSpace)
                {
                    validCharacter = false;
                }
            }
        }
        
        return true;
    }

    @Override
    public void close() throws IOException
    {
//        write(new char[]{(char) 4});
        
        super.close();
    }
}
