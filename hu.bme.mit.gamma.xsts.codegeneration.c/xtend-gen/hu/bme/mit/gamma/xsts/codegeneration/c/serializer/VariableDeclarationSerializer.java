package hu.bme.mit.gamma.xsts.codegeneration.c.serializer;

import hu.bme.mit.gamma.expression.model.BooleanTypeDefinition;
import hu.bme.mit.gamma.expression.model.DecimalTypeDefinition;
import hu.bme.mit.gamma.expression.model.EnumerationTypeDefinition;
import hu.bme.mit.gamma.expression.model.IntegerTypeDefinition;
import hu.bme.mit.gamma.expression.model.RationalTypeDefinition;
import hu.bme.mit.gamma.expression.model.Type;
import hu.bme.mit.gamma.expression.model.TypeReference;
import java.util.Arrays;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Serializer for variable declarations.
 */
@SuppressWarnings("all")
public class VariableDeclarationSerializer {
  /**
   * Transforms a string with underscores to camel case by converting each word's first letter
   * after an underscore to uppercase.
   * 
   * @param input the string to transform
   * @return the transformed string in camel case
   */
  public String transformString(final String input) {
    final String[] parts = input.split("_");
    final Function1<String, String> _function = (String it) -> {
      return VariableDeclarationSerializer.toFirstUpper(it);
    };
    final List<String> transformedParts = ListExtensions.<String, String>map(((List<String>)Conversions.doWrapArray(parts)), _function);
    return IterableExtensions.join(transformedParts, "_");
  }

  /**
   * Converts a string to title case by capitalizing the first letter.
   * 
   * @param input the string to convert
   * @return the converted string in title case
   */
  public static String toFirstUpper(final String input) {
    String _upperCase = input.substring(0, 1).toUpperCase();
    String _substring = input.substring(1);
    return (_upperCase + _substring);
  }

  /**
   * Throws an IllegalArgumentException since the Type class is not supported.
   * 
   * @param type the Type object to serialize
   * @param name the name of the variable declaration
   * @return nothing, an exception is thrown
   * @throws IllegalArgumentException always
   */
  protected String _serialize(final Type type, final String name) {
    throw new IllegalArgumentException(("Not supported type: " + type));
  }

  /**
   * Serializes the TypeReference object by calling the serialize method on the referenced type.
   * 
   * @param type the TypeReference object to serialize
   * @param name the name of the variable declaration
   * @return the serialized type reference as a string
   */
  protected String _serialize(final TypeReference type, final String name) {
    StringConcatenation _builder = new StringConcatenation();
    String _serialize = this.serialize(type.getReference().getType(), name);
    _builder.append(_serialize);
    return _builder.toString();
  }

  /**
   * Serializes the BooleanTypeDefinition object as 'bool'.
   * 
   * @param type the BooleanTypeDefinition object to serialize
   * @param name the name of the variable declaration
   * @return the serialized boolean type as a string
   */
  protected String _serialize(final BooleanTypeDefinition type, final String name) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("bool");
    return _builder.toString();
  }

  /**
   * Serializes the IntegerTypeDefinition object as 'int'.
   * 
   * @param type the IntegerTypeDefinition object to serialize
   * @param name the name of the variable declaration
   * @return the serialized integer type as a string
   */
  protected String _serialize(final IntegerTypeDefinition type, final String name) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("int");
    return _builder.toString();
  }

  /**
   * Serializes the DecimalTypeDefinition object as 'float'.
   * 
   * @param type the DecimalTypeDefinition object to serialize
   * @param name the name of the variable declaration
   * @return the serialized decimal type as a string
   */
  protected String _serialize(final DecimalTypeDefinition type, final String name) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("float");
    return _builder.toString();
  }

  /**
   * Serializes the RationalTypeDefinition object as 'float'.
   * 
   * @param type the RationalTypeDefinition object to serialize
   * @param name the name of the variable declaration
   * @return the serialized rational type as a string
   */
  protected String _serialize(final RationalTypeDefinition type, final String name) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("float");
    return _builder.toString();
  }

  /**
   * Serializes the EnumerationTypeDefinition object as an enum with the transformed name.
   * 
   * @param type the EnumerationTypeDefinition object to serialize
   * @param name the name of the variable declaration
   * @return the serialized enum name as a string
   */
  protected String _serialize(final EnumerationTypeDefinition type, final String name) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("enum ");
    String _transformString = this.transformString(name);
    _builder.append(_transformString);
    return _builder.toString();
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
    } else if (type instanceof TypeReference) {
      return _serialize((TypeReference)type, name);
    } else if (type != null) {
      return _serialize(type, name);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(type, name).toString());
    }
  }
}
