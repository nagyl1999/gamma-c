package hu.bme.mit.gamma.xsts.codegeneration.c.platforms;

import java.util.HashMap;

/**
 * Class to manage the platforms and retrieve platform instances.
 */
@SuppressWarnings("all")
public class Platforms {
  /**
   * Map to store the platform instances.
   */
  private static final HashMap<SupportedPlatforms, IPlatform> platforms = Platforms.addPlatforms();

  /**
   * Adds the platform instances to the map.
   * 
   * @return The map of platforms.
   */
  private static HashMap<SupportedPlatforms, IPlatform> addPlatforms() {
    final HashMap<SupportedPlatforms, IPlatform> temp = new HashMap<SupportedPlatforms, IPlatform>();
    UnixPlatform _unixPlatform = new UnixPlatform();
    temp.put(SupportedPlatforms.UNIX, _unixPlatform);
    return temp;
  }

  /**
   * Gets the platform instance for the given platform enum constant.
   * 
   * @param platform The supported platform.
   * @return The platform instance.
   */
  public static IPlatform get(final SupportedPlatforms platform) {
    return Platforms.platforms.get(platform);
  }
}
