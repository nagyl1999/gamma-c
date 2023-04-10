package hu.bme.mit.gamma.xsts.codegeneration.c

import java.util.ArrayList;
import hu.bme.mit.gamma.xsts.model.*;
import hu.bme.mit.gamma.xsts.codegeneration.c.model.*;
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.*;

import java.io.File;
import java.nio.file.Files
import java.nio.file.Paths
import org.eclipse.emf.common.util.URI
import hu.bme.mit.gamma.expression.model.TypeReference

class CodeBuilder {
	
	private XSTS xsts;
	private String name;
	private String stName;
	
	private CodeModel code;
	private TestModel test;
	private HeaderModel header;
	
	private final ActionSerializer actionSerializer = new ActionSerializer;
	private final TypeDeclarationSerializer typeDeclarationSerializer = new TypeDeclarationSerializer;
	private final VariableDeclarationSerializer variableDeclarationSerializer = new VariableDeclarationSerializer;
	
	public static ArrayList<String> componentVariables = new ArrayList();
	
	public new(XSTS xsts) {
		this.xsts = xsts;
		this.name = xsts.name.toFirstUpper;
		this.code = new CodeModel(name);
		this.test = new TestModel(name);
		this.header = new HeaderModel(name);
		this.stName = name + "Statechart";
		xsts.variableDeclarations.forEach[variableDeclaration |
    		componentVariables.add(variableDeclaration.name)
		]
	}
	
	public def void constructHeader() {
		/* Enum Type Declarations */
		header.addContent('''«FOR typeDeclaration : xsts.typeDeclarations SEPARATOR '\n'»«typeDeclarationSerializer.serialize(typeDeclaration)»«ENDFOR»''');

		/* Struct Declaration */
		header.addContent('''
			/* Structure representing «name» component */
			typedef struct {
				«FOR variableDeclaration : xsts.variableDeclarations SEPARATOR '\n'»«variableDeclarationSerializer.serialize(variableDeclaration.type, variableDeclaration.name)» «variableDeclaration.name»;«ENDFOR»
			} «stName»;
		''');
		
		/* Declare functions */
		header.addContent('''
			/* Reset component «name» */
			void reset«stName»(«stName»* statechart);
			/* Initialize component «name» */
			void initialize«stName»(«stName»* statechart);
			/* Entry event of component «name» */
			void entryEvents«stName»(«stName»* statechart);
			/* Clear input events of component «name» */
			void clearInEvents«stName»(«stName»* statechart);
			/* Clear output events of component «name» */
			void clearOutEvents«stName»(«stName»* statechart);
			/* Transitions of component «name» */
			void changeState«stName»(«stName»* statechart);
			/* Run cycle in component «name» */
			void runCycle«stName»(«stName»* statechart);
		''');
		
		/* End if in header guard */
		header.addContent('''
			#endif /* «name.toUpperCase»_HEADER */
		''');
	}
	
	public def void constructCode() {
		/* Reset struct */
		code.addContent('''
			/* Reset component «name» */
			void reset«stName»(«stName»* statechart) {
				«actionSerializer.serialize(xsts.variableInitializingTransition.action)»
			}
		''');
		
		/* Initialize struct */
		code.addContent('''
			/* Initialize component «name» */
			void initialize«stName»(«stName»* statechart) {
				«actionSerializer.serialize(xsts.configurationInitializingTransition.action)»
			}
		''');
		
		/* Entry Events */
		code.addContent('''
			/* Entry event of component «name» */
			void entryEvents«stName»(«stName»* statechart) {
				«actionSerializer.serialize(xsts.entryEventTransition.action)»
			}
		''');
		
		/* Reset In Events */
		code.addContent('''
			/* Clear input events of component «name» */
			void clearInEvents«stName»(«stName»* statechart) {
				«actionSerializer.serialize(xsts.inEventTransition.action)»
			}
		''');
		
		/* Reset Out Events */
		code.addContent('''
			/* Clear output events of component «name» */
			void clearOutEvents«stName»(«stName»* statechart) {
				«actionSerializer.serialize(xsts.outEventTransition.action)»
			}
		''');
		
		/* Transitions */
		code.addContent('''
			/* Transitions of component «name» */
			void changeState«stName»(«stName»* statechart) {
				«FOR transition : xsts.transitions SEPARATOR '\n'»«actionSerializer.serialize(transition.action)»«ENDFOR»
			}
		''');
		
		/* Run cycle */
		code.addContent('''
			/* Run cycle of component «name» */
			void runCycle«stName»(«stName»* statechart) {
				clearOutEvents«stName»(statechart);
				changeState«stName»(statechart);
				clearInEvents«stName»(statechart);
			}
		''');
	}
	
	public def void constructTest() {
		/* Print states */
		test.addContent('''
		void printStates(«stName»* statechart) {
			«FOR variableDeclaration : xsts.variableDeclarations SEPARATOR '\n'»
				«IF variableDeclaration.type instanceof TypeReference»
					printf("«variableDeclaration.name»: %d\n", statechart->«variableDeclaration.name»);
				«ENDIF»
			«ENDFOR»
		}
		''');
		
		/* Main function */
		test.addContent('''
			/* Main function */
			int main() {
				«stName» statechart;
				
				resetSystemStatechart(&statechart);
				initializeSystemStatechart(&statechart);
				entryEventsSystemStatechart(&statechart);
				
				while (1) {
					printStates(&statechart);
					runCycleSystemStatechart(&statechart);
					sleep(1);
				}
			}
		''');
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
		test.save(local);
		header.save(local);
		
	}
	
}