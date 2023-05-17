package hu.bme.mit.gamma.xsts.codegeneration.c

import org.eclipse.emf.common.util.URI
import hu.bme.mit.gamma.xsts.codegeneration.c.platforms.SupportedPlatforms

/**
 * An interface for defining statechart code generation behavior.
 */
interface IStatechartCode {
	/**
	 * Constructs the header of the statechart code.
	 */
	public def void constructHeader();
	
	/**
	 * Constructs the body of the statechart code.
	 */
	public def void constructCode();
	
	/**
	 * Saves the generated code to the specified URI.
	 * 
	 * @param uri the URI to save the code to
	 */
	public def void save(URI uri);
	
	/**
 	 * Sets the platform for the generator.
 	 * 
 	 * @param platform the platform to set
 	 */
	public def void setPlatform(SupportedPlatforms platform);
}