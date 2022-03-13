public class MismatchedAddresses extends RuntimeException {

  public MismatchedAddresses(String addresses) {
      /*
        Call super passing message: "The reservation mailing address and lodging address must be the same " + addresses
        Assign parameter addresses to attribute addresses
      */
      super("The reservation mailing address and lodging address must be the same " + addresses);
  }

  public String toString(String addresses) {
    /*
    Generate and return a message this.getClass().getSimpleName() + ": The reservation mailing address must be the same as the lodging address or null"
    */
    return this.getClass().getSimpleName() + ": The reservation mailing address and lodging address must be the same " + addresses;

  }  

}