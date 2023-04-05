package hu.bme.mit.gamma.xsts.codegeneration.c.model;

import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.InputOutput;

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
