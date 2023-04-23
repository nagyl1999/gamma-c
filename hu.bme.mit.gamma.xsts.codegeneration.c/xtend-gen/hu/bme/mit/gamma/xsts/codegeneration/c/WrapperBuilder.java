package hu.bme.mit.gamma.xsts.codegeneration.c;

import hu.bme.mit.gamma.xsts.model.XSTS;
import org.eclipse.emf.common.util.URI;

@SuppressWarnings("all")
public class WrapperBuilder implements IStatechartCode {
  private XSTS xsts;

  private String name;

  private String stName;

  public WrapperBuilder(final XSTS xsts) {
  }

  @Override
  public void constructHeader() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }

  @Override
  public void constructCode() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }

  @Override
  public void save(final URI uri) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
