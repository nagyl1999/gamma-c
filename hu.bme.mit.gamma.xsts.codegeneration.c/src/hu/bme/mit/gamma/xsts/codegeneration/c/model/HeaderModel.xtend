package hu.bme.mit.gamma.xsts.codegeneration.c.model

import java.nio.file.Files
import java.nio.file.Paths
import org.eclipse.emf.common.util.URI
import java.io.File

class HeaderModel {
	private String name;
	private String content;
	
	public new(String name) {
		this.name = '''«name.toLowerCase».h''';
		this.content = '''
			#include <stdbool.h>
		''';
	}
	
	public def void save(URI uri) {
		val URI local = uri.appendSegment(name);
		if (new File(local.toFileString()).exists())
			Files.delete(Paths.get(local.toFileString()));
		
		Files.createFile(Paths.get(local.toFileString()));
		Files.write(Paths.get(local.toFileString()), content.getBytes);
	}
	
	public def void addContent(String content) {
		this.content += content;
	}
	
	public override String toString() {
		return this.content;
	}
	
}