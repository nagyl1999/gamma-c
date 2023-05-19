package hu.bme.mit.gamma.xsts.codegeneration.c;

import com.google.common.collect.Iterables;
import hu.bme.mit.gamma.expression.model.VariableDeclaration;
import hu.bme.mit.gamma.xsts.codegeneration.c.model.CodeModel;
import hu.bme.mit.gamma.xsts.codegeneration.c.model.HeaderModel;
import hu.bme.mit.gamma.xsts.codegeneration.c.platforms.Platforms;
import hu.bme.mit.gamma.xsts.codegeneration.c.platforms.SupportedPlatforms;
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.VariableDeclarationSerializer;
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.VariableDiagnoser;
import hu.bme.mit.gamma.xsts.model.XSTS;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class WrapperBuilder implements IStatechartCode {
  private XSTS xsts;

  private String name;

  private String stName;

  private HashSet<VariableDeclaration> inputs = new HashSet<VariableDeclaration>();

  private HashSet<VariableDeclaration> outputs = new HashSet<VariableDeclaration>();

  private CodeModel code;

  private HeaderModel header;

  private SupportedPlatforms platform = SupportedPlatforms.UNIX;

  private final VariableDiagnoser variableDiagnoser = new VariableDiagnoser();

  private final VariableDeclarationSerializer variableDeclarationSerializer = new VariableDeclarationSerializer();

  public WrapperBuilder(final XSTS xsts) {
    this.xsts = xsts;
    String _firstUpper = StringExtensions.toFirstUpper(xsts.getName());
    String _plus = (_firstUpper + "Wrapper");
    this.name = _plus;
    String _name = xsts.getName();
    String _plus_1 = (_name + "Statechart");
    this.stName = _plus_1;
    CodeModel _codeModel = new CodeModel(this.name);
    this.code = _codeModel;
    HeaderModel _headerModel = new HeaderModel(this.name);
    this.header = _headerModel;
    Iterables.<VariableDeclaration>addAll(this.inputs, this.variableDiagnoser.retrieveInEvents(xsts));
    Iterables.<VariableDeclaration>addAll(this.inputs, this.variableDiagnoser.retrieveInEventParameters(xsts));
    Iterables.<VariableDeclaration>addAll(this.outputs, this.variableDiagnoser.retrieveOutEvents(xsts));
    Iterables.<VariableDeclaration>addAll(this.outputs, this.variableDiagnoser.retrieveOutEventParameters(xsts));
  }

  @Override
  public void setPlatform(final SupportedPlatforms platform) {
    this.platform = platform;
  }

  @Override
  public void constructHeader() {
    StringConcatenation _builder = new StringConcatenation();
    String _headers = Platforms.get(this.platform).getHeaders();
    _builder.append(_headers);
    _builder.newLineIfNotEmpty();
    this.header.addContent(_builder.toString());
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("#include \"");
    String _lowerCase = this.xsts.getName().toLowerCase();
    _builder_1.append(_lowerCase);
    _builder_1.append(".h\"");
    _builder_1.newLineIfNotEmpty();
    this.header.addContent(_builder_1.toString());
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("/* Wrapper for statechart ");
    _builder_2.append(this.stName);
    _builder_2.append(" */");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("typedef struct {");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append(this.stName, "\t");
    _builder_2.append(" ");
    String _lowerCase_1 = this.stName.toLowerCase();
    _builder_2.append(_lowerCase_1, "\t");
    _builder_2.append(";");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    String _struct = Platforms.get(this.platform).getStruct();
    _builder_2.append(_struct, "\t");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("} ");
    _builder_2.append(this.name);
    _builder_2.append(";");
    _builder_2.newLineIfNotEmpty();
    this.header.addContent(_builder_2.toString());
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("/* Initialize component ");
    _builder_3.append(this.name);
    _builder_3.append(" */");
    _builder_3.newLineIfNotEmpty();
    _builder_3.append("void initialize");
    _builder_3.append(this.name);
    _builder_3.append("(");
    _builder_3.append(this.name);
    _builder_3.append(" *statechart);");
    _builder_3.newLineIfNotEmpty();
    _builder_3.append("/* Calculate Timeout events */");
    _builder_3.newLine();
    _builder_3.append("void time");
    _builder_3.append(this.name);
    _builder_3.append("(");
    _builder_3.append(this.name);
    _builder_3.append("* statechart);");
    _builder_3.newLineIfNotEmpty();
    _builder_3.append("/* Run cycle of component ");
    _builder_3.append(this.name);
    _builder_3.append(" */");
    _builder_3.newLineIfNotEmpty();
    _builder_3.append("void runCycle");
    _builder_3.append(this.name);
    _builder_3.append("(");
    _builder_3.append(this.name);
    _builder_3.append("* statechart);");
    _builder_3.newLineIfNotEmpty();
    this.header.addContent(_builder_3.toString());
    StringConcatenation _builder_4 = new StringConcatenation();
    {
      for(final VariableDeclaration variable : this.inputs) {
        _builder_4.append("/* Setter for ");
        String _firstUpper = StringExtensions.toFirstUpper(variable.getName());
        _builder_4.append(_firstUpper);
        _builder_4.append(" */");
        _builder_4.newLineIfNotEmpty();
        _builder_4.append("void set");
        String _firstUpper_1 = StringExtensions.toFirstUpper(variable.getName());
        _builder_4.append(_firstUpper_1);
        _builder_4.append("(");
        _builder_4.append(this.name);
        _builder_4.append("* statechart, ");
        String _serialize = this.variableDeclarationSerializer.serialize(variable.getType(), variable.getName());
        _builder_4.append(_serialize);
        _builder_4.append(" value);");
        _builder_4.newLineIfNotEmpty();
      }
    }
    this.header.addContent(_builder_4.toString());
    StringConcatenation _builder_5 = new StringConcatenation();
    {
      for(final VariableDeclaration variable_1 : this.outputs) {
        _builder_5.append("/* Getter for ");
        String _firstUpper_2 = StringExtensions.toFirstUpper(variable_1.getName());
        _builder_5.append(_firstUpper_2);
        _builder_5.append(" */");
        _builder_5.newLineIfNotEmpty();
        String _serialize_1 = this.variableDeclarationSerializer.serialize(variable_1.getType(), variable_1.getName());
        _builder_5.append(_serialize_1);
        _builder_5.append(" get");
        String _firstUpper_3 = StringExtensions.toFirstUpper(variable_1.getName());
        _builder_5.append(_firstUpper_3);
        _builder_5.append("(");
        _builder_5.append(this.name);
        _builder_5.append("* statechart);");
        _builder_5.newLineIfNotEmpty();
      }
    }
    this.header.addContent(_builder_5.toString());
    StringConcatenation _builder_6 = new StringConcatenation();
    _builder_6.append("#endif /* ");
    String _upperCase = this.name.toUpperCase();
    _builder_6.append(_upperCase);
    _builder_6.append("_HEADER */");
    _builder_6.newLineIfNotEmpty();
    this.header.addContent(_builder_6.toString());
  }

  @Override
  public void constructCode() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/* Initialize component ");
    _builder.append(this.name);
    _builder.append(" */");
    _builder.newLineIfNotEmpty();
    _builder.append("void initialize");
    _builder.append(this.name);
    _builder.append("(");
    _builder.append(this.name);
    _builder.append("* statechart) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _initialization = Platforms.get(this.platform).getInitialization();
    _builder.append(_initialization, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("reset");
    _builder.append(this.stName, "\t");
    _builder.append("(&statechart->");
    String _lowerCase = this.stName.toLowerCase();
    _builder.append(_lowerCase, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("initialize");
    _builder.append(this.stName, "\t");
    _builder.append("(&statechart->");
    String _lowerCase_1 = this.stName.toLowerCase();
    _builder.append(_lowerCase_1, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("entryEvents");
    _builder.append(this.stName, "\t");
    _builder.append("(&statechart->");
    String _lowerCase_2 = this.stName.toLowerCase();
    _builder.append(_lowerCase_2, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/* Calculate Timeout events */");
    _builder.newLine();
    _builder.append("void time");
    _builder.append(this.name);
    _builder.append("(");
    _builder.append(this.name);
    _builder.append("* statechart) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _timer = Platforms.get(this.platform).getTimer();
    _builder.append(_timer, "\t");
    _builder.newLineIfNotEmpty();
    {
      Iterable<VariableDeclaration> _retrieveTimeouts = this.variableDiagnoser.retrieveTimeouts(this.xsts);
      for(final VariableDeclaration variable : _retrieveTimeouts) {
        _builder.append("\t");
        _builder.append("/* Add elapsed time to timeout variable ");
        String _name = variable.getName();
        _builder.append(_name, "\t");
        _builder.append(" */");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("statechart->");
        String _lowerCase_3 = this.stName.toLowerCase();
        _builder.append(_lowerCase_3, "\t");
        _builder.append(".");
        String _name_1 = variable.getName();
        _builder.append(_name_1, "\t");
        _builder.append(" += milliseconds;");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/* Run cycle of component ");
    _builder.append(this.name);
    _builder.append(" */");
    _builder.newLineIfNotEmpty();
    _builder.append("void runCycle");
    _builder.append(this.name);
    _builder.append("(");
    _builder.append(this.name);
    _builder.append("* statechart) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("time");
    _builder.append(this.name, "\t");
    _builder.append("(statechart);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("runCycle");
    _builder.append(this.stName, "\t");
    _builder.append("(&statechart->");
    String _lowerCase_4 = this.stName.toLowerCase();
    _builder.append(_lowerCase_4, "\t");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    this.code.addContent(_builder.toString());
    StringConcatenation _builder_1 = new StringConcatenation();
    {
      boolean _hasElements = false;
      for(final VariableDeclaration variable_1 : this.inputs) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder_1.appendImmediate("\n", "");
        }
        _builder_1.append("/* Setter for ");
        String _firstUpper = StringExtensions.toFirstUpper(variable_1.getName());
        _builder_1.append(_firstUpper);
        _builder_1.append(" */");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("void set");
        String _firstUpper_1 = StringExtensions.toFirstUpper(variable_1.getName());
        _builder_1.append(_firstUpper_1);
        _builder_1.append("(");
        _builder_1.append(this.name);
        _builder_1.append("* statechart, ");
        String _serialize = this.variableDeclarationSerializer.serialize(variable_1.getType(), variable_1.getName());
        _builder_1.append(_serialize);
        _builder_1.append(" value) {");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("\t");
        _builder_1.append("statechart->");
        String _lowerCase_5 = this.stName.toLowerCase();
        _builder_1.append(_lowerCase_5, "\t");
        _builder_1.append(".");
        String _name_2 = variable_1.getName();
        _builder_1.append(_name_2, "\t");
        _builder_1.append(" = value;");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("}");
        _builder_1.newLine();
      }
    }
    this.code.addContent(_builder_1.toString());
    StringConcatenation _builder_2 = new StringConcatenation();
    {
      boolean _hasElements_1 = false;
      for(final VariableDeclaration variable_2 : this.outputs) {
        if (!_hasElements_1) {
          _hasElements_1 = true;
        } else {
          _builder_2.appendImmediate("\n", "");
        }
        _builder_2.append("/* Getter for ");
        String _firstUpper_2 = StringExtensions.toFirstUpper(variable_2.getName());
        _builder_2.append(_firstUpper_2);
        _builder_2.append(" */");
        _builder_2.newLineIfNotEmpty();
        String _serialize_1 = this.variableDeclarationSerializer.serialize(variable_2.getType(), variable_2.getName());
        _builder_2.append(_serialize_1);
        _builder_2.append(" get");
        String _firstUpper_3 = StringExtensions.toFirstUpper(variable_2.getName());
        _builder_2.append(_firstUpper_3);
        _builder_2.append("(");
        _builder_2.append(this.name);
        _builder_2.append("* statechart) {");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        _builder_2.append("return statechart->");
        String _lowerCase_6 = this.stName.toLowerCase();
        _builder_2.append(_lowerCase_6, "\t");
        _builder_2.append(".");
        String _name_3 = variable_2.getName();
        _builder_2.append(_name_3, "\t");
        _builder_2.append(";");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("}");
        _builder_2.newLine();
      }
    }
    this.code.addContent(_builder_2.toString());
  }

  @Override
  public void save(final URI uri) {
    try {
      URI local = uri.appendSegment("src-gen");
      String _fileString = local.toFileString();
      boolean _exists = new File(_fileString).exists();
      boolean _not = (!_exists);
      if (_not) {
        Files.createDirectories(Paths.get(local.toFileString()));
      }
      local = local.appendSegment(this.xsts.getName().toLowerCase());
      String _fileString_1 = local.toFileString();
      boolean _exists_1 = new File(_fileString_1).exists();
      boolean _not_1 = (!_exists_1);
      if (_not_1) {
        Files.createDirectories(Paths.get(local.toFileString()));
      }
      this.code.save(local);
      this.header.save(local);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
