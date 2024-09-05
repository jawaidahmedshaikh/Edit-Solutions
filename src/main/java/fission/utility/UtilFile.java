/*
 * User: sdorman
 * Date: Jun 17, 2003
 * Time: 10:48:13 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package fission.utility;

import java.io.*;
import java.util.*;



public class UtilFile
{
    public static final String DIRECTORY_DELIMITER = "/";


    /**
     * Creates a directory (does not create missing parent directories)
     * @return true if directory was successfully created, false otherwise
     */
    public static boolean createDirectory(String directory)
    {
        File f = new File(directory);

        boolean dirMade = f.mkdir();

        if (!dirMade)
        {
            throw new RuntimeException("Failed to create directory " + f.getAbsolutePath());
        }
        else
        {
            return true;
        }
    }

    /**
     * Creates a full path of directories if they don't exist.  Creates parent and children directories
     * @param path  - full path to be created
     * @return true if directory was created, false otherwise
     */
    public static boolean createDirectories(String path)
    {
        boolean created = false;

        File fPath = new File(path);

        if (! fPath.exists())
        {
            String parent = fPath.getParent();
            if (parent != null)
            {
                UtilFile.createDirectories(parent);
            }

            created = fPath.mkdir();

             if (! created)
            {
                throw new RuntimeException("Failed to create directory " + fPath.getName() + " in path " +
                        fPath.getAbsolutePath() + ". Check permissions in " + fPath.getParent() + ".");
            }
        }

        return created;
    }

    /**
     * Removes a directory and its contents
     * @return true if directory was successfully removed, false otherwise
     * @throws java.lang.Exception if directory specified does not exist or is not a directory
     */
    public static boolean removeDirectory(String directory) throws Exception
    {
        File fDirectory = new File(directory);

        if (!fDirectory.isDirectory())
        {
            throw new Exception(directory + " is not a valid directory, can not remove");
        }

        //  Get all files and directories in this directory and remove them
        File[] files = fDirectory.listFiles();

        if (files != null)
        {
            for (int i = 0; i < files.length; i++)
            {
                File file = files[i];

                if (file.isFile())
                {
                    boolean fdel = file.delete();
                }
                else if (file.isDirectory())
                {
                    removeDirectory(file.getAbsolutePath());
                }
            }
        }


        boolean dirWasDeleted = fDirectory.delete();

        if (! dirWasDeleted)
        {
            //  The following loop is a temp fix for an odd OS/JVM problem.  When deleting a directory with subdirectories,
            //  the subdirectories are deleted but sometimes are still listed as being there (via listFiles()). The
            //  current directory won't get deleted because it is not empty.  The following loop just slows things down
            //  to give the OS and/or JVM time to update its indexes.
            //  Very odd and frustrating problem!  And not always repeatable!!!
            boolean del = false;

            for (int i = 0; i < 100; i++)
            {
                Thread.sleep(10);
                del = fDirectory.delete();

                if (del)      // if it deleted it, exit the loop
                {
                    break;
                }
            }
            if (!del)   // if it went through the whole loop and still couldn't delete it, throw error
            {
                throw new RuntimeException("Failed to delete " + fDirectory.getAbsoluteFile());
            }

            return del;
        }
        else
        {
            return true;
        }
    }

    /**
     * Removes a file
     * @param file
     * @return  true if successfully removed file, false otherwise
     */
    public static boolean removeFile(String filename)
    {
        boolean deleted = false;

        File file = new File(filename);

        if (file.exists())
        {
            deleted = file.delete();
        }

        return deleted;
    }


    /**
     * Moves a file from the old filename (and path) to the new filename (and path)
     * @param fromFilename
     * @param toFilename
     * @param force - if true, forces move over an existing file
     * @return
     */
    public static boolean moveFile(String fromFilename, String toFilename, boolean force)  throws Exception
    {
        File fileFrom = new File(fromFilename);
        File fileTo = new File(toFilename);

        if (force)
        {
            if (fileTo.exists())
            {
                boolean fileToDeleted = fileTo.delete();
            }
        }

        boolean rename = fileFrom.renameTo(fileTo);

        return rename;
    }

    /**
     * Moves the contents of a directory to a new directory.
     *
     * @param fromDir
     * @param toDir
     * @param force - if true, the move is forced over existing files (with the same filename) in the new directory
     * @return
     * @throws java.lang.Exception - if directory to be moved is not a valid directory (must be a directory and must exist)
     */
    public static boolean moveDirectoryContents(String fromDir, String toDir, boolean force) throws Exception
    {
        File directory = new File(fromDir);

        if (!directory.isDirectory())
        {
            throw new Exception(fromDir + " is not a valid directory, can not move contents");
        }

        //  Get all files and directories in the "fromDir" and move them to the "toDir"
        File[] files = directory.listFiles();

        if (files != null)
        {
            if (! UtilFile.directoryExists(toDir))  // if directory we are moving to does not exist, create it
            {
                UtilFile.createDirectory(toDir);
            }

            for (int i = 0; i < files.length; i++)
            {
                File file = files[i];

                if (file.isFile())
                {
                    File toFile = new File(toDir + DIRECTORY_DELIMITER + file.getName());

                    if (force)
                    {
                        if (toFile.exists())    // if the location of where it is to be moved to already exists, remove it
                        {
                            boolean delToFile = toFile.delete();
                        }
                    }
                    boolean rename = file.renameTo(toFile);
                }
                else if (file.isDirectory())
                {
                    String currentDir = file.getAbsolutePath();
                    String newDir = toDir + DIRECTORY_DELIMITER + file.getName();
                    UtilFile.moveDirectoryContents(currentDir, newDir, force);
                    if (file.exists())
                    {
                        boolean delSubDir = file.delete();  // delete sub directory once all its files have been moved
                    }
                }
            }
        }

        //  Test to see if "fromDir" is now empty.  If so, move was successful
        if (UtilFile.isEmpty(directory.getAbsolutePath()))
        {
            return true;
        }
        else
        {
            if (UtilFile.containsOnlyDirectories(directory.getAbsolutePath()))
            {
                return true;
            }
            return false;
        }
    }

    /**
     * Returns just the filename from the fully specified path.  It does not verify the existence of the file.
     * @param fullPath
     * @return fileName
     */
    public static String getFileName(String fullPath)
    {
        File f = new File(fullPath);

        return f.getName();
    }

    /**
     * Tests to see if a directory contains only directories
     * @param directory
     * @return true if contains only directories, false otherwise
     */
    public static boolean containsOnlyDirectories(String directory)
    {
        File f = new File(directory);

        File[] files = f.listFiles();

        if (files != null)
        {
            for (int i = 0; i < files.length; i++)
            {
                File file = files[i];
                if (!file.isDirectory())
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Tests to see if a directory is empty
     * @param directory
     * @return  true if empty, false otherwise
     */
    public static boolean isEmpty(String directory)
    {
        File fDirectory = new File(directory);

        File[] files = fDirectory.listFiles();

        if (files == null || files.length <= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Tests to see if a directory already exists
     * @param directory
     * @return true if directory exists, false otherwise
     */
    public static boolean directoryExists(String directory)
    {
        File f = new File(directory);

        return f.exists();
    }

    /**
     * Test to see if the directory is writable
     * @param directory
     * @return true if directory is writable, false otherwise
     */
    public static boolean directoryIsWritable(String directory)
    {
        File f = new File(directory);

        return f.canWrite();
    }

    /**
     * Tests to see if a file already exists
     * @param file
     * @return true if file exists, false otherwise
     */
    public static boolean fileExists(String file)
    {
        File f = new File(file);

        return f.exists();
    }

    public static String[] readFile(String fileName) throws IOException
    {
        File file = new File(fileName);

        return UtilFile.readFile(file);
    }

    public static String[] readFile(File file) throws IOException
    {
        List readLines = new ArrayList();

        StringBuilder inputLines = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = null;

        while ((line = reader.readLine()) != null)
        {
            inputLines.append(line);
            readLines.add(line);
        }

        reader.close();

        return (String[]) readLines.toArray(new String[readLines.size()]);
    }
}
