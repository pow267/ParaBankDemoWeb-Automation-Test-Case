export class TransferAPI {
    constructor(request) {
        this.request = request;
    }
    async transfer(fromAccountId, toAccountId, amount) {
        const res = await this.request.post(`transfer?fromAccountId=${fromAccountId}&toAccountId=${toAccountId}&amount=${amount}`, {
            headers: { 'Accept': 'application/json' }
        });
        return res;
    }
}