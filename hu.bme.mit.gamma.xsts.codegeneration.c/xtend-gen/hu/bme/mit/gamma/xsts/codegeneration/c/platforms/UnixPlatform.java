package hu.bme.mit.gamma.xsts.codegeneration.c.platforms;

import org.eclipse.xtend2.lib.StringConcatenation;

/**
 * Implementation of the IPlatform interface for Unix platforms.
 */
@SuppressWarnings("all")
public class UnixPlatform implements IPlatform {
  /**
   * Returns the headers specific to unix platforms.
   * 
   * @return the headers as a string
   */
  @Override
  public String getHeaders() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include <sys/time.h>");
    return _builder.toString();
  }

  /**
   * Returns the part of struct specific to unix platforms.
   * 
   * @return the struct as a string
   */
  @Override
  public String getStruct() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("struct timeval tval_before, tval_after, tval_result;");
    return _builder.toString();
  }

  /**
   * Returns the timer initialization specific to unix platforms.
   * 
   * @return the initialization as a string
   */
  @Override
  public String getInitialization() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("gettimeofday(&statechart->tval_before, NULL);  // start measuring time during initialization");
    return _builder.toString();
  }

  /**
   * Returns the timer specific to unix platforms.
   * 
   * @return the timer as a string
   */
  @Override
  public String getTimer() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("gettimeofday(&statechart->tval_after, NULL);");
    _builder.newLine();
    _builder.append("timersub(&statechart->tval_after, &statechart->tval_before, &statechart->tval_result);");
    _builder.newLine();
    _builder.append("unsigned int milliseconds = (unsigned int)statechart->tval_result.tv_sec * 1000 + (unsigned int)statechart->tval_result.tv_usec / 1000;");
    _builder.newLine();
    _builder.append("gettimeofday(&statechart->tval_before, NULL);");
    _builder.newLine();
    return _builder.toString();
  }
}
