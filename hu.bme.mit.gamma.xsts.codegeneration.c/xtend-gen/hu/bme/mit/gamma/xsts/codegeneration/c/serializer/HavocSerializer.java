package hu.bme.mit.gamma.xsts.codegeneration.c.serializer;

import hu.bme.mit.gamma.expression.model.BooleanTypeDefinition;
import hu.bme.mit.gamma.expression.model.DecimalTypeDefinition;
import hu.bme.mit.gamma.expression.model.DirectReferenceExpression;
import hu.bme.mit.gamma.expression.model.EnumerationLiteralDefinition;
import hu.bme.mit.gamma.expression.model.EnumerationTypeDefinition;
import hu.bme.mit.gamma.expression.model.Expression;
import hu.bme.mit.gamma.expression.model.IntegerTypeDefinition;
import hu.bme.mit.gamma.expression.model.RationalTypeDefinition;
import hu.bme.mit.gamma.expression.model.Type;
import java.util.Arrays;
import java.util.Random;
import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Serializes different types of definitions and expressions into their string representation.
 * Supports boolean, integer, decimal, rational, and enumeration types.
 * Also supports direct reference expressions.
 */
@SuppressWarnings("all")
public class HavocSerializer {
  private Random random = new Random();

  /**
   * Throws an exception for unsupported types.
   * 
   * @param type the type to be serialized
   * @param name the name of the type to be serialized
   * @return an exception message for unsupported types
   * @throws IllegalArgumentException if the type is not supported
   */
  protected String _serialize(final Type type, final String name) {
    throw new IllegalArgumentException(("Not supported type: " + type));
  }

  /**
   * Serializes boolean types into their string representation.
   * 
   * @param type the boolean type to be serialized
   * @param name the name of the boolean type to be serialized
   * @return the string representation of the serialized boolean type
   */
  protected String _serialize(final BooleanTypeDefinition type, final String name) {
    return Boolean.valueOf(this.random.nextBoolean()).toString();
  }

  /**
   * Serializes integer types into their string representation.
   * 
   * @param type the integer type to be serialized
   * @param name the name of the integer type to be serialized
   * @return the string representation of the serialized integer type
   */
  protected String _serialize(final IntegerTypeDefinition type, final String name) {
    return Integer.valueOf(this.random.nextInt()).toString();
  }

  /**
   * Serializes decimal types into their string representation.
   * 
   * @param type the decimal type to be serialized
   * @param name the name of the decimal type to be serialized
   * @return the string representation of the serialized decimal type
   */
  protected String _serialize(final DecimalTypeDefinition type, final String name) {
    return Float.valueOf(this.random.nextFloat()).toString();
  }

  /**
   * Serializes rational types into their string representation.
   * 
   * @param type the rational type to be serialized
   * @param name the name of the rational type to be serialized
   * @return the string representation of the serialized rational type
   */
  protected String _serialize(final RationalTypeDefinition type, final String name) {
    return Float.valueOf(this.random.nextFloat()).toString();
  }

  /**
   * Serializes enumeration types into their string representation.
   * 
   * @param type the enumeration type to be serialized
   * @param name the name of the enumeration type to be serialized
   * @return the string representation of the serialized enumeration type
   */
  protected String _serialize(final EnumerationTypeDefinition type, final String name) {
    final EnumerationLiteralDefinition literal = type.getLiterals().get(this.random.nextInt(type.getLiterals().size()));
    StringConcatenation _builder = new StringConcatenation();
    String _name = literal.getName();
    _builder.append(_name);
    _builder.append("_");
    String _lowerCase = name.toLowerCase();
    _builder.append(_lowerCase);
    return _builder.toString();
  }

  /**
   * Serializes the given expression.
   * 
   * @param expression the expression to serialize
   * @return the serialized expression as a string
   * @throws IllegalArgumentException if the expression is not supported
   */
  protected String _serialize(final Expression expression) {
    throw new IllegalArgumentException(("Not supported expression: " + expression));
  }

  /**
   * Serializes direct reference expressions into their string representation.
   * 
   * @param expression the direct reference expression to be serialized
   * @return the string representation of the serialized direct reference expression
   */
  protected String _serialize(final DirectReferenceExpression expression) {
    return this.serialize(expression.getDeclaration().getType(), expression.getDeclaration().getName());
  }

  public String serialize(final Type type, final String name) {
    if (type instanceof EnumerationTypeDefinition) {
      return _serialize((EnumerationTypeDefinition)type, name);
    } else if (type instanceof DecimalTypeDefinition) {
      return _serialize((DecimalTypeDefinition)type, name);
    } else if (type instanceof IntegerTypeDefinition) {
      return _serialize((IntegerTypeDefinition)type, name);
    } else if (type instanceof RationalTypeDefinition) {
      return _serialize((RationalTypeDefinition)type, name);
    } else if (type instanceof BooleanTypeDefinition) {
      return _serialize((BooleanTypeDefinition)type, name);
    } else if (type != null) {
      return _serialize(type, name);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(type, name).toString());
    }
  }

  public String serialize(final Expression expression) {
    if (expression instanceof DirectReferenceExpression) {
      return _serialize((DirectReferenceExpression)expression);
    } else if (expression != null) {
      return _serialize(expression);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expression).toString());
    }
  }
}
