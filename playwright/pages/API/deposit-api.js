export class DepositAPI {
    constructor(request) {
        this.request = request;
    }
    async deposit(accountID, amount) {
        const res = await this.request.post(`deposit?accountId=${accountID}&amount=${amount}`, {
            headers: { 'Accept': 'application/json' }
        });
        return res;
    }
}