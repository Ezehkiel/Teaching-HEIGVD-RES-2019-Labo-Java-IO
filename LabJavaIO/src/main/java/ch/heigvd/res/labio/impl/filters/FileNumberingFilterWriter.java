package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {
  private int lineCounter = 1;
  private int previousChar = -1;
  private static final char R_SEPARATOR = '\r';
  private static final char N_SEPARATOR = '\n';
  private static final String T_SEPARATOR = "\t";

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    this.write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = off; i < off + len; i++){
      this.write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {

    /* Check if this is the first line */
    if (lineCounter == 1){
      out.write(lineCounter + T_SEPARATOR);
      out.write(c);
      lineCounter++;
    } else {
      // Check for the MACOS separator
      if (previousChar == R_SEPARATOR && c != N_SEPARATOR) {
        out.write(lineCounter + T_SEPARATOR);
        lineCounter++;
      }

      out.write(c);

      /* Check if we have a Unix / Windows new line */
      if (c == N_SEPARATOR){
        out.write(lineCounter + T_SEPARATOR);
        lineCounter++;
      }
    }
    /* update previous char to further check if it is the MACOS separator */
    previousChar = c;
  }
}
