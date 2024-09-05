package accounting.ap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.logging.LoggerManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ThreadSafeFileWriter {

    private static final ThreadSafeFileWriter instance = new ThreadSafeFileWriter();


    private ThreadSafeFileWriter()
    {
        super();
    }


    public void writeToFile(String filePath, String content)
    {
        File file = new File(filePath) ;
        Logger logger = LogManager.getLogger(this.getClass());
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {
           bw.write(content);
           bw.flush();
        } catch (IOException e) {
            logger.error(e);
        }

    }

    public static ThreadSafeFileWriter getInstance() {
        return instance;
    }
}
