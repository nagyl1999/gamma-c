package hu.bme.mit.gamma.xsts.codegeneration.c.platforms

/**
 * Interface representing a platform.
 */
interface IPlatform {
	public static String CLOCK_VARIABLE_NAME = '__milliseconds__';
	
	/**
     * Returns the headers specific to the platform.
     * 
     * @return the headers as a string
     */
	def String getHeaders();
	
	/**
     * Returns the part of struct specific to the platform.
     * 
     * @return the struct as a string
     */
	def String getStruct();
	
	/**
     * Returns the timer initialization specific to the platform.
     * 
     * @return the initialization as a string
     */
	def String getInitialization();
	
	/**
     * Returns the timer specific to the platform. All platforms
     * should use 'unsigned int __milliseconds__' (value of
     * IPlatform.CLOCK_VARIABLE_NAME) indicating the elapsed time.
     * 
     * @return the timer as a string
     */
	def String getTimer();
	
}