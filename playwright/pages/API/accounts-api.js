export class AccountsAPI {
    constructor(request) {
        this.request = request;
    }

    async getAccountDetails(accountID) {
        const res = await this.request.get(`accounts/${accountID}`, {
            headers: { 'accept': 'application/json' }
        });
        return res;
    }
}