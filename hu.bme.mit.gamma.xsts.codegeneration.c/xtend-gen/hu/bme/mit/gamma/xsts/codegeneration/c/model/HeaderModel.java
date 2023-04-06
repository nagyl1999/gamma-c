package hu.bme.mit.gamma.xsts.codegeneration.c.model;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class HeaderModel {
  private String name;

  private String content;

  public HeaderModel(final String name) {
    StringConcatenation _builder = new StringConcatenation();
    String _lowerCase = name.toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(".h");
    this.name = _builder.toString();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("#include <stdbool.h>");
    _builder_1.newLine();
    this.content = _builder_1.toString();
  }

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

  public void addContent(final String content) {
    String _content = this.content;
    this.content = (_content + content);
  }

  @Override
  public String toString() {
    return this.content;
  }
}