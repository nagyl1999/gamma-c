package hu.bme.mit.gamma.xsts.codegeneration.c.model

class TestModel extends FileModel {
	
	public new(String name) {
		super('''«name.toLowerCase»_test.c''');
		this.content = '''
			#include <stdio.h>
			#include <stdlib.h>
			#include <stdbool.h>
			
			#ifdef _WIN32
			#include <windows.h>
			#define sleep(x) Sleep(x * 1000)
			#else
			#include <unistd.h>
			#define sleep(x) sleep(x)
			#endif
			
			#include "«name.toLowerCase».h"
		''';
	}
	
}