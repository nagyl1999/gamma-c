package hu.bme.mit.gamma.xsts.codegeneration.c.serializer

import hu.bme.mit.gamma.xsts.model.*;
import hu.bme.mit.gamma.expression.model.*;

import static extension hu.bme.mit.gamma.xsts.derivedfeatures.XstsDerivedFeatures.*;


/**
 * This class provides a serializer for actions in XSTS models.
 */
class ActionSerializer {
	
	private final ExpressionSerializer expressionSerializer = new ExpressionSerializer;
	private final TypeDeclarationSerializer typeDeclarationSerializer = new TypeDeclarationSerializer;
	private final VariableDeclarationSerializer variableDeclarationSerializer = new VariableDeclarationSerializer;
	
	/**
	 * Serializes an initializing action.
 	 * 
 	 * @param xSts an XSTS model
  	 * @return a CharSequence that represents the serialized initializing action
  	 */
	def CharSequence serializeInitializingAction(XSTS xSts) {
		return '''«xSts.initializingAction.serialize»''';
	}
	
	/**
 	 * Throws an IllegalArgumentException if the action is not supported.
	 * 
 	 * @param action an action
	 * @return a CharSequence that represents the serialized action
 	 */
	def dispatch CharSequence serialize(Action action) {
		throw new IllegalArgumentException("Not supported action: " + action);
	}
	
	/**
 	 * Serializes an IfAction.
 	 * 
  	 * @param action an IfAction
	 * @return a CharSequence that represents the serialized IfAction
 	 */
	def dispatch CharSequence serialize(IfAction action) {
		return '''
			if («expressionSerializer.serialize(action.condition)») {
				«action.then.serialize»
			}else {
				«action.^else.serialize»
			}
		''';
	}
	
	/**
 	 * Serializes a SequentialAction.
	 * 
	 * @param action a SequentialAction
 	 * @return a CharSequence that represents the serialized SequentialAction
	 */
	def dispatch CharSequence serialize(SequentialAction action) {
		return '''«FOR xstsSubaction : action.actions SEPARATOR '\n'»«xstsSubaction.serialize»«ENDFOR»''';
	}
	
	/**
	 * Serializes a ParallelAction.
	 * 
	 * @param action a ParallelAction
	 * @return a CharSequence that represents the serialized ParallelAction
	 */
	def dispatch CharSequence serialize(ParallelAction action) {
		return '''«FOR xstsSubaction : action.actions SEPARATOR '\n'»«xstsSubaction.serialize»«ENDFOR»''';
	}
	
	/**
	 * Serializes a NonDeterministicAction.
	 * 
	 * @param action a NonDeterministicAction
	 * @return a CharSequence that represents the serialized NonDeterministicAction
	 */
	def dispatch CharSequence serialize(NonDeterministicAction action) {
		return '''«FOR xstsSubaction : action.actions SEPARATOR '\n'»«xstsSubaction.serialize»«ENDFOR»''';
	}
	
	/**
	 * Serializes an AssignmentAction.
	 * 
	 * @param action an AssignmentAction
	 * @return a CharSequence that represents the serialized AssignmentAction
	 */
	def dispatch CharSequence serialize(AssignmentAction action) {
		return '''«expressionSerializer.serialize(action.lhs)» = «expressionSerializer.serialize(action.rhs)»;''';
	}
	
	/**
	 * Serializes a VariableDeclarationAction.
	 * 
	 * @param action a VariableDeclarationAction
	 * @return a CharSequence that represents the serialized VariableDeclarationAction
	 */
	def dispatch CharSequence serialize(VariableDeclarationAction action) {
		return '''«variableDeclarationSerializer.serialize(action.variableDeclaration.type, action.variableDeclaration.name)» «action.variableDeclaration.name»;''';
	}
	
	/**
	 * Serializes an EmptyAction.
	 * 
	 * @param action an EmptyAction
	 * @return a CharSequence that represents the serialized EmptyAction
	 */
	def dispatch CharSequence serialize(EmptyAction action) {
		return '''/* Empty Action */''';
	}
	
}