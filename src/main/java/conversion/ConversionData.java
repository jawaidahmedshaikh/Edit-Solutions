package conversion;

import edit.common.EDITDateTime;
import edit.common.exceptions.SEGConversionException;

import edit.services.config.ServicesConfig;

import fission.utility.ConversionUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.Reader;

import java.io.StringReader;
import java.io.Writer;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * Represents an instance of the raw data inputFile that is consumed
 * by the conversion engine to convert Flat inputFile information to 
 * an XML representation.
 */
public class ConversionData
{
    /**
     * The raw inputFile to be converted to an XML equivalent.
     */
    private File inputFile;
    
    /**
     * The converted raw input file in XML format.
     */
    private File outputFile;
    
    /**
     * Stream used to read file input data.
     */
    private BufferedReader inputDataReader;
    
    /**
     * Stream used to output data in the same directory as the input data.
     */
    private BufferedWriter outputDataWriter;
    
    /**
     * The name of the inputFile containing raw data to convert.
     * @param inputFileName the name of the raw data inputFile (does not include the path) / the path is configured in the configuration inputFile
     */
    public ConversionData(String inputFileName) 
    {
        inputFile = buildInputFile(inputFileName);        
    }
    
    /**
     * The inputFile identifying the raw data to convert.
     * @param file
     */
    public ConversionData(File file)
    {
        this.inputFile = file;
    }
    
    public ConversionData()
    {
        
    }

    /**
     * Creates the actual File instance for the specified fileName using
     * the path of the data inputFile identified in the configuration inputFile.
     * @param fileName the simple inputFile name of the data inputFile
     * @return the fully qualified File instance
     */
    private final File buildInputFile(String fileName)
    {
        String directoryPath = ServicesConfig.getConversionData().getDirectory();
        
        String absoluteFilePath = directoryPath + File.separator +  fileName;
        
        return new File(absoluteFilePath);
    }

    /**
     * The inputFile containing the raw data to convert.
     * @return
     */
    private File getInputFile()
    {
        return inputFile;
    }
    
    /**
     * The output file acting as the destination for the converted input file. Its
     * fully qualified name is that of the input file's with a different extension.
     * @return
     */
    private File buildOutputFile(String templateName)
    {
        String inputFileName = getInputFile().getAbsolutePath();
        
        String outputFileName = inputFileName + "." + templateName + ".log";
        
        return new File(outputFileName);
    }

    /**
     * The directory path where both input and output files will be stored.
     * @return
     */
    private String getDirectory()
    {
        File inputFile = getInputFile();
        
        String absolutePath = inputFile.getAbsolutePath();
        
        String inputFileName = inputFile.getName();     
        
        String directoryPath = absolutePath.substring(0, absolutePath.length() - inputFileName.length());
        
        return directoryPath;
    }
    
    /**
     * The Element representation of this entity in the format of:
     * 
     * <ConversionDataFileVO>
     *      <FileName></FileName>
     *      <FileSize></FileSize>
     * </ConversionDataFileVO> 
     * @return DOM4J Element
     */
    public Element getAsElement()
    {
        // ConversionDateFile
        Element conversionDataFileElement = new DefaultElement("ConversionDataFileVO");
        
        // FileName
        Element fileNameElement = new DefaultElement("FileName");

        fileNameElement.setText(getInputFile().getName());

        // FileSize        
        Element fileSizeElement = new DefaultElement("FileSize");
        
        fileSizeElement.setText(Long.toString(getInputFile().length()));
        
        // Attach
        conversionDataFileElement.add(fileNameElement);
        
        conversionDataFileElement.add(fileSizeElement);
        
        return conversionDataFileElement;
    }
    
    /**
     * Gets the contents of the underlying inputFile as a Reader backed
     * by the BufferedReader.
     * 
     * The user is expected to close the Reader upon completion using the
     * closeInputDataReader() method.
     * @return
     */
    public BufferedReader openInputDataReader()
    {
        if (inputDataReader == null)
        {
            try
            {
                inputDataReader = new BufferedReader(new FileReader(getInputFile()));
            }
            catch (FileNotFoundException e)
            {
                System.out.println(e);
                
                e.printStackTrace();
            }
        }

        return inputDataReader;
    }
    
    /**
     * Closes the specified BufferedReader.
     */
    public void closeInputDataReader()
    {
        try
        {
            if (this.inputDataReader != null)
            {
                inputDataReader.close();
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
        }
        finally
        {
            this.inputDataReader = null;
        }
    }
    
    /**
     * Closes the specified BufferedWriter.
     */
    public void closeOutputDataWriter()
    {
        try
        {
            if (this.outputDataWriter != null)
            {
                outputDataWriter.close();
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
            
            e.printStackTrace();
        }
        finally
        {
            this.outputDataWriter = null;
        }
    }    
    
    /**
     * Returns all files in the directory specified for housing the
     * files containing raw data for converion.
     * @return
     */
    public static ConversionData[] getAllConversionDataFiles()
    {
        File conversionFileDirectory = new File(ServicesConfig.getConversionData().getDirectory());        
        
        File[] conversionFiles = conversionFileDirectory.listFiles();
        
        ConversionData[] conversionDataFiles = new ConversionData[conversionFiles.length];
        
        for (int i = 0; i < conversionFiles.length; i++)
        {
            conversionDataFiles[i] = new ConversionData(conversionFiles[i]);
        }
        
        return conversionDataFiles;
    }

    /**
     * The output file backed by a BufferedWriter. The user is expected to close
     * this Writer upon completion.
     * @return
     */
    public BufferedWriter openOutputDataWriter(String templateName) throws IOException
    {
        if (outputDataWriter == null)
        {
            try
            {
                FileWriter fileWriter = new FileWriter(buildOutputFile(templateName), true);
                
                outputDataWriter = new BufferedWriter(fileWriter);
            }
            catch (FileNotFoundException e)
            {
                System.out.println(e);
                
                e.printStackTrace();
            }
        }

        return outputDataWriter;
    }
}
