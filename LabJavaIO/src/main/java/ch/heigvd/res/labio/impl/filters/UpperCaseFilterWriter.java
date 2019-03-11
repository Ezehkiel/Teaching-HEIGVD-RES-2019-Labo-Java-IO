package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    super.write(str.substring(off, off+ len).toUpperCase(), 0, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    char[] tmp = cbuf;
    for (int i = 0; i < len; ++i){
      tmp[i+off] = Character.toUpperCase(tmp[i+off]);
    }
    super.write(tmp, off, len);

  }

  @Override
  public void write(int c) throws IOException {
    super.write(Character.toUpperCase(c));
  }

}
