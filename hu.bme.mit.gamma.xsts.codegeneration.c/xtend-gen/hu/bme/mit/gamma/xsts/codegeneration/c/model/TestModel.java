package hu.bme.mit.gamma.xsts.codegeneration.c.model;

import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

@SuppressWarnings("all")
public class TestModel extends FileModel {
  public TestModel(final String name) {
    super(new Function0<String>() {
      @Override
      public String apply() {
        StringConcatenation _builder = new StringConcatenation();
        String _lowerCase = name.toLowerCase();
        _builder.append(_lowerCase);
        _builder.append("_test.c");
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
    _builder.append("#ifdef _WIN32");
    _builder.newLine();
    _builder.append("#include <windows.h>");
    _builder.newLine();
    _builder.append("#define sleep(x) Sleep(x * 1000)");
    _builder.newLine();
    _builder.append("#else");
    _builder.newLine();
    _builder.append("#include <unistd.h>");
    _builder.newLine();
    _builder.append("#define sleep(x) sleep(x)");
    _builder.newLine();
    _builder.append("#endif");
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
