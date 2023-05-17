package hu.bme.mit.gamma.xsts.codegeneration.c.platforms

import java.util.HashMap

/**
 * Enum representing the supported platforms.
 */
enum SupportedPlatforms {
	UNIX
}

/**
 * Class to manage the platforms and retrieve platform instances.
 */
class Platforms {
	
	/**
     * Map to store the platform instances.
     */
	private static final HashMap<SupportedPlatforms, IPlatform> platforms = addPlatforms();
	
	/**
     * Adds the platform instances to the map.
     *
     * @return The map of platforms.
     */
	private static def addPlatforms() {
		val temp = new HashMap<SupportedPlatforms, IPlatform>();
		
		/* add all available platforms here */
		temp.put(SupportedPlatforms.UNIX, new UnixPlatform());
		
		return temp;
	}
	
	/**
     * Gets the platform instance for the given platform enum constant.
     *
     * @param platform The supported platform.
     * @return The platform instance.
     */
	public static def get(SupportedPlatforms platform) {
		return platforms.get(platform);
	}
	
}