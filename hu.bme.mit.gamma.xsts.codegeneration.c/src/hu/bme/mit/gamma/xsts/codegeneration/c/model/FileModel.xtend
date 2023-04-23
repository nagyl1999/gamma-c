package hu.bme.mit.gamma.xsts.codegeneration.c.model

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import org.eclipse.emf.common.util.URI


/**
 * Represents a file in the generated C code.
 */
abstract class FileModel {
	/** The name of the file. */
	protected String name;
	/** The content of the file. */
	protected String content;
	
	/** New line */
	public static final String NEW_LINE =
	'''

	''';
	
	/**
     * Constructs a new {@code FileModel} instance with the given name.
     * 
     * @param name the name of the file
     */
	public new(String name) {
		this.name = name
	}
	
	/**
     * Saves the file to the given URI.
     * 
     * @param uri the URI where the file should be saved
     */
	public def void save(URI uri) {
		val URI local = uri.appendSegment(name);
		if (new File(local.toFileString()).exists())
			Files.delete(Paths.get(local.toFileString()));
			
		Files.createFile(Paths.get(local.toFileString()));
		Files.write(Paths.get(local.toFileString()), content.getBytes);
	}
	
	/**
     * Adds content to the file.
     * 
     * @param content the content to be added to the file
     */
	public def void addContent(String content) {
		this.content += NEW_LINE + content;
	}
	
	/**
     * Returns the content of the file.
     * 
     * @return the content of the file
     */
	public override String toString() {
		return this.content;
	}
	
}