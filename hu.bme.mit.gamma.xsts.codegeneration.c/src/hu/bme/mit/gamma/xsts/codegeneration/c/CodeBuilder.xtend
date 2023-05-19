package hu.bme.mit.gamma.xsts.codegeneration.c

import java.util.ArrayList;
import hu.bme.mit.gamma.xsts.model.*;
import hu.bme.mit.gamma.xsts.codegeneration.c.model.*;
import hu.bme.mit.gamma.xsts.codegeneration.c.serializer.*;

import java.io.File;
import java.util.HashSet
import java.nio.file.Files
import java.nio.file.Paths
import org.eclipse.emf.common.util.URI
import hu.bme.mit.gamma.expression.model.TypeReference
import hu.bme.mit.gamma.expression.model.VariableDeclaration
import hu.bme.mit.gamma.xsts.codegeneration.c.platforms.SupportedPlatforms
import hu.bme.mit.gamma.lowlevel.xsts.transformation.VariableGroupRetriever
import hu.bme.mit.gamma.expression.model.ClockVariableDeclarationAnnotation

class CodeBuilder implements IStatechartCode {
	
	private XSTS xsts;
	private String name;
	private String stName;
	
	private CodeModel code;
	private TestModel test;
	private HeaderModel header;
	
	private SupportedPlatforms platform = SupportedPlatforms.UNIX;
	
	private final ActionSerializer actionSerializer = new ActionSerializer;
	private final ExpressionSerializer expressionSerializer = new ExpressionSerializer;
	private final VariableGroupRetriever variableGroupRetriever = VariableGroupRetriever.INSTANCE;
	private final TypeDeclarationSerializer typeDeclarationSerializer = new TypeDeclarationSerializer;
	private final VariableDeclarationSerializer variableDeclarationSerializer = new VariableDeclarationSerializer;
	
	private HashSet<VariableDeclaration> inputs = new HashSet();
	private HashSet<VariableDeclaration> outputs = new HashSet();
	public static ArrayList<String> componentVariables = new ArrayList();
	
	public new(XSTS xsts) {
		this.xsts = xsts;
		this.name = xsts.name.toFirstUpper;
		this.stName = name + "Statechart";
		/* code files */
		this.code = new CodeModel(name);
		this.test = new TestModel(name);
		this.header = new HeaderModel(name);
		/* all non-local variable names */
		xsts.variableDeclarations.forEach[variableDeclaration |
    		componentVariables.add(variableDeclaration.name)
		];
		/* in & out events and parameters in a unique set */
		inputs.addAll(variableGroupRetriever.getSystemInEventVariableGroup(xsts).variables);
		inputs.addAll(variableGroupRetriever.getSystemInEventParameterVariableGroup(xsts).variables);
		outputs.addAll(variableGroupRetriever.getSystemOutEventVariableGroup(xsts).variables);
		outputs.addAll(variableGroupRetriever.getSystemOutEventParameterVariableGroup(xsts).variables);
		/* optional */
		this.constructTest();
	}
	
	public override setPlatform(SupportedPlatforms platform) {
		this.platform = platform;
	}
	
	public override void constructHeader() {
		/* Enum Type Declarations */
		header.addContent('''«FOR typeDeclaration : xsts.typeDeclarations SEPARATOR '\n'»
			«typeDeclarationSerializer.serialize(typeDeclaration)»
		«ENDFOR»''');

		/* Struct Declaration */
		header.addContent('''
			/* Structure representing «name» component */
			typedef struct {
				«FOR variableDeclaration : xsts.variableDeclarations»
					«variableDeclarationSerializer.serialize(
						variableDeclaration.type,
						 variableDeclaration.annotations.exists[type | type instanceof ClockVariableDeclarationAnnotation],
						  variableDeclaration.name
					)» «variableDeclaration.name»;
				«ENDFOR»
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
	
	public override void constructCode() {
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
				«FOR input : inputs SEPARATOR '\n'»statechart->«input.name» = «expressionSerializer.serialize(input.expression)»;«ENDFOR»
			}
		''');
		
		/* Reset Out Events */
		code.addContent('''
			/* Clear output events of component «name» */
			void clearOutEvents«stName»(«stName»* statechart) {
				«FOR output : outputs SEPARATOR '\n'»statechart->«output.name» = «expressionSerializer.serialize(output.expression)»;«ENDFOR»
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
			«FOR variableDeclaration : xsts.variableDeclarations»
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
			
				reset«stName»(&statechart);
				initialize«stName»(&statechart);
				entryEvents«stName»(&statechart);
			
				while (1) {
					printStates(&statechart);
					runCycle«stName»(&statechart);
					sleep(1);
				}
			}
		''');
	}
	
	public override void save(URI uri) {
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