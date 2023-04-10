package hu.bme.mit.gamma.xsts.codegeneration.c;

import hu.bme.mit.gamma.expression.model.TypeDeclaration;
import hu.bme.mit.gamma.expression.model.VariableDeclaration;
import hu.bme.mit.gamma.xsts.codegeneration.c.model.CodeModel;
import hu.bme.mit.gamma.xsts.codegeneration.c.model.HeaderModel;
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.ActionSerializer;
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.TypeDeclarationSerializer;
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.VariableDeclarationSerializer;
import hu.bme.mit.gamma.xsts.model.XSTS;
import hu.bme.mit.gamma.xsts.model.XTransition;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class CodeBuilder {
  private XSTS xsts;

  private String name;

  private String stName;

  private CodeModel code;

  private HeaderModel header;

  private final ActionSerializer actionSerializer = new ActionSerializer();

  private final TypeDeclarationSerializer typeDeclarationSerializer = new TypeDeclarationSerializer();

  private final VariableDeclarationSerializer variableDeclarationSerializer = new VariableDeclarationSerializer();

  public static ArrayList<String> componentVariables = new ArrayList<String>();

  public CodeBuilder(final XSTS xsts) {
    this.xsts = xsts;
    this.name = StringExtensions.toFirstUpper(xsts.getName());
    CodeModel _codeModel = new CodeModel(this.name);
    this.code = _codeModel;
    HeaderModel _headerModel = new HeaderModel(this.name);
    this.header = _headerModel;
    this.stName = (this.name + "Statechart");
    final Consumer<VariableDeclaration> _function = (VariableDeclaration variableDeclaration) -> {
      CodeBuilder.componentVariables.add(variableDeclaration.getName());
    };
    xsts.getVariableDeclarations().forEach(_function);
  }

  public void constructHeader() {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<TypeDeclaration> _typeDeclarations = this.xsts.getTypeDeclarations();
      for(final TypeDeclaration typeDeclaration : _typeDeclarations) {
        String _serialize = this.typeDeclarationSerializer.serialize(typeDeclaration);
        _builder.append(_serialize);
      }
    }
    this.header.addContent(_builder.toString());
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("/* Structure representing ");
    _builder_1.append(this.name);
    _builder_1.append(" component */");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("typedef struct {");
    _builder_1.newLine();
    _builder_1.append("\t");
    {
      EList<VariableDeclaration> _variableDeclarations = this.xsts.getVariableDeclarations();
      boolean _hasElements = false;
      for(final VariableDeclaration variableDeclaration : _variableDeclarations) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder_1.appendImmediate("\n", "\t");
        }
        String _serialize_1 = this.variableDeclarationSerializer.serialize(variableDeclaration.getType(), variableDeclaration.getName());
        _builder_1.append(_serialize_1, "\t");
        _builder_1.append(" ");
        String _name = variableDeclaration.getName();
        _builder_1.append(_name, "\t");
        _builder_1.append(";");
      }
    }
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("} ");
    _builder_1.append(this.stName);
    _builder_1.append(";");
    _builder_1.newLineIfNotEmpty();
    this.header.addContent(_builder_1.toString());
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("/* Reset component ");
    _builder_2.append(this.name);
    _builder_2.append(" */");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("void reset");
    _builder_2.append(this.stName);
    _builder_2.append("(");
    _builder_2.append(this.stName);
    _builder_2.append("* statechart);");
    _builder_2.newLineIfNotEmpty();
    _builder_2.newLine();
    _builder_2.append("/* Initialize component ");
    _builder_2.append(this.name);
    _builder_2.append(" */");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("void initialize");
    _builder_2.append(this.stName);
    _builder_2.append("(");
    _builder_2.append(this.stName);
    _builder_2.append("* statechart);");
    _builder_2.newLineIfNotEmpty();
    _builder_2.newLine();
    _builder_2.append("/* Entry event of component ");
    _builder_2.append(this.name);
    _builder_2.append(" */");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("void entryEvents");
    _builder_2.append(this.stName);
    _builder_2.append("(");
    _builder_2.append(this.stName);
    _builder_2.append("* statechart);");
    _builder_2.newLineIfNotEmpty();
    _builder_2.newLine();
    _builder_2.append("/* Clear input events of component ");
    _builder_2.append(this.name);
    _builder_2.append(" */");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("void clearInEvents");
    _builder_2.append(this.stName);
    _builder_2.append("(");
    _builder_2.append(this.stName);
    _builder_2.append("* statechart);");
    _builder_2.newLineIfNotEmpty();
    _builder_2.newLine();
    _builder_2.append("/* Clear output events of component ");
    _builder_2.append(this.name);
    _builder_2.append(" */");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("void clearOutEvents");
    _builder_2.append(this.stName);
    _builder_2.append("(");
    _builder_2.append(this.stName);
    _builder_2.append("* statechart);");
    _builder_2.newLineIfNotEmpty();
    _builder_2.newLine();
    _builder_2.append("/* Transitions of component ");
    _builder_2.append(this.name);
    _builder_2.append(" */");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("void changeState");
    _builder_2.append(this.stName);
    _builder_2.append("(");
    _builder_2.append(this.stName);
    _builder_2.append("* statechart);");
    _builder_2.newLineIfNotEmpty();
    _builder_2.newLine();
    _builder_2.append("/* Run cycle in component ");
    _builder_2.append(this.name);
    _builder_2.append(" */");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("void runCycle");
    _builder_2.append(this.stName);
    _builder_2.append("(");
    _builder_2.append(this.stName);
    _builder_2.append("* statechart);");
    _builder_2.newLineIfNotEmpty();
    this.header.addContent(_builder_2.toString());
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("#endif /* ");
    String _upperCase = this.name.toUpperCase();
    _builder_3.append(_upperCase);
    _builder_3.append("_HEADER */");
    _builder_3.newLineIfNotEmpty();
    this.header.addContent(_builder_3.toString());
  }

  public void constructCode() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/* Reset component ");
    _builder.append(this.name);
    _builder.append(" */");
    _builder.newLineIfNotEmpty();
    _builder.append("void reset");
    _builder.append(this.stName);
    _builder.append("(");
    _builder.append(this.stName);
    _builder.append("* statechart) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _serialize = this.actionSerializer.serialize(this.xsts.getVariableInitializingTransition().getAction());
    _builder.append(_serialize, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    this.code.addContent(_builder.toString());
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("/* Initialize component ");
    _builder_1.append(this.name);
    _builder_1.append(" */");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("void initialize");
    _builder_1.append(this.stName);
    _builder_1.append("(");
    _builder_1.append(this.stName);
    _builder_1.append("* statechart) {");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t");
    CharSequence _serialize_1 = this.actionSerializer.serialize(this.xsts.getConfigurationInitializingTransition().getAction());
    _builder_1.append(_serialize_1, "\t");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("}");
    _builder_1.newLine();
    this.code.addContent(_builder_1.toString());
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("/* Entry event of component ");
    _builder_2.append(this.name);
    _builder_2.append(" */");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("void entryEvents");
    _builder_2.append(this.stName);
    _builder_2.append("(");
    _builder_2.append(this.stName);
    _builder_2.append("* statechart) {");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("\t");
    CharSequence _serialize_2 = this.actionSerializer.serialize(this.xsts.getEntryEventTransition().getAction());
    _builder_2.append(_serialize_2, "\t");
    _builder_2.newLineIfNotEmpty();
    _builder_2.append("}");
    _builder_2.newLine();
    this.code.addContent(_builder_2.toString());
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("/* Clear input events of component ");
    _builder_3.append(this.name);
    _builder_3.append(" */");
    _builder_3.newLineIfNotEmpty();
    _builder_3.append("void clearInEvents");
    _builder_3.append(this.stName);
    _builder_3.append("(");
    _builder_3.append(this.stName);
    _builder_3.append("* statechart) {");
    _builder_3.newLineIfNotEmpty();
    _builder_3.append("\t");
    CharSequence _serialize_3 = this.actionSerializer.serialize(this.xsts.getInEventTransition().getAction());
    _builder_3.append(_serialize_3, "\t");
    _builder_3.newLineIfNotEmpty();
    _builder_3.append("}");
    _builder_3.newLine();
    this.code.addContent(_builder_3.toString());
    StringConcatenation _builder_4 = new StringConcatenation();
    _builder_4.append("/* Clear output events of component ");
    _builder_4.append(this.name);
    _builder_4.append(" */");
    _builder_4.newLineIfNotEmpty();
    _builder_4.append("void clearOutEvents");
    _builder_4.append(this.stName);
    _builder_4.append("(");
    _builder_4.append(this.stName);
    _builder_4.append("* statechart) {");
    _builder_4.newLineIfNotEmpty();
    _builder_4.append("\t");
    CharSequence _serialize_4 = this.actionSerializer.serialize(this.xsts.getOutEventTransition().getAction());
    _builder_4.append(_serialize_4, "\t");
    _builder_4.newLineIfNotEmpty();
    _builder_4.append("}");
    _builder_4.newLine();
    this.code.addContent(_builder_4.toString());
    StringConcatenation _builder_5 = new StringConcatenation();
    _builder_5.append("/* Transitions of component ");
    _builder_5.append(this.name);
    _builder_5.append(" */");
    _builder_5.newLineIfNotEmpty();
    _builder_5.append("void changeState");
    _builder_5.append(this.stName);
    _builder_5.append("(");
    _builder_5.append(this.stName);
    _builder_5.append("* statechart) {");
    _builder_5.newLineIfNotEmpty();
    _builder_5.append("\t");
    {
      EList<XTransition> _transitions = this.xsts.getTransitions();
      boolean _hasElements = false;
      for(final XTransition transition : _transitions) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder_5.appendImmediate("\n", "\t");
        }
        CharSequence _serialize_5 = this.actionSerializer.serialize(transition.getAction());
        _builder_5.append(_serialize_5, "\t");
      }
    }
    _builder_5.newLineIfNotEmpty();
    _builder_5.append("}");
    _builder_5.newLine();
    this.code.addContent(_builder_5.toString());
    StringConcatenation _builder_6 = new StringConcatenation();
    _builder_6.append("/* Run cycle of component ");
    _builder_6.append(this.name);
    _builder_6.append(" */");
    _builder_6.newLineIfNotEmpty();
    _builder_6.append("void runCycle");
    _builder_6.append(this.stName);
    _builder_6.append("(");
    _builder_6.append(this.stName);
    _builder_6.append("* statechart) {");
    _builder_6.newLineIfNotEmpty();
    _builder_6.append("\t");
    _builder_6.append("clearOutEvents");
    _builder_6.append(this.stName, "\t");
    _builder_6.append("(statechart);");
    _builder_6.newLineIfNotEmpty();
    _builder_6.append("\t");
    _builder_6.append("changeState");
    _builder_6.append(this.stName, "\t");
    _builder_6.append("(statechart);");
    _builder_6.newLineIfNotEmpty();
    _builder_6.append("\t");
    _builder_6.append("clearInEvents");
    _builder_6.append(this.stName, "\t");
    _builder_6.append("(statechart);");
    _builder_6.newLineIfNotEmpty();
    _builder_6.append("}");
    _builder_6.newLine();
    this.code.addContent(_builder_6.toString());
  }

  public void save(final URI uri) {
    try {
      URI local = uri.appendSegment("src-gen");
      String _fileString = local.toFileString();
      boolean _exists = new File(_fileString).exists();
      boolean _not = (!_exists);
      if (_not) {
        Files.createDirectories(Paths.get(local.toFileString()));
      }
      local = local.appendSegment(this.name.toLowerCase());
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
