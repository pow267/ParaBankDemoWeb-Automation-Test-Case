export class RequestLoanAPI {
    constructor(request) {
        this.request = request;
    }
    async requestLoan(customerID, amount, downPayment, accountID) {
        const res = await this.request.post(`requestLoan?customerId=${customerID}&amount=${amount}&downPayment=${downPayment}&fromAccountId=${accountID}`, {
            headers: {
                'Accept': 'application/json',
            },
        });
        return res;
    }
}