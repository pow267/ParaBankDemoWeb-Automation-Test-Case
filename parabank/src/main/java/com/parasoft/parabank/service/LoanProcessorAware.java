package com.parasoft.parabank.service;

import com.parasoft.parabank.domain.logic.LoanProvider;

/**
 * <DL><DT>Description:</DT><DD>
 * Interface for beans that need to be aware of the loan processor.
 * </DD>
 * <DT>Date:</DT><DD>Jun 6, 2016</DD>
 * </DL>
 *
 * @author dev - Nick Rapoport
 *
 */
public interface LoanProcessorAware {

    /**
     * <DL><DT>Description:</DT><DD>
     * Sets the loan processor.
     * </DD>
     * <DT>Date:</DT><DD>Jun 6, 2016</DD>
     * </DL>
     * @param loanProcessor
     */
    void setLoanProcessor(LoanProvider loanProcessor);

    /**
     * <DL><DT>Description:</DT><DD>
     * Getter for the loanProcessor property
     * </DD>
     * <DT>Date:</DT><DD>Jun 6, 2016</DD>
     * </DL>
     * @return the value of loanProcessor field
     */
    LoanProvider getLoanProcessor();

}
