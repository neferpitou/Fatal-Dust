package etc;

/**
 * An interface which holds String variables to correspond to the different
 * views in the kernel's hashmap. These variables should be used by all views
 * to access the instance of a desired view in the hashmap.
 * 
 * @author Marcos Davila
 * @date 2/27/2014
 *
 */
public interface ViewLabels {

	public String MAIN = "MAIN";
	public String OPTIONS = "OPTIONS";
	public String SELECT = "SELECT";
	public String VERSUS = "VERSUS";
	public String LOADING = "LOADING";
}
