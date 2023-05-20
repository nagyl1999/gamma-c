package hu.bme.mit.gamma.xsts.codegeneration.c.model

/**
 * The CodeModel represents the C code file to be generated. It extends the FileModel class.
 * It contains the file name and content.
 */
class CodeModel extends FileModel {
	
	/**
	 * Creates a new CodeModel instance with the given name.
	 * 
	 * @param name the name of the C file to be generated
	 */
	new(String name) {
		super('''«name.toLowerCase».c''');
		this.content = '''
			#include <stdio.h>
			#include <stdlib.h>
			#include <stdbool.h>
			
			#include "«name.toLowerCase».h"
		''';
	}
	
}