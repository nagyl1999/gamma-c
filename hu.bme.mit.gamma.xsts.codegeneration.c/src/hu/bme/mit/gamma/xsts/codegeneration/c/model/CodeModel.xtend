package hu.bme.mit.gamma.xsts.codegeneration.c.model

class CodeModel {
	private String name;
	private String content;
	
	public new(String name) {
		this.name = name;
		this.content = '''
			#include <stdio.h>
			#include <stdlib.h>
			#include <stdbool.h>
		''';
	}
	
	public def void save() {
		println(toString());
	}
	
	public def void addContent(String content) {
		this.content += content;
	}
	
	public override String toString() {
		return this.content;
	}
	
}