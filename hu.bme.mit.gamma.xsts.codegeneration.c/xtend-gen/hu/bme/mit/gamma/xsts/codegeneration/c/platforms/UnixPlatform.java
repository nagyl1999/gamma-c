package hu.bme.mit.gamma.xsts.codegeneration.c.platforms;

import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class UnixPlatform implements IPlatform {
  @Override
  public String getHeaders() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include <sys/time.h>");
    return _builder.toString();
  }

  @Override
  public String getStruct() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("struct timeval tval_before, tval_after, tval_result;");
    return _builder.toString();
  }

  @Override
  public String getInitialization() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("gettimeofday(&statechart->tval_before, NULL);  // start measuring time during initialization");
    return _builder.toString();
  }

  @Override
  public String getTimer() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("gettimeofday(&statechart->tval_after, NULL);");
    _builder.newLine();
    _builder.append("timersub(&statechart->tval_after, &statechart->tval_before, &statechart->tval_result);");
    _builder.newLine();
    _builder.append("int milliseconds = (int)statechart->tval_result.tv_sec * 1000 + (int)statechart->tval_result.tv_usec / 1000;");
    _builder.newLine();
    _builder.append("gettimeofday(&statechart->tval_before, NULL);");
    _builder.newLine();
    return _builder.toString();
  }
}
