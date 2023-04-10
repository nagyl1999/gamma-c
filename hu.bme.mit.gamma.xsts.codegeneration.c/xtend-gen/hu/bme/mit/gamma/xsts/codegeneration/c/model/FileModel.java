package hu.bme.mit.gamma.xsts.codegeneration.c.model;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Represents a file in the generated C code.
 */
@SuppressWarnings("all")
public class FileModel {
  /**
   * The name of the file.
   */
  protected String name;

  /**
   * The content of the file.
   */
  protected String content;

  /**
   * Constructs a new {@code FileModel} instance with the given name.
   * 
   * @param name the name of the file
   */
  public FileModel(final String name) {
    this.name = name;
  }

  /**
   * Saves the file to the given URI.
   * 
   * @param uri the URI where the file should be saved
   */
  public void save(final URI uri) {
    try {
      final URI local = uri.appendSegment(this.name);
      String _fileString = local.toFileString();
      boolean _exists = new File(_fileString).exists();
      if (_exists) {
        Files.delete(Paths.get(local.toFileString()));
      }
      Files.createFile(Paths.get(local.toFileString()));
      Files.write(Paths.get(local.toFileString()), this.content.getBytes());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Adds content to the file.
   * 
   * @param content the content to be added to the file
   */
  public void addContent(final String content) {
    String _content = this.content;
    this.content = (_content + content);
  }

  /**
   * Returns the content of the file.
   * 
   * @return the content of the file
   */
  @Override
  public String toString() {
    return this.content;
  }
}
