package com.parasoft.parabank.messaging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Objects;
import jakarta.annotation.Resource;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.oxm.Marshaller;

import com.parasoft.parabank.domain.LoanRequest;
import com.parasoft.parabank.domain.LoanResponse;
import com.parasoft.parabank.test.util.AbstractParaBankDataSourceTest;

/**
 * @req PAR-38
 *
 */
public class JmsLoanProviderTest extends AbstractParaBankDataSourceTest {
    @Resource(name = "jmsLoanProvider")
    private JmsLoanProvider jmsLoanProvider;

    @Resource(name = "jaxb2Marshaller")
    private Marshaller marshaller;

    private JmsTemplate jmsTemplate;

    @Override
    public void onSetUp() throws Exception {
        super.onSetUp();

        //        final ConnectionFactory connectionFactory =
        //            new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        //        jmsTemplate = new JmsTemplate();
        //        jmsTemplate.setConnectionFactory(connectionFactory);
        //jmsLoanProvider.setJmsTemplate(jmsTemplate);
        jmsTemplate = Objects.requireNonNull(jmsLoanProvider).getJmsTemplate();
    }

    public void setJmsLoanProvider(final JmsLoanProvider jmsLoanProvider) {
        this.jmsLoanProvider = jmsLoanProvider;
    }

    public void setMarshaller(final Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    @Test
    public void testRequestLoan() {
        Objects.requireNonNull(jmsTemplate).send("queue.test.response", (MessageCreator) session -> {
            final LoanResponse loanResponse = new LoanResponse();
            loanResponse.setApproved(true);
            final TextMessage message = Objects.requireNonNull(session).createTextMessage();
            message.setText(MarshalUtil.marshal(Objects.requireNonNull(marshaller), loanResponse));
            return message;
        });
        final LoanResponse loanResponse = Objects.requireNonNull(jmsLoanProvider).requestLoan(new LoanRequest());
        assertTrue(Objects.requireNonNull(loanResponse).isApproved());
    }

    @Test
    public void testRequestLoanException() {
        final TextMessage message = new ActiveMQTextMessage() {
            @Override
            public String getText() throws JMSException {
                throw new JMSException(null);
            }
        };
        final LoanResponse loanResponse = Objects.requireNonNull(jmsLoanProvider).processResponse(message);
        assertNull(loanResponse);
    }

    @Test
    public void testRequestLoanTimeout() {
        Objects.requireNonNull(jmsTemplate).setReceiveTimeout(1);
        final LoanResponse loanResponse = Objects.requireNonNull(jmsLoanProvider).requestLoan(new LoanRequest());
        assertFalse(Objects.requireNonNull(loanResponse).isApproved());
        assertNotNull(loanResponse.getResponseDate());
        assertEquals("error.timeout", loanResponse.getMessage());
    }
}
