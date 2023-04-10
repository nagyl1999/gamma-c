package hu.bme.mit.gamma.xsts.codegeneration.c.model;

import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

/**
 * The CodeModel represents the C code file to be generated. It extends the FileModel class.
 * It contains the file name and content.
 */
@SuppressWarnings("all")
public class CodeModel extends FileModel {
  /**
   * Creates a new CodeModel instance with the given name.
   * 
   * @param name the name of the C file to be generated
   */
  public CodeModel(final String name) {
    super(new Function0<String>() {
      @Override
      public String apply() {
        StringConcatenation _builder = new StringConcatenation();
        String _lowerCase = name.toLowerCase();
        _builder.append(_lowerCase);
        _builder.append(".c");
        return _builder.toString();
      }
    }.apply());
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include <stdio.h>");
    _builder.newLine();
    _builder.append("#include <stdlib.h>");
    _builder.newLine();
    _builder.append("#include <stdbool.h>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#include \"");
    String _lowerCase = name.toLowerCase();
    _builder.append(_lowerCase);
    _builder.append(".h\"");
    _builder.newLineIfNotEmpty();
    this.content = _builder.toString();
  }
}
