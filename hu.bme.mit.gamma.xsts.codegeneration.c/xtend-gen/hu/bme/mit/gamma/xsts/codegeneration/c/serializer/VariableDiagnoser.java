package hu.bme.mit.gamma.xsts.codegeneration.c.serializer;

import com.google.common.collect.Iterables;
import hu.bme.mit.gamma.expression.model.EnumerableTypeDefinition;
import hu.bme.mit.gamma.expression.model.TypeReference;
import hu.bme.mit.gamma.expression.model.VariableDeclaration;
import hu.bme.mit.gamma.xsts.model.ComponentParameterGroup;
import hu.bme.mit.gamma.xsts.model.GroupAnnotation;
import hu.bme.mit.gamma.xsts.model.InEventGroup;
import hu.bme.mit.gamma.xsts.model.InEventParameterGroup;
import hu.bme.mit.gamma.xsts.model.OutEventGroup;
import hu.bme.mit.gamma.xsts.model.OutEventParameterGroup;
import hu.bme.mit.gamma.xsts.model.PlainVariableGroup;
import hu.bme.mit.gamma.xsts.model.RegionGroup;
import hu.bme.mit.gamma.xsts.model.TimeoutGroup;
import hu.bme.mit.gamma.xsts.model.VariableGroup;
import hu.bme.mit.gamma.xsts.model.XSTS;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class VariableDiagnoser {
  public Iterable<VariableDeclaration> retrieveInEvents(final XSTS xSts) {
    final Function1<VariableGroup, Boolean> _function = (VariableGroup it) -> {
      GroupAnnotation _annotation = it.getAnnotation();
      return Boolean.valueOf((_annotation instanceof InEventGroup));
    };
    final Function1<VariableGroup, EList<VariableDeclaration>> _function_1 = (VariableGroup it) -> {
      return it.getVariables();
    };
    return Iterables.<VariableDeclaration>filter((Iterables.<VariableDeclaration>concat(IterableExtensions.<VariableGroup, EList<VariableDeclaration>>map(IterableExtensions.<VariableGroup>filter(xSts.getVariableGroups(), _function), _function_1))), VariableDeclaration.class);
  }

  public Iterable<VariableDeclaration> retrieveOutEvents(final XSTS xSts) {
    final Function1<VariableGroup, Boolean> _function = (VariableGroup it) -> {
      GroupAnnotation _annotation = it.getAnnotation();
      return Boolean.valueOf((_annotation instanceof OutEventGroup));
    };
    final Function1<VariableGroup, EList<VariableDeclaration>> _function_1 = (VariableGroup it) -> {
      return it.getVariables();
    };
    return Iterables.<VariableDeclaration>filter((Iterables.<VariableDeclaration>concat(IterableExtensions.<VariableGroup, EList<VariableDeclaration>>map(IterableExtensions.<VariableGroup>filter(xSts.getVariableGroups(), _function), _function_1))), VariableDeclaration.class);
  }

  public Iterable<VariableDeclaration> retrieveInEventParameters(final XSTS xSts) {
    final Function1<VariableGroup, Boolean> _function = (VariableGroup it) -> {
      GroupAnnotation _annotation = it.getAnnotation();
      return Boolean.valueOf((_annotation instanceof InEventParameterGroup));
    };
    final Function1<VariableGroup, EList<VariableDeclaration>> _function_1 = (VariableGroup it) -> {
      return it.getVariables();
    };
    return Iterables.<VariableDeclaration>filter((Iterables.<VariableDeclaration>concat(IterableExtensions.<VariableGroup, EList<VariableDeclaration>>map(IterableExtensions.<VariableGroup>filter(xSts.getVariableGroups(), _function), _function_1))), VariableDeclaration.class);
  }

  public Iterable<VariableDeclaration> retrieveOutEventParameters(final XSTS xSts) {
    final Function1<VariableGroup, Boolean> _function = (VariableGroup it) -> {
      GroupAnnotation _annotation = it.getAnnotation();
      return Boolean.valueOf((_annotation instanceof OutEventParameterGroup));
    };
    final Function1<VariableGroup, EList<VariableDeclaration>> _function_1 = (VariableGroup it) -> {
      return it.getVariables();
    };
    return Iterables.<VariableDeclaration>filter((Iterables.<VariableDeclaration>concat(IterableExtensions.<VariableGroup, EList<VariableDeclaration>>map(IterableExtensions.<VariableGroup>filter(xSts.getVariableGroups(), _function), _function_1))), VariableDeclaration.class);
  }

  public Iterable<VariableDeclaration> retrieveTimeouts(final XSTS xSts) {
    final Function1<VariableGroup, Boolean> _function = (VariableGroup it) -> {
      GroupAnnotation _annotation = it.getAnnotation();
      return Boolean.valueOf((_annotation instanceof TimeoutGroup));
    };
    final Function1<VariableGroup, EList<VariableDeclaration>> _function_1 = (VariableGroup it) -> {
      return it.getVariables();
    };
    return Iterables.<VariableDeclaration>concat(IterableExtensions.<VariableGroup, EList<VariableDeclaration>>map(IterableExtensions.<VariableGroup>filter(xSts.getVariableGroups(), _function), _function_1));
  }

  public Iterable<VariableDeclaration> retrieveNotTimeoutVariables(final XSTS xSts) {
    final Function1<VariableGroup, Boolean> _function = (VariableGroup it) -> {
      GroupAnnotation _annotation = it.getAnnotation();
      return Boolean.valueOf((!(_annotation instanceof TimeoutGroup)));
    };
    final Function1<VariableGroup, EList<VariableDeclaration>> _function_1 = (VariableGroup it) -> {
      return it.getVariables();
    };
    return Iterables.<VariableDeclaration>concat(IterableExtensions.<VariableGroup, EList<VariableDeclaration>>map(IterableExtensions.<VariableGroup>filter(xSts.getVariableGroups(), _function), _function_1));
  }

  public Iterable<VariableDeclaration> retrieveRegionVariables(final XSTS xSts) {
    final Function1<VariableGroup, Boolean> _function = (VariableGroup it) -> {
      GroupAnnotation _annotation = it.getAnnotation();
      return Boolean.valueOf((_annotation instanceof RegionGroup));
    };
    final Function1<VariableGroup, EList<VariableDeclaration>> _function_1 = (VariableGroup it) -> {
      return it.getVariables();
    };
    return Iterables.<VariableDeclaration>concat(IterableExtensions.<VariableGroup, EList<VariableDeclaration>>map(IterableExtensions.<VariableGroup>filter(xSts.getVariableGroups(), _function), _function_1));
  }

  public Iterable<VariableDeclaration> retrieveComponentParameters(final XSTS xSts) {
    final Function1<VariableGroup, Boolean> _function = (VariableGroup it) -> {
      GroupAnnotation _annotation = it.getAnnotation();
      return Boolean.valueOf((_annotation instanceof ComponentParameterGroup));
    };
    final Function1<VariableGroup, EList<VariableDeclaration>> _function_1 = (VariableGroup it) -> {
      return it.getVariables();
    };
    return Iterables.<VariableDeclaration>concat(IterableExtensions.<VariableGroup, EList<VariableDeclaration>>map(IterableExtensions.<VariableGroup>filter(xSts.getVariableGroups(), _function), _function_1));
  }

  public Iterable<VariableDeclaration> retrievePlainVariables(final XSTS xSts) {
    final Function1<VariableGroup, Boolean> _function = (VariableGroup it) -> {
      GroupAnnotation _annotation = it.getAnnotation();
      return Boolean.valueOf((_annotation instanceof PlainVariableGroup));
    };
    final Function1<VariableGroup, EList<VariableDeclaration>> _function_1 = (VariableGroup it) -> {
      return it.getVariables();
    };
    return Iterables.<VariableDeclaration>concat(IterableExtensions.<VariableGroup, EList<VariableDeclaration>>map(IterableExtensions.<VariableGroup>filter(xSts.getVariableGroups(), _function), _function_1));
  }

  public Iterable<VariableDeclaration> retrieveEnumVariables(final XSTS xSts) {
    final Function1<VariableDeclaration, Boolean> _function = (VariableDeclaration it) -> {
      return Boolean.valueOf(((it.getType() instanceof EnumerableTypeDefinition) || 
        ((it.getType() instanceof TypeReference) && 
          (((TypeReference) it.getType()).getReference().getType() instanceof EnumerableTypeDefinition))));
    };
    return IterableExtensions.<VariableDeclaration>filter(this.retrieveNotTimeoutVariables(xSts), _function);
  }
}
