/**
 * Copyright (c) 2018-2023 Contributors to the Gamma project
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * SPDX-License-Identifier: EPL-1.0
 */
package hu.bme.mit.gamma.xsts.codegeneration.c.platforms;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to manage the platforms and retrieve platform instances.
 */
@SuppressWarnings("all")
public class Platforms {
  /**
   * Map to store the platform instances.
   */
  private static final Map<SupportedPlatforms, IPlatform> platforms = Platforms.addPlatforms();

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
