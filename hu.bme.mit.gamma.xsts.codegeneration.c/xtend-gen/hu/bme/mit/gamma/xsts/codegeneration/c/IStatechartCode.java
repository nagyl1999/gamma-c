package hu.bme.mit.gamma.xsts.codegeneration.c;

import hu.bme.mit.gamma.xsts.codegeneration.c.platforms.SupportedPlatforms;
import org.eclipse.emf.common.util.URI;

/**
 * An interface for defining statechart code generation behavior.
 */
@SuppressWarnings("all")
public interface IStatechartCode {
  /**
   * Constructs the header of the statechart code.
   */
  void constructHeader();

  /**
   * Constructs the body of the statechart code.
   */
  void constructCode();

  /**
   * Saves the generated code to the specified URI.
   * 
   * @param uri the URI to save the code to
   */
  void save(final URI uri);

  /**
   * Sets the platform for the generator.
   * 
   * @param platform the platform to set
   */
  void setPlatform(final SupportedPlatforms platform);
}
