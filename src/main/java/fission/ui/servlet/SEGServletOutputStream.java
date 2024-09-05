package fission.ui.servlet;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

/**
 * A concrete implementation of the ServletOutputStream. There is no
 * unique functionality. The ServletOutputStream is protected mandating this
 * subclass.
 */
public class SEGServletOutputStream extends ServletOutputStream
{
  /**
   * The underlying byte stream.
   */
  private ByteArrayOutputStream byteArrayOutputStream;
  
  /**
   * A wrapper to the byte stream for easier manipulation.
   */
  private DataOutputStream dataOutputStream;

  public SEGServletOutputStream()
  {
    initDataOutputStream();
  }

  public void write(int b) throws IOException
  {
    dataOutputStream.write(b);
  }

  public void write(byte[] b) throws IOException
  {
    dataOutputStream.write(b);
  }

  public void write(byte[] b, int off, int len) throws IOException
  {
    dataOutputStream.write(b, off, len);
  }
  
  /**
   * Gets the current contents of the output stream as a byte array.
   * @return
   */
  public byte[] getData() throws IOException
  {
    dataOutputStream.flush();
    
    return byteArrayOutputStream.toByteArray();    
  }

  /**
   * A misnomer since there doesn't really exist a "clear" for the underlying
   * byte stream. Instead, the current stream is closed and new ones
   * are initialized.
   * @throws IOException
   */
  public void clear() throws IOException
  {
    close();
    
    initDataOutputStream();
  }
  
  /**
   * Creates the underling ByteArrayOutputStream and its wrapper
   * DataOutputStream.
   */
  private void initDataOutputStream()
  {
    byteArrayOutputStream = new ByteArrayOutputStream(); 
    
    dataOutputStream = new DataOutputStream(byteArrayOutputStream);    
  }
  
  /**
   * Closes and then nullifiees the underlying streams.
   * @throws IOException
   */
  public void close() throws IOException
  {
    if (dataOutputStream != null)
    {
      dataOutputStream.close();
    }
    
    if (byteArrayOutputStream != null)
    {
      byteArrayOutputStream.close();
    }
  }
}
