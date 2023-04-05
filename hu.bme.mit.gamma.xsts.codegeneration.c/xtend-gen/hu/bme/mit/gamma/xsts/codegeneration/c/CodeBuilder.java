package hu.bme.mit.gamma.xsts.codegeneration.c;

import hu.bme.mit.gamma.expression.model.TypeDeclaration;
import hu.bme.mit.gamma.xsts.codegeneration.c.model.CodeModel;
import hu.bme.mit.gamma.xsts.codegeneration.c.model.HeaderModel;
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.TypeDeclarationSerializer;
import hu.bme.mit.gamma.xsts.model.XSTS;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class CodeBuilder {
  private XSTS xsts;

  private String name;

  private CodeModel code;

  private HeaderModel header;

  @Extension
  private final TypeDeclarationSerializer typeDeclarationSerializer = new TypeDeclarationSerializer();

  public CodeBuilder(final XSTS xsts) {
    this.xsts = xsts;
    this.name = StringExtensions.toFirstUpper(xsts.getName());
    CodeModel _codeModel = new CodeModel(this.name);
    this.code = _codeModel;
    HeaderModel _headerModel = new HeaderModel(this.name);
    this.header = _headerModel;
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
  }

  public void constructCode() {
  }

  public void save() {
    this.code.save();
    this.header.save();
  }
}
