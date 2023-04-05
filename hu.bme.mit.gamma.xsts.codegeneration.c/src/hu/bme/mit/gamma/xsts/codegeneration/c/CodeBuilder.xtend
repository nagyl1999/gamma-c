package hu.bme.mit.gamma.xsts.codegeneration.c

import hu.bme.mit.gamma.xsts.model.*;
import hu.bme.mit.gamma.xsts.codegeneration.c.model.*;
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.*;

class CodeBuilder {
	
	private XSTS xsts;
	private String name;
	
	private CodeModel code;
	private HeaderModel header;
	
	private final extension TypeDeclarationSerializer typeDeclarationSerializer = new TypeDeclarationSerializer
	
	public new(XSTS xsts) {
		this.xsts = xsts;
		this.name = xsts.name.toFirstUpper;
		this.code = new CodeModel(name);
		this.header = new HeaderModel(name);
	}
	
	public def void constructHeader() {
		/* TypeDeclarations */
		header.addContent('''«FOR typeDeclaration : xsts.typeDeclarations»«typeDeclaration.serialize»«ENDFOR»''');
	}
	
	public def void constructCode() {
		
	}
	
	public def void save() {
		code.save();
		header.save();
	}
	
}