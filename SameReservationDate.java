public class SameReservationDate extends RuntimeException {

  public SameReservationDate(String resDate) {
      /*
        Call super passing message: "The reservation start and end dates have the same value " + resDate
        Assign parameter resDate to attribute resDate
      */
      super("The reservation start date and end date have the same value " + resDate);
  }

  public String toString(String resDate) {
    /*
    Generate and return a message this.getClass().getSimpleName() + ": The reservation start date and end date have the same value"
    */
    return this.getClass().getSimpleName() + ": The reservation start date and end date have the same value " + resDate;
  }  

}