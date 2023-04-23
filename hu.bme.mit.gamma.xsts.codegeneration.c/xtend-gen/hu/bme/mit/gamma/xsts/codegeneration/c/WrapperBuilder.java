package hu.bme.mit.gamma.xsts.codegeneration.c;

import hu.bme.mit.gamma.xsts.codegeneration.c.model.CodeModel;
import hu.bme.mit.gamma.xsts.codegeneration.c.model.HeaderModel;
import hu.bme.mit.gamma.xsts.model.XSTS;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class WrapperBuilder implements IStatechartCode {
  private XSTS xsts;

  private String name;

  private String stName;

  private CodeModel code;

  private HeaderModel header;

  public WrapperBuilder(final XSTS xsts) {
    this.xsts = xsts;
    String _firstUpper = StringExtensions.toFirstUpper(xsts.getName());
    String _plus = (_firstUpper + "Wrapper");
    this.name = _plus;
    this.stName = (this.name + "Statechart");
    CodeModel _codeModel = new CodeModel(this.name);
    this.code = _codeModel;
    HeaderModel _headerModel = new HeaderModel(this.name);
    this.header = _headerModel;
  }

  @Override
  public void constructHeader() {
  }

  @Override
  public void constructCode() {
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
