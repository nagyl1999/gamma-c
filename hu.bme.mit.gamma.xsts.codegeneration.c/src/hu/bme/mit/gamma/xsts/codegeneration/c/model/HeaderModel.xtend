package hu.bme.mit.gamma.xsts.codegeneration.c.model

import java.util.ArrayList;

/**
 * Represents a C header file model.
 */
class HeaderModel extends FileModel {
	
	/**
	 * Extra headers apart from the basic imports
	 */
	private ArrayList<String> headers = new ArrayList();
	
	/**
     * Creates a new HeaderModel instance with the given name.
     * 
     * @param name the name of the header file
     */
	public new(String name) {
		super('''«name.toLowerCase».h''');
		this.content = '''
			#include <stdbool.h>
			«FOR header : headers SEPARATOR '\n'»«header»«ENDFOR»
			
			/* header guard */
			#ifndef «name.toUpperCase»_HEADER
			#define «name.toUpperCase»_HEADER
		''';
	}
	
}