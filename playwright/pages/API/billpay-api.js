export class BillpayAPI {
    constructor(request) {
        this.request = request;
    }
    async pay(accountID, amount, payee) {
        const res = await this.request.post(`billpay?accountId=${accountID}&amount=${amount}`, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: payee
        });
        return res;
    }
}