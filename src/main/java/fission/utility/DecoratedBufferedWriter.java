package fission.utility;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class DecoratedBufferedWriter extends BufferedWriter {
    /** This BufferedWriter class will supercede the existing
     *  BufferedWriter class so that we can control the output to the
     *  CSV file (we need to reorder the data before it is written.
     */
     
     private List v;
     private BufferedWriter bw;
     
     public DecoratedBufferedWriter(Writer w) {
        super(w);
        bw = new BufferedWriter(w);
        v = new ArrayList();
     }
     
     public void write(String s, int pos, int length) {
        v.add(s.substring(0,s.length()));
     }
     
     public void write(String s) {
        v.add(s);
     }
     
     public void newLine() {
        v.add("\n");
     }
     
     public List getLines() {
        return v;
     }
     
     public void setLines(List v) {
        this.v = v;
     }
     
     public void commitLinesToStream() throws IOException {
        for (int i = 0; i < v.size(); i++) {
            bw.write((String) v.get(i));
        }
        bw.flush();
        v.clear();
     }
     
     public void close() throws IOException {
        super.close();
        bw.close();
     }   
}