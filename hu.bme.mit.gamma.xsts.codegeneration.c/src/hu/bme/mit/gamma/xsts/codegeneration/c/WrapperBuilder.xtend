package hu.bme.mit.gamma.xsts.codegeneration.c

import hu.bme.mit.gamma.xsts.model.XSTS
import org.eclipse.emf.common.util.URI
import hu.bme.mit.gamma.xsts.codegeneration.c.model.CodeModel
import hu.bme.mit.gamma.xsts.codegeneration.c.model.HeaderModel
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.VariableDiagnoser
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.VariableDeclarationSerializer
import hu.bme.mit.gamma.expression.model.VariableDeclaration
import java.util.HashSet
import hu.bme.mit.gamma.xsts.codegeneration.c.platforms.SupportedPlatforms

class WrapperBuilder implements IStatechartCode {
	
	private XSTS xsts;
	private String name;
	private String stName;
	private HashSet<VariableDeclaration> inputs = new HashSet();
	private HashSet<VariableDeclaration> outputs = new HashSet();
	
	private CodeModel code;
	private HeaderModel header;
	
	private SupportedPlatforms platform = SupportedPlatforms.UNIX;
	
	private final VariableDiagnoser variableDiagnoser = new VariableDiagnoser;
	private final VariableDeclarationSerializer variableDeclarationSerializer = new VariableDeclarationSerializer;
	
	public new(XSTS xsts) {
		this.xsts = xsts
		this.name = xsts.name.toFirstUpper + "Wrapper";
		this.stName = xsts.name + "Statechart";
		/* code files */
		this.code = new CodeModel(name);
		this.header = new HeaderModel(name);
		/* in & out events and parameters in a unique set */
		inputs.addAll(variableDiagnoser.retrieveInEvents(xsts));
		inputs.addAll(variableDiagnoser.retrieveInEventParameters(xsts));
		outputs.addAll(variableDiagnoser.retrieveOutEvents(xsts) );
		outputs.addAll(variableDiagnoser.retrieveOutEventParameters(xsts));
	}
	
	public override setPlatform(SupportedPlatforms platform) {
		this.platform = platform;
	}
	
	override constructHeader() {
		/* Inculde statechart header */
		header.addContent('''
			#include "«xsts.name.toLowerCase».h"
		''');
		
		/* Wrapper Struct */
		header.addContent('''
			/* Wrapper for statechart «stName» */
			typedef struct {
				«stName» «stName.toLowerCase»;
				struct timeval tval_before, tval_after, tval_result;
			} «name»;
		''');
		
		/* Initialization & Cycle declaration */
		header.addContent('''
			/* Initialize component «name» */
			void initialize«name»(«name» *statechart);
			/* Calculate Timeout events */
			void time«name»(«name»* statechart);
			/* Run cycle of component «name» */
			void runCycle«name»(«name»* statechart);
		''');
		
		/* Setter declarations */
		header.addContent('''
			«FOR variable : inputs»
				/* Setter for «variable.name.toFirstUpper» */
				void set«variable.name.toFirstUpper»(«name»* statechart, «variableDeclarationSerializer.serialize(variable.type, variable.name)» value);
			«ENDFOR»
		''');
		
		/* Getter declarations */
		header.addContent('''
		«FOR variable : outputs»
			/* Getter for «variable.name.toFirstUpper» */
			«variableDeclarationSerializer.serialize(variable.type, variable.name)» get«variable.name.toFirstUpper»(«name»* statechart);
		«ENDFOR»
		''');
		
		/* End if in header guard */
		header.addContent('''
			#endif /* «name.toUpperCase»_HEADER */
		''');
	}
	
	override constructCode() {
		/* Initialize wrapper & Run cycle*/
		code.addContent('''
			/* Initialize component «name» */
			void initialize«name»(«name»* statechart) {
				gettimeofday(&statechart->tval_before, NULL);  // start measuring time during initialization
				reset«stName»(&statechart->«stName.toLowerCase»);
				initialize«stName»(&statechart->«stName.toLowerCase»);
				entryEvents«stName»(&statechart->«stName.toLowerCase»);
			}
			
			/* Calculate Timeout events */
			void time«name»(«name»* statechart) {
				gettimeofday(&statechart->tval_after, NULL);
				timersub(&statechart->tval_after, &statechart->tval_before, &statechart->tval_result);
				int milliseconds = (int)statechart->tval_result.tv_sec * 1000 + (int)statechart->tval_result.tv_usec / 1000;
				«FOR variable : variableDiagnoser.retrieveTimeouts(xsts) SEPARATOR '\n'»
					/* Add elapsed time to timeout variable «variable.name» */
					statechart->«stName.toLowerCase».«variable.name» += milliseconds;
				«ENDFOR»
				gettimeofday(&statechart->tval_before, NULL);
			}
			
			/* Run cycle of component «name» */
			void runCycle«name»(«name»* statechart) {
				time«name»(statechart);
				runCycle«stName»(&statechart->«stName.toLowerCase»);
			}
		''');
		
		/* In Events & Parameters */
		code.addContent('''
			«FOR variable : inputs SEPARATOR '\n'»
				/* Setter for «variable.name.toFirstUpper» */
				void set«variable.name.toFirstUpper»(«name»* statechart, «variableDeclarationSerializer.serialize(variable.type, variable.name)» value) {
					statechart->«stName.toLowerCase».«variable.name» = value;
				}
			«ENDFOR»
		''');
		
		/* Out Events & Parameters */
		code.addContent('''
		«FOR variable : outputs SEPARATOR '\n'»
			/* Getter for «variable.name.toFirstUpper» */
			«variableDeclarationSerializer.serialize(variable.type, variable.name)» get«variable.name.toFirstUpper»(«name»* statechart) {
				return statechart->«stName.toLowerCase».«variable.name»;
			}
		«ENDFOR»
		''');
	}
	
	override save(URI uri) {
		/* create src-gen if not present */
		var URI local = uri.appendSegment("src-gen");
		if (!new File(local.toFileString()).exists())
			Files.createDirectories(Paths.get(local.toFileString()));
			
		/* create c codegen folder if not present */
		local = local.appendSegment(xsts.name.toLowerCase)
		if (!new File(local.toFileString()).exists())
			Files.createDirectories(Paths.get(local.toFileString()));
		
		/* save models */
		code.save(local);
		header.save(local);
	}
	
	
	
}