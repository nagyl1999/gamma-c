package hu.bme.mit.gamma.xsts.codegeneration.c;

import hu.bme.mit.gamma.expression.model.TypeDeclaration;
import hu.bme.mit.gamma.expression.model.VariableDeclaration;
import hu.bme.mit.gamma.xsts.codegeneration.c.model.CodeModel;
import hu.bme.mit.gamma.xsts.codegeneration.c.model.HeaderModel;
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.TypeDeclarationSerializer;
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.VariableDeclarationSerializer;
import hu.bme.mit.gamma.xsts.model.XSTS;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
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

  private final TypeDeclarationSerializer typeDeclarationSerializer = new TypeDeclarationSerializer();

  private final VariableDeclarationSerializer variableDeclarationSerializer = new VariableDeclarationSerializer();

  public CodeBuilder(final XSTS xsts) {
    this.xsts = xsts;
    this.name = StringExtensions.toFirstUpper(xsts.getName());
    CodeModel _codeModel = new CodeModel(this.name);
    this.code = _codeModel;
    HeaderModel _headerModel = new HeaderModel(this.name);
    this.header = _headerModel;
    this.stName = (this.name + "Statechart");
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
    _builder_1.newLine();
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
  }

  public void constructCode() {
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
