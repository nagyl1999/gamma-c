package hu.bme.mit.gamma.xsts.codegeneration.c

import hu.bme.mit.gamma.xsts.model.*;
import hu.bme.mit.gamma.xsts.codegeneration.c.model.*;
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.*;

import java.io.File;
import java.nio.file.Files
import java.nio.file.Paths
import org.eclipse.emf.common.util.URI

class CodeBuilder {
	
	private XSTS xsts;
	private String name;
	private String stName;
	
	private CodeModel code;
	private HeaderModel header;
	
	private final TypeDeclarationSerializer typeDeclarationSerializer = new TypeDeclarationSerializer;
	private final VariableDeclarationSerializer variableDeclarationSerializer = new VariableDeclarationSerializer;
	
	public new(XSTS xsts) {
		this.xsts = xsts;
		this.name = xsts.name.toFirstUpper;
		this.code = new CodeModel(name);
		this.header = new HeaderModel(name);
		this.stName = name + "Statechart";
	}
	
	public def void constructHeader() {
		/* Enum Type Declarations */
		header.addContent('''«FOR typeDeclaration : xsts.typeDeclarations»«typeDeclarationSerializer.serialize(typeDeclaration)»«ENDFOR»''');
		/* Struct Declaration */
		header.addContent('''
			
			/* Structure representing «name» component */
			typedef struct {
				«FOR variableDeclaration : xsts.variableDeclarations SEPARATOR '\n'»«variableDeclarationSerializer.serialize(variableDeclaration.type, variableDeclaration.name)» «variableDeclaration.name»;«ENDFOR»
			} «stName»;
		''');
	}
	
	public def void constructCode() {
		
	}
	
	public def void save(URI uri) {
		/* create src-gen if not present */
		var URI local = uri.appendSegment("src-gen");
		if (!new File(local.toFileString()).exists())
			Files.createDirectories(Paths.get(local.toFileString()));
			
		/* create c codegen folder if not present */
		local = local.appendSegment(name.toLowerCase)
		if (!new File(local.toFileString()).exists())
			Files.createDirectories(Paths.get(local.toFileString()));
		
		/* save models */
		code.save(local);
		header.save(local);
	}
	
}