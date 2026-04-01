package com.parasoft.parabank.service;

/**
 * <DL><DT>Description:</DT><DD>
 * Interface for beans that need to be aware of the loan provider name.
 * </DD>
 * <DT>Date:</DT><DD>Jun 6, 2016</DD>
 * </DL>
 *
 * @author dev - Nick Rapoport
 *
 */
public interface LoanProviderNameAware {

    /**
     * <DL><DT>Description:</DT><DD>
     * Sets the loan provider name.
     * </DD>
     * <DT>Date:</DT><DD>Jun 6, 2016</DD>
     * </DL>
     * @param loanProviderName
     */
    void setLoanProviderName(String loanProviderName);

    /**
     * <DL><DT>Description:</DT><DD>
     * Getter for the loanProviderName property
     * </DD>
     * <DT>Date:</DT><DD>Jun 6, 2016</DD>
     * </DL>
     * @return the value of loanProviderName field
     */
    String getLoanProviderName();

}
