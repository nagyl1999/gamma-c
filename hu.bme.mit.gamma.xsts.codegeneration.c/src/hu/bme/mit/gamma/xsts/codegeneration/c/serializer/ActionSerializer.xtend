package hu.bme.mit.gamma.xsts.codegeneration.c.serializer

import hu.bme.mit.gamma.xsts.model.*;
import hu.bme.mit.gamma.expression.model.*;

import static extension hu.bme.mit.gamma.xsts.derivedfeatures.XstsDerivedFeatures.*;

class ActionSerializer {
	
	private final ExpressionSerializer expressionSerializer = new ExpressionSerializer;
	private final TypeDeclarationSerializer typeDeclarationSerializer = new TypeDeclarationSerializer;
	private final VariableDeclarationSerializer variableDeclarationSerializer = new VariableDeclarationSerializer;
	
	def CharSequence serializeInitializingAction(XSTS xSts) {
		return '''«xSts.initializingAction.serialize»''';
	}
	
	def dispatch CharSequence serialize(Action action) {
		throw new IllegalArgumentException("Not supported action: " + action);
	}
	
	def dispatch CharSequence serialize(IfAction action) {
		return '''
			if («expressionSerializer.serialize(action.condition)») {
				«action.then.serialize»
			}else {
				«action.^else.serialize»
			}
		''';
	}
	
	def dispatch CharSequence serialize(SequentialAction action) {
		return '''«FOR xStsSubaction : action.actions SEPARATOR '\n'»«xStsSubaction.serialize»«ENDFOR»''';
	}
	
	def dispatch CharSequence serialize(ParallelAction action) {
		return '''«FOR xStsSubaction : action.actions SEPARATOR '\n'»«xStsSubaction.serialize»«ENDFOR»''';
	}
	
	def dispatch CharSequence serialize(AssignmentAction action) {
		return '''«expressionSerializer.serialize(action.lhs)» = «expressionSerializer.serialize(action.rhs)»;''';
	}
	
	def dispatch CharSequence serialize(VariableDeclarationAction action) {
		return '''«variableDeclarationSerializer.serialize(action.variableDeclaration.type, action.variableDeclaration.name)» «action.variableDeclaration.name»;''';
	}
	
	def dispatch CharSequence serialize(EmptyAction action) {
		return '''/* Empty Action */''';
	}
	
}