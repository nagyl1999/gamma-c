package hu.bme.mit.gamma.xsts.codegeneration.c.platforms;

/**
 * Interface representing a platform.
 */
@SuppressWarnings("all")
public interface IPlatform {
  /**
   * Returns the headers specific to the platform.
   * 
   * @return the headers as a string
   */
  String getHeaders();

  /**
   * Returns the part of struct specific to the platform.
   * 
   * @return the struct as a string
   */
  String getStruct();

  /**
   * Returns the timer specific to the platform.
   * 
   * @return the timer as a string
   */
  String getTimer();
}
