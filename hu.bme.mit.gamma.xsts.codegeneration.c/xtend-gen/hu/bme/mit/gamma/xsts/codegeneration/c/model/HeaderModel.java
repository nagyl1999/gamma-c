package hu.bme.mit.gamma.xsts.codegeneration.c.model;

import java.util.ArrayList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

/**
 * Represents a C header file model.
 */
@SuppressWarnings("all")
public class HeaderModel extends FileModel {
  /**
   * Extra headers apart from the basic imports
   */
  private ArrayList<String> headers = new ArrayList<String>();

  /**
   * Creates a new HeaderModel instance with the given name.
   * 
   * @param name the name of the header file
   */
  public HeaderModel(final String name) {
    super(new Function0<String>() {
      @Override
      public String apply() {
        StringConcatenation _builder = new StringConcatenation();
        String _lowerCase = name.toLowerCase();
        _builder.append(_lowerCase);
        _builder.append(".h");
        return _builder.toString();
      }
    }.apply());
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include <stdbool.h>");
    _builder.newLine();
    _builder.append("#include <sys/time.h>");
    _builder.newLine();
    {
      boolean _hasElements = false;
      for(final String header : this.headers) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("\n", "");
        }
        _builder.append(header);
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("/* header guard */");
    _builder.newLine();
    _builder.append("#ifndef ");
    String _upperCase = name.toUpperCase();
    _builder.append(_upperCase);
    _builder.append("_HEADER");
    _builder.newLineIfNotEmpty();
    _builder.append("#define ");
    String _upperCase_1 = name.toUpperCase();
    _builder.append(_upperCase_1);
    _builder.append("_HEADER");
    _builder.newLineIfNotEmpty();
    this.content = _builder.toString();
  }
}
