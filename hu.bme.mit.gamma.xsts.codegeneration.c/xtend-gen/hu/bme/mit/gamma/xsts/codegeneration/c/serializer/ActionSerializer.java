package hu.bme.mit.gamma.xsts.codegeneration.c.serializer;

import hu.bme.mit.gamma.xsts.derivedfeatures.XstsDerivedFeatures;
import hu.bme.mit.gamma.xsts.model.Action;
import hu.bme.mit.gamma.xsts.model.AssignmentAction;
import hu.bme.mit.gamma.xsts.model.EmptyAction;
import hu.bme.mit.gamma.xsts.model.IfAction;
import hu.bme.mit.gamma.xsts.model.ParallelAction;
import hu.bme.mit.gamma.xsts.model.SequentialAction;
import hu.bme.mit.gamma.xsts.model.VariableDeclarationAction;
import hu.bme.mit.gamma.xsts.model.XSTS;
import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class ActionSerializer {
  private final ExpressionSerializer expressionSerializer = new ExpressionSerializer();

  private final TypeDeclarationSerializer typeDeclarationSerializer = new TypeDeclarationSerializer();

  private final VariableDeclarationSerializer variableDeclarationSerializer = new VariableDeclarationSerializer();

  public CharSequence serializeInitializingAction(final XSTS xSts) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _serialize = this.serialize(XstsDerivedFeatures.getInitializingAction(xSts));
    _builder.append(_serialize);
    return _builder;
  }

  protected CharSequence _serialize(final Action action) {
    throw new IllegalArgumentException(("Not supported action: " + action));
  }

  protected CharSequence _serialize(final IfAction action) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (");
    String _serialize = this.expressionSerializer.serialize(action.getCondition());
    _builder.append(_serialize);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _serialize_1 = this.serialize(action.getThen());
    _builder.append(_serialize_1, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}else {");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _serialize_2 = this.serialize(action.getElse());
    _builder.append(_serialize_2, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  protected CharSequence _serialize(final SequentialAction action) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Action> _actions = action.getActions();
      boolean _hasElements = false;
      for(final Action xStsSubaction : _actions) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("\n", "");
        }
        CharSequence _serialize = this.serialize(xStsSubaction);
        _builder.append(_serialize);
      }
    }
    return _builder;
  }

  protected CharSequence _serialize(final ParallelAction action) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Action> _actions = action.getActions();
      boolean _hasElements = false;
      for(final Action xStsSubaction : _actions) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate("\n", "");
        }
        CharSequence _serialize = this.serialize(xStsSubaction);
        _builder.append(_serialize);
      }
    }
    return _builder;
  }

  protected CharSequence _serialize(final AssignmentAction action) {
    StringConcatenation _builder = new StringConcatenation();
    String _serialize = this.expressionSerializer.serialize(action.getLhs());
    _builder.append(_serialize);
    _builder.append(" = ");
    String _serialize_1 = this.expressionSerializer.serialize(action.getRhs());
    _builder.append(_serialize_1);
    _builder.append(";");
    return _builder;
  }

  protected CharSequence _serialize(final VariableDeclarationAction action) {
    StringConcatenation _builder = new StringConcatenation();
    String _serialize = this.variableDeclarationSerializer.serialize(action.getVariableDeclaration().getType(), action.getVariableDeclaration().getName());
    _builder.append(_serialize);
    _builder.append(" ");
    String _name = action.getVariableDeclaration().getName();
    _builder.append(_name);
    _builder.append(";");
    return _builder;
  }

  protected CharSequence _serialize(final EmptyAction action) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/* Empty Action */");
    return _builder;
  }

  public CharSequence serialize(final Action action) {
    if (action instanceof AssignmentAction) {
      return _serialize((AssignmentAction)action);
    } else if (action instanceof ParallelAction) {
      return _serialize((ParallelAction)action);
    } else if (action instanceof SequentialAction) {
      return _serialize((SequentialAction)action);
    } else if (action instanceof EmptyAction) {
      return _serialize((EmptyAction)action);
    } else if (action instanceof IfAction) {
      return _serialize((IfAction)action);
    } else if (action instanceof VariableDeclarationAction) {
      return _serialize((VariableDeclarationAction)action);
    } else if (action != null) {
      return _serialize(action);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(action).toString());
    }
  }
}
