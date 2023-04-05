package hu.bme.mit.gamma.xsts.codegeneration.c.model;

import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class CodeModel {
  private String name;

  private String content;

  public CodeModel(final String name) {
    this.name = name;
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include <stdio.h>");
    _builder.newLine();
    _builder.append("#include <stdlib.h>");
    _builder.newLine();
    _builder.append("#include <stdbool.h>");
    _builder.newLine();
    this.content = _builder.toString();
  }

  public void save() {
    InputOutput.<String>println(this.toString());
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
